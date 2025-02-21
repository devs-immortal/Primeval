package net.cr24.primeval.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.cr24.primeval.Primeval;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.component.ComponentType;
import net.minecraft.fluid.Fluid;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Pair;
import net.minecraft.util.dynamic.Codecs;

import java.util.function.UnaryOperator;

public class PrimevalDataComponentTypes {

    // Registered Components

    public static final ComponentType<FluidContentComponent> FLUID_CONTENTS = register("fluid_contents", (builder) -> builder.codec(FluidContentComponent.CODEC).packetCodec(FluidContentComponent.PACKET_CODEC).cache());


    // Util

    private static <T> ComponentType<T> register(String id, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Primeval.identify(id), builderOperator.apply(ComponentType.builder()).build());
    }

    public static void init() {
    }

    // Component for storing a fluid
    public record FluidContentComponent(RegistryEntry<Fluid> fluid, int amount) {

        public static final Codec<FluidContentComponent> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
                Registries.FLUID.getEntryCodec().fieldOf("fluid").forGetter(FluidContentComponent::fluid),
                Codecs.POSITIVE_INT.fieldOf("amount").forGetter(FluidContentComponent::amount)
        ).apply(instance, FluidContentComponent::new));

        public static final PacketCodec<RegistryByteBuf, FluidContentComponent> PACKET_CODEC = PacketCodec.tuple(
                PacketCodecs.registryEntry(RegistryKeys.FLUID), FluidContentComponent::fluid,
                PacketCodecs.INTEGER, FluidContentComponent::amount,
                FluidContentComponent::new
        );

        public RegistryEntry<Fluid> fluid() {
            return this.fluid;
        }

        public int amount() {
            return this.amount;
        }

        public static FluidContentComponent fromPair(Pair<RegistryEntry<Fluid>, Integer> pair) {
            return new FluidContentComponent(pair.getLeft(), pair.getRight());
        }
    }
}
