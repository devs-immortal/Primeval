package net.cr24.primeval.block.plant;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.cr24.primeval.block.PrimevalFarmlandBlock;
import net.cr24.primeval.initialization.PrimevalTags;
import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

import java.util.ArrayList;
import java.util.List;

public class PrimevalCropBlock extends PlantBlock {

    public static final MapCodec<PrimevalCropBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            Codecs.POSITIVE_INT.fieldOf("max_age").forGetter((block) -> block.maxAge),
            Codecs.POSITIVE_INT.listOf().fieldOf("heights").forGetter((block) -> block.heights.stream().map((vs) -> ((int) vs.getMax(Direction.Axis.Y))).toList()),
            createSettingsCodec()
    ).apply(instance, PrimevalCropBlock::new));

    public static final IntProperty AGE;
    private final int maxAge;
    private final List<VoxelShape> heights;

    public PrimevalCropBlock(int maxAge, List<Integer> heightList, Settings settings) {
        super(settings);
        this.maxAge = maxAge;
        heights = new ArrayList<>(heightList.size());
        for (Integer integer : heightList) {
            heights.add(getShapeWithHeight(integer));
        }
        if (heights.size() != maxAge+1) {
            System.err.println("Crop Block age and height array does not match: "+ maxAge + " " + heights);
        }
    }

    @Override
    protected MapCodec<? extends PlantBlock> getCodec() {
        return CODEC;
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
                    world.setBlockState(pos.down(), soil.with(PrimevalFarmlandBlock.FERTILIZED, 0).with(PrimevalFarmlandBlock.TYPE, PrimevalFarmlandBlock.PrimevalFarmlandBlockFertilizerType.NONE));
                } else {
                    world.setBlockState(pos.down(), soil.with(PrimevalFarmlandBlock.FERTILIZED, soilFertilized-1));
                }
            }
        }

    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(PrimevalTags.Blocks.FARMLAND);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return heights.get(state.get(AGE));
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
