package net.cr24.primeval.screen;

import net.cr24.primeval.Primeval;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class PrimevalScreens {

    public static final MenuType<Primeval3x5ContainerScreenHandler> GENERIC_3X5_HANDLER = registerHandler("generic_3x5", Primeval3x5ContainerScreenHandler::create);

    public static void init() {
    }

    public static void initClient() {
        MenuScreens.register(GENERIC_3X5_HANDLER, Primeval3x5ContainerScreen::new);
    }

    private static <T extends AbstractContainerMenu> MenuType<T> registerHandler(String id, MenuType.MenuSupplier<T> factory) {
        return Registry.register(BuiltInRegistries.MENU, Primeval.identify(id), new MenuType<>(factory, FeatureFlags.VANILLA_SET));
    }


}
