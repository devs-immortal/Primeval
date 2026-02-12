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

public class MalachiteCopperOreFieldStructure extends Structure {

    public static final MapCodec<MalachiteCopperOreFieldStructure> CODEC = simpleCodec(MalachiteCopperOreFieldStructure::new);

    public MalachiteCopperOreFieldStructure(StructureSettings config) {
        super(config);
    }

    public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext context) {
        return onTopOfChunkCenter(context, Heightmap.Types.WORLD_SURFACE_WG, (collector) -> {
            addPieces(collector, context);
        });
    }

    private static void addPieces(StructurePiecesBuilder collector, Structure.GenerationContext context) {
        int height;
        if (context.biomeSource().getNoiseBiome(context.chunkPos().getMinBlockX(), 100, context.chunkPos().getMinBlockZ(), context.randomState().sampler()).is(PrimevalTags.Biomes.RAISED_ORES)) {
            height = context.random().nextIntBetweenInclusive(-15, 0);
        } else {
            height = context.random().nextIntBetweenInclusive(-50, -25);
        }
        BlockState[] ores = new BlockState[] {
                PrimevalBlocks.COPPER_MALACHITE_ORE.large().defaultBlockState(),
                PrimevalBlocks.COPPER_MALACHITE_ORE.medium().defaultBlockState(),
                PrimevalBlocks.COPPER_MALACHITE_ORE.small().defaultBlockState(),
                PrimevalBlocks.STONE.defaultBlockState()
        };
        // Motherlode
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX(), context.chunkPos().getMinBlockZ(), 0, 20, 9, 2, 3, 0.3f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX(), context.chunkPos().getMinBlockZ(), height, 70, 10, 5, 7, 0.3f, ores));
        // Inner ring
        int innerRingRadius = 26;
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX()+innerRingRadius, context.chunkPos().getMinBlockZ(), height, 40, 7, 3, 5, 0.6f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX()-innerRingRadius, context.chunkPos().getMinBlockZ(), height, 40, 7, 3, 5, 0.7f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX(), context.chunkPos().getMinBlockZ()+innerRingRadius, height, 40, 7, 3, 5, 0.6f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX(), context.chunkPos().getMinBlockZ()-innerRingRadius, height, 40, 7, 3, 5, 0.7f, ores));
        // Outer Ring
        int outerRingRadius1 = 54;
        int outerRingRadius2 = 40;
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX()+outerRingRadius1, context.chunkPos().getMinBlockZ(), height, 40, 4, 3, 4, 0.9f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX()-outerRingRadius1, context.chunkPos().getMinBlockZ(), height, 40, 4, 3, 4, 0.9f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX(), context.chunkPos().getMinBlockZ()+outerRingRadius1, height, 40, 4, 3, 4, 0.9f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX(), context.chunkPos().getMinBlockZ()-outerRingRadius1, height, 40, 4, 3, 4, 0.9f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX()+outerRingRadius2, context.chunkPos().getMinBlockZ()+outerRingRadius2, height, 35, 5, 3, 4, 1.2f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX()-outerRingRadius2, context.chunkPos().getMinBlockZ()+outerRingRadius2, height, 35, 5, 3, 4, 1.2f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX()+outerRingRadius2, context.chunkPos().getMinBlockZ()-outerRingRadius2, height, 35, 5, 3, 4, 1.2f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getMinBlockX()-outerRingRadius2, context.chunkPos().getMinBlockZ()-outerRingRadius2, height, 35, 5, 3, 4, 1.2f, ores));
    }

    public StructureType<?> type() {
        return PrimevalStructures.MALACHITE_COPPER_ORE_FIELD;
    }
}
