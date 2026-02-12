package net.cr24.primeval.block.functional;

import net.cr24.primeval.screen.PrimevalCraftingScreenHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.CraftingTableBlock;
import net.minecraft.world.level.block.state.BlockState;

public class PrimevalCraftingTableBlock extends CraftingTableBlock {

    public PrimevalCraftingTableBlock(Properties settings) {
        super(settings);
    }

    protected MenuProvider getMenuProvider(BlockState state, Level world, BlockPos pos) {
        return new SimpleMenuProvider((syncId, inventory, player) -> new PrimevalCraftingScreenHandler(syncId, inventory, ContainerLevelAccess.create(world, pos)), Component.translatable("container.crafting"));
    }

}
