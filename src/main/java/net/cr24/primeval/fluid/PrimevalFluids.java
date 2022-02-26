package net.cr24.primeval.fluid;

import net.cr24.primeval.PrimevalMain;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.util.registry.Registry;

public class PrimevalFluids {

    //public static final FlowableFluid FLOWING_MOLTEN_COPPER = registerFluid("flowing_molten_copper", new LavaFluid.Flowing());
    public static final Fluid MOLTEN_COPPER = registerFluid("molten_copper", new LavaFluid.Still());
    public static final Fluid MOLTEN_BOTCHED_ALLOY = registerFluid("molten_botched_alloy", new LavaFluid.Still());


    public static void init() {}

    private static Fluid registerFluid(String id, Fluid fluid) {
        return Registry.register(Registry.FLUID, PrimevalMain.getId(id), fluid);
    }
}
