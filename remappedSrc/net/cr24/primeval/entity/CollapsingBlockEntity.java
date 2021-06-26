package net.cr24.primeval.entity;

import com.google.common.collect.Lists;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;

public class CollapsingBlockEntity extends FallingBlockEntity {

    private BlockState block;
    public int timeFalling;
    public boolean dropItem;
    public BlockPos origin;
    public BlockState sourceBlock;
    public NbtCompound blockEntityData;

    public CollapsingBlockEntity(World world, double x, double y, double z, BlockState block, BlockPos origin, BlockState sourceBlock) {
        super(world, x, y, z, block);
        this.block = block;
        this.inanimate = true;
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
                if (this.world.getBlockState(blockPos2).isOf(block) || this.world.getBlockState(blockPos2).isOf(source)) {
                    this.world.removeBlock(blockPos2, false);
                } else if (!this.world.isClient) {
                    this.discard();
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

                        this.discard();
                    }
                } else {
                    BlockState blockState = this.world.getBlockState(blockPos2);
                    this.setVelocity(this.getVelocity().multiply(0.7D, -0.5D, 0.7D));
                    if (!blockState.isOf(Blocks.MOVING_PISTON)) {
                        this.discard();
                        // !destroyedOnLanding
                        if (this.block.contains(Properties.WATERLOGGED) && this.world.getFluidState(blockPos2).getFluid() == Fluids.WATER) {
                            this.block = this.block.with(Properties.WATERLOGGED, true);
                        }

                        if (this.world.setBlockState(blockPos2, this.block, 3)) {
                            if (block instanceof FallingBlock) {
                                ((FallingBlock)block).onLanding(this.world, blockPos2, this.block, blockState, this);
                            }

                            if (this.blockEntityData != null && block instanceof BlockEntityProvider) {
                                BlockEntity blockEntity = this.world.getBlockEntity(blockPos2);
                                if (blockEntity != null) {
                                    NbtCompound compoundTag = blockEntity.writeNbt(new NbtCompound());

                                    for (String string : this.blockEntityData.getKeys()) {
                                        NbtElement nbtElement = this.blockEntityData.get(string);
                                        if (!"x".equals(string) && !"y".equals(string) && !"z".equals(string)) {
                                            compoundTag.put(string, nbtElement.copy());
                                        }
                                    }

                                    try {
                                        blockEntity.readNbt(compoundTag);
                                    } catch (Exception var16) {
                                        LOGGER.error("Failed to load block entity from falling block", var16);
                                    }
                                    blockEntity.markDirty();
                                }
                            }
                        } else if (this.dropItem && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                            this.dropItem(block);
                        }
                    }
                }
            }

            this.setVelocity(this.getVelocity().multiply(0.98D));
        }
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        int i = MathHelper.ceil(fallDistance - 1.0F);
        if (i > 0) {
            List<Entity> list = Lists.newArrayList(this.world.getOtherEntities(this, this.getBoundingBox()));

            for (Entity entity : list) {
                entity.damage(damageSource, Math.min(MathHelper.floor((float) i * 2f), 40f));
            }
        }
        return false;
    }
}
