package net.cr24.primeval.block.entity;

import net.cr24.primeval.block.functional.QuernBlock;
import net.cr24.primeval.initialization.PrimevalBlocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.random.Random;

public class QuernBlockEntityRenderer implements BlockEntityRenderer<QuernBlockEntity> {

    public QuernBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    @Override
    public void render(QuernBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        // how far the item being querned has sunk into the quern
        double sinkProgress = 0.7-(entity.currentAngle / 360D)*0.2;
        matrices.translate(0.5, sinkProgress, 0.5);
        MinecraftClient.getInstance().getItemRenderer().renderItem(entity.inputItem, ModelTransformationMode.GROUND, light, overlay, matrices, vertexConsumers, entity.getWorld(), 0);
        matrices.translate(0, -sinkProgress, 0);
        // render quern wheel if present, at current angle from BE
        if (entity.wheelDamage == -1) return;
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.currentAngle));
        MinecraftClient.getInstance().getBlockRenderManager().renderBlock(PrimevalBlocks.QUERN.getDefaultState().with(QuernBlock.WHEELED, true), entity.getPos(), entity.getWorld(), matrices, vertexConsumers.getBuffer(RenderLayer.getCutout()), false, Random.create());
    }
}
