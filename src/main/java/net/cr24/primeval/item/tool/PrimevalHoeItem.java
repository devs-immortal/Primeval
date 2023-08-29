package net.cr24.primeval.item.tool;

import net.cr24.primeval.item.IWeightedItem;
import net.cr24.primeval.item.Size;
import net.cr24.primeval.item.Weight;
import net.minecraft.block.Block;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;

public class PrimevalHoeItem extends ToolItem implements IWeightedItem {

    private final Weight weight;
    private final Size size;

    public static HashMap<Block, Block> hoeables = new HashMap<>();

    public PrimevalHoeItem(ToolMaterial material, Settings settings, Weight weight, Size size) {
        super(material, settings);
        this.weight = weight;
        this.size = size;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();
        Block targetBlock = world.getBlockState(pos).getBlock();
        if (hoeables.containsKey(targetBlock) && world.getBlockState(pos.up()).isAir()) {
            var playerEntity = context.getPlayer();
            world.setBlockState(pos, hoeables.get(targetBlock).getDefaultState());
            world.playSound(playerEntity, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0f, 1.0f);
            if (playerEntity != null) {
                context.getStack().damage(1, playerEntity, (p) -> {
                    p.sendToolBreakStatus(context.getHand());
                });
            }
            return ActionResult.SUCCESS;
        } else {
            return super.useOnBlock(context);
        }
    }

    @Override
    public Weight getWeight() {
        return weight;
    }

    @Override
    public Size getSize() {
        return size;
    }
}
