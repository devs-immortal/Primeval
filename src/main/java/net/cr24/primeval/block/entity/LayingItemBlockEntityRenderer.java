package net.cr24.primeval.block.entity;

import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.OverlayTexture;
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
import org.jetbrains.annotations.Nullable;

public class LayingItemBlockEntityRenderer implements BlockEntityRenderer<LayingItemBlockEntity, LayingItemBlockEntityRenderer.LayingItemBlockEntityRenderState> {

    private final ItemModelManager itemModelManager;

    public LayingItemBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.itemModelManager = ctx.itemModelManager();
    }

    @Override
    public void render(LayingItemBlockEntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        if (state.itemState != null) {
            matrices.translate(0.5, 0.0, 0.37);
            matrices.scale(0.7f, 0.7f, 0.7f);
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90 * state.itemRotation));
            matrices.translate(0.0, -0.1, -0.0);
            state.itemState.render(matrices, queue, state.lightmapCoordinates, OverlayTexture.DEFAULT_UV, 0);
        }
    }

    public void updateRenderState(LayingItemBlockEntity blockEntity, LayingItemBlockEntityRenderState renderState, float f, Vec3d vec3d, @Nullable ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlayCommand) {
        BlockEntityRenderState.updateBlockEntityRenderState(blockEntity, renderState, crumblingOverlayCommand);
        ItemRenderState itemRenderState = new ItemRenderState();
        this.itemModelManager.clearAndUpdate(itemRenderState, blockEntity.getItem(), ItemDisplayContext.FIXED, blockEntity.getWorld(), null, blockEntity.getRandomInt());
        renderState.itemState = itemRenderState;
        renderState.itemRotation = blockEntity.getRandomInt();
    }

    @Override
    public LayingItemBlockEntityRenderState createRenderState() {
        return new LayingItemBlockEntityRenderState();
    }

    public static class LayingItemBlockEntityRenderState extends BlockEntityRenderState {
        public ItemRenderState itemState;
        public int itemRotation;

        public LayingItemBlockEntityRenderState() {
        }
    }
}
