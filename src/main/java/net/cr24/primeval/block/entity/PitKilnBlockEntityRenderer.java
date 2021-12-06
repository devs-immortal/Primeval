package net.cr24.primeval.block.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

public class PitKilnBlockEntityRenderer implements BlockEntityRenderer<PitKilnBlockEntity> {

    public PitKilnBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(PitKilnBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemStack[] items = entity.getItems();
        matrices.translate(0.25, 0.125, 0.25);
        MinecraftClient.getInstance().getItemRenderer().renderItem(items[0], ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, 0);
        matrices.translate(0.5, 0, 0);
        MinecraftClient.getInstance().getItemRenderer().renderItem(items[1], ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, 0);
        matrices.translate(-0.5, 0, 0.5);
        MinecraftClient.getInstance().getItemRenderer().renderItem(items[2], ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, 0);
        matrices.translate(0.5, 0, 0);
        MinecraftClient.getInstance().getItemRenderer().renderItem(items[3], ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, 0);
    }
}
