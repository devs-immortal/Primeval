package net.cr24.primeval.block.entity;

import net.cr24.primeval.Primeval;
import net.cr24.primeval.block.functional.PrimevalCampfireBlock;
import net.cr24.primeval.initialization.PrimevalBlocks;
import net.cr24.primeval.recipe.OpenFireRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.ServerRecipeManager;
import net.minecraft.recipe.input.SingleStackRecipeInput;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.storage.NbtWriteView;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Clearable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PrimevalCampfireBlockEntity extends BlockEntity implements Clearable {

    private final DefaultedList<ItemStack> itemsBeingCooked = DefaultedList.ofSize(4, ItemStack.EMPTY);
    private final int[] cookingTimes = new int[4];
    private final int[] cookingTotalTimes = new int[4];
    private int burnTime = 0;
    private int fuel = 0;
    private boolean lit = false;
    private static final int MAX_FUEL = 12000;

    public PrimevalCampfireBlockEntity(BlockPos pos, BlockState state) {
        super(PrimevalBlocks.CAMPFIRE_BLOCK_ENTITY, pos, state);
    }

    protected void readData(ReadView view) {
        super.readData(view);
        this.itemsBeingCooked.clear();
        Inventories.readData(view, this.itemsBeingCooked);
        var cookingTime = view.getOptionalIntArray("CookingTimes");
        if (cookingTime.isPresent()) {
            System.arraycopy(cookingTime.get(), 0, this.cookingTimes, 0, Math.min(this.cookingTotalTimes.length, cookingTime.get().length));
        }
        var cookingTotalTime = view.getOptionalIntArray("CookingTotalTimes");
        if (cookingTotalTime.isPresent()) {
            System.arraycopy(cookingTotalTime.get(), 0, this.cookingTotalTimes, 0, Math.min(this.cookingTotalTimes.length, cookingTotalTime.get().length));
        }
        this.burnTime = view.getInt("BurnTime", 0);
        this.fuel = view.getInt("Fuel", 0);
        this.lit = view.getBoolean("Lit", false);
    }

    protected void writeData(WriteView view) {
        super.writeData(view);
        Inventories.writeData(view, this.itemsBeingCooked);
        view.putIntArray("CookingTimes", this.cookingTimes);
        view.putIntArray("CookingTotalTimes", this.cookingTotalTimes);
        view.putInt("BurnTime", this.burnTime);
        view.putInt("Fuel", this.fuel);
        view.putBoolean("Lit", this.lit);
    }

    public DefaultedList<ItemStack> getItemsBeingCooked() {
        return this.itemsBeingCooked;
    }

    public int[] getCookingTimes() {
        return this.cookingTimes;
    }

    public List<ItemStack> retrieveCookedItems() {
        ArrayList<ItemStack> arr = new ArrayList<>();
        for (int i = 0; i < this.itemsBeingCooked.size(); ++i) {
            ItemStack itemStack = this.itemsBeingCooked.get(i);
            if (this.cookingTimes[i] != -1) continue;
            arr.add(itemStack);
            this.itemsBeingCooked.set(i, ItemStack.EMPTY);
            this.cookingTimes[i] = 0;
            this.cookingTotalTimes[i] = 0;
        }
        this.updateListeners();
        return arr;
    }

    public boolean addFuel(BlockState state, World world, BlockPos pos, int amount) {
        if (this.fuel + amount < MAX_FUEL) {
            this.fuel += amount;
            this.updateListeners();
            updateKindling(state, world, pos, this.fuel);
            return true;
        }
        return false;
    }

    private void updateKindling(BlockState state, World world, BlockPos pos, int fuelAmount) {
        int kState;
        if (fuelAmount > 8000) {
            kState = 3;
        } else if (fuelAmount > 4000) {
            kState = 2;
        } else if (fuelAmount > 0) {
            kState = 1;
        } else {
            kState = 0;
        }
        world.setBlockState(pos, state.with(PrimevalCampfireBlock.KINDLING, kState));
    }

    private int updateFireHeight(BlockState state, World world, BlockPos pos, int fireLevel) {
        int fState;
        if (fireLevel > 3600) {
            fState = 3;
        } else if (fireLevel > 3000) {
            fState = 2;
        } else if (fireLevel > 1800) {
            fState = 1;
        } else {
            fState = 0;
        }
        world.setBlockState(pos, state.with(PrimevalCampfireBlock.FIRE_SCALE, fState));
        return fState;
    }

    public boolean addItem(ItemStack item, int cookTime) {
        for (int i = 0; i < this.itemsBeingCooked.size(); ++i) {
            ItemStack itemStack = this.itemsBeingCooked.get(i);
            if (!itemStack.isEmpty()) continue;
            this.cookingTotalTimes[i] = cookTime;
            this.cookingTimes[i] = 0;
            this.itemsBeingCooked.set(i, item.split(1));
            this.updateListeners();
            return true;
        }
        return false;
    }

    public void setLit(boolean lighted) {
        this.lit = lighted;
        if (!lighted) this.burnTime = 0;
        this.updateListeners();
    }

    public static void tick(World world, BlockPos pos, BlockState state, PrimevalCampfireBlockEntity blockEntity, ServerRecipeManager.MatchGetter<SingleStackRecipeInput, OpenFireRecipe> recipeMatchGetter) {
        // If client, just make particles
        if (world.isClient()) {
            clientParticles(world, pos, state, blockEntity);
            return;
        }
        boolean bl = false; // Marked true if something needs to be updated- ie item has cooked
        if (blockEntity.lit && blockEntity.fuel > 0) {
            blockEntity.fuel--;
            blockEntity.burnTime++;
            for (int i = 0; i < blockEntity.itemsBeingCooked.size(); ++i) {
                ItemStack itemStack = blockEntity.itemsBeingCooked.get(i);
                if (itemStack.isEmpty() || blockEntity.cookingTimes[i] == -1) continue;
                blockEntity.cookingTimes[i] += blockEntity.updateFireHeight(state, world, pos, blockEntity.burnTime)+1;
                if (blockEntity.cookingTimes[i] >= blockEntity.cookingTotalTimes[i] && world instanceof ServerWorld) {
                    SingleStackRecipeInput singleStackRecipeInput = new SingleStackRecipeInput(itemStack);
                    Optional<ItemStack> result = recipeMatchGetter.getFirstMatch(singleStackRecipeInput, (ServerWorld) world).map((recipe) -> (recipe.value()).craft(singleStackRecipeInput, world.getRegistryManager()));
                    if (result.isPresent()) {
                        blockEntity.itemsBeingCooked.set(i, result.get());
                    }
                    blockEntity.cookingTimes[i] = -1;
                    bl = true;
                }
            }
            blockEntity.updateKindling(state, world, pos, blockEntity.fuel);
            blockEntity.updateFireHeight(state, world, pos, blockEntity.burnTime);
            world.updateListeners(pos, state, state, Block.NOTIFY_ALL);
        } else if (blockEntity.lit){
            blockEntity.setLit(false);
            world.setBlockState(pos, world.getBlockState(pos).with(PrimevalCampfireBlock.LIT, false).with(PrimevalCampfireBlock.KINDLING, 0));
            world.updateListeners(pos, state, state, Block.NOTIFY_ALL);
        }
        if (bl) {
            CampfireBlockEntity.markDirty(world, pos, state);
        }
    }

    private static void clientParticles(World world, BlockPos pos, BlockState state, PrimevalCampfireBlockEntity blockEntity) {
        if (!state.get(PrimevalCampfireBlock.LIT)) return;
        Random random = world.random;
        for (int j = 0; j < blockEntity.itemsBeingCooked.size(); ++j) {
            if (blockEntity.itemsBeingCooked.get(j).isEmpty() || random.nextFloat() < 0.95f) continue;
            double d = (double)pos.getX() + 0.15 + 0.7*(j % 2);
            double e = (double)pos.getY() + 0.3;
            double g = (double)pos.getZ() + 0.15;
            if (j > 1) g += 0.7;
            for (int k = 0; k < 4; ++k) {
                world.addParticleClient(ParticleTypes.SMOKE, d, e, g, 0.0, 5.0E-4, 0.0);
            }
        }
    }

    private void updateListeners() {
        this.markDirty();
        this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        var writeView = NbtWriteView.create(Primeval.errorReporter(this), registries);
        writeData(writeView);
        return writeView.getNbt();
    }

    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public void clear() {
        this.itemsBeingCooked.clear();
    }
}
