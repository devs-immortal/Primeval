package net.cr24.primeval.world.gen.structure;

import net.cr24.primeval.Primeval;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

public class PrimevalStructures {

    /* BIOME TAGS */
    public static final TagKey<Biome> HAS_NATIVE_COPPER = tagkey("has_copper");

    private static TagKey<Biome> tagkey(String id) {
        return TagKey.create(Registries.BIOME, Primeval.identify(id));
    }

    /* STRUCTURES */

    public static final TagKey<Structure> NATIVE_COPPER_ORE_FIELD_KEY = tagKey("copper_native_ore_field");
    public static final StructureType<NativeCopperOreFieldStructure> NATIVE_COPPER_ORE_FIELD = () -> NativeCopperOreFieldStructure.CODEC;
    public static final TagKey<Structure> MALACHITE_COPPER_ORE_FIELD_KEY = tagKey("copper_malachite_ore_field");
    public static final StructureType<MalachiteCopperOreFieldStructure> MALACHITE_COPPER_ORE_FIELD = () -> MalachiteCopperOreFieldStructure.CODEC;
    public static final TagKey<Structure> CASSITERITE_TIN_ORE_FIELD_KEY = tagKey("tin_cassiterite_ore_field");
    public static final StructureType<CassiteriteTinOreFieldStructure> CASSITERITE_TIN_ORE_FIELD = () -> CassiteriteTinOreFieldStructure.CODEC;
    public static final TagKey<Structure> SPHALERITE_ZINC_ORE_FIELD_KEY = tagKey("zinc_sphalerite_ore_field");
    public static final StructureType<SphaleriteZincOreFieldStructure> SPHALERITE_ZINC_ORE_FIELD = () -> SphaleriteZincOreFieldStructure.CODEC;
    public static final TagKey<Structure> LAZURITE_ORE_FIELD_KEY = tagKey("lazurite_ore_field");
    public static final StructureType<LazuriteOreFieldStructure> LAZURITE_ORE_FIELD = () -> LazuriteOreFieldStructure.CODEC;
    public static final TagKey<Structure> NATIVE_GOLD_ORE_FIELD_KEY = tagKey("gold_native_ore_field");
    public static final StructureType<NativeGoldOreFieldStructure> NATIVE_GOLD_ORE_FIELD = () -> NativeGoldOreFieldStructure.CODEC;
    public static final StructurePieceType ORE_FIELD_PIECE = OreFieldGenerator.Blob::new;

    private static TagKey<Structure> tagKey(String name) {
        return TagKey.create(Registries.STRUCTURE, Primeval.identify(name));
    }

    public static void init() {
        register(NATIVE_COPPER_ORE_FIELD_KEY, NATIVE_COPPER_ORE_FIELD);
        register(MALACHITE_COPPER_ORE_FIELD_KEY, MALACHITE_COPPER_ORE_FIELD);
        register(CASSITERITE_TIN_ORE_FIELD_KEY, CASSITERITE_TIN_ORE_FIELD);
        register(SPHALERITE_ZINC_ORE_FIELD_KEY, SPHALERITE_ZINC_ORE_FIELD);
        register(LAZURITE_ORE_FIELD_KEY, LAZURITE_ORE_FIELD);
        register(NATIVE_GOLD_ORE_FIELD_KEY, NATIVE_GOLD_ORE_FIELD);
        register(Primeval.identify("ore_field_blob"), ORE_FIELD_PIECE);
    }

    private static <T extends Structure> void register(TagKey<? extends T> name, StructureType<? extends T> type) {
        var id = name.location();
        Registry.register(BuiltInRegistries.STRUCTURE_TYPE, id, type);
    }
    private static <T extends Structure> void register(Identifier id, StructurePieceType pieceType) {
        Registry.register(BuiltInRegistries.STRUCTURE_PIECE, id, pieceType);
    }
}
