package net.cr24.primeval.block.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.block.entity.CampfireBlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;

import java.util.List;

public class AshPileBlockEntityRenderer implements BlockEntityRenderer<AshPileBlockEntity> {

    public AshPileBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(AshPileBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        List<ItemStack> items = entity.getItems();
        matrices.translate(0.25, 0.125, 0.25);
        MinecraftClient.getInstance().getItemRenderer().renderItem(items.get(0), ModelTransformationMode.GROUND, light, overlay, matrices, vertexConsumers, entity.getWorld(), 0);
        matrices.translate(0.5, 0, 0);
        MinecraftClient.getInstance().getItemRenderer().renderItem(items.get(1), ModelTransformationMode.GROUND, light, overlay, matrices, vertexConsumers, entity.getWorld(), 0);
        matrices.translate(-0.5, 0, 0.5);
        MinecraftClient.getInstance().getItemRenderer().renderItem(items.get(2), ModelTransformationMode.GROUND, light, overlay, matrices, vertexConsumers, entity.getWorld(), 0);
        matrices.translate(0.5, 0, 0);
        MinecraftClient.getInstance().getItemRenderer().renderItem(items.get(3), ModelTransformationMode.GROUND, light, overlay, matrices, vertexConsumers, entity.getWorld(), 0);
    }
}
