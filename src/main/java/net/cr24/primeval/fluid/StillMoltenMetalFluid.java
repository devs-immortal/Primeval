package net.cr24.primeval.fluid;

import net.cr24.primeval.block.PrimevalBlocks;
import net.cr24.primeval.item.PrimevalItems;
import net.minecraft.block.*;
import net.minecraft.fluid.*;
import net.minecraft.item.Item;
import net.minecraft.world.*;


abstract class StillMoltenMetalFluid extends LavaFluid implements FallbackFluid {

    public Fluid getFlowing() {
        return Fluids.FLOWING_LAVA; // No flowing metals because I'm lazy
    }

    public int getFlowSpeed(WorldView world) {
        return 0;
    }

    public boolean matchesType(Fluid fluid) {
        return fluid == this;
    }

    public int getLevelDecreasePerBlock(WorldView world) {
        return 16;
    }

    public int getTickRate(WorldView world) {
        return 30;
    }

    public int getLevel(FluidState state) {
        return 8;
    }

    public boolean isStill(FluidState state) {
        return true;
    }

    public static class Copper extends StillMoltenMetalFluid {

        public Fluid getStill() {
            return PrimevalFluids.MOLTEN_COPPER;
        }

        public BlockState toBlockState(FluidState state) {
            return PrimevalBlocks.MOLTEN_COPPER.getDefaultState().with(FluidBlock.LEVEL, getBlockStateLevel(state));
        }

        public Item getFallbackItem() {
            return PrimevalItems.COPPER_CHUNK;
        }
    }

    public static class Tin extends StillMoltenMetalFluid {

        public Fluid getStill() {
            return PrimevalFluids.MOLTEN_TIN;
        }

        public BlockState toBlockState(FluidState state) {
            return PrimevalBlocks.MOLTEN_TIN.getDefaultState().with(FluidBlock.LEVEL, getBlockStateLevel(state));
        }

        public Item getFallbackItem() {
            return PrimevalItems.TIN_CHUNK;
        }
    }

    public static class Zinc extends StillMoltenMetalFluid {

        public Fluid getStill() {
            return PrimevalFluids.MOLTEN_ZINC;
        }

        public BlockState toBlockState(FluidState state) {
            return PrimevalBlocks.MOLTEN_ZINC.getDefaultState().with(FluidBlock.LEVEL, getBlockStateLevel(state));
        }

        public Item getFallbackItem() {
            return PrimevalItems.ZINC_CHUNK;
        }
    }

    public static class Bronze extends StillMoltenMetalFluid {

        public Fluid getStill() {
            return PrimevalFluids.MOLTEN_BRONZE;
        }

        public BlockState toBlockState(FluidState state) {
            return PrimevalBlocks.MOLTEN_BRONZE.getDefaultState().with(FluidBlock.LEVEL, getBlockStateLevel(state));
        }

        public Item getFallbackItem() {
            return PrimevalItems.BRONZE_CHUNK;
        }
    }

    public static class Brass extends StillMoltenMetalFluid {

        public Fluid getStill() {
            return PrimevalFluids.MOLTEN_BRASS;
        }

        public BlockState toBlockState(FluidState state) {
            return PrimevalBlocks.MOLTEN_BRASS.getDefaultState().with(FluidBlock.LEVEL, getBlockStateLevel(state));
        }

        public Item getFallbackItem() {
            return PrimevalItems.BRASS_CHUNK;
        }
    }

    public static class Botched extends StillMoltenMetalFluid {

        public Fluid getStill() {
            return PrimevalFluids.MOLTEN_BOTCHED_ALLOY;
        }

        public BlockState toBlockState(FluidState state) {
            return PrimevalBlocks.MOLTEN_BOTCHED_ALLOY.getDefaultState().with(FluidBlock.LEVEL, getBlockStateLevel(state));
        }

        public Item getFallbackItem() {
            return PrimevalItems.BOTCHED_ALLOY_CHUNK;
        }
    }

}
