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
import net.cr24.primeval.block.PrimevalBlocks;
import net.minecraft.text.Text;
import java.util.List;

public class OpenFireDisplayCategory implements DisplayCategory<OpenFireDisplay> {

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(PrimevalBlocks.CAMPFIRE);
    }

    @Override
    public Text getTitle() {
        return Text.translatable("category.primeval.open_fire");
    }

    @Override
    public CategoryIdentifier<? extends OpenFireDisplay> getCategoryIdentifier() {
        return PrimevalREIIntegration.OPEN_FIRE;
    }

    @Override
    public List<Widget> setupDisplay(OpenFireDisplay display, Rectangle bounds) {
        Point startPoint = new Point(bounds.getCenterX() - 41, bounds.y + 10);
        List<Widget> widgets = Lists.newArrayList();
        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 61, startPoint.y + 9)));
        widgets.add(Widgets.createBurningFire(new Point(startPoint.x + 1, startPoint.y + 20)).animationDurationMS(4000.0D));
        widgets.add(Widgets.createArrow(new Point(startPoint.x + 24, startPoint.y + 8)).animationDurationMS(4000.0D));
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 1, startPoint.y + 1)).entries(display.getIn()).markInput());
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 61, startPoint.y + 9)).entries(display.getOut()).disableBackground().markOutput());
        return widgets;
    }

    public int getDisplayHeight() {
        return 49;
    }
}
