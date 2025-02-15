package net.cr24.primeval.initialization;

import net.cr24.primeval.PrimevalMain;
import net.cr24.primeval.world.trunker.BirchTrunker;
import net.cr24.primeval.world.trunker.OakTrunker;
import net.cr24.primeval.world.trunker.SpruceTrunker;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;

import static net.cr24.primeval.initialization.PrimevalBlocks.*;

public class PrimevalItemGroups {

    public static final RegistryKey<ItemGroup> BLOCKS = create("blocks", FabricItemGroup.builder()
            .icon(() -> new ItemStack(DIRT))
            .entries((context, entries) -> {
                // Terrain Blocks
                entries.add(DIRT);
                entries.add(COARSE_DIRT);
                entries.add(CLAY);
                entries.add(MUD);
                entries.add(DRY_DIRT);
                entries.add(GRASSY_DIRT);
                entries.add(GRASSY_CLAY);
                entries.add(SAND);
                entries.add(GRAVEL);
                entries.add(COBBLESTONE);
                entries.add(STONE);
                entries.add(SANDSTONE);
                // Plants
                entries.add(OAK_SAPLING);
                entries.add(BIRCH_SAPLING);
                entries.add(SPRUCE_SAPLING);
                entries.add(GRASS);
                entries.add(BUSH);
                entries.add(SPIKED_PLANT);
                entries.add(LEAFY_PLANT);
                entries.add(SHRUB);
                entries.add(MOSS);
                // Flowers
                entries.add(POPPY);
                entries.add(DANDELION);
                entries.add(OXEYE_DAISY);
                entries.add(CORNFLOWER);
                entries.add(LILY_OF_THE_VALLEY);

                entries.add(REEDS);
                entries.add(RIVER_GRASS);

            }));

    public static void init() {
    }

    // item group registry helper
    private static RegistryKey<ItemGroup> create(String id, ItemGroup.Builder itemGroup) {
        var key = RegistryKey.of(RegistryKeys.ITEM_GROUP, PrimevalMain.identify(id));
        Registry.register(Registries.ITEM_GROUP, key, itemGroup.displayName(Text.translatable("itemGroup.primeval." + id)).build());
        return key;
    }

}
