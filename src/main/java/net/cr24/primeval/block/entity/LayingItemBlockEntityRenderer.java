package net.cr24.primeval.block.entity;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.DisplayEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ModelTransformationMode;
import net.minecraft.util.math.RotationAxis;
import org.joml.Quaternionf;

public class LayingItemBlockEntityRenderer implements BlockEntityRenderer<LayingItemBlockEntity> {

    public LayingItemBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {
    }

    @Override
    public void render(LayingItemBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        ItemStack item = entity.getItem();
        int randomInt = entity.getRandomInt();
        matrices.translate(0.5, 0.0, 0.5);
        matrices.scale(1.2f, 1.2f, 1.2f);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(-90));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90 * randomInt));
        matrices.translate(0.0, -0.1, -0.0);
        MinecraftClient.getInstance().getItemRenderer().renderItem(item, ModelTransformationMode.GROUND, light, overlay, matrices, vertexConsumers, entity.getWorld(), 0);
    }
}
