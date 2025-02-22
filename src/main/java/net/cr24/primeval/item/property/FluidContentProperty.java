package net.cr24.primeval.item.property;

import com.mojang.serialization.MapCodec;
import net.cr24.primeval.item.MoldItem;
import net.cr24.primeval.util.PrimevalDataComponentTypes;
import net.minecraft.client.render.item.property.select.SelectProperty;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import org.jetbrains.annotations.Nullable;

public record FluidContentProperty() implements SelectProperty<RegistryKey<Fluid>> {

    public static final SelectProperty.Type<FluidContentProperty, RegistryKey<Fluid>> TYPE = Type.create(MapCodec.unit(new FluidContentProperty()), RegistryKey.createCodec(RegistryKeys.FLUID));

    @Nullable
    @Override
    public RegistryKey<Fluid> getValue(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity user, int seed, ModelTransformationMode modelTransformationMode) {
        if (stack.contains(PrimevalDataComponentTypes.FLUID_CONTENTS) && stack.getItem() instanceof MoldItem) {
            var component = stack.get(PrimevalDataComponentTypes.FLUID_CONTENTS);
            if (component.amount() == ((MoldItem) stack.getItem()).getCapacity())
                return component.fluid().getKey().get();
        }
        return null;
    }

    @Override
    public Type<? extends SelectProperty<RegistryKey<Fluid>>, RegistryKey<Fluid>> getType() {
        return TYPE;
    }
}
