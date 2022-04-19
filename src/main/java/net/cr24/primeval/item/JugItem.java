package net.cr24.primeval.item;

import net.minecraft.item.Item;

public class JugItem extends Item implements IWeightedItem {

    private final Weight weight;
    private final Size size;

    public JugItem(Settings settings, Weight weight, Size size) {
        super(settings.maxCount(1));
        this.weight = weight;
        this.size = size;
    }

    @Override
    public Weight getWeight() {
        return this.weight;
    }

    @Override
    public Size getSize() {
        return this.size;
    }
}
