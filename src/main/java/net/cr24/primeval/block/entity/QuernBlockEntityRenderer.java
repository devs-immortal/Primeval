package net.cr24.primeval.block.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.cr24.primeval.block.functional.QuernBlock;
import net.cr24.primeval.initialization.PrimevalBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class QuernBlockEntityRenderer implements BlockEntityRenderer<QuernBlockEntity, QuernBlockEntityRenderer.QuernBlockEntityRenderState> {

    private final ItemModelResolver itemModelManager;

    public QuernBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        this.itemModelManager = ctx.itemModelResolver();
    }

    @Override
    public void submit(QuernBlockEntityRenderState state, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraState) {
        // how far the item being querned has sunk into the quern
        if (state.itemState == null) return;
        matrices.translate(0.5, 0.0, 0.5);
        matrices.pushPose();
        double sinkProgress = 0.9-(state.currentAngle / 360D)*0.2;
        matrices.translate(0.0, sinkProgress, 0.0);
        matrices.scale(0.5f, 0.5f, 0.5f);
        state.itemState.submit(matrices, queue, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        matrices.translate(0, -(sinkProgress + 0.6), 0);
        matrices.popPose();
        // render quern wheel if present, at current angle from BE
        if (!state.hasWheel) return;
        matrices.mulPose(Axis.YP.rotationDegrees(state.currentAngle));
        var renderManager = Minecraft.getInstance().getBlockRenderer();
        var wheelState = PrimevalBlocks.QUERN.defaultBlockState().setValue(QuernBlock.WHEELED, true);
        queue.submitBlock(matrices, wheelState, state.lightCoords, state.breakProgress == null ? OverlayTexture.NO_OVERLAY : state.breakProgress.progress(), 0);
        //renderManager.renderBlock(wheelState, entity.getPos(), entity.getWorld(), matrices, vertexConsumers.getBuffer(RenderLayer.getCutout()), false, renderManager.getModel(wheelState).getParts(Random.create()));
    }

    public void updateRenderState(QuernBlockEntity blockEntity, QuernBlockEntityRenderState renderState, float f, Vec3 vec3d, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlayCommand) {
        BlockEntityRenderState.extractBase(blockEntity, renderState, crumblingOverlayCommand);
        ItemStackRenderState itemRenderState = new ItemStackRenderState();
        this.itemModelManager.updateForTopItem(itemRenderState, blockEntity.inputItem, ItemDisplayContext.FIXED, blockEntity.getLevel(), null, 0);
        renderState.itemState = itemRenderState;
        renderState.currentAngle = blockEntity.currentAngle;
        renderState.hasWheel = blockEntity.wheelDamage != -1;
    }

    @Override
    public QuernBlockEntityRenderState createRenderState() {
        return new QuernBlockEntityRenderState();
    }

    public static class QuernBlockEntityRenderState extends BlockEntityRenderState {
        public ItemStackRenderState itemState;
        public float currentAngle = 0;
        public boolean hasWheel = false;

        public QuernBlockEntityRenderState() {
        }
    }
}
