package net.cr24.primeval.item;

import net.cr24.primeval.block.PrimevalFarmlandBlock;
import net.cr24.primeval.block.plant.PrimevalCropBlock;
import net.cr24.primeval.util.Size;
import net.cr24.primeval.util.Weight;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FertilizerItem extends WeightedItem {

    private final int fertilizeAmount;
    private final PrimevalFarmlandBlock.PrimevalFarmlandBlockFertilizerType type;

    public FertilizerItem(int fertilizeAmount, PrimevalFarmlandBlock.PrimevalFarmlandBlockFertilizerType type, Weight weight, Size size, Settings settings) {
        super(weight, size, settings);
        this.fertilizeAmount = fertilizeAmount;
        this.type = type;
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
        int fertilization = farmlandState.get(PrimevalFarmlandBlock.FERTILIZED);
        if (fertilization >= fertilizeAmount) {
            return super.useOnBlock(context);
        } else {
            PlayerEntity playerEntity = context.getPlayer();
            if (!playerEntity.isCreative()) {
                Hand hand = context.getHand();
                ItemStack stack = playerEntity.getStackInHand(hand);
                stack.decrement(1);
            }
            world.playSound(playerEntity, pos, SoundEvents.BLOCK_COMPOSTER_READY, SoundCategory.BLOCKS, 1.0f, 1.4f);
            world.setBlockState(farmlandPos, farmlandState.with(PrimevalFarmlandBlock.FERTILIZED, fertilizeAmount).with(PrimevalFarmlandBlock.TYPE, type));
            return ActionResult.SUCCESS;
        }

    }

}
