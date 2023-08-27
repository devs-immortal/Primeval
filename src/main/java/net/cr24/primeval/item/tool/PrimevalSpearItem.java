package net.cr24.primeval.item.tool;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.cr24.primeval.item.Size;
import net.cr24.primeval.item.Weight;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ToolMaterial;

import java.util.UUID;

public class PrimevalSpearItem extends PrimevalSwordItem {
    private static final UUID REACH_MODIFIER_ID = UUID.fromString("E70BE4E9-B163-4CC1-8848-F860B0BC02FC");
    private static final UUID ATTACK_RANGE_MODIFIER_ID = UUID.fromString("7CB7BC58-D3BA-40AE-BC95-F8C38fE144FF");
    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    public PrimevalSpearItem(ToolMaterial toolMaterial, float attackDamage, float attackSpeed, Settings settings, Weight weight, Size size) {
        super(toolMaterial, attackDamage, attackSpeed, settings, weight, size);
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", attackDamage, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", attackSpeed, EntityAttributeModifier.Operation.ADDITION));
        builder.put(ReachEntityAttributes.REACH, new EntityAttributeModifier(REACH_MODIFIER_ID, "Weapon modifier", 1.5, EntityAttributeModifier.Operation.ADDITION));
        builder.put(ReachEntityAttributes.ATTACK_RANGE, new EntityAttributeModifier(ATTACK_RANGE_MODIFIER_ID, "Weapon modifier", 1, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return this.attributeModifiers;
        }
        return super.getAttributeModifiers(slot);
    }
}
