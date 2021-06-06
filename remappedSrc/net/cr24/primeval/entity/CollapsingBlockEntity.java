package net.cr24.primeval.entity;

import com.google.common.collect.Lists;
import net.cr24.primeval.block.CollapsibleBlock;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.AutomaticItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
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

public class CollapsingBlockEntity extends FallingBlockEntity {

    private BlockState block;
    private boolean destroyedOnLanding = false;
    public int timeFalling;
    public boolean dropItem;
    public BlockPos origin;
    public CompoundTag blockEntityData;

    public CollapsingBlockEntity(World world, double x, double y, double z, BlockState block, BlockPos origin) {
        super(world, x, y, z, block);
        this.block = block;
        this.inanimate = true;
        this.updatePosition(x, y + (double)((1.0F - this.getHeight()) / 2.0F), z);
        this.setVelocity(Vec3d.ZERO);
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
        this.origin = origin;
        this.setFallingBlockPos(this.getBlockPos());
    }

    @Override
    public void tick() {
        if (this.block.isAir()) {
            this.remove();
        } else {
            Block block = this.block.getBlock();
            BlockPos blockPos2;
            if (this.timeFalling++ == 0) {
                blockPos2 = this.origin;
                if (this.world.getBlockState(blockPos2).isOf(block)) {
                    this.world.removeBlock(blockPos2, false);
                } else if (!this.world.isClient) {
                    this.remove();
                    return;
                }
            }

            if (!this.hasNoGravity()) {
                this.setVelocity(this.getVelocity().add(0.0D, -0.04D, 0.0D));
            }

            this.move(MovementType.SELF, this.getVelocity());
            if (!this.world.isClient) {
                blockPos2 = this.getBlockPos();
                boolean bl = this.block.getBlock() instanceof ConcretePowderBlock;
                boolean bl2 = bl && this.world.getFluidState(blockPos2).isIn(FluidTags.WATER);
                double d = this.getVelocity().lengthSquared();
                if (bl && d > 1.0D) {
                    BlockHitResult blockHitResult = this.world.raycast(new RaycastContext(new Vec3d(this.prevX, this.prevY, this.prevZ), this.getPos(), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.SOURCE_ONLY, this));
                    if (blockHitResult.getType() != HitResult.Type.MISS && this.world.getFluidState(blockHitResult.getBlockPos()).isIn(FluidTags.WATER)) {
                        blockPos2 = blockHitResult.getBlockPos();
                        bl2 = true;
                    }
                }

                if (!this.onGround && !bl2) {
                    if (!this.world.isClient && (this.timeFalling > 100 && (blockPos2.getY() < 1 || blockPos2.getY() > 256) || this.timeFalling > 600)) {
                        if (this.dropItem && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                            this.dropItem(block);
                        }

                        this.remove();
                    }
                } else {
                    BlockState blockState = this.world.getBlockState(blockPos2);
                    this.setVelocity(this.getVelocity().multiply(0.7D, -0.5D, 0.7D));
                    if (!blockState.isOf(Blocks.MOVING_PISTON)) {
                        this.remove();
                        if (!this.destroyedOnLanding) {
                            boolean bl3 = blockState.canReplace(new AutomaticItemPlacementContext(this.world, blockPos2, Direction.DOWN, ItemStack.EMPTY, Direction.UP));
                            boolean bl4 = CollapsibleBlock.canFallThrough(this.world.getBlockState(blockPos2.down())) && (!bl || !bl2);
                            boolean bl5 = this.block.canPlaceAt(this.world, blockPos2) && !bl4;
                            if (true) {
                                if (this.block.contains(Properties.WATERLOGGED) && this.world.getFluidState(blockPos2).getFluid() == Fluids.WATER) {
                                    this.block = (BlockState)this.block.with(Properties.WATERLOGGED, true);
                                }

                                if (this.world.setBlockState(blockPos2, this.block, 3)) {
                                    if (block instanceof FallingBlock) {
                                        ((FallingBlock)block).onLanding(this.world, blockPos2, this.block, blockState, this);
                                    }

                                    if (this.blockEntityData != null && block instanceof BlockEntityProvider) {
                                        BlockEntity blockEntity = this.world.getBlockEntity(blockPos2);
                                        if (blockEntity != null) {
                                            CompoundTag compoundTag = blockEntity.toTag(new CompoundTag());
                                            Iterator var13 = this.blockEntityData.getKeys().iterator();

                                            while(var13.hasNext()) {
                                                String string = (String)var13.next();
                                                Tag tag = this.blockEntityData.get(string);
                                                if (!"x".equals(string) && !"y".equals(string) && !"z".equals(string)) {
                                                    compoundTag.put(string, tag.copy());
                                                }
                                            }

                                            blockEntity.fromTag(this.block, compoundTag);
                                            blockEntity.markDirty();
                                        }
                                    }
                                } else if (this.dropItem && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                                    this.dropItem(block);
                                }
                            } else if (this.dropItem && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                                this.dropItem(block);
                            }
                        } else if (block instanceof FallingBlock) {
                            ((FallingBlock)block).onDestroyedOnLanding(this.world, blockPos2, this);
                        }
                    }
                }
            }

            this.setVelocity(this.getVelocity().multiply(0.98D));
        }
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
        int i = MathHelper.ceil(fallDistance - 1.0F);
        if (i > 0) {
            List<Entity> list = Lists.newArrayList(this.world.getOtherEntities(this, this.getBoundingBox()));
            boolean bl = this.block.isIn(BlockTags.ANVIL);
            DamageSource damageSource = bl ? DamageSource.ANVIL : DamageSource.FALLING_BLOCK;
            Iterator var7 = list.iterator();

            while(var7.hasNext()) {
                Entity entity = (Entity)var7.next();
                entity.damage(damageSource, (float)Math.min(MathHelper.floor((float)i * 2f), 40f));
            }
        }
        return false;
    }
}
