package net.cr24.primeval.fluid;

import net.cr24.primeval.Primeval;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.List;

public class PrimevalFluids {

    public static final FlowableFluid MOLTEN_COPPER = registerFluid("molten_copper", new StillMoltenMetalFluid.Copper());
    public static final FlowableFluid MOLTEN_TIN = registerFluid("molten_tin", new StillMoltenMetalFluid.Tin());
    public static final FlowableFluid MOLTEN_ZINC = registerFluid("molten_zinc", new StillMoltenMetalFluid.Zinc());

    public static final FlowableFluid MOLTEN_BRONZE = registerFluid("molten_bronze", new StillMoltenMetalFluid.Bronze());
    public static final FlowableFluid MOLTEN_BRASS = registerFluid("molten_brass", new StillMoltenMetalFluid.Brass());
    public static final FlowableFluid MOLTEN_PEWTER = registerFluid("molten_pewter", new StillMoltenMetalFluid.Pewter());
    public static final FlowableFluid MOLTEN_GOLD = registerFluid("molten_gold", new StillMoltenMetalFluid.Gold());
    public static final FlowableFluid MOLTEN_BOTCHED_ALLOY = registerFluid("molten_botched_alloy", new StillMoltenMetalFluid.Botched());


    public static List<Fluid> TOOL_MOLD_FLUIDS = List.of(
            MOLTEN_COPPER,
            MOLTEN_BRONZE
    );
    public static List<Fluid> ALL_MOLD_FLUIDS = List.of(
            MOLTEN_COPPER,
            MOLTEN_TIN,
            MOLTEN_ZINC,
            MOLTEN_BRONZE,
            MOLTEN_BRASS,
            MOLTEN_PEWTER,
            MOLTEN_GOLD,
            MOLTEN_BOTCHED_ALLOY
    );

    public static void init() {}

    @Environment(EnvType.CLIENT)
    public static void initClient() {

        setupFluidRendering(MOLTEN_COPPER, "molten_copper", 0xcbbbcb);

        setupFluidRendering(MOLTEN_TIN, "molten_tin", 0xcbbbcb);

        setupFluidRendering(MOLTEN_ZINC, "molten_zinc", 0xcbbbcb);

        setupFluidRendering(MOLTEN_BRONZE, "molten_bronze", 0xcbbbcb);

        setupFluidRendering(MOLTEN_BRASS, "molten_brass", 0xcbbbcb);

        setupFluidRendering(MOLTEN_PEWTER, "molten_pewter", 0xcbbbcb);

        setupFluidRendering(MOLTEN_GOLD, "molten_gold", 0xcbbbcb);

        setupFluidRendering(MOLTEN_BOTCHED_ALLOY, "molten_botched_alloy", 0xcbbbcb);
    }

    private static FlowableFluid registerFluid(String id, Fluid fluid) {
        return (FlowableFluid)Registry.register(Registries.FLUID, Primeval.identify(id), fluid);
    }

    @Environment(EnvType.CLIENT)
    private static void setupFluidRendering(Fluid still, String textureFluidId, int color) {
        setupFluidRendering(still, still, textureFluidId, color);
    }

    @Environment(EnvType.CLIENT)
    private static void setupFluidRendering(Fluid still, Fluid flowing, String textureFluidId, int color) {

        FluidRenderHandlerRegistry.INSTANCE.register(still, flowing, new SimpleFluidRenderHandler(
                Primeval.identify("block/" + textureFluidId),
                Primeval.identify("block/" + textureFluidId+ "_flow"),
                color
        ));

        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), still);
    }

}
