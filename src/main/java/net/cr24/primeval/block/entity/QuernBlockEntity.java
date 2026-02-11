package net.cr24.primeval.block.entity;

import net.cr24.primeval.Primeval;
import net.cr24.primeval.PrimevalSoundEvents;
import net.cr24.primeval.initialization.PrimevalBlocks;
import net.cr24.primeval.initialization.PrimevalItems;
import net.cr24.primeval.initialization.PrimevalRecipes;
import net.cr24.primeval.recipe.QuernRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.CampfireCookingRecipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.storage.NbtWriteView;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Clearable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

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

    public static void tick(World world, BlockPos pos, BlockState state, QuernBlockEntity blockEntity, ServerRecipeManager.MatchGetter<SingleStackRecipeInput, QuernRecipe> recipeMatchGetter) {
        float newAngle = MathHelper.lerp(0.05f, blockEntity.currentAngle, blockEntity.targetAngle+FLOW_ANGLE);
        if (newAngle < (blockEntity.targetAngle-2)) {
            blockEntity.makeParticles(world, pos);
        }
        blockEntity.currentAngle = newAngle;
        blockEntity.markDirty();
        if (blockEntity.currentAngle >= 359.97 && world instanceof ServerWorld) {

                world.playSound(null, pos, PrimevalSoundEvents.QUERN_PROCESS, SoundCategory.BLOCKS, 0.8f, 0.8f);
                blockEntity.process((ServerWorld) world, pos, recipeMatchGetter);

        }
    }

    public void process(ServerWorld world, BlockPos pos, ServerRecipeManager.MatchGetter<SingleStackRecipeInput, QuernRecipe> recipeMatchGetter) {
        SingleStackRecipeInput singleStackRecipeInput = new SingleStackRecipeInput(this.inputItem);
        var quernRecipe = recipeMatchGetter.getFirstMatch(singleStackRecipeInput, world);

        if (quernRecipe.isPresent()) {
            int remainder = inputItem.getCount();
            for (int i = 0; i < inputItem.getCount(); i++) {
                Block.dropStack(world, pos, Direction.UP, quernRecipe.get().value().getResult());
                wheelDamage += quernRecipe.get().value().getWheelDamage();
                remainder--;
                if (wheelDamage > PrimevalItems.QUERN_WHEEL.getComponents().get(DataComponentTypes.MAX_DAMAGE)) {
                    wheelDamage = -1;
                    breakParticles(world, pos);
                    if (!world.isClient())
                        world.playSound(null, pos, PrimevalSoundEvents.QUERN_BREAK, SoundCategory.BLOCKS, 0.3f, 0.8f);
                    break;
                }
            }
            Block.dropStack(world, pos, Direction.UP, new ItemStack(inputItem.getItem(), remainder));
            this.targetAngle = 0;
            this.currentAngle = 0;
            this.inputItem = ItemStack.EMPTY;
            this.markDirty();
            world.updateListeners(pos, this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
        }
    }

    public void makeParticles(World world, BlockPos pos) {
        if (world.isClient) {
            Random rand = Random.create();
            world.addParticleClient(
                    new ItemStackParticleEffect(ParticleTypes.ITEM, inputItem),
                    pos.getX()+0.5f, pos.getY()+0.5f, pos.getZ()+0.5f,
                    rand.nextFloat()*0.8-0.4, rand.nextFloat()*0.01, rand.nextFloat()*0.8-0.4
            );
            world.addParticleClient(
                    new BlockStateParticleEffect(ParticleTypes.BLOCK, PrimevalBlocks.SMOOTH_STONE.block().getDefaultState()),
                    pos.getX()+0.5f, pos.getY()+0.5f, pos.getZ()+0.5f,
                    rand.nextFloat()*0.4-0.2, 0, rand.nextFloat()*0.4-0.2
            );
        }
    }
    public void breakParticles(World world, BlockPos pos) {
        if (world.isClient) {
            Random rand = Random.create();
            for (int i = 0; i < 16; i++) {
                world.addParticleClient(
                        new BlockStateParticleEffect(ParticleTypes.BLOCK, PrimevalBlocks.QUERN.getDefaultState()),
                        pos.getX()+0.5f, pos.getY()+0.5f, pos.getZ()+0.5f,
                        rand.nextFloat()*0.5-0.25, rand.nextFloat()*0.2, rand.nextFloat()*0.5-0.25
                );
            }
        }
    }

    public boolean tryTurnWheel(World world, BlockPos pos, int amount) {
        if (wheelDamage == -1 || currentAngle < (targetAngle-0.03f) || inputItem.isEmpty()) return false;
        targetAngle += amount;
        makeParticles(world, pos);
        this.markDirty();
        return true;
    }

    public boolean tryAddWheel(ItemStack wheel) {
        if (wheelDamage > -1) return false;
        wheelDamage = wheel.getDamage();
        this.markDirty();
        return true;
    }

    public ItemStack getWheelToDrop() {
        if (wheelDamage == -1) return ItemStack.EMPTY;
        ItemStack wheel = new ItemStack(PrimevalItems.QUERN_WHEEL);
        wheel.setDamage(wheelDamage);
        return wheel;
    }

    public boolean tryPutInputItem(ItemStack item) {
        if (wheelDamage > -1 && inputItem.isEmpty()){
            inputItem = item;
            this.markDirty();
            return true;
        }
        return false;
    }

    public ItemStack tryRetrieveInputItem() {
        if (currentAngle <= 20) {
            ItemStack out = inputItem;
            inputItem = ItemStack.EMPTY;
            this.markDirty();
            return out;
        } else {
            return ItemStack.EMPTY;
        }
    }

    protected void readData(ReadView view) {
        super.readData(view);
        this.inputItem = view.read("input_item", ItemStack.CODEC).orElse(ItemStack.EMPTY);
        wheelDamage = view.getInt("wheel_health", -1);
        targetAngle = view.getFloat("target_angle", 0);
        currentAngle = view.getFloat("current_angle", FLOW_ANGLE);
    }

    protected void writeData(WriteView view) {
        super.writeData(view);
        if (!this.inputItem.isEmpty()) {
            view.put("input_item", ItemStack.CODEC, this.inputItem);
        }
        view.putInt("wheel_health", wheelDamage);
        view.putFloat("target_angle", targetAngle);
        view.putFloat("current_angle", currentAngle);
    }

    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        var writeView = NbtWriteView.create(Primeval.errorReporter(this), registries);
        writeData(writeView);
        return writeView.getNbt();
    }

    @Override
    public void clear() {
        inputItem = ItemStack.EMPTY;
    }
}
