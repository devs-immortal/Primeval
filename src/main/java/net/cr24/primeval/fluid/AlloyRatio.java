package net.cr24.primeval.fluid;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;

import java.util.HashMap;

public class AlloyRatio {

    private HashMap<FluidVariant, RangedInt> fluids;
    private FluidVariant result;

    public AlloyRatio (HashMap<FluidVariant, RangedInt> fluids, FluidVariant result) {
        this.fluids = fluids;
        this.result = result;
    }

    public FluidVariant getResult() {
        return result;
    }

    public boolean satisfies(HashMap<FluidVariant, Integer> fluidsIn, double overallFluid) {
        if (fluids.keySet().size() == fluidsIn.keySet().size()) {
            double min = 1.0;
            double amount;
            for (FluidVariant f : fluids.keySet()) {
                if (fluidsIn.containsKey(f)) {
                    amount = fluidsIn.get(f);
                    if (amount/overallFluid < min) min = amount/overallFluid;
                } else {
                    return false;
                }
            }
            double ratio;
            for (FluidVariant f : fluids.keySet()) {
                ratio = (1 / min) * fluidsIn.get(f)/overallFluid;
                if (!fluids.get(f).valueIsWithin(ratio)) return false;
            }
            return true;
        }
        return false;
    }


    public static class RangedInt {
        private double upper;
        private double lower;

        public RangedInt(int u, int l) {
            this.upper = u;
            this.lower = l;
        }

        public boolean valueIsWithin(double value) {
            return (value <= upper && value >= lower);
        }
    }

}
