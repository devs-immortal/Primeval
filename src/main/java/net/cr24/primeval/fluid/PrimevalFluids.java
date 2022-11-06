package net.cr24.primeval.fluid;

import net.cr24.primeval.PrimevalMain;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockRenderView;

import java.util.function.Function;

public class PrimevalFluids {

    public static final FlowableFluid MOLTEN_COPPER = registerFluid("molten_copper", new StillMoltenMetalFluid.Copper());
    public static final FlowableFluid MOLTEN_TIN = registerFluid("molten_tin", new StillMoltenMetalFluid.Tin());
    public static final FlowableFluid MOLTEN_ZINC = registerFluid("molten_zinc", new StillMoltenMetalFluid.Zinc());

    public static final FlowableFluid MOLTEN_BRONZE = registerFluid("molten_bronze", new StillMoltenMetalFluid.Bronze());
    public static final FlowableFluid MOLTEN_BRASS = registerFluid("molten_brass", new StillMoltenMetalFluid.Brass());
    public static final FlowableFluid MOLTEN_PEWTER = registerFluid("molten_pewter", new StillMoltenMetalFluid.Pewter());
    public static final FlowableFluid MOLTEN_GOLD = registerFluid("molten_gold", new StillMoltenMetalFluid.Gold());
    public static final FlowableFluid MOLTEN_BOTCHED_ALLOY = registerFluid("molten_botched_alloy", new StillMoltenMetalFluid.Botched());


    public static void init() {}

    @Environment(EnvType.CLIENT)
    public static void clientInit() {
        setupFluidRendering(MOLTEN_COPPER, null, PrimevalMain.getId("molten_copper"), 0xcbbbcb);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), MOLTEN_COPPER);

        setupFluidRendering(MOLTEN_TIN, null, PrimevalMain.getId("molten_tin"), 0xcbbbcb);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), MOLTEN_TIN);

        setupFluidRendering(MOLTEN_ZINC, null, PrimevalMain.getId("molten_zinc"), 0xcbbbcb);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), MOLTEN_ZINC);

        setupFluidRendering(MOLTEN_BRONZE, null, PrimevalMain.getId("molten_bronze"), 0xcbbbcb);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), MOLTEN_BRONZE);

        setupFluidRendering(MOLTEN_BRASS, null, PrimevalMain.getId("molten_brass"), 0xcbbbcb);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), MOLTEN_BRASS);

        setupFluidRendering(MOLTEN_PEWTER, null, PrimevalMain.getId("molten_pewter"), 0xcbbbcb);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), MOLTEN_PEWTER);

        setupFluidRendering(MOLTEN_GOLD, null, PrimevalMain.getId("molten_gold"), 0xcbbbcb);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), MOLTEN_GOLD);

        setupFluidRendering(MOLTEN_BOTCHED_ALLOY, null, PrimevalMain.getId("molten_botched_alloy"), 0xcbbbcb);
        BlockRenderLayerMap.INSTANCE.putFluids(RenderLayer.getTranslucent(), MOLTEN_BOTCHED_ALLOY);
    }

    private static FlowableFluid registerFluid(String id, Fluid fluid) {
        return (FlowableFluid)Registry.register(Registry.FLUID, PrimevalMain.getId(id), fluid);
    }

    /*
     * Fluid rendering setup from Spectrum,
     * by DaFaqs, credit to him for this method
     */
    @Environment(EnvType.CLIENT)
    private static void setupFluidRendering(final Fluid still, final Fluid flowing, final Identifier textureFluidId, final int color) {
        final Identifier stillSpriteId = new Identifier(textureFluidId.getNamespace(), "block/" + textureFluidId.getPath());
        final Identifier flowingSpriteId = new Identifier(textureFluidId.getNamespace(), "block/" + textureFluidId.getPath() + "_flow");

        // If they're not already present, add the sprites to the block atlas
        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register((atlasTexture, registry) -> {
            registry.register(stillSpriteId);
            registry.register(flowingSpriteId);
        });

        final Identifier fluidId = Registry.FLUID.getId(still);
        final Identifier listenerId = new Identifier(fluidId.getNamespace(), fluidId.getPath() + "_reload_listener");
        final Sprite[] fluidSprites = { null, null };

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {

            /**
             * Get the sprites from the block atlas when resources are reloaded
             */
            @Override
            public void reload(ResourceManager manager) {
                final Function<Identifier, Sprite> atlas = MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE);
                fluidSprites[0] = atlas.apply(stillSpriteId);
                fluidSprites[1] = atlas.apply(flowingSpriteId);
            }

            @Override
            public Identifier getFabricId() {
                return listenerId;
            }
        });

        // The FluidRenderer gets the sprites and color from a FluidRenderHandler during rendering
        final FluidRenderHandler renderHandler = new FluidRenderHandler() {
            @Override
            public Sprite[] getFluidSprites(BlockRenderView view, BlockPos pos, FluidState state) {
                return fluidSprites;
            }

            @Override
            public int getFluidColor(BlockRenderView view, BlockPos pos, FluidState state) {
                return color;
            }
        };

        FluidRenderHandlerRegistry.INSTANCE.register(still, renderHandler);
        //FluidRenderHandlerRegistry.INSTANCE.register(flowing, renderHandler); // Temp removed because of flowing shenanigans
    }

}
