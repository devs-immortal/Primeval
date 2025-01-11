package net.cr24.primeval.block.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class PrimevalCampfireBlockEntityRenderer  implements BlockEntityRenderer<PrimevalCampfireBlockEntity> {
    public final static Quaternionf NEGATIVE_X = new Quaternionf(-1.0f, 0.0f, 0.0f, 0.0f);

    public PrimevalCampfireBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(PrimevalCampfireBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        DefaultedList<ItemStack> items = entity.getItemsBeingCooked();
        matrices.scale(0.6f, 0.5f, 0.6f);
        matrices.multiply(NEGATIVE_X.rotateX(90));
        matrices.translate(0.25, -0.37, 0.4);
        MinecraftClient.getInstance().getItemRenderer().renderItem(items.get(0), ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, 0);
        matrices.translate(1.15, 0, 0);
        MinecraftClient.getInstance().getItemRenderer().renderItem(items.get(1), ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, 0);
        matrices.translate(-1.15, -1.15, 0);
        MinecraftClient.getInstance().getItemRenderer().renderItem(items.get(2), ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, 0);
        matrices.translate(1.15, 0, 0);
        MinecraftClient.getInstance().getItemRenderer().renderItem(items.get(3), ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, 0);
    }
}
