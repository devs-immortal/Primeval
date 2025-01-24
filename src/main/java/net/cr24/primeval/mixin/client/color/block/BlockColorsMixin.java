package net.cr24.primeval.mixin.client.color.block;

import net.cr24.primeval.block.PrimevalBlocks;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.world.biome.FoliageColors;
import net.minecraft.world.biome.GrassColors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockColors.class)
public class BlockColorsMixin {

    @Inject(method = "create", at = @At("RETURN"), cancellable = true)
    private static void create(CallbackInfoReturnable<BlockColors> info) {
        BlockColors origin = info.getReturnValue();
        origin.registerColorProvider(
                ((state, world, pos, tintIndex) ->  world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getColor(0.5D, 1.0D)),
                PrimevalBlocks.GRASSY_DIRT
        );
        origin.registerColorProvider(
                ((state, world, pos, tintIndex) ->  world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getColor(0.5D, 1.0D)),
                PrimevalBlocks.GRASSY_CLAY
        );
        origin.registerColorProvider(
                ((state, world, pos, tintIndex) ->  world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getColor(0.5D, 1.0D)),
                PrimevalBlocks.GRASS
        );
        origin.registerColorProvider(
                ((state, world, pos, tintIndex) ->  world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getColor(0.5D, 1.0D)),
                PrimevalBlocks.BUSH
        );
        origin.registerColorProvider(
                ((state, world, pos, tintIndex) ->  world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getColor(0.5D, 1.0D)),
                PrimevalBlocks.SPIKED_PLANT
        );
        origin.registerColorProvider(
                ((state, world, pos, tintIndex) ->  world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getColor(0.5D, 1.0D)),
                PrimevalBlocks.LEAFY_PLANT
        );
        origin.registerColorProvider(
                ((state, world, pos, tintIndex) ->  world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getColor(0.5D, 1.0D)),
                PrimevalBlocks.SHRUB
        );
        origin.registerColorProvider(
                ((state, world, pos, tintIndex) ->  world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : FoliageColors.getColor(0.5D, 1.0D)),
                PrimevalBlocks.OAK_LEAVES
        );
        origin.registerColorProvider(
                ((state, world, pos, tintIndex) ->  world != null && pos != null ? BiomeColors.getFoliageColor(world, pos)+2621440 : FoliageColors.getColor(0.5D, 1.0D)),
                PrimevalBlocks.BIRCH_LEAVES
        );
        origin.registerColorProvider(
                ((state, world, pos, tintIndex) ->  world != null && pos != null ? BiomeColors.getFoliageColor(world, pos)-4082973 : FoliageColors.getColor(0.5D, 1.0D)),
                PrimevalBlocks.SPRUCE_LEAVES
        );
        info.setReturnValue(origin);
    }
}