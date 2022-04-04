package net.cr24.primeval.util;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class RangedValue {
    private double upper;
    private double lower;

    public RangedValue(double u, double l) {
        if (l > u) {
            System.out.println("Invalid Alloy Ratio is being defined!\n  Upper: "+u+"\n  Lower: "+l);
        }
        this.upper = u;
        this.lower = l;
    }

    public double getUpper() {
        return this.upper;
    }

    public double getLower() {
        return this.lower;
    }

    public boolean valueIsWithin(double value) {
        return (value <= upper && value >= lower);
    }

    public String toString() {
        return "RangedValue between "+this.upper+" and "+this.lower;
    }

    public TranslatableText toPercentLabel() {
        int upperPercent = (int) (100 * this.upper);
        int lowerPercent = (int) (100 * this.lower);
        return new TranslatableText("" + upperPercent + "-" + lowerPercent + "%");
    }
}
