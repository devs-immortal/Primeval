package net.cr24.primeval.item;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.hud.DebugHud;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.potion.PotionUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.List;

public class FirestarterItem extends WeightedItem {
    public FirestarterItem(Settings settings, Weight weight, Size size) {
        super(settings, weight, size);
    }

//    public ActionResult useOnBlock(ItemUsageContext context) {
//        ItemStack offHand = context.getPlayer().getStackInHand(Hand.OFF_HAND);
//        if (offHand.getItem() == PrimevalItems.STICK) {
//            World world = context.getWorld();
//            BlockPos pos = context.getBlockPos();
//            PlayerEntity playerEntity = context.getPlayer();
//            BlockPos blockPos2 = pos.offset(context.getSide());
//            if (AbstractFireBlock.canPlaceAt(world, blockPos2, context.getPlayerFacing())) {
//                setFire(world, playerEntity, blockPos2);
//                return ActionResult.SUCCESS;
//            }
//        }
//        return ActionResult.PASS;
//    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!(user instanceof PlayerEntity)) {
            return stack;
        }
        ItemStack offHand = user.getStackInHand(Hand.OFF_HAND);
        Vec3d result = user.raycast(10, 0, false).getPos();
        if (offHand.getItem() == PrimevalItems.STICK) {
            BlockPos pos = new BlockPos(result.x, result.y, result.z);

            if (AbstractFireBlock.canPlaceAt(world, pos, user.getHorizontalFacing())) {
                setFire(world, (PlayerEntity) user, pos);
            } else {
                BlockPos pos2 = pos.offset(user.getHorizontalFacing().getOpposite());
                if (AbstractFireBlock.canPlaceAt(world, pos2, user.getHorizontalFacing())) {
                    setFire(world, (PlayerEntity) user, pos2);
                }
            }

        }
        return stack;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 32;
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack offHand = user.getStackInHand(Hand.OFF_HAND);
        if (hand == Hand.MAIN_HAND && offHand.getItem() == PrimevalItems.STICK) {
            return ItemUsage.consumeHeldItem(world, user, hand);
        }
        return TypedActionResult.fail(user.getStackInHand(hand));
    }

    private void setFire(World world, PlayerEntity player, BlockPos pos) {
        world.playSound(player, pos, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.BLOCKS, 1.0f, world.getRandom().nextFloat() * 0.4f + 0.8f);
        BlockState blockState2 = AbstractFireBlock.getState(world, pos);
        world.setBlockState(pos, blockState2, Block.NOTIFY_ALL | Block.REDRAW_ON_MAIN_THREAD);
    }

}
