package net.cr24.primeval.fluid;

import net.cr24.primeval.initialization.PrimevalBlocks;
import net.cr24.primeval.initialization.PrimevalItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.LavaFluid;


abstract class StillMoltenMetalFluid extends LavaFluid implements FallbackFluid {

    public Fluid getFlowing() {
        return Fluids.FLOWING_LAVA; // No flowing metals because I'm lazy
    }

    public int getFlowSpeed(LevelReader world) {
        return 0;
    }

    public boolean isSame(Fluid fluid) {
        return fluid == this;
    }

    public int getDropOff(LevelReader world) {
        return 16;
    }

    public int getTickDelay(LevelReader world) {
        return 30;
    }

    public int getAmount(FluidState state) {
        return 8;
    }

    public boolean isSource(FluidState state) {
        return true;
    }

    public static class Copper extends StillMoltenMetalFluid {

        public Fluid getSource() {
            return PrimevalFluids.MOLTEN_COPPER;
        }

        public BlockState createLegacyBlock(FluidState state) {
            return PrimevalBlocks.MOLTEN_COPPER.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state));
        }

        public Item getFallbackItem() {
            return PrimevalItems.COPPER_CHUNK;
        }
    }

    public static class Tin extends StillMoltenMetalFluid {

        public Fluid getSource() {
            return PrimevalFluids.MOLTEN_TIN;
        }

        public BlockState createLegacyBlock(FluidState state) {
            return PrimevalBlocks.MOLTEN_TIN.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state));
        }

        public Item getFallbackItem() {
            return PrimevalItems.TIN_CHUNK;
        }
    }

    public static class Zinc extends StillMoltenMetalFluid {

        public Fluid getSource() {
            return PrimevalFluids.MOLTEN_ZINC;
        }

        public BlockState createLegacyBlock(FluidState state) {
            return PrimevalBlocks.MOLTEN_ZINC.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state));
        }

        public Item getFallbackItem() {
            return PrimevalItems.ZINC_CHUNK;
        }
    }

    public static class Bronze extends StillMoltenMetalFluid {

        public Fluid getSource() {
            return PrimevalFluids.MOLTEN_BRONZE;
        }

        public BlockState createLegacyBlock(FluidState state) {
            return PrimevalBlocks.MOLTEN_BRONZE.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state));
        }

        public Item getFallbackItem() {
            return PrimevalItems.BRONZE_CHUNK;
        }
    }

    public static class Brass extends StillMoltenMetalFluid {

        public Fluid getSource() {
            return PrimevalFluids.MOLTEN_BRASS;
        }

        public BlockState createLegacyBlock(FluidState state) {
            return PrimevalBlocks.MOLTEN_BRASS.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state));
        }

        public Item getFallbackItem() {
            return PrimevalItems.BRASS_CHUNK;
        }
    }

    public static class Pewter extends StillMoltenMetalFluid {

        public Fluid getSource() {
            return PrimevalFluids.MOLTEN_PEWTER;
        }

        public BlockState createLegacyBlock(FluidState state) {
            return PrimevalBlocks.MOLTEN_PEWTER.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state));
        }

        public Item getFallbackItem() {
            return PrimevalItems.PEWTER_CHUNK;
        }
    }

    public static class Gold extends StillMoltenMetalFluid {

        public Fluid getSource() {
            return PrimevalFluids.MOLTEN_GOLD;
        }

        public BlockState createLegacyBlock(FluidState state) {
            return PrimevalBlocks.MOLTEN_GOLD.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state));
        }

        public Item getFallbackItem() {
            return PrimevalItems.GOLD_CHUNK;
        }
    }

    public static class Botched extends StillMoltenMetalFluid {

        public Fluid getSource() {
            return PrimevalFluids.MOLTEN_BOTCHED_ALLOY;
        }

        public BlockState createLegacyBlock(FluidState state) {
            return PrimevalBlocks.MOLTEN_BOTCHED_ALLOY.defaultBlockState().setValue(LiquidBlock.LEVEL, getLegacyLevel(state));
        }

        public Item getFallbackItem() {
            return PrimevalItems.BOTCHED_ALLOY_CHUNK;
        }
    }

}
