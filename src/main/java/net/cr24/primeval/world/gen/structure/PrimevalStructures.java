package net.cr24.primeval.world.gen.structure;

import net.cr24.primeval.PrimevalMain;
import net.minecraft.structure.StructurePieceType;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;

public class PrimevalStructures {

    /* BIOME TAGS */
    public static final TagKey<Biome> HAS_NATIVE_COPPER = tagkey("has_native_copper");

    private static TagKey<Biome> tagkey(String id) {
        return TagKey.of(Registry.BIOME_KEY, new Identifier(id));
    }

    /* STRUCTURES */

    public static final TagKey<Structure> ORE_FIELD_KEY = tagKey("ore_field");
    public static final StructureType<OreFieldStructure> ORE_FIELD = () -> OreFieldStructure.CODEC;
    public static final StructurePieceType ORE_FIELD_PIECE = OreFieldGenerator.Blob::new;

    private static TagKey<Structure> tagKey(String name) {
        return TagKey.of(Registry.STRUCTURE_KEY, PrimevalMain.getId(name));
    }

    public static void init() {
        register(ORE_FIELD_KEY, ORE_FIELD, ORE_FIELD_PIECE);
    }

    private static <T extends Structure> void register(TagKey<? extends T> name, StructureType<? extends T> type, StructurePieceType pieceType) {
        var id = name.id();
        Registry.register(Registry.STRUCTURE_TYPE, id, type);
        Registry.register(Registry.STRUCTURE_PIECE, id, pieceType);
    }
}
