package net.cr24.primeval.item.tool;

import net.cr24.primeval.block.PrimevalFarmlandBlock;
import net.cr24.primeval.block.plant.PrimevalCropBlock;
import net.cr24.primeval.item.PrimevalItems;
import net.cr24.primeval.item.Size;
import net.cr24.primeval.item.Weight;
import net.cr24.primeval.item.WeightedItem;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class WaterWoodenBucketItem extends WeightedItem {

    public WaterWoodenBucketItem(Settings settings, Weight weight, Size size) {
        super(settings, weight, size, 1);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();
        Block targetBlock = world.getBlockState(pos).getBlock();
        BlockPos farmlandPos;
        if (targetBlock instanceof PrimevalFarmlandBlock) {
            farmlandPos = pos;
        } else if (targetBlock instanceof PrimevalCropBlock && world.getBlockState(pos.down()).getBlock() instanceof PrimevalFarmlandBlock) {
            farmlandPos = pos.down();
        } else {
            return super.useOnBlock(context);
        }
        BlockState farmlandState = world.getBlockState(farmlandPos);
        if (!(farmlandState.get(PrimevalFarmlandBlock.MOISTURE) == 0)) {
            return super.useOnBlock(context);
        } else {
            PlayerEntity playerEntity = context.getPlayer();
            if (!playerEntity.isCreative()) {
                Hand hand = context.getHand();
                ItemStack newStack = new ItemStack(PrimevalItems.WOODEN_BUCKET);
                playerEntity.setStackInHand(hand, newStack);
            }
            Vec3d hitPos = context.getHitPos();
            Random random = world.getRandom();
            makeParticles(hitPos, world, random);
            world.playSound(playerEntity, pos, SoundEvents.ENTITY_GENERIC_SPLASH, SoundCategory.BLOCKS, 1.0f, 1.0f);
            world.setBlockState(farmlandPos, farmlandState.with(PrimevalFarmlandBlock.MOISTURE, 1));
            return ActionResult.SUCCESS;
        }
    }

    private static void makeParticles(Vec3d hitPos, World world, Random random) {
        for (int i = 0; i < 6; i++) {
            double x = hitPos.x + ((random.nextDouble()-0.5)*0.3);
            double y = hitPos.y + ((random.nextDouble()-0.5)*0.1);
            double z = hitPos.z + ((random.nextDouble()-0.5)*0.3);
            double velocityX = (random.nextDouble()-0.5)*2;
            double velocityY = (random.nextDouble()+0.5);
            double velocityZ = (random.nextDouble()-0.5)*2;
            world.addParticle(ParticleTypes.SPLASH, x, y, z, velocityX, velocityY, velocityZ);
        }
    }

    @Override
    public Weight getWeight() {
        return this.weight;
    }

    @Override
    public Size getSize() {
        return this.size;
    }
}
