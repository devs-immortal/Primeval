package net.cr24.primeval.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.cr24.primeval.Primeval;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.material.Fluid;
import java.util.function.UnaryOperator;

public class PrimevalDataComponentTypes {

    // Registered Components

    public static final DataComponentType<FluidContentComponent> FLUID_CONTENTS = register("fluid_contents", (builder) -> builder.persistent(FluidContentComponent.CODEC).networkSynchronized(FluidContentComponent.PACKET_CODEC).cacheEncoding());


    // Util

    private static <T> DataComponentType<T> register(String id, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Primeval.identify(id), builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void init() {
    }

    // Component for storing a fluid
    public record FluidContentComponent(Holder<Fluid> fluid, int amount) {

        public static final Codec<FluidContentComponent> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                BuiltInRegistries.FLUID.holderByNameCodec().fieldOf("fluid").forGetter(FluidContentComponent::fluid),
                ExtraCodecs.POSITIVE_INT.fieldOf("amount").forGetter(FluidContentComponent::amount)
        ).apply(instance, FluidContentComponent::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, FluidContentComponent> PACKET_CODEC = StreamCodec.composite(
                ByteBufCodecs.holderRegistry(Registries.FLUID), FluidContentComponent::fluid,
                ByteBufCodecs.INT, FluidContentComponent::amount,
                FluidContentComponent::new
        );

        public Holder<Fluid> fluid() {
            return this.fluid;
        }

        public int amount() {
            return this.amount;
        }

        public static FluidContentComponent fromPair(Tuple<Holder<Fluid>, Integer> pair) {
            return new FluidContentComponent(pair.getA(), pair.getB());
        }
    }
}
