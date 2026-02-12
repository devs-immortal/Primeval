package net.cr24.primeval.item.tool;

import net.cr24.primeval.block.PrimevalFarmlandBlock;
import net.cr24.primeval.block.plant.PrimevalCropBlock;
import net.cr24.primeval.initialization.PrimevalItems;
import net.cr24.primeval.util.Size;
import net.cr24.primeval.util.Weight;
import net.cr24.primeval.item.WeightedItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import java.util.List;

public class WaterWoodenBucketItem extends WeightedItem {

    public WaterWoodenBucketItem(Weight weight, Size size, net.minecraft.world.item.Item.Properties settings) {
        super(weight, size, 1, settings);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        BlockPos pos = context.getClickedPos();
        Level world = context.getLevel();
        Block targetBlock = world.getBlockState(pos).getBlock();
        BlockPos farmlandPos;
        if (targetBlock instanceof PrimevalFarmlandBlock) {
            farmlandPos = pos;
        } else if (targetBlock instanceof PrimevalCropBlock && world.getBlockState(pos.below()).getBlock() instanceof PrimevalFarmlandBlock) {
            farmlandPos = pos.below();
        } else {
            return super.useOn(context);
        }
        BlockState farmlandState = world.getBlockState(farmlandPos);
        if (!(farmlandState.getValue(PrimevalFarmlandBlock.MOISTURE) == 0)) {
            return super.useOn(context);
        } else {
            Player playerEntity = context.getPlayer();
            if (!playerEntity.isCreative()) {
                InteractionHand hand = context.getHand();
                ItemStack newStack = new ItemStack(PrimevalItems.WOODEN_BUCKET);
                playerEntity.setItemInHand(hand, newStack);
            }
            Vec3 hitPos = context.getClickLocation();
            RandomSource random = world.getRandom();
            makeParticles(hitPos, world, random);
            world.playSound(playerEntity, pos, SoundEvents.GENERIC_SPLASH, SoundSource.BLOCKS, 1.0f, 1.0f);
            world.setBlockAndUpdate(farmlandPos, farmlandState.setValue(PrimevalFarmlandBlock.MOISTURE, 1));
            return InteractionResult.SUCCESS;
        }
    }

    private static void makeParticles(Vec3 hitPos, Level world, RandomSource random) {
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
