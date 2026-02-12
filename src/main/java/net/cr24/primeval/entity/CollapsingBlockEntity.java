package net.cr24.primeval.entity;

import com.google.common.collect.Lists;
import net.cr24.primeval.initialization.PrimevalTags;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.phys.Vec3;
import java.util.function.Predicate;

public class CollapsingBlockEntity extends FallingBlockEntity {

    private final BlockState block;
    public BlockPos origin;
    public BlockState sourceBlock;

    public CollapsingBlockEntity(Level world, double x, double y, double z, BlockState block, BlockPos origin, BlockState sourceBlock) {
        super(EntityType.FALLING_BLOCK, world);
        this.block = block;
        this.blocksBuilding = true;
        this.absSnapTo(x, y + (double)((1.0F - this.getBbHeight()) / 2.0F), z);
        this.setDeltaMovement(Vec3.ZERO);
        this.xo = x;
        this.yo = y;
        this.zo = z;
        this.origin = origin;
        this.sourceBlock = sourceBlock;
        this.setStartPos(this.blockPosition());
    }

    @Override
    public void tick() {
        if (this.block.isAir()) {
            this.discard();
        } else {
            Block block = this.block.getBlock();
            Block source = this.sourceBlock.getBlock();
            if (++this.time == 1) {
                if (level().getBlockState(this.origin).is(block) || level().getBlockState(this.origin).is(source)) {
                    level().removeBlock(this.origin, false);
                } else if (!level().isClientSide()) {
                    this.discard();
                    return;
                }
            }
            this.applyGravity();
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.applyEffectsFromBlocks();
            this.handlePortal();
            if (this.level() instanceof ServerLevel serverWorld) {
                if (this.isAlive()) {
                    BlockPos blockPos = this.blockPosition();
                    if (!this.onGround()) { // still falling
                        if (this.time > 100 && (blockPos.getY() <= this.level().getMinY() || blockPos.getY() > this.level().getMaxY()) || this.time > 600) {
                            if (this.dropItem && serverWorld.getGameRules().get(GameRules.ENTITY_DROPS)) {
                                this.spawnAtLocation(serverWorld, block);
                            }
                            this.discard();
                        }
                    } else { // hit ground
                        BlockState blockState = this.level().getBlockState(blockPos);
                        if (blockState.is(PrimevalTags.Blocks.COLLAPSING_NO_CRUSH)) {
                            blockPos = blockPos.above();
                            blockState = level().getBlockState(blockPos);
                        }
                        this.setDeltaMovement(this.getDeltaMovement().multiply(0.7, -0.5, 0.7));
                        if (!blockState.is(Blocks.MOVING_PISTON)) {
                            if (this.level().setBlock(blockPos, this.block, 3) || this.level().setBlock(blockPos.above(), this.block, 3)) {
                                ((ServerLevel)this.level()).getChunkSource().chunkMap.sendToTrackingPlayers(this, new ClientboundBlockUpdatePacket(blockPos, this.level().getBlockState(blockPos)));
                                this.discard();
                            } else if (this.dropItem && serverWorld.getGameRules().get(GameRules.ENTITY_DROPS) && this.random.nextBoolean()) {
                                this.discard();
                                this.callOnBrokenAfterFall(block, blockPos);
                                this.spawnAtLocation(serverWorld, block);
                            }
                        }
                    }
                }
            }
            this.setDeltaMovement(this.getDeltaMovement().scale(0.98));
        }
    }

    @Override
    public boolean causeFallDamage(double fallDistance, float damagePerDistance, DamageSource damageSource) {
        int i = Mth.ceil(fallDistance - 1.0F);
        if (i > 0) {
            Predicate<Entity> predicate = EntitySelector.NO_CREATIVE_OR_SPECTATOR.and((entity) -> entity.isAlive() && (entity instanceof LivingEntity || entity instanceof ItemEntity));

            DamageSource source = this.damageSources().fallingBlock(this);
            float damageAmount = Math.min(Mth.floor((float)i * 2.0f), 40.0f);
            this.level().getEntities(this, this.getBoundingBox(), predicate).forEach((entity) -> entity.hurt(source, damageAmount));
        }
        return false;
    }

    public BlockState getBlockState() {
        return this.block;
    }
}
