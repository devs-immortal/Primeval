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

public class CassiteriteTinOreFieldStructure extends Structure {

    public static final MapCodec<CassiteriteTinOreFieldStructure> CODEC = simpleCodec(CassiteriteTinOreFieldStructure::new);

    public CassiteriteTinOreFieldStructure(StructureSettings config) {
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
            height = context.random().nextIntBetweenInclusive(-10, -5);
        } else {
            height = context.random().nextIntBetweenInclusive(-40, -22);
        }
        BlockState[] ores = new BlockState[] {
                PrimevalBlocks.TIN_CASSITERITE_ORE.large().defaultBlockState(),
                PrimevalBlocks.TIN_CASSITERITE_ORE.medium().defaultBlockState(),
                PrimevalBlocks.TIN_CASSITERITE_ORE.small().defaultBlockState(),
                PrimevalBlocks.STONE.defaultBlockState()
        };
        // Motherlode
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX(), context.chunkPos().getMinBlockZ(), 0, 30, 8, 3, 5, 0.4f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX(), context.chunkPos().getMinBlockZ(), height, 80, 7, 8, 9, 0.7f, ores));
        // Inner ring
        int innerRingRadius = 40;
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX()+innerRingRadius, context.chunkPos().getMinBlockZ(), height, 60, 6, 3, 5, 0.8f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX()-innerRingRadius, context.chunkPos().getMinBlockZ(), height, 60, 6, 3, 5, 0.8f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX(), context.chunkPos().getMinBlockZ()+innerRingRadius, height, 60, 6, 3, 5, 0.8f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX(), context.chunkPos().getMinBlockZ()-innerRingRadius, height, 60, 6, 3, 5, 0.8f, ores));
        // Outer Ring
        int outerRingRadius1 = 60;
        int outerRingRadius2 = 48;
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX()+outerRingRadius1, context.chunkPos().getMinBlockZ(), height, 42, 5, 3, 4, 1.0f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX()-outerRingRadius1, context.chunkPos().getMinBlockZ(), height, 42, 5, 3, 4, 1.0f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX(), context.chunkPos().getMinBlockZ()+outerRingRadius1, height, 42, 5, 3, 4, 1.0f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX(), context.chunkPos().getMinBlockZ()-outerRingRadius1, height, 42, 5, 3, 4, 1.0f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX()+outerRingRadius2, context.chunkPos().getMinBlockZ()+outerRingRadius2, height, 35, 6, 3, 4, 1.3f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX()-outerRingRadius2, context.chunkPos().getMinBlockZ()+outerRingRadius2, height, 35, 6, 3, 4, 1.3f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX()+outerRingRadius2, context.chunkPos().getMinBlockZ()-outerRingRadius2, height, 35, 6, 3, 4, 1.3f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX()-outerRingRadius2, context.chunkPos().getMinBlockZ()-outerRingRadius2, height, 35, 6, 3, 4, 1.3f, ores));
    }

    public StructureType<?> type() {
        return PrimevalStructures.CASSITERITE_TIN_ORE_FIELD;
    }
}
