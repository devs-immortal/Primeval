package net.cr24.primeval.block.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
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

public class LayingItemBlockEntityRenderer implements BlockEntityRenderer<LayingItemBlockEntity, LayingItemBlockEntityRenderer.LayingItemBlockEntityRenderState> {

    private final ItemModelResolver itemModelManager;

    public LayingItemBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        this.itemModelManager = ctx.itemModelResolver();
    }

    @Override
    public void submit(LayingItemBlockEntityRenderState state, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraState) {
        if (state.itemState != null) {
            matrices.translate(0.5, 0.0, 0.37);
            matrices.scale(0.7f, 0.7f, 0.7f);
            matrices.mulPose(Axis.XP.rotationDegrees(-90));
            matrices.mulPose(Axis.ZP.rotationDegrees(90 * state.itemRotation));
            matrices.translate(0.0, -0.1, -0.0);
            state.itemState.submit(matrices, queue, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        }
    }

    @Override
    public void extractRenderState(LayingItemBlockEntity blockEntity, LayingItemBlockEntityRenderState renderState, float f, Vec3 vec3d, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlayCommand) {
        BlockEntityRenderState.extractBase(blockEntity, renderState, crumblingOverlayCommand);
        ItemStackRenderState itemRenderState = new ItemStackRenderState();
        this.itemModelManager.updateForTopItem(itemRenderState, blockEntity.getItem(), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, blockEntity.getRandomInt());
        renderState.itemState = itemRenderState;
        renderState.itemRotation = blockEntity.getRandomInt();
    }

    @Override
    public LayingItemBlockEntityRenderState createRenderState() {
        return new LayingItemBlockEntityRenderState();
    }

    public static class LayingItemBlockEntityRenderState extends BlockEntityRenderState {
        public ItemStackRenderState itemState;
        public int itemRotation;

        public LayingItemBlockEntityRenderState() {
        }
    }
}
