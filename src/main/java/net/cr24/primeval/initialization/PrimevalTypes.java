package net.cr24.primeval.initialization;

import net.cr24.primeval.PrimevalMain;
import net.fabricmc.fabric.api.object.builder.v1.block.type.BlockSetTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.block.type.WoodTypeBuilder;
import net.minecraft.block.BlockSetType;
import net.minecraft.block.WoodType;

public class PrimevalTypes {

    public static class BlockSet {
        public static BlockSetType OAK = BlockSetTypeBuilder.copyOf(BlockSetType.OAK).build(PrimevalMain.identify("oak"));
        public static BlockSetType BIRCH = BlockSetTypeBuilder.copyOf(BlockSetType.OAK).build(PrimevalMain.identify("birch"));
        public static BlockSetType SPRUCE = BlockSetTypeBuilder.copyOf(BlockSetType.OAK).build(PrimevalMain.identify("spruce"));
        public static BlockSetType WICKER = BlockSetTypeBuilder.copyOf(BlockSetType.OAK).build(PrimevalMain.identify("wicker"));
    }

    public static class Wood {
        public static WoodType OAK = WoodTypeBuilder.copyOf(WoodType.OAK).register(PrimevalMain.identify("oak"), BlockSet.OAK);
        public static WoodType BIRCH = WoodTypeBuilder.copyOf(WoodType.OAK).register(PrimevalMain.identify("birch"), BlockSet.BIRCH);
        public static WoodType SPRUCE = WoodTypeBuilder.copyOf(WoodType.OAK).register(PrimevalMain.identify("spruce"), BlockSet.SPRUCE);
        public static WoodType WICKER = WoodTypeBuilder.copyOf(WoodType.OAK).register(PrimevalMain.identify("wicker"), BlockSet.WICKER);
    }


    public static void init() {
    }

}
