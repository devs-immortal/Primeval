package net.cr24.primeval.block.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class AshPileBlockEntityRenderer implements BlockEntityRenderer<AshPileBlockEntity> {

    public AshPileBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(AshPileBlockEntity entity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {
        List<ItemStack> items = entity.getItems();
        matrices.translate(0.25, 0.125, 0.25);
        MinecraftClient.getInstance().getItemRenderer().renderItem(items.get(0), ItemDisplayContext.GROUND, light, overlay, matrices, vertexConsumers, entity.getWorld(), 0);
        matrices.translate(0.5, 0, 0);
        MinecraftClient.getInstance().getItemRenderer().renderItem(items.get(1), ItemDisplayContext.GROUND, light, overlay, matrices, vertexConsumers, entity.getWorld(), 0);
        matrices.translate(-0.5, 0, 0.5);
        MinecraftClient.getInstance().getItemRenderer().renderItem(items.get(2), ItemDisplayContext.GROUND, light, overlay, matrices, vertexConsumers, entity.getWorld(), 0);
        matrices.translate(0.5, 0, 0);
        MinecraftClient.getInstance().getItemRenderer().renderItem(items.get(3), ItemDisplayContext.GROUND, light, overlay, matrices, vertexConsumers, entity.getWorld(), 0);
    }
}
