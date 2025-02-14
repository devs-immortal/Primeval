package net.cr24.primeval.fluid;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Pair;

public class PrimevalFluidUtil {

    public static float fluidToIntegerId(Fluid f) {
        if (f == PrimevalFluids.MOLTEN_COPPER) {
            return 0.01F;
        } else if (f == PrimevalFluids.MOLTEN_TIN) {
            return 0.02F;
        } else if (f == PrimevalFluids.MOLTEN_BRONZE) {
            return 0.03F;
        } else if (f == PrimevalFluids.MOLTEN_ZINC) {
            return 0.04F;
        } else if (f == PrimevalFluids.MOLTEN_BRASS) {
            return 0.05F;
        } else if (f == PrimevalFluids.MOLTEN_PEWTER) {
            return 0.06F;
        } else if (f == PrimevalFluids.MOLTEN_GOLD) {
            return 0.07F;
        } else if (f == PrimevalFluids.MOLTEN_BOTCHED_ALLOY) {
            return 1F;
        }
        return 0F;
    }

}
