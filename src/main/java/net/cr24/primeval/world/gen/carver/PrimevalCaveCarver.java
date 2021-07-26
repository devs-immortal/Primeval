package net.cr24.primeval.world.gen.carver;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import net.cr24.primeval.block.PrimevalBlocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.carver.CarverContext;
import net.minecraft.world.gen.carver.CaveCarver;
import net.minecraft.world.gen.carver.CaveCarverConfig;
import net.minecraft.world.gen.chunk.AquiferSampler;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

public class PrimevalCaveCarver extends CaveCarver {
    public PrimevalCaveCarver(Codec<CaveCarverConfig> codec) {
        super(codec);
        this.alwaysCarvableBlocks = ImmutableSet.of(PrimevalBlocks.DIRT, PrimevalBlocks.STONE, PrimevalBlocks.GRASSY_DIRT);
    }

    protected int getMaxCaveCount() {
        return 6;
    }


    protected float getTunnelSystemWidth(Random random) {
        return (random.nextFloat() + random.nextFloat());
    }


    protected double getTunnelSystemHeightWidthRatio() {
        return 2.0D;
    }


    protected void carveTunnels(CarverContext context, CaveCarverConfig config, Chunk chunk, Function<BlockPos, Biome> posToBiome, long seed, AquiferSampler aquiferSampler, double x, double y, double z, double horizontalScale, double verticalScale, float width, float yaw, float pitch, int branchStartIndex, int branchCount, double yawPitchRatio, BitSet carvingMask, SkipPredicate skipPredicate) {
        Random random = new Random(seed);
        int i = random.nextInt(branchCount / 2) + branchCount / 4;
        boolean bl = random.nextInt(6) == 0;
        float f = 0.0F;
        float g = 0.0F;

        for(int j = branchStartIndex; j < branchCount; ++j) {
            double d = 1.5D + (double)(MathHelper.sin(3.1415927F * (float)j / (float)branchCount) * width);
            double e = d * yawPitchRatio;
            float h = MathHelper.cos(pitch);
            x += (MathHelper.cos(yaw) * h);
            y += MathHelper.sin(pitch);
            z += (MathHelper.sin(yaw) * h);
            pitch *= bl ? 0.92F : 0.7F;
            pitch += g * 0.1F;
            yaw += f * 0.1F;
            g *= 0.9F;
            f *= 0.75F;
            g += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
            f += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
            if (j == i && width > 1.0F) {
                this.carveTunnels(context, config, chunk, posToBiome, random.nextLong(), aquiferSampler, x, y, z, horizontalScale, verticalScale, random.nextFloat() * 0.5F + 0.5F, yaw - 0.5707964F, pitch / 2.0F, j, branchCount, 1.0D, carvingMask, skipPredicate);
                this.carveTunnels(context, config, chunk, posToBiome, random.nextLong(), aquiferSampler, x, y, z, horizontalScale, verticalScale, random.nextFloat() * 0.5F + 0.5F, yaw + 0.5707964F, pitch / 2.0F, j, branchCount, 1.0D, carvingMask, skipPredicate);
                return;
            }

            if (random.nextInt(3) != 0) {
                if (!canCarveBranch(chunk.getPos(), x, z, j, branchCount, width)) {
                    return;
                }

                this.carveRegion(context, config, chunk, posToBiome, seed, aquiferSampler, x, y, z, d * horizontalScale, e * verticalScale, carvingMask, skipPredicate);
            }
        }

    }
}
