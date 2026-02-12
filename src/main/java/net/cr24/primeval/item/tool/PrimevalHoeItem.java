package net.cr24.primeval.item.tool;

import net.cr24.primeval.item.IWeightedItem;
import net.cr24.primeval.util.Size;
import net.cr24.primeval.util.Weight;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.function.Consumer;

public class PrimevalHoeItem extends Item implements IWeightedItem {

    private final Weight weight;
    private final Size size;

    public static HashMap<Block, Block> hoeables = new HashMap<>();

    public PrimevalHoeItem(ToolMaterial material, float attackDamage, float attackSpeed, Weight weight, Size size, net.minecraft.item.Item.Settings settings) {
        super(settings.tool(material, BlockTags.HOE_MINEABLE, attackDamage, attackSpeed, 0.0F));
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
            if (!world.isClient()) {
                if (playerEntity != null) {
                    context.getStack().damage(1, playerEntity, context.getHand());
                }
            }
            return ActionResult.SUCCESS;
        } else {
            return super.useOnBlock(context);
        }
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
        textConsumer.accept((Text.translatable("⚖ ").append(this.weight.getText()).append(" ⤧ ").append(this.size.getText())).formatted(Formatting.GRAY));
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
