package net.cr24.primeval.fluid;

import net.cr24.primeval.PrimevalMain;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.LavaFluid;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

public class PrimevalFluids {

    public static final Fluid MOLTEN_COPPER = registerFluid("molten_copper", new LavaFluid.Still());
    public static final Fluid MOLTEN_TIN = registerFluid("molten_tin", new LavaFluid.Still());

    public static final Fluid MOLTEN_BRONZE = registerFluid("molten_bronze", new LavaFluid.Still());
    public static final Fluid MOLTEN_BOTCHED_ALLOY = registerFluid("molten_botched_alloy", new LavaFluid.Still());


    public static final AlloyRatio BRONZE_RATIO = new AlloyRatio(
            new HashMap<>() {{
                put(FluidVariant.of(MOLTEN_COPPER), new AlloyRatio.RangedInt(9, 8));
                put(FluidVariant.of(MOLTEN_TIN), new AlloyRatio.RangedInt(2, 1));
            }},
            FluidVariant.of(MOLTEN_BRONZE)
    );

    public static final AlloyRatio[] alloys = new AlloyRatio[] {BRONZE_RATIO};

    public static void init() {}

    private static Fluid registerFluid(String id, Fluid fluid) {
        return Registry.register(Registry.FLUID, PrimevalMain.getId(id), fluid);
    }

    public static Pair<FluidVariant, Integer> combineFluids(HashMap<FluidVariant, Integer> fluids) {
        if (fluids.keySet().size() == 1) {
            for ( FluidVariant fluid : fluids.keySet()) {
                return new Pair<>(fluid, fluids.get(fluid));
            }
        }
        int overallFluid = 0;
        for ( FluidVariant fluid : fluids.keySet()) {

            System.out.print(fluid.toNbt().getString("fluid") + " | " + fluids.get(fluid));
            overallFluid += fluids.get(fluid);
        }
        for (AlloyRatio a : alloys) {
            if (a.satisfies(fluids, overallFluid)) {
                return new Pair<>(a.getResult(), overallFluid);
            }
        }

        return new Pair<>(FluidVariant.of(MOLTEN_BOTCHED_ALLOY), overallFluid);
    }

}
