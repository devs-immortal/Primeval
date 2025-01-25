package net.cr24.primeval.entity;

import com.google.common.collect.Lists;
import net.cr24.primeval.block.PrimevalBlockTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.state.property.Properties;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class CollapsingBlockEntity extends FallingBlockEntity {

    private BlockState block;
    public int timeFalling;
    public boolean dropItem;
    public BlockPos origin;
    public BlockState sourceBlock;
    public NbtCompound blockEntityData;

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
            BlockPos blockPos2;
            if (this.timeFalling++ == 0) {
                blockPos2 = this.origin;
                if (getWorld().getBlockState(blockPos2).isOf(block) || getWorld().getBlockState(blockPos2).isOf(source)) {
                    getWorld().removeBlock(blockPos2, false);
                } else if (!getWorld().isClient) {
                    this.discard();
                    return;
                }
            }

            if (!this.hasNoGravity()) {
                this.setVelocity(this.getVelocity().add(0.0D, -0.04D, 0.0D));
            }

            this.move(MovementType.SELF, this.getVelocity());
            if (!getWorld().isClient) {
                blockPos2 = this.getBlockPos();
                boolean bl = this.block.getBlock() instanceof ConcretePowderBlock;
                boolean bl2 = bl && getWorld().getFluidState(blockPos2).isIn(FluidTags.WATER);
                double d = this.getVelocity().lengthSquared();
                if (bl && d > 1.0D) {
                    BlockHitResult blockHitResult = getWorld().raycast(new RaycastContext(new Vec3d(this.prevX, this.prevY, this.prevZ), this.getPos(), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.SOURCE_ONLY, this));
                    if (blockHitResult.getType() != HitResult.Type.MISS && getWorld().getFluidState(blockHitResult.getBlockPos()).isIn(FluidTags.WATER)) {
                        blockPos2 = blockHitResult.getBlockPos();
                        bl2 = true;
                    }
                }

                if (!this.isOnGround() && !bl2) {
                    if (!getWorld().isClient && (this.timeFalling > 100 && (blockPos2.getY() < 1 || blockPos2.getY() > 256) || this.timeFalling > 600)) {
                        if (this.dropItem && getWorld().getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                            this.dropItem(block);
                        }

                        this.discard();
                    }
                } else {
                    BlockState blockState = getWorld().getBlockState(blockPos2);
                    this.setVelocity(this.getVelocity().multiply(0.7D, -0.5D, 0.7D));
                    if (!blockState.isOf(Blocks.MOVING_PISTON)) {
                        if (blockState.isIn(PrimevalBlockTags.COLLAPSING_NO_CRUSH)) {
                            blockPos2 = blockPos2.up();
                            blockState = getWorld().getBlockState(blockPos2);
                        }
                        this.discard();
                        // !destroyedOnLanding
                        if (this.block.contains(Properties.WATERLOGGED) && getWorld().getFluidState(blockPos2).getFluid() == Fluids.WATER) {
                            this.block = this.block.with(Properties.WATERLOGGED, true);
                        }

                        if (getWorld().setBlockState(blockPos2, this.block, 3)) {
                            if (block instanceof FallingBlock) {
                                ((FallingBlock)block).onLanding(getWorld(), blockPos2, this.block, blockState, this);
                            }

                            if (this.blockEntityData != null && this.block.hasBlockEntity()) {
                                BlockEntity $$11 = getWorld().getBlockEntity(blockPos2);
                                if ($$11 != null) {
                                    NbtCompound $$12 = $$11.createNbt();
                                    Iterator var13 = this.blockEntityData.getKeys().iterator();

                                    while(var13.hasNext()) {
                                        String $$13 = (String)var13.next();
                                        $$12.put($$13, this.blockEntityData.get($$13).copy());
                                    }

                                    try {
                                        $$11.readNbt($$12);
                                    } catch (Exception var15) {
                                    }

                                    $$11.markDirty();
                                }
                            }
                        } else if (this.dropItem && getWorld().getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                            this.dropItem(block);
                        }
                    }
                }
            }

            this.setVelocity(this.getVelocity().multiply(0.98D));
        }
    }

    @Override
    public BlockState getBlockState() {
        return this.block;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        int i = MathHelper.ceil(fallDistance - 1.0F);
        if (i > 0) {
            List<Entity> list = Lists.newArrayList(this.world.getOtherEntities(this, this.getBoundingBox()));

            for (Entity entity : list) {
                entity.serverDamage(damageSource, Math.min(MathHelper.floor((float) i * 2f), 40f));
            }
        }
        return false;
    }
}
