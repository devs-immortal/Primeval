package net.cr24.primeval.data.model;

import net.minecraft.block.Block;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TextureKey;
import net.minecraft.data.client.TextureMap;
import net.minecraft.data.client.TexturedModel;

public class PrimevalTexturedModels {

    public static final TexturedModel.Factory ASH_PILE = TexturedModel.makeFactory(TextureMap::sideAndTop, PrimevalModels.ASH_PILE);
    public static final TexturedModel.Factory CUBE = TexturedModel.makeFactory(TextureMap::all, Models.CUBE);

    public static TextureMap sideTopBottom(Block block) {
        return new TextureMap().put(TextureKey.SIDE, TextureMap.getSubId(block, "_side")).put(TextureKey.TOP, TextureMap.getSubId(block, "_top")).put(TextureKey.BOTTOM, TextureMap.getSubId(block, "_bottom"));
    }
}
