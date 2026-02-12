package net.cr24.primeval.item.tool;

import net.cr24.primeval.item.IWeightedItem;
import net.cr24.primeval.util.Size;
import net.cr24.primeval.util.Weight;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import java.util.HashMap;
import java.util.function.Consumer;

public class PrimevalHoeItem extends Item implements IWeightedItem {

    private final Weight weight;
    private final Size size;

    public static HashMap<Block, Block> hoeables = new HashMap<>();

    public PrimevalHoeItem(ToolMaterial material, float attackDamage, float attackSpeed, Weight weight, Size size, net.minecraft.world.item.Item.Properties settings) {
        super(settings.tool(material, BlockTags.MINEABLE_WITH_HOE, attackDamage, attackSpeed, 0.0F));
        this.weight = weight;
        this.size = size;
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos pos = context.getClickedPos();
        Level world = context.getLevel();
        Block targetBlock = world.getBlockState(pos).getBlock();
        if (hoeables.containsKey(targetBlock) && world.getBlockState(pos.above()).isAir()) {
            var playerEntity = context.getPlayer();
            world.setBlockAndUpdate(pos, hoeables.get(targetBlock).defaultBlockState());
            world.playSound(playerEntity, pos, SoundEvents.HOE_TILL, SoundSource.BLOCKS, 1.0f, 1.0f);
            if (!world.isClientSide()) {
                if (playerEntity != null) {
                    context.getItemInHand().hurtAndBreak(1, playerEntity, context.getHand());
                }
            }
            return InteractionResult.SUCCESS;
        } else {
            return super.useOn(context);
        }
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay displayComponent, Consumer<Component> textConsumer, TooltipFlag type) {
        super.appendHoverText(stack, context, displayComponent, textConsumer, type);
        textConsumer.accept((Component.translatable("⚖ ").append(this.weight.getText()).append(" ⤧ ").append(this.size.getText())).withStyle(ChatFormatting.GRAY));
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
