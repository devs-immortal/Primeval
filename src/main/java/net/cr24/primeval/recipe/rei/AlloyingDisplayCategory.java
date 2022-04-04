package net.cr24.primeval.recipe.rei;

import com.google.common.collect.Lists;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.common.displays.cooking.DefaultSmeltingDisplay;
import net.cr24.primeval.item.PrimevalItems;
import net.cr24.primeval.util.RangedValue;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.HashMap;
import java.util.List;

public class AlloyingDisplayCategory implements DisplayCategory<AlloyingDisplay> {
    @Override
    public Renderer getIcon() {
        return EntryStacks.of(PrimevalItems.FIRED_CLAY_VESSEL);
    }

    @Override
    public Text getTitle() {
        return new TranslatableText("category.primeval.alloying");
    }

    @Override
    public CategoryIdentifier<? extends AlloyingDisplay> getCategoryIdentifier() {
        return PrimevalREIIntegration.ALLOYING;
    }

    @Override
    public List<Widget> setupDisplay(AlloyingDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 41, bounds.getCenterY() - 8);

        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));

        HashMap<EntryIngredient, RangedValue> fluids = display.getFluidRatios();
        int xOffset = 0;
        for (EntryIngredient ingredient : fluids.keySet()) {
            RangedValue range = fluids.get(ingredient);
            widgets.add(Widgets.createLabel(new Point(startPoint.x - 8 + xOffset, startPoint.y - 5), range.toPercentLabel()).noShadow().color(0xFF404040, 0xFFBBBBBB));
            widgets.add(Widgets.createSlot(new Point(startPoint.x - 16 + xOffset, startPoint.y + 5)).entries(ingredient).markInput());
            xOffset += 41;
        }

        widgets.add(Widgets.createArrow(new Point(startPoint.x + 55, startPoint.y - 1)));
        widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 86, startPoint.y)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 86, startPoint.y)).entries(display.getOut()).disableBackground().markOutput());
        return widgets;
    }

    public int getDisplayHeight() {
        return 42;
    }
}
