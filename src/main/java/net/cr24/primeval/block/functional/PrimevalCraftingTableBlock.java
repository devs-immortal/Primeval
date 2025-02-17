package net.cr24.primeval.block.functional;

import net.cr24.primeval.screen.PrimevalCraftingScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PrimevalCraftingTableBlock extends CraftingTableBlock {

    public PrimevalCraftingTableBlock(Settings settings) {
        super(settings);
    }

    protected NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> new PrimevalCraftingScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, pos)), Text.translatable("container.crafting"));
    }

}
