package net.cr24.primeval.block.plant;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.cr24.primeval.block.PrimevalFarmlandBlock;
import net.cr24.primeval.initialization.PrimevalTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.VegetationBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import java.util.ArrayList;
import java.util.List;

public class PrimevalCropBlock extends VegetationBlock {

    public static final MapCodec<PrimevalCropBlock> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
            ExtraCodecs.POSITIVE_INT.fieldOf("max_age").forGetter((block) -> block.maxAge),
            ExtraCodecs.POSITIVE_INT.listOf().fieldOf("heights").forGetter((block) -> block.heights.stream().map((vs) -> ((int) vs.max(Direction.Axis.Y))).toList()),
            propertiesCodec()
    ).apply(instance, PrimevalCropBlock::new));

    public static final IntegerProperty AGE;
    private final int maxAge;
    private final List<VoxelShape> heights;

    public PrimevalCropBlock(int maxAge, List<Integer> heightList, Properties settings) {
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
    protected MapCodec<? extends VegetationBlock> codec() {
        return CODEC;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        super.randomTick(state, world, pos, random);

        int currentAge = state.getValue(AGE);
        if (currentAge == maxAge) return;

        BlockState soil = world.getBlockState(pos.below());
        if (!(soil.getBlock() instanceof PrimevalFarmlandBlock)) return;
        int soilMoisture = soil.getValue(PrimevalFarmlandBlock.MOISTURE);
        int soilFertilized = soil.getValue(PrimevalFarmlandBlock.FERTILIZED);

        double growthChance = 0.6;
        growthChance += 0.13d * soilFertilized;
        if (soilMoisture == 0) growthChance = growthChance / 2;

        if (random.nextDouble() <= growthChance) {
            world.setBlockAndUpdate(pos, state.setValue(AGE, currentAge+1));
            if (soilFertilized > 0) {
                if (soilFertilized == 1) {
                    world.setBlockAndUpdate(pos.below(), soil.setValue(PrimevalFarmlandBlock.FERTILIZED, 0).setValue(PrimevalFarmlandBlock.TYPE, PrimevalFarmlandBlock.PrimevalFarmlandBlockFertilizerType.NONE));
                } else {
                    world.setBlockAndUpdate(pos.below(), soil.setValue(PrimevalFarmlandBlock.FERTILIZED, soilFertilized-1));
                }
            }
        }

    }

    @Override
    protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
        return floor.is(PrimevalTags.Blocks.FARMLAND);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return heights.get(Math.min(state.getValue(AGE), maxAge));
    }

    protected static VoxelShape getShapeWithHeight(double height) {
        return Block.box(1.0, 0.0, 1.0, 15.0, height, 15.0);
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    static {
        AGE = IntegerProperty.create("age", 0, 7);
    }

}
