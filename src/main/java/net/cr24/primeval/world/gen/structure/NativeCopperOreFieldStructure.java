package net.cr24.primeval.world.gen.structure;

import com.mojang.serialization.Codec;
import net.cr24.primeval.block.PrimevalBlocks;
import net.cr24.primeval.world.PrimevalWorld;
import net.minecraft.block.BlockState;
import net.minecraft.structure.StructurePiecesCollector;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;

import java.util.Optional;

public class NativeCopperOreFieldStructure extends Structure {

    public static final Codec<NativeCopperOreFieldStructure> CODEC = createCodec(NativeCopperOreFieldStructure::new);

    public NativeCopperOreFieldStructure(Config config) {
        super(config);
    }

    public Optional<Structure.StructurePosition> getStructurePosition(Structure.Context context) {
        return getStructurePosition(context, Heightmap.Type.WORLD_SURFACE_WG, (collector) -> {
            addPieces(collector, context);
        });
    }

    private static void addPieces(StructurePiecesCollector collector, Structure.Context context) {
        int height;
        height = context.random().nextBetween(-40, -10);
        BlockState[] ores = new BlockState[] {
                PrimevalBlocks.COPPER_NATIVE_ORE.large().getDefaultState(),
                PrimevalBlocks.COPPER_NATIVE_ORE.medium().getDefaultState(),
                PrimevalBlocks.COPPER_NATIVE_ORE.small().getDefaultState(),
                PrimevalBlocks.STONE.getDefaultState()
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
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX()+outerRingRadius1, context.chunkPos().getStartZ(), height, 42, 5, 3, 4, 0.9f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX()-outerRingRadius1, context.chunkPos().getStartZ(), height, 42, 5, 3, 4, 0.9f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX(), context.chunkPos().getStartZ()+outerRingRadius1, height, 42, 5, 3, 4, 0.9f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX(), context.chunkPos().getStartZ()-outerRingRadius1, height, 42, 5, 3, 4, 0.9f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX()+outerRingRadius2, context.chunkPos().getStartZ()+outerRingRadius2, height, 35, 6, 3, 4, 1.2f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX()-outerRingRadius2, context.chunkPos().getStartZ()+outerRingRadius2, height, 35, 6, 3, 4, 1.2f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX()+outerRingRadius2, context.chunkPos().getStartZ()-outerRingRadius2, height, 35, 6, 3, 4, 1.2f, ores));
        collector.addPiece(new OreFieldGenerator.Blob(context.random(), context.chunkPos().getStartX()-outerRingRadius2, context.chunkPos().getStartZ()-outerRingRadius2, height, 35, 6, 3, 4, 1.2f, ores));
    }

    public StructureType<?> getType() {
        return PrimevalStructures.NATIVE_COPPER_ORE_FIELD;
    }
}
