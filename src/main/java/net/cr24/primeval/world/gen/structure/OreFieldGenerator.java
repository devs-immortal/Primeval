package net.cr24.primeval.world.gen.structure;

import net.cr24.primeval.initialization.PrimevalBlocks;
import net.cr24.primeval.initialization.PrimevalTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.ScatteredFeaturePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import java.awt.geom.Point2D;

public class OreFieldGenerator {

    public static class Blob extends ScatteredFeaturePiece {

        public final int height;
        public final int size;
        public final int[] ballParams;
        public final float richness;
        public final BlockState largeState;
        public final BlockState mediumState;
        public final BlockState smallState;
        public final BlockState extraState;

        public Blob(RandomSource random, int x, int z, int height, int size, int ballCount, int ballSizeMin, int ballSizeMax, float richness, BlockState[] states) {
            super(PrimevalStructures.ORE_FIELD_PIECE, x, height, z, size, 20, size, Direction.NORTH);
            this.height = height;
            this.size = size;
            int[] params = new int[ballCount * 4];
            for (int i = 0; i < ballCount * 4; i += 4) {
                int ballSize = random.nextIntBetweenInclusive(ballSizeMin, ballSizeMax);
                params[i] = ballSize - 2;
                params[i + 1] = random.nextIntBetweenInclusive(ballSize, size - ballSize);
                params[i + 2] = random.nextIntBetweenInclusive(0, 6);
                params[i + 3] = random.nextIntBetweenInclusive(ballSize, size - ballSize);
            }
            this.ballParams = params;
            this.richness = richness;
            this.largeState = states[0];
            this.mediumState = states[1];
            this.smallState = states[2];
            this.extraState = states[3];
        }

        public Blob(CompoundTag nbt) {
            super(PrimevalStructures.ORE_FIELD_PIECE, nbt);
            this.height = nbt.getInt("Height").orElseThrow();
            this.size = nbt.getInt("Size").orElseThrow();
            this.ballParams = nbt.getIntArray("BallParams").orElseThrow();
            this.richness = nbt.getFloat("Richness").orElseThrow();
            this.largeState = NbtUtils.readBlockState(BuiltInRegistries.BLOCK, nbt.getCompound("LargeState").orElseThrow());
            this.mediumState = NbtUtils.readBlockState(BuiltInRegistries.BLOCK, nbt.getCompound("MediumState").orElseThrow());
            this.smallState = NbtUtils.readBlockState(BuiltInRegistries.BLOCK, nbt.getCompound("SmallState").orElseThrow());
            this.extraState = NbtUtils.readBlockState(BuiltInRegistries.BLOCK, nbt.getCompound("ExtraState").orElseThrow());
        }


        public Blob(StructurePieceSerializationContext structureContext, CompoundTag nbtCompound) {
            this(nbtCompound);
        }

        protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag nbt) {
            super.addAdditionalSaveData(context, nbt);
            nbt.putInt("Height", this.height);
            nbt.putInt("Size", this.size);
            nbt.putIntArray("BallParams", this.ballParams);
            nbt.putFloat("Richness", this.richness);
            nbt.put("LargeState", NbtUtils.writeBlockState(this.largeState));
            nbt.put("MediumState", NbtUtils.writeBlockState(this.mediumState));
            nbt.put("SmallState", NbtUtils.writeBlockState(this.smallState));
            nbt.put("ExtraState", NbtUtils.writeBlockState(this.extraState));
        }

        public void postProcess(WorldGenLevel world, StructureManager structureAccessor, ChunkGenerator chunkGenerator, RandomSource random, BoundingBox chunkBox, ChunkPos chunkPos, BlockPos pivot) {
            if (this.updateAverageGroundHeight(world, chunkBox, height)) {
                for (int i = 0; i < ballParams.length; i += 4) {
                    Ball b = new Ball(ballParams[i], ballParams[i + 1], ballParams[i + 2], ballParams[i + 3], richness, largeState, mediumState, smallState, extraState);
                    b.generate(this, world, chunkBox, random);
                }
            }
        }

        public void pAddBlock(WorldGenLevel world, BlockState block, int x, int y, int z, BoundingBox box) {
            this.placeBlock(world, block, x, y, z, box);
        }

        public boolean validBlock(WorldGenLevel world, int x, int y, int z, BoundingBox box, RandomSource random) {
            return this.getBlock(world, x, y, z, box).is(PrimevalTags.Blocks.ORE_REPLACEABLE) ||
                    this.getBlock(world, x, y, z, box).is(PrimevalTags.Blocks.ORE_SEMI_REPLACEABLE) && random.nextBoolean();
        }

    }

    private static class Ball {

        public final int size;
        public final int xOffset;
        public final int yOffset;
        public final int zOffset;
        public final float richness;
        private final Point2D center = new Point2D.Float(0, 0);
        public final BlockState largeState;
        public final BlockState mediumState;
        public final BlockState smallState;
        public final BlockState extraState;

        public Ball(int size, int xOffset, int yOffset, int zOffset, float richness, BlockState largeState, BlockState mediumState, BlockState smallState, BlockState extraState) {
            this.size = size;
            this.xOffset = xOffset;
            this.yOffset = yOffset;
            this.zOffset = zOffset;
            this.richness = richness;
            this.largeState = largeState;
            this.mediumState = mediumState;
            this.smallState = smallState;
            this.extraState = extraState;
        }

        public void generate(Blob blob, WorldGenLevel world, BoundingBox chunkBox, RandomSource random) {
            for (int i = -size; i < size; i++) {
                for (int j = -size; j < size; j++) {
                    for (int k = -size / 2; k <= size / 2; k++) {
                        if (center.distance(i, j) < size - Math.abs(k / 2) && blob.validBlock(world, xOffset + i, yOffset + k, zOffset + j, chunkBox, random)) {
                            float threshold = random.nextFloat();
                            if (threshold > richness) {
                                blob.pAddBlock(world, largeState, xOffset + i, yOffset + k, zOffset + j, chunkBox);
                            } else if (threshold > richness / 2) {
                                blob.pAddBlock(world, mediumState, xOffset + i, yOffset + k, zOffset + j, chunkBox);
                            } else if (threshold > richness / 4) {
                                blob.pAddBlock(world, smallState, xOffset + i, yOffset + k, zOffset + j, chunkBox);
                            } else if (threshold > richness / 8) {
                                blob.pAddBlock(world, extraState, xOffset + i, yOffset + k, zOffset + j, chunkBox);
                            } else {
                                blob.pAddBlock(world, PrimevalBlocks.GRAVEL.defaultBlockState(), xOffset + i, yOffset + k, zOffset + j, chunkBox);
                            }
                        }
                    }
                }
            }
        }
    }

}
