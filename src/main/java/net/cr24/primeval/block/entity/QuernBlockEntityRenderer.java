package net.cr24.primeval.block.entity;

import net.cr24.primeval.block.functional.QuernBlock;
import net.cr24.primeval.initialization.PrimevalBlocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.state.BlockEntityRenderState;
import net.minecraft.client.render.command.ModelCommandRenderer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.state.CameraRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

public class QuernBlockEntityRenderer implements BlockEntityRenderer<QuernBlockEntity, QuernBlockEntityRenderer.QuernBlockEntityRenderState> {

    private final ItemModelManager itemModelManager;

    public QuernBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.itemModelManager = ctx.itemModelManager();
    }

    @Override
    public void render(QuernBlockEntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        // how far the item being querned has sunk into the quern
        if (state.itemState == null) return;
        matrices.translate(0.5, 0.0, 0.5);
        matrices.push();
        double sinkProgress = 0.9-(state.currentAngle / 360D)*0.2;
        matrices.translate(0.0, sinkProgress, 0.0);
        matrices.scale(0.5f, 0.5f, 0.5f);
        state.itemState.render(matrices, queue, state.lightmapCoordinates, OverlayTexture.DEFAULT_UV, 0);
        matrices.translate(0, -(sinkProgress + 0.6), 0);
        matrices.pop();
        // render quern wheel if present, at current angle from BE
        if (!state.hasWheel) return;
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(state.currentAngle));
        var renderManager = MinecraftClient.getInstance().getBlockRenderManager();
        var wheelState = PrimevalBlocks.QUERN.getDefaultState().with(QuernBlock.WHEELED, true);
        queue.submitBlock(matrices, wheelState, state.lightmapCoordinates, state.crumblingOverlay == null ? OverlayTexture.DEFAULT_UV : state.crumblingOverlay.progress(), 0);
        //renderManager.renderBlock(wheelState, entity.getPos(), entity.getWorld(), matrices, vertexConsumers.getBuffer(RenderLayer.getCutout()), false, renderManager.getModel(wheelState).getParts(Random.create()));
    }

    public void updateRenderState(QuernBlockEntity blockEntity, QuernBlockEntityRenderState renderState, float f, Vec3d vec3d, @Nullable ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlayCommand) {
        BlockEntityRenderState.updateBlockEntityRenderState(blockEntity, renderState, crumblingOverlayCommand);
        ItemRenderState itemRenderState = new ItemRenderState();
        this.itemModelManager.clearAndUpdate(itemRenderState, blockEntity.inputItem, ItemDisplayContext.FIXED, blockEntity.getWorld(), null, 0);
        renderState.itemState = itemRenderState;
        renderState.currentAngle = blockEntity.currentAngle;
        renderState.hasWheel = blockEntity.wheelDamage != -1;
    }

    @Override
    public QuernBlockEntityRenderState createRenderState() {
        return new QuernBlockEntityRenderState();
    }

    public static class QuernBlockEntityRenderState extends BlockEntityRenderState {
        public ItemRenderState itemState;
        public float currentAngle = 0;
        public boolean hasWheel = false;

        public QuernBlockEntityRenderState() {
        }
    }
}
