package net.cr24.primeval.recipe.rei;

import com.google.common.collect.Lists;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.client.categories.DefaultWaxingCategory;
import net.cr24.primeval.item.PrimevalItems;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.ArrayList;
import java.util.List;

public class PitKilnFiringDisplayCategory implements DisplayCategory<PitKilnFiringDisplay> {
    @Override
    public Renderer getIcon() {
        return EntryStacks.of(PrimevalItems.STRAW);
    }

    @Override
    public Text getTitle() {
        return new TranslatableText("category.primeval.pit_kiln_firing");
    }

    @Override
    public CategoryIdentifier getCategoryIdentifier() {
        return PrimevalREIIntegration.PIT_KILN_FIRING;
    }

    @Override
    public List<Widget> setupDisplay(PitKilnFiringDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 41, bounds.getCenterY() - 13);
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createArrow(new Point(startPoint.x + 27, startPoint.y + 4)));
        widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 61, startPoint.y + 5)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 4, startPoint.y + 5)).entries(display.getIn()).markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 61, startPoint.y + 5)).entries(display.getOut()).disableBackground().markOutput());
        return widgets;
    }

    public int getDisplayHeight() {
        return 36;
    }
}
