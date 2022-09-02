package net.cr24.primeval.data;

import net.cr24.primeval.data.model.PrimevalBlockStateModelGenerator;
import net.cr24.primeval.data.model.PrimevalItemModelGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;

public class PrimevalModelProvider extends FabricModelProvider {
    public PrimevalModelProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator modelGenerator) {
        PrimevalBlockStateModelGenerator.register(modelGenerator);
    }

    @Override
    public void generateItemModels(ItemModelGenerator modelGenerator) {
        PrimevalItemModelGenerator.register(modelGenerator);
    }

}