package net.cr24.primeval.block.plant;

import net.cr24.primeval.block.PrimevalBlockTags;
import net.cr24.primeval.block.PrimevalFarmlandBlock;
import net.cr24.primeval.block.PrimevalFarmlandBlockFertilizerType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PlantBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

import java.util.Arrays;

public class PrimevalCropBlock extends PlantBlock {

    public static final IntProperty AGE;
    private final int maxAge;
    private final VoxelShape[] heights;

    public PrimevalCropBlock(Settings settings, int maxAge, double[] heightArray) {
        super(settings);
        this.maxAge = maxAge;
        heights = new VoxelShape[heightArray.length];
        for (int i = 0; i < heightArray.length; i++) {
            heights[i] = getShapeWithHeight(heightArray[i]);
        }
        if (heights.length != maxAge+1) {
            System.err.println("Crop Block age and height array does not match: "+ maxAge + " " + Arrays.toString(heights));
        }
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        super.randomTick(state, world, pos, random);

        int currentAge = state.get(AGE);
        if (currentAge == maxAge) return;

        BlockState soil = world.getBlockState(pos.down());
        if (!(soil.getBlock() instanceof PrimevalFarmlandBlock)) return;
        int soilMoisture = soil.get(PrimevalFarmlandBlock.MOISTURE);
        int soilFertilized = soil.get(PrimevalFarmlandBlock.FERTILIZED);

        double growthChance = 0.6;
        growthChance += 0.05d * soilFertilized;
        if (soilMoisture == 0) growthChance = growthChance / 2;

        if (random.nextDouble() <= growthChance) {
            world.setBlockState(pos, state.with(AGE, currentAge+1));
            if (soilFertilized > 0) {
                if (soilFertilized == 1) {
                    world.setBlockState(pos.down(), soil.with(PrimevalFarmlandBlock.FERTILIZED, 0).with(PrimevalFarmlandBlock.TYPE, PrimevalFarmlandBlockFertilizerType.NONE));
                } else {
                    world.setBlockState(pos.down(), soil.with(PrimevalFarmlandBlock.FERTILIZED, soilFertilized-1));
                }
            }
        }

    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(PrimevalBlockTags.FARMLAND);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return heights[state.get(AGE)];
    }

    protected static VoxelShape getShapeWithHeight(double height) {
        return Block.createCuboidShape(1.0, 0.0, 1.0, 15.0, height, 15.0);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    static {
        AGE = IntProperty.of("age", 0, 7);
    }

}
