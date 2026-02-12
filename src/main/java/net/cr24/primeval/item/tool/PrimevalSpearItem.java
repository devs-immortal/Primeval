package net.cr24.primeval.item.tool;

import net.cr24.primeval.item.IWeightedItem;
import net.cr24.primeval.util.Size;
import net.cr24.primeval.util.Weight;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import java.util.List;
import java.util.function.Consumer;

public class PrimevalSpearItem extends Item implements IWeightedItem {

    private final Weight weight;
    private final Size size;

    public PrimevalSpearItem(ToolMaterial toolMaterial, float attackDamage, float attackSpeed, Weight weight, Size size, net.minecraft.world.item.Item.Properties settings) {
        super(applySettings(toolMaterial, attackDamage, attackSpeed, settings));
        this.weight = weight;
        this.size = size;
    }

    public boolean canMine(BlockState state, Level world, BlockPos pos, Player miner) {
        return !miner.isCreative();
    }

    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
    }

    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.hurtAndBreak(1, attacker, EquipmentSlot.MAINHAND);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay displayComponent, Consumer<Component> textConsumer, TooltipFlag type) {
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

    private static net.minecraft.world.item.Item.Properties applySettings(ToolMaterial toolMaterial, float attackDamage, float attackSpeed, net.minecraft.world.item.Item.Properties settings) {
        return settings.durability(toolMaterial.durability())
                .repairable(toolMaterial.repairItems())
                .enchantable(toolMaterial.enchantmentValue())
                .attributes(ItemAttributeModifiers.builder()
                        .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, attackDamage, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                        .add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                        .add(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(Identifier.withDefaultNamespace("block_interaction_range"), 1.5, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                        .add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(Identifier.withDefaultNamespace("entity_interaction_range"), 1, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                        .build());

    }
}
