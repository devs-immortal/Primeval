package net.cr24.primeval.entity;

import com.google.common.collect.Lists;
import net.cr24.primeval.initialization.PrimevalTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.AutomaticItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public class CollapsingBlockEntity extends FallingBlockEntity {

    private final BlockState block;
    public BlockPos origin;
    public BlockState sourceBlock;

    public CollapsingBlockEntity(World world, double x, double y, double z, BlockState block, BlockPos origin, BlockState sourceBlock) {
        super(EntityType.FALLING_BLOCK, world);
        this.block = block;
        this.intersectionChecked = true;
        this.updatePosition(x, y + (double)((1.0F - this.getHeight()) / 2.0F), z);
        this.setVelocity(Vec3d.ZERO);
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
        this.origin = origin;
        this.sourceBlock = sourceBlock;
        this.setFallingBlockPos(this.getBlockPos());
    }

    @Override
    public void tick() {
        if (this.block.isAir()) {
            this.discard();
        } else {
            Block block = this.block.getBlock();
            Block source = this.sourceBlock.getBlock();
            if (++this.timeFalling == 1) {
                if (getWorld().getBlockState(this.origin).isOf(block) || getWorld().getBlockState(this.origin).isOf(source)) {
                    getWorld().removeBlock(this.origin, false);
                } else if (!getWorld().isClient) {
                    this.discard();
                    return;
                }
            }
            this.applyGravity();
            this.move(MovementType.SELF, this.getVelocity());
            this.tickBlockCollision();
            this.tickPortalTeleportation();
            if (this.getWorld() instanceof ServerWorld serverWorld) {
                if (this.isAlive()) {
                    BlockPos blockPos = this.getBlockPos();
                    if (!this.isOnGround()) { // still falling
                        if (this.timeFalling > 100 && (blockPos.getY() <= this.getWorld().getBottomY() || blockPos.getY() > this.getWorld().getTopYInclusive()) || this.timeFalling > 600) {
                            if (this.dropItem && serverWorld.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                                this.dropItem(serverWorld, block);
                            }
                            this.discard();
                        }
                    } else { // hit ground
                        BlockState blockState = this.getWorld().getBlockState(blockPos);
                        if (blockState.isIn(PrimevalTags.Blocks.COLLAPSING_NO_CRUSH)) {
                            blockPos = blockPos.up();
                            blockState = getWorld().getBlockState(blockPos);
                        }
                        this.setVelocity(this.getVelocity().multiply(0.7, -0.5, 0.7));
                        if (!blockState.isOf(Blocks.MOVING_PISTON)) {
                            if (this.getWorld().setBlockState(blockPos, this.block, 3) || this.getWorld().setBlockState(blockPos.up(), this.block, 3)) {
                                ((ServerWorld)this.getWorld()).getChunkManager().chunkLoadingManager.sendToOtherNearbyPlayers(this, new BlockUpdateS2CPacket(blockPos, this.getWorld().getBlockState(blockPos)));
                                this.discard();
                            } else if (this.dropItem && serverWorld.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS) && this.random.nextBoolean()) {
                                this.discard();
                                this.onDestroyedOnLanding(block, blockPos);
                                this.dropItem(serverWorld, block);
                            }
                        }
                    }
                }
            }
            this.setVelocity(this.getVelocity().multiply(0.98));
        }
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        int i = MathHelper.ceil(fallDistance - 1.0F);
        if (i > 0) {
            Predicate<Entity> predicate = EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.and((entity) -> entity.isAlive() && (entity instanceof LivingEntity || entity instanceof ItemEntity));

            DamageSource source = this.getDamageSources().fallingBlock(this);
            float damageAmount = Math.min(MathHelper.floor((float)i * 2.0f), 40.0f);
            this.getWorld().getOtherEntities(this, this.getBoundingBox(), predicate).forEach((entity) -> entity.serverDamage(source, damageAmount));
        }
        return false;
    }

    public BlockState getBlockState() {
        return this.block;
    }
}
