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
import net.cr24.primeval.PrimevalMain;
import net.cr24.primeval.item.PrimevalItems;
import net.minecraft.text.Text;

import java.util.List;

public class QuernDisplayCategory implements DisplayCategory<QuernDisplay> {

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(PrimevalItems.QUERN_WHEEL);
    }

    @Override
    public Text getTitle() {
        return Text.translatable("category.primeval.quern");
    }

    @Override
    public CategoryIdentifier<? extends QuernDisplay> getCategoryIdentifier() {
        return PrimevalREIIntegration.QUERN;
    }

    @Override
    public List<Widget> setupDisplay(QuernDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 41, bounds.getCenterY() - 13);
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createTexturedWidget(PrimevalMain.getId("textures/gui/quern_wheel_widget.png"), startPoint.x + 13, startPoint.y + 4, 0, 0, 51, 17, 51, 17));
        widgets.add(Widgets.createLabel(new Point(startPoint.x+40, startPoint.y+19), Text.of(display.getWheelDamage()+"*")));
        widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 75, startPoint.y + 5)));
        widgets.add(Widgets.createSlot(new Point(startPoint.x - 10, startPoint.y + 5)).entries(display.getIn()).markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 75, startPoint.y + 5)).entries(display.getOut()).disableBackground().markOutput());
        return widgets;
    }

    public int getDisplayHeight() {
        return 49;
    }
}
