package net.cr24.primeval.block;

import net.minecraft.util.StringIdentifiable;

public enum PrimevalFarmlandBlockFertilizerType implements StringIdentifiable {
    NONE("none"),
    BONEMEAL("bonemeal");

    private final String name;

    private PrimevalFarmlandBlockFertilizerType(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
