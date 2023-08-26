package net.cr24.primeval.world.gen.structure;

import com.mojang.serialization.Codec;
import net.cr24.primeval.block.PrimevalBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.structure.StructurePiecesCollector;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;

import java.util.Optional;

public class LazuriteOreFieldStructure extends Structure {

    public static final Codec<LazuriteOreFieldStructure> CODEC = createCodec(LazuriteOreFieldStructure::new);

    public LazuriteOreFieldStructure(Config config) {
        super(config);
    }

    public Optional<StructurePosition> getStructurePosition(Structure.Context context) {
        return getStructurePosition(context, Heightmap.Type.WORLD_SURFACE_WG, (collector) -> {
            addPieces(collector, context);
        });
    }

    private static void addPieces(StructurePiecesCollector collector, Structure.Context context) {
        int height = context.random().nextBetween(-80, -60);
        BlockState[] ores = new BlockState[] {
                PrimevalBlocks.LAZURITE_ORE.large().getDefaultState(),
                PrimevalBlocks.LAZURITE_ORE.medium().getDefaultState(),
                PrimevalBlocks.LAZURITE_ORE.small().getDefaultState(),
                PrimevalBlocks.GOLD_NATIVE_ORE.small().getDefaultState()
        };
        // Motherlode
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX(), context.chunkPos().getStartZ(), height, 45, 5, 5, 7, 0.45f, ores));
        // Inner ring
        int innerRingRadius = 36;
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX()+innerRingRadius, context.chunkPos().getStartZ(), height, 50, 4, 3, 5, 0.8f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX()-innerRingRadius, context.chunkPos().getStartZ(), height, 50, 4, 3, 5, 0.7f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX(), context.chunkPos().getStartZ()+innerRingRadius, height, 50, 4, 3, 5, 0.8f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX(), context.chunkPos().getStartZ()-innerRingRadius, height, 50, 4, 3, 5, 0.7f, ores));
        // Outer Ring
        int outerRingRadius1 = 72;
        int outerRingRadius2 = 54;
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX()+outerRingRadius1, context.chunkPos().getStartZ(), height, 55, 6, 3, 4, 0.9f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX()-outerRingRadius1, context.chunkPos().getStartZ(), height, 55, 6, 3, 4, 1.0f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX(), context.chunkPos().getStartZ()+outerRingRadius1, height, 55, 6, 3, 4, 0.9f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX(), context.chunkPos().getStartZ()-outerRingRadius1, height, 55, 6, 3, 4, 1.0f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX()+outerRingRadius2, context.chunkPos().getStartZ()+outerRingRadius2, height, 45, 5, 3, 4, 1.5f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX()-outerRingRadius2, context.chunkPos().getStartZ()+outerRingRadius2, height, 45, 5, 3, 4, 1.5f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX()+outerRingRadius2, context.chunkPos().getStartZ()-outerRingRadius2, height, 45, 5, 3, 4, 1.5f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX()-outerRingRadius2, context.chunkPos().getStartZ()-outerRingRadius2, height, 45, 5, 3, 4, 1.5f, ores));
    }

    public StructureType<?> getType() {
        return PrimevalStructures.LAZURITE_ORE_FIELD;
    }
}
