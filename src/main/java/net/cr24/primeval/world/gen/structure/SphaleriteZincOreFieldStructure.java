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

public class SphaleriteZincOreFieldStructure extends Structure {

    public static final MapCodec<SphaleriteZincOreFieldStructure> CODEC = simpleCodec(SphaleriteZincOreFieldStructure::new);

    public SphaleriteZincOreFieldStructure(StructureSettings config) {
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
            height = context.random().nextIntBetweenInclusive(-8, 0);
        } else {
            height = context.random().nextIntBetweenInclusive(-30, -12);
        }
        BlockState[] ores = new BlockState[] {
                PrimevalBlocks.ZINC_SPHALERITE_ORE.large().defaultBlockState(),
                PrimevalBlocks.ZINC_SPHALERITE_ORE.medium().defaultBlockState(),
                PrimevalBlocks.ZINC_SPHALERITE_ORE.small().defaultBlockState(),
                PrimevalBlocks.ZINC_SPHALERITE_ORE.small().defaultBlockState()
        };
        // Motherlode
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX(), context.chunkPos().getMinBlockZ(), 0, 30, 7, 3, 4, 0.5f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX(), context.chunkPos().getMinBlockZ(), height, 60, 8, 7, 9, 0.5f, ores));
        // Inner ring
        int innerRingRadius = 32;
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX()+innerRingRadius, context.chunkPos().getMinBlockZ(), height, 50, 6, 3, 6, 0.6f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX()-innerRingRadius, context.chunkPos().getMinBlockZ(), height, 50, 6, 3, 6, 0.7f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX(), context.chunkPos().getMinBlockZ()+innerRingRadius, height, 50, 6, 3, 6, 0.6f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX(), context.chunkPos().getMinBlockZ()-innerRingRadius, height, 50, 6, 3, 6, 0.7f, ores));
        // Outer Ring
        int outerRingRadius1 = 60;
        int outerRingRadius2 = 48;
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX()+outerRingRadius1, context.chunkPos().getMinBlockZ(), height, 42, 4, 3, 4, 0.9f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX()-outerRingRadius1, context.chunkPos().getMinBlockZ(), height, 42, 4, 3, 4, 0.9f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX(), context.chunkPos().getMinBlockZ()+outerRingRadius1, height, 42, 4, 3, 4, 0.9f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX(), context.chunkPos().getMinBlockZ()-outerRingRadius1, height, 42, 4, 3, 4, 0.9f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX()+outerRingRadius2, context.chunkPos().getMinBlockZ()+outerRingRadius2, height, 35, 5, 3, 4, 1.2f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX()-outerRingRadius2, context.chunkPos().getMinBlockZ()+outerRingRadius2, height, 35, 5, 3, 4, 1.2f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX()+outerRingRadius2, context.chunkPos().getMinBlockZ()-outerRingRadius2, height, 35, 5, 3, 4, 1.2f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX()-outerRingRadius2, context.chunkPos().getMinBlockZ()-outerRingRadius2, height, 35, 5, 3, 4, 1.2f, ores));
    }

    public StructureType<?> type() {
        return PrimevalStructures.SPHALERITE_ZINC_ORE_FIELD;
    }
}
