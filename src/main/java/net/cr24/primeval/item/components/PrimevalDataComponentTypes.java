package net.cr24.primeval.item.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.cr24.primeval.PrimevalMain;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.impl.transfer.VariantCodecs;
import net.minecraft.component.ComponentType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.dynamic.Codecs;

import java.util.function.UnaryOperator;

public class PrimevalDataComponentTypes {

    // Registered Components

    public static final ComponentType<FluidContentComponent> FLUID_CONTENTS = register("fluid_contents", (builder) -> builder.codec(FluidContentComponent.CODEC).packetCodec(FluidContentComponent.PACKET_CODEC).cache());


    // Util

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, PrimevalMain.getId(id), builderOperator.apply(ComponentType.builder()).build());
    }

    public static void init() {
    }

    // Component for storing a fluid
    public record FluidContentComponent(FluidVariant fluid, int amount) {

        public static final Codec<FluidContentComponent> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                FluidVariant.CODEC.fieldOf("fluid").forGetter(FluidContentComponent::fluid),
                Codecs.POSITIVE_INT.fieldOf("amount").forGetter(FluidContentComponent::amount)
        ).apply(instance, FluidContentComponent::new));

        public static final PacketCodec<RegistryByteBuf, FluidContentComponent> PACKET_CODEC = PacketCodec.tuple(
                FluidVariant.PACKET_CODEC, FluidContentComponent::fluid,
                PacketCodecs.INTEGER, FluidContentComponent::amount,
                FluidContentComponent::new
        );

        public FluidVariant fluid() {
            return this.fluid;
        }

        public int amount() {
            return this.amount;
        }

    }

}
