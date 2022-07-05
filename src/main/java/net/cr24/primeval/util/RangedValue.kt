package net.cr24.primeval.util

import net.minecraft.text.Text

class RangedValue(var upper: Double, var lower: Double) {
    init {
        if (lower > upper) {
            println("Invalid Alloy Ratio is being defined!\n  Upper: $upper\n  Lower: $lower")
        }
    }

    fun valueIsWithin(value: Double): Boolean {
        return value in lower..upper
    }

    override fun toString(): String {
        return "RangedValue between $upper and $lower"
    }

    fun toPercentLabel(): Text? {
        val upperPercent = (100 * upper).toInt()
        val lowerPercent = (100 * lower).toInt()
        return Text.translatable("$upperPercent-$lowerPercent%")
    }
}