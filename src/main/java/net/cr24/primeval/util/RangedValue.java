package net.cr24.primeval.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.cr24.primeval.Primeval;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.TransferVariant;
import net.fabricmc.fabric.impl.transfer.VariantCodecs;
import net.fabricmc.fabric.impl.transfer.fluid.FluidVariantImpl;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;

public class RangedValue {

    // Codecs
    public static final Codec<RangedValue> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            ExtraCodecs.POSITIVE_FLOAT.fieldOf("max").forGetter(RangedValue::getUpper),
            ExtraCodecs.POSITIVE_FLOAT.fieldOf("min").forGetter(RangedValue::getLower)
    ).apply(instance, RangedValue::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, RangedValue> PACKET_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, RangedValue::getUpper,
            ByteBufCodecs.FLOAT, RangedValue::getLower,
            RangedValue::new);

    private float upperAmount;
    private float lowerAmount;

    public RangedValue(float u, float l) {
        if (l > u) {
            Primeval.LOGGER.error("Invalid Alloy Ratio is being defined!\n  Upper: "+u+"\n  Lower: "+l);
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

    public float getHalfway() {
        return (this.upperAmount + this.lowerAmount) / 2f;
    }

    // Util
    public boolean valueIsWithin(float value) {
        return (value <= upperAmount && value >= lowerAmount);
    }

//    public String toString() {
//        return "RangedFluid of "+this.fluid+" between "+this.upperAmount+" and "+this.lowerAmount;
//    }

    public Component toPercentLabel() {
        int upperPercent = (int) (100 * this.upperAmount);
        int lowerPercent = (int) (100 * this.lowerAmount);
        return Component.translatable(lowerPercent + "-" + upperPercent + "%");
    }
}
