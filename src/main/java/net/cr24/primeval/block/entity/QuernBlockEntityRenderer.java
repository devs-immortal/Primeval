package net.cr24.primeval.block.entity;

import net.cr24.primeval.block.PrimevalBlocks;
import net.cr24.primeval.block.functional.QuernBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ModelTransformationMode;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import net.minecraft.util.math.random.Random;

public class QuernBlockEntityRenderer implements BlockEntityRenderer<QuernBlockEntity> {

    public QuernBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(QuernBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        double sinkProgress = 0.7-(entity.currentAngle / 360D)*0.2;
        matrices.translate(0.5, sinkProgress, 0.5);
        MinecraftClient.getInstance().getItemRenderer().renderItem(entity.inputItem, ModelTransformationMode.GROUND, light, overlay, matrices, vertexConsumers, entity.getWorld(), 0);
        matrices.translate(0, -sinkProgress, 0);

        if (entity.wheelDamage == -1) return;
        // TODO: Test if this is correct
        Quaternionf rotation = new Quaternionf(0.0f, 1.0f, 0.0f, 0.0f).rotateY(entity.currentAngle);
        matrices.multiply(rotation);
        MinecraftClient.getInstance().getBlockRenderManager().renderBlock(PrimevalBlocks.QUERN.getDefaultState().with(QuernBlock.WHEELED, true), entity.getPos(), entity.getWorld(), matrices, vertexConsumers.getBuffer(RenderLayer.getCutout()), false, Random.create());
    }
}
