package net.cr24.primeval.world.gen.structure;

import com.mojang.serialization.Codec;
import net.cr24.primeval.block.PrimevalBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.structure.StructurePiecesCollector;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;

import java.util.Optional;

public class SphaleriteZincOreFieldStructure extends Structure {

    public static final Codec<SphaleriteZincOreFieldStructure> CODEC = createCodec(SphaleriteZincOreFieldStructure::new);

    public SphaleriteZincOreFieldStructure(Config config) {
        super(config);
    }

    public Optional<StructurePosition> getStructurePosition(Structure.Context context) {
        return getStructurePosition(context, Heightmap.Type.WORLD_SURFACE_WG, (collector) -> {
            addPieces(collector, context);
        });
    }

    private static void addPieces(StructurePiecesCollector collector, Structure.Context context) {
        int height = context.random().nextBetween(-30, -12);
        BlockState[] ores = new BlockState[] {
                PrimevalBlocks.ZINC_SPHALERITE_ORE.large().getDefaultState(),
                PrimevalBlocks.ZINC_SPHALERITE_ORE.medium().getDefaultState(),
                PrimevalBlocks.ZINC_SPHALERITE_ORE.small().getDefaultState(),
                PrimevalBlocks.ZINC_SPHALERITE_ORE.small().getDefaultState()
        };
        // Motherlode
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX(), context.chunkPos().getStartZ(), height, 60, 8, 7, 9, 0.5f, ores));
        // Inner ring
        int innerRingRadius = 32;
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX()+innerRingRadius, context.chunkPos().getStartZ(), height, 50, 6, 3, 6, 0.6f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX()-innerRingRadius, context.chunkPos().getStartZ(), height, 50, 6, 3, 6, 0.7f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX(), context.chunkPos().getStartZ()+innerRingRadius, height, 50, 6, 3, 6, 0.6f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX(), context.chunkPos().getStartZ()-innerRingRadius, height, 50, 6, 3, 6, 0.7f, ores));
        // Outer Ring
        int outerRingRadius1 = 60;
        int outerRingRadius2 = 48;
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX()+outerRingRadius1, context.chunkPos().getStartZ(), height, 42, 4, 3, 4, 0.9f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX()-outerRingRadius1, context.chunkPos().getStartZ(), height, 42, 4, 3, 4, 0.9f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX(), context.chunkPos().getStartZ()+outerRingRadius1, height, 42, 4, 3, 4, 0.9f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX(), context.chunkPos().getStartZ()-outerRingRadius1, height, 42, 4, 3, 4, 0.9f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX()+outerRingRadius2, context.chunkPos().getStartZ()+outerRingRadius2, height, 35, 5, 3, 4, 1.2f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX()-outerRingRadius2, context.chunkPos().getStartZ()+outerRingRadius2, height, 35, 5, 3, 4, 1.2f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX()+outerRingRadius2, context.chunkPos().getStartZ()-outerRingRadius2, height, 35, 5, 3, 4, 1.2f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX()-outerRingRadius2, context.chunkPos().getStartZ()-outerRingRadius2, height, 35, 5, 3, 4, 1.2f, ores));
    }

    public StructureType<?> getType() {
        return PrimevalStructures.SPHALERITE_ZINC_ORE_FIELD;
    }
}
