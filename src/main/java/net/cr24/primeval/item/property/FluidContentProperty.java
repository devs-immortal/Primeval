package net.cr24.primeval.item.property;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.cr24.primeval.item.MoldItem;
import net.cr24.primeval.util.PrimevalDataComponentTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;

public record FluidContentProperty() implements SelectItemModelProperty<ResourceKey<Fluid>> {

    public static final Codec<ResourceKey<Fluid>> CODEC = ResourceKey.codec(Registries.FLUID);
    public static final SelectItemModelProperty.Type<FluidContentProperty, ResourceKey<Fluid>> TYPE = Type.create(MapCodec.unit(new FluidContentProperty()), ResourceKey.codec(Registries.FLUID));

    @Nullable
    @Override
    public ResourceKey<Fluid> get(ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity user, int seed, ItemDisplayContext displayContext) {
        if (stack.has(PrimevalDataComponentTypes.FLUID_CONTENTS) && stack.getItem() instanceof MoldItem) {
            var component = stack.get(PrimevalDataComponentTypes.FLUID_CONTENTS);
            if (component.amount() == ((MoldItem) stack.getItem()).getCapacity())
                return component.fluid().unwrapKey().get();
        }
        return null;
    }

    @Override
    public Codec<ResourceKey<Fluid>> valueCodec() {
        return CODEC;
    }

    @Override
    public Type<? extends SelectItemModelProperty<ResourceKey<Fluid>>, ResourceKey<Fluid>> type() {
        return TYPE;
    }
}
