package net.cr24.primeval.item;

import net.cr24.primeval.block.PrimevalFarmlandBlock;
import net.cr24.primeval.block.plant.PrimevalCropBlock;
import net.cr24.primeval.util.Size;
import net.cr24.primeval.util.Weight;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class FertilizerItem extends WeightedItem {

    private final int fertilizeAmount;
    private final PrimevalFarmlandBlock.PrimevalFarmlandBlockFertilizerType type;

    public FertilizerItem(int fertilizeAmount, PrimevalFarmlandBlock.PrimevalFarmlandBlockFertilizerType type, Weight weight, Size size, Item.Properties settings) {
        super(weight, size, settings);
        this.fertilizeAmount = fertilizeAmount;
        this.type = type;
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
        int fertilization = farmlandState.getValue(PrimevalFarmlandBlock.FERTILIZED);
        if (fertilization >= fertilizeAmount) {
            return super.useOn(context);
        } else {
            Player playerEntity = context.getPlayer();
            if (!playerEntity.isCreative()) {
                InteractionHand hand = context.getHand();
                ItemStack stack = playerEntity.getItemInHand(hand);
                stack.shrink(1);
            }
            world.playSound(playerEntity, pos, SoundEvents.COMPOSTER_READY, SoundSource.BLOCKS, 1.0f, 1.4f);
            world.setBlockAndUpdate(farmlandPos, farmlandState.setValue(PrimevalFarmlandBlock.FERTILIZED, fertilizeAmount).setValue(PrimevalFarmlandBlock.TYPE, type));
            return InteractionResult.SUCCESS;
        }

    }

}
