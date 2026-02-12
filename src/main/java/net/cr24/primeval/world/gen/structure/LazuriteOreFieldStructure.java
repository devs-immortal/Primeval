package net.cr24.primeval.world.gen.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.cr24.primeval.initialization.PrimevalBlocks;
import net.cr24.primeval.initialization.PrimevalTags;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import java.util.Optional;

public class LazuriteOreFieldStructure extends Structure {

    public static final MapCodec<LazuriteOreFieldStructure> CODEC = simpleCodec(LazuriteOreFieldStructure::new);

    public LazuriteOreFieldStructure(StructureSettings config) {
        super(config);
    }

    public Optional<GenerationStub> findGenerationPoint(Structure.GenerationContext context) {
        return onTopOfChunkCenter(context, Heightmap.Types.WORLD_SURFACE_WG, (collector) -> {
            addPieces(collector, context);
        });
    }

    private static void addPieces(StructurePiecesBuilder collector, Structure.GenerationContext context) {
        int height;
        if (context.biomeSource().getNoiseBiome(context.chunkPos().getMinBlockX(), 100, context.chunkPos().getMinBlockZ(), context.randomState().sampler()).is(PrimevalTags.Biomes.RAISED_ORES)) {
            height = context.random().nextIntBetweenInclusive(-40, -25);
        } else {
            height = context.random().nextIntBetweenInclusive(-80, -60);
        }
        BlockState[] ores = new BlockState[] {
                PrimevalBlocks.LAZURITE_ORE.large().defaultBlockState(),
                PrimevalBlocks.LAZURITE_ORE.medium().defaultBlockState(),
                PrimevalBlocks.LAZURITE_ORE.small().defaultBlockState(),
                PrimevalBlocks.GOLD_NATIVE_ORE.small().defaultBlockState()
        };
        // Motherlode
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX(), context.chunkPos().getMinBlockZ(), 0, 10, 4, 2, 3, 0.5f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX(), context.chunkPos().getMinBlockZ(), height, 45, 5, 5, 7, 0.45f, ores));
        // Inner ring
        int innerRingRadius = 36;
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX()+innerRingRadius, context.chunkPos().getMinBlockZ(), height, 50, 4, 3, 5, 0.8f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX()-innerRingRadius, context.chunkPos().getMinBlockZ(), height, 50, 4, 3, 5, 0.7f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX(), context.chunkPos().getMinBlockZ()+innerRingRadius, height, 50, 4, 3, 5, 0.8f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX(), context.chunkPos().getMinBlockZ()-innerRingRadius, height, 50, 4, 3, 5, 0.7f, ores));
        // Outer Ring
        int outerRingRadius1 = 72;
        int outerRingRadius2 = 54;
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX()+outerRingRadius1, context.chunkPos().getMinBlockZ(), height, 55, 6, 3, 4, 0.9f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX()-outerRingRadius1, context.chunkPos().getMinBlockZ(), height, 55, 6, 3, 4, 1.0f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX(), context.chunkPos().getMinBlockZ()+outerRingRadius1, height, 55, 6, 3, 4, 0.9f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX(), context.chunkPos().getMinBlockZ()-outerRingRadius1, height, 55, 6, 3, 4, 1.0f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX()+outerRingRadius2, context.chunkPos().getMinBlockZ()+outerRingRadius2, height, 45, 5, 3, 4, 1.5f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX()-outerRingRadius2, context.chunkPos().getMinBlockZ()+outerRingRadius2, height, 45, 5, 3, 4, 1.5f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX()+outerRingRadius2, context.chunkPos().getMinBlockZ()-outerRingRadius2, height, 45, 5, 3, 4, 1.5f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX()-outerRingRadius2, context.chunkPos().getMinBlockZ()-outerRingRadius2, height, 45, 5, 3, 4, 1.5f, ores));
    }

    public StructureType<?> type() {
        return PrimevalStructures.LAZURITE_ORE_FIELD;
    }
}
