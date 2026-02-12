package net.cr24.primeval.block.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.OverlayTexture;
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
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PrimevalCampfireBlockEntityRenderer implements BlockEntityRenderer<PrimevalCampfireBlockEntity, FourItemBlockEntityRenderState> {

    private final ItemModelManager itemModelManager;

    public PrimevalCampfireBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
        this.itemModelManager = ctx.itemModelManager();
    }

    @Override
    public void render(FourItemBlockEntityRenderState state, MatrixStack matrices, OrderedRenderCommandQueue queue, CameraRenderState cameraState) {
        matrices.scale(0.6f, 0.5f, 0.6f);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90));
        matrices.translate(0.25, -0.37, 0.4);
        state.itemStates.get(0).render(matrices, queue, state.lightmapCoordinates, OverlayTexture.DEFAULT_UV, 0);
        matrices.translate(1.15, 0, 0);
        state.itemStates.get(1).render(matrices, queue, state.lightmapCoordinates, OverlayTexture.DEFAULT_UV, 0);
        matrices.translate(-1.15, -1.15, 0);
        state.itemStates.get(2).render(matrices, queue, state.lightmapCoordinates, OverlayTexture.DEFAULT_UV, 0);
        matrices.translate(1.15, 0, 0);
        state.itemStates.get(3).render(matrices, queue, state.lightmapCoordinates, OverlayTexture.DEFAULT_UV, 0);
    }

    public void updateRenderState(PrimevalCampfireBlockEntity blockEntity, FourItemBlockEntityRenderState renderState, float f, Vec3d vec3d, @Nullable ModelCommandRenderer.CrumblingOverlayCommand crumblingOverlayCommand) {
        BlockEntityRenderState.updateBlockEntityRenderState(blockEntity, renderState, crumblingOverlayCommand);
        List<ItemStack> items = blockEntity.getItemsBeingCooked();
        int i = (int)blockEntity.getPos().asLong();
        renderState.itemStates = new ArrayList<>(items.size());

        for(int j = 0; j < items.size(); ++j) {
            ItemRenderState itemRenderState = new ItemRenderState();
            this.itemModelManager.clearAndUpdate(itemRenderState, items.get(j), ItemDisplayContext.FIXED, blockEntity.getWorld(), null, i + j);
            renderState.itemStates.add(itemRenderState);
        }
    }

    @Override
    public FourItemBlockEntityRenderState createRenderState() {
        return new FourItemBlockEntityRenderState();
    }
}
