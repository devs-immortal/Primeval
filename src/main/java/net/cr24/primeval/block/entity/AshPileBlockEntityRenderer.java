package net.cr24.primeval.block.entity;

import org.jetbrains.annotations.Nullable;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class AshPileBlockEntityRenderer implements BlockEntityRenderer<AshPileBlockEntity, FourItemBlockEntityRenderState> {

    private final ItemModelResolver itemModelManager;

    public AshPileBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        this.itemModelManager = ctx.itemModelResolver();
    }

    @Override
    public void submit(FourItemBlockEntityRenderState state, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraState) {
        matrices.scale(0.5f, 0.5f, 0.5f);
        matrices.translate(0.5, 0.47, 0.5);
        state.itemStates.get(0).submit(matrices, queue, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        matrices.translate(1.0, 0, 0);
        state.itemStates.get(1).submit(matrices, queue, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        matrices.translate(-1.0, 0, 1.0);
        state.itemStates.get(2).submit(matrices, queue, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
        matrices.translate(1.0, 0, 0);
        state.itemStates.get(3).submit(matrices, queue, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);
    }

    public void updateRenderState(AshPileBlockEntity blockEntity, FourItemBlockEntityRenderState renderState, float f, Vec3 vec3d, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlayCommand) {
        BlockEntityRenderState.extractBase(blockEntity, renderState, crumblingOverlayCommand);
        List<ItemStack> items = blockEntity.getItems();
        int i = (int)blockEntity.getBlockPos().asLong();
        renderState.itemStates = new ArrayList<>(items.size());

        for(int j = 0; j < items.size(); ++j) {
            ItemStackRenderState itemRenderState = new ItemStackRenderState();
            this.itemModelManager.updateForTopItem(itemRenderState, items.get(j), ItemDisplayContext.FIXED, blockEntity.getLevel(), null, i + j);
            renderState.itemStates.add(itemRenderState);
        }
    }

    @Override
    public FourItemBlockEntityRenderState createRenderState() {
        return new FourItemBlockEntityRenderState();
    }

}
