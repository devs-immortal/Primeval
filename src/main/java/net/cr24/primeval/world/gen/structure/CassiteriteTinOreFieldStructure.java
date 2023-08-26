package net.cr24.primeval.world.gen.structure;

import com.mojang.serialization.Codec;
import net.cr24.primeval.block.PrimevalBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.structure.StructurePiecesCollector;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;

import java.util.Optional;

public class CassiteriteTinOreFieldStructure extends Structure {

    public static final Codec<CassiteriteTinOreFieldStructure> CODEC = createCodec(CassiteriteTinOreFieldStructure::new);

    public CassiteriteTinOreFieldStructure(Config config) {
        super(config);
    }

    public Optional<StructurePosition> getStructurePosition(Structure.Context context) {
        return getStructurePosition(context, Heightmap.Type.WORLD_SURFACE_WG, (collector) -> {
            addPieces(collector, context);
        });
    }

    private static void addPieces(StructurePiecesCollector collector, Structure.Context context) {
        int height = context.random().nextBetween(-40, -22);
        BlockState[] ores = new BlockState[] {
                PrimevalBlocks.TIN_CASSITERITE_ORE.large().getDefaultState(),
                PrimevalBlocks.TIN_CASSITERITE_ORE.medium().getDefaultState(),
                PrimevalBlocks.TIN_CASSITERITE_ORE.small().getDefaultState(),
                PrimevalBlocks.STONE.getDefaultState()
        };
        // Motherlode
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX(), context.chunkPos().getStartZ(), height, 80, 7, 8, 9, 0.7f, ores));
        // Inner ring
        int innerRingRadius = 40;
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX()+innerRingRadius, context.chunkPos().getStartZ(), height, 60, 6, 3, 5, 0.8f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX()-innerRingRadius, context.chunkPos().getStartZ(), height, 60, 6, 3, 5, 0.8f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX(), context.chunkPos().getStartZ()+innerRingRadius, height, 60, 6, 3, 5, 0.8f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX(), context.chunkPos().getStartZ()-innerRingRadius, height, 60, 6, 3, 5, 0.8f, ores));
        // Outer Ring
        int outerRingRadius1 = 60;
        int outerRingRadius2 = 48;
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX()+outerRingRadius1, context.chunkPos().getStartZ(), height, 42, 5, 3, 4, 1.0f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX()-outerRingRadius1, context.chunkPos().getStartZ(), height, 42, 5, 3, 4, 1.0f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX(), context.chunkPos().getStartZ()+outerRingRadius1, height, 42, 5, 3, 4, 1.0f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX(), context.chunkPos().getStartZ()-outerRingRadius1, height, 42, 5, 3, 4, 1.0f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX()+outerRingRadius2, context.chunkPos().getStartZ()+outerRingRadius2, height, 35, 6, 3, 4, 1.3f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX()-outerRingRadius2, context.chunkPos().getStartZ()+outerRingRadius2, height, 35, 6, 3, 4, 1.3f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX()+outerRingRadius2, context.chunkPos().getStartZ()-outerRingRadius2, height, 35, 6, 3, 4, 1.3f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX()-outerRingRadius2, context.chunkPos().getStartZ()-outerRingRadius2, height, 35, 6, 3, 4, 1.3f, ores));
    }

    public StructureType<?> getType() {
        return PrimevalStructures.CASSITERITE_TIN_ORE_FIELD;
    }
}
