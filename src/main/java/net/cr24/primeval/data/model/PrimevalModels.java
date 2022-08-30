package net.cr24.primeval.data.model;

import net.minecraft.data.client.Model;
import net.minecraft.data.client.TextureKey;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class PrimevalModels {

    public static final Model ASH_PILE = blockModel("base_ash_pile", TextureKey.TOP, TextureKey.SIDE);

    private static Model blockModel(String parent, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(new Identifier("primeval", "block/" + parent)), Optional.empty(), requiredTextureKeys);
    }

    private static Model blockModel(String parent, String variant, TextureKey ... requiredTextureKeys) {
        return new Model(Optional.of(new Identifier("primeval", "block/" + parent)), Optional.of(variant), requiredTextureKeys);
    }

    private static Model itemModel(String parent, TextureKey ... requiredTextureKeys) {
        return new Model(Optional.of(new Identifier("primeval", "item/" + parent)), Optional.empty(), requiredTextureKeys);
    }

}
