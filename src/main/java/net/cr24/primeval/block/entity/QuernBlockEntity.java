package net.cr24.primeval.block.entity;

import net.cr24.primeval.Primeval;
import net.cr24.primeval.PrimevalSoundEvents;
import net.cr24.primeval.initialization.PrimevalBlocks;
import net.cr24.primeval.initialization.PrimevalItems;
import net.cr24.primeval.initialization.PrimevalRecipes;
import net.cr24.primeval.recipe.QuernRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Clearable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import java.util.Optional;

public class QuernBlockEntity extends BlockEntity implements Clearable {

    public ItemStack inputItem;
    public int wheelDamage;
    public float targetAngle;
    public float currentAngle;

    public static final int FLOW_ANGLE = 20;

    public QuernBlockEntity(BlockPos pos, BlockState state) {
        super(PrimevalBlocks.QUERN_BLOCK_ENTITY, pos, state);
        inputItem = ItemStack.EMPTY;
        wheelDamage = -1;
        targetAngle = 0;
        currentAngle = FLOW_ANGLE;
    }

    public static void tick(Level world, BlockPos pos, BlockState state, QuernBlockEntity blockEntity, RecipeManager.CachedCheck<SingleRecipeInput, QuernRecipe> recipeMatchGetter) {
        float newAngle = Mth.lerp(0.05f, blockEntity.currentAngle, blockEntity.targetAngle+FLOW_ANGLE);
        if (newAngle < (blockEntity.targetAngle-2)) {
            blockEntity.makeParticles(world, pos);
        }
        blockEntity.currentAngle = newAngle;
        blockEntity.setChanged();
        if (blockEntity.currentAngle >= 359.97 && world instanceof ServerLevel) {
                world.playSound(null, pos, PrimevalSoundEvents.QUERN_PROCESS, SoundSource.BLOCKS, 0.8f, 0.8f);
                blockEntity.process((ServerLevel) world, pos, recipeMatchGetter);
        }
    }

    public void process(ServerLevel world, BlockPos pos, RecipeManager.CachedCheck<SingleRecipeInput, QuernRecipe> recipeMatchGetter) {
        SingleRecipeInput singleStackRecipeInput = new SingleRecipeInput(this.inputItem);
        var quernRecipe = recipeMatchGetter.getRecipeFor(singleStackRecipeInput, world);

        if (quernRecipe.isPresent()) {
            int remainder = inputItem.getCount();
            for (int i = 0; i < inputItem.getCount(); i++) {
                Block.popResourceFromFace(world, pos, Direction.UP, quernRecipe.get().value().getResult());
                wheelDamage += quernRecipe.get().value().getWheelDamage();
                remainder--;
                if (wheelDamage > PrimevalItems.QUERN_WHEEL.components().get(DataComponents.MAX_DAMAGE)) {
                    wheelDamage = -1;
                    breakParticles(world, pos);
                    if (!world.isClientSide())
                        world.playSound(null, pos, PrimevalSoundEvents.QUERN_BREAK, SoundSource.BLOCKS, 0.3f, 0.8f);
                    break;
                }
            }
            Block.popResourceFromFace(world, pos, Direction.UP, new ItemStack(inputItem.getItem(), remainder));
            this.targetAngle = 0;
            this.currentAngle = 0;
            this.inputItem = ItemStack.EMPTY;
            this.setChanged();
            world.sendBlockUpdated(pos, this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
        }
    }

    public void makeParticles(Level world, BlockPos pos) {
        if (world.isClientSide()) {
            RandomSource rand = RandomSource.create();
            world.addParticle(
                    new ItemParticleOption(ParticleTypes.ITEM, inputItem),
                    pos.getX()+0.5f, pos.getY()+0.5f, pos.getZ()+0.5f,
                    rand.nextFloat()*0.8-0.4, rand.nextFloat()*0.01, rand.nextFloat()*0.8-0.4
            );
            world.addParticle(
                    new BlockParticleOption(ParticleTypes.BLOCK, PrimevalBlocks.SMOOTH_STONE.block().defaultBlockState()),
                    pos.getX()+0.5f, pos.getY()+0.5f, pos.getZ()+0.5f,
                    rand.nextFloat()*0.4-0.2, 0, rand.nextFloat()*0.4-0.2
            );
        }
    }
    public void breakParticles(Level world, BlockPos pos) {
        if (world.isClientSide()) {
            RandomSource rand = RandomSource.create();
            for (int i = 0; i < 16; i++) {
                world.addParticle(
                        new BlockParticleOption(ParticleTypes.BLOCK, PrimevalBlocks.QUERN.defaultBlockState()),
                        pos.getX()+0.5f, pos.getY()+0.5f, pos.getZ()+0.5f,
                        rand.nextFloat()*0.5-0.25, rand.nextFloat()*0.2, rand.nextFloat()*0.5-0.25
                );
            }
        }
    }

    public boolean tryTurnWheel(Level world, BlockPos pos, int amount) {
        if (wheelDamage == -1 || currentAngle < (targetAngle-0.03f) || inputItem.isEmpty()) return false;
        targetAngle += amount;
        makeParticles(world, pos);
        this.setChanged();
        return true;
    }

    public boolean tryAddWheel(ItemStack wheel) {
        if (wheelDamage > -1) return false;
        wheelDamage = wheel.getDamageValue();
        this.setChanged();
        return true;
    }

    public ItemStack getWheelToDrop() {
        if (wheelDamage == -1) return ItemStack.EMPTY;
        ItemStack wheel = new ItemStack(PrimevalItems.QUERN_WHEEL);
        wheel.setDamageValue(wheelDamage);
        return wheel;
    }

    public boolean tryPutInputItem(ItemStack item) {
        if (wheelDamage > -1 && inputItem.isEmpty()){
            inputItem = item;
            this.setChanged();
            return true;
        }
        return false;
    }

    public ItemStack tryRetrieveInputItem() {
        if (currentAngle <= 20) {
            ItemStack out = inputItem;
            inputItem = ItemStack.EMPTY;
            this.setChanged();
            return out;
        } else {
            return ItemStack.EMPTY;
        }
    }

    protected void loadAdditional(ValueInput view) {
        super.loadAdditional(view);
        this.inputItem = view.read("input_item", ItemStack.CODEC).orElse(ItemStack.EMPTY);
        wheelDamage = view.getIntOr("wheel_health", -1);
        targetAngle = view.getFloatOr("target_angle", 0);
        currentAngle = view.getFloatOr("current_angle", FLOW_ANGLE);
    }

    protected void saveAdditional(ValueOutput view) {
        super.saveAdditional(view);
        if (!this.inputItem.isEmpty()) {
            view.store("input_item", ItemStack.CODEC, this.inputItem);
        }
        view.putInt("wheel_health", wheelDamage);
        view.putFloat("target_angle", targetAngle);
        view.putFloat("current_angle", currentAngle);
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        var writeView = TagValueOutput.createWithContext(Primeval.errorReporter(this), registries);
        saveAdditional(writeView);
        return writeView.buildResult();
    }

    @Override
    public void clearContent() {
        inputItem = ItemStack.EMPTY;
    }
}
