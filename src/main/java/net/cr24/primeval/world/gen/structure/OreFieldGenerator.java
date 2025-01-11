package net.cr24.primeval.world.gen.structure;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.registry.Registries;
import net.minecraft.structure.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.awt.geom.Point2D;

public class OreFieldGenerator {

    public static class Blob extends ShiftableStructurePiece {

        public final int height;
        public final int size;
        public final int[] ballParams;
        public final float richness;
        public final BlockState largeState;
        public final BlockState mediumState;
        public final BlockState smallState;
        public final BlockState extraState;

        public Blob(Random random, int x, int z, int height, int size, int ballCount, int ballSizeMin, int ballSizeMax, float richness, BlockState[] states) {
            super(PrimevalStructures.ORE_FIELD_PIECE, x, height, z, size, 20, size, Direction.NORTH);
            this.height = height;
            this.size = size;
            int[] params = new int[ballCount * 4];
            for (int i = 0; i < ballCount * 4; i += 4) {
                int ballSize = random.nextBetween(ballSizeMin, ballSizeMax);
                params[i] = ballSize - 2;
                params[i + 1] = random.nextBetween(ballSize, size - ballSize);
                params[i + 2] = random.nextBetween(0, 6);
                params[i + 3] = random.nextBetween(ballSize, size - ballSize);
            }
            this.ballParams = params;
            this.richness = richness;
            this.largeState = states[0];
            this.mediumState = states[1];
            this.smallState = states[2];
            this.extraState = states[3];
        }

        public Blob(NbtCompound nbt) {
            super(PrimevalStructures.ORE_FIELD_PIECE, nbt);
            this.height = nbt.getInt("Height");
            this.size = nbt.getInt("Size");
            this.ballParams = nbt.getIntArray("BallParams");
            this.richness = nbt.getFloat("Richness");
            this.largeState = NbtHelper.toBlockState(Registries.BLOCK.getReadOnlyWrapper(), nbt.getCompound("LargeState"));
            this.mediumState = NbtHelper.toBlockState(Registries.BLOCK.getReadOnlyWrapper(), nbt.getCompound("MediumState"));
            this.smallState = NbtHelper.toBlockState(Registries.BLOCK.getReadOnlyWrapper(), nbt.getCompound("SmallState"));
            this.extraState = NbtHelper.toBlockState(Registries.BLOCK.getReadOnlyWrapper(), nbt.getCompound("ExtraState"));
        }


        public Blob(StructureContext structureContext, NbtCompound nbtCompound) {
            this(nbtCompound);
        }

        protected void writeNbt(StructureContext context, NbtCompound nbt) {
            super.writeNbt(context, nbt);
            nbt.putInt("Height", this.height);
            nbt.putInt("Size", this.size);
            nbt.putIntArray("BallParams", this.ballParams);
            nbt.putFloat("Richness", this.richness);
            nbt.put("LargeState", NbtHelper.fromBlockState(this.largeState));
            nbt.put("MediumState", NbtHelper.fromBlockState(this.mediumState));
            nbt.put("SmallState", NbtHelper.fromBlockState(this.smallState));
            nbt.put("ExtraState", NbtHelper.fromBlockState(this.extraState));
        }

        public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox chunkBox, ChunkPos chunkPos, BlockPos pivot) {
            if (this.adjustToAverageHeight(world, chunkBox, height)) {
                for (int i = 0; i < ballParams.length; i += 4) {
                    Ball b = new Ball(ballParams[i], ballParams[i + 1], ballParams[i + 2], ballParams[i + 3], richness, largeState, mediumState, smallState, extraState);
                    b.generate(this, world, chunkBox, random);
                }
            }
        }

        public void pAddBlock(StructureWorldAccess world, BlockState block, int x, int y, int z, BlockBox box) {
            this.addBlock(world, block, x, y, z, box);
        }

        public boolean validBlock(StructureWorldAccess world, int x, int y, int z, BlockBox box) {
            return this.getBlockAt(world, x, y, z, box).isIn(BlockTags.BASE_STONE_OVERWORLD);
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

        public void generate(Blob blob, StructureWorldAccess world, BlockBox chunkBox, Random random) {
            for (int i = -size; i < size; i++) {
                for (int j = -size; j < size; j++) {
                    for (int k = -size / 2; k <= size / 2; k++) {
                        if (center.distance(i, j) < size - Math.abs(k / 2) && blob.validBlock(world, xOffset + i, yOffset + k, zOffset + j, chunkBox)) {
                            float threshold = random.nextFloat();
                            if (threshold > richness) {
                                blob.pAddBlock(world, largeState, xOffset + i, yOffset + k, zOffset + j, chunkBox);
                            } else if (threshold > richness / 2) {
                                blob.pAddBlock(world, mediumState, xOffset + i, yOffset + k, zOffset + j, chunkBox);
                            } else if (threshold > richness / 4) {
                                blob.pAddBlock(world, smallState, xOffset + i, yOffset + k, zOffset + j, chunkBox);
                            } else if (threshold > richness / 8) {
                                blob.pAddBlock(world, extraState, xOffset + i, yOffset + k, zOffset + j, chunkBox);
                            }
                        }
                    }
                }
            }
        }
    }

}
