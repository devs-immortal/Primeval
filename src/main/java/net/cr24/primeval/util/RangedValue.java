package net.cr24.primeval.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.TransferVariant;
import net.fabricmc.fabric.impl.transfer.VariantCodecs;
import net.fabricmc.fabric.impl.transfer.fluid.FluidVariantImpl;
import net.minecraft.component.ComponentChanges;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.dynamic.Codecs;

public class RangedValue {

    // Codecs
    public static final Codec<RangedValue> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codecs.POSITIVE_FLOAT.fieldOf("max").forGetter(RangedValue::getUpper),
            Codecs.POSITIVE_FLOAT.fieldOf("min").forGetter(RangedValue::getLower)
    ).apply(instance, RangedValue::new));

    public static final PacketCodec<RegistryByteBuf, RangedValue> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.FLOAT, RangedValue::getUpper,
            PacketCodecs.FLOAT, RangedValue::getLower,
            RangedValue::new);

    private FluidVariant fluid;
    private float upperAmount;
    private float lowerAmount;

    public RangedValue(float u, float l) {
        if (l > u) {
            System.out.println("Invalid Alloy Ratio is being defined!\n  Upper: "+u+"\n  Lower: "+l);
        }
        this.upperAmount = u;
        this.lowerAmount = l;
    }

    // Accessors
    public float getUpper() {
        return this.upperAmount;
    }

    public float getLower() {
        return this.lowerAmount;
    }

    // Util
    public boolean valueIsWithin(double value) {
        return (value <= upperAmount && value >= lowerAmount);
    }

    public String toString() {
        return "RangedFluid of "+this.fluid+" between "+this.upperAmount+" and "+this.lowerAmount;
    }

    public Text toPercentLabel() {
        int upperPercent = (int) (100 * this.upperAmount);
        int lowerPercent = (int) (100 * this.lowerAmount);
        return Text.translatable("" + upperPercent + "-" + lowerPercent + "%");
    }
}
