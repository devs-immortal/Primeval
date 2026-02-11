package net.cr24.primeval.block.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;

public class PrimevalCampfireBlockEntityRenderer  implements BlockEntityRenderer<PrimevalCampfireBlockEntity> {

    public PrimevalCampfireBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(PrimevalCampfireBlockEntity entity, float tickProgress, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay, Vec3d cameraPos) {
        DefaultedList<ItemStack> items = entity.getItemsBeingCooked();
        matrices.scale(0.6f, 0.5f, 0.6f);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90));
        matrices.translate(0.25, -0.37, 0.4);
        MinecraftClient.getInstance().getItemRenderer().renderItem(items.get(0), ItemDisplayContext.GROUND, light, overlay, matrices, vertexConsumers, entity.getWorld(), 0);
        matrices.translate(1.15, 0, 0);
        MinecraftClient.getInstance().getItemRenderer().renderItem(items.get(1), ItemDisplayContext.GROUND, light, overlay, matrices, vertexConsumers, entity.getWorld(), 0);
        matrices.translate(-1.15, -1.15, 0);
        MinecraftClient.getInstance().getItemRenderer().renderItem(items.get(2), ItemDisplayContext.GROUND, light, overlay, matrices, vertexConsumers, entity.getWorld(), 0);
        matrices.translate(1.15, 0, 0);
        MinecraftClient.getInstance().getItemRenderer().renderItem(items.get(3), ItemDisplayContext.GROUND, light, overlay, matrices, vertexConsumers, entity.getWorld(), 0);
    }
}
