package net.cr24.primeval.screen;

import net.cr24.primeval.Primeval;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;

public class PrimevalScreens {

    public static final ScreenHandlerType<Primeval3x5ContainerScreenHandler> GENERIC_3X5_HANDLER = registerHandler("generic_3x5", Primeval3x5ContainerScreenHandler::create);





    public static void init() {
        HandledScreens.register(GENERIC_3X5_HANDLER, Primeval3x5ContainerScreen::new);
    }

    private static <T extends ScreenHandler> ScreenHandlerType<T> registerHandler(String id, ScreenHandlerType.Factory<T> factory) {
        return Registry.register(Registries.SCREEN_HANDLER, Primeval.identify(id), new ScreenHandlerType(factory, FeatureFlags.VANILLA_FEATURES));
    }


}
