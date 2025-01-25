package net.cr24.primeval.block.entity;

import net.cr24.primeval.block.PrimevalBlocks;
import net.cr24.primeval.block.functional.PrimevalCampfireBlock;
import net.cr24.primeval.recipe.PrimevalRecipes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Clearable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

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

    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.readNbt(nbt, registries);
        int[] is;
        this.itemsBeingCooked.clear();
        Inventories.readNbt(nbt, this.itemsBeingCooked, registries);
        if (nbt.contains("CookingTimes", 11)) {
            is = nbt.getIntArray("CookingTimes");
            System.arraycopy(is, 0, this.cookingTimes, 0, Math.min(this.cookingTotalTimes.length, is.length));
        }
        if (nbt.contains("CookingTotalTimes", 11)) {
            is = nbt.getIntArray("CookingTotalTimes");
            System.arraycopy(is, 0, this.cookingTotalTimes, 0, Math.min(this.cookingTotalTimes.length, is.length));
        }
        this.burnTime = nbt.getInt("BurnTime");
        this.fuel = nbt.getInt("Fuel");
        this.lit = nbt.getBoolean("Lit");
    }

    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        super.writeNbt(nbt, registries);
        Inventories.writeNbt(nbt, this.itemsBeingCooked, true, registries);
        nbt.putIntArray("CookingTimes", this.cookingTimes);
        nbt.putIntArray("CookingTotalTimes", this.cookingTotalTimes);
        nbt.putInt("BurnTime", this.burnTime);
        nbt.putInt("Fuel", this.fuel);
        nbt.putBoolean("Lit", this.lit);
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

    public static void tick(World world, BlockPos pos, BlockState state, PrimevalCampfireBlockEntity blockEntity) {
        // If client, just make particles
        if (world.isClient) {
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
                if (blockEntity.cookingTimes[i] >= blockEntity.cookingTotalTimes[i]) {
                    SimpleInventory inventory = new SimpleInventory(itemStack);
                    ItemStack craftingResult = world.getRecipeManager().getFirstMatch(PrimevalRecipes.OPEN_FIRE, inventory, world).map(recipe -> recipe.craft(inventory)).orElse(itemStack);
                    blockEntity.itemsBeingCooked.set(i, craftingResult);
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
                world.addParticle(ParticleTypes.SMOKE, d, e, g, 0.0, 5.0E-4, 0.0);
            }
        }
    }

    private void updateListeners() {
        this.markDirty();
        this.getWorld().updateListeners(this.getPos(), this.getCachedState(), this.getCachedState(), Block.NOTIFY_ALL);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        NbtCompound nbtCompound = new NbtCompound();
        Inventories.writeNbt(nbtCompound, this.itemsBeingCooked, true, registries);
        nbtCompound.putInt("BurnTime", this.burnTime);
        nbtCompound.putInt("Fuel", this.fuel);
        nbtCompound.putBoolean("Lit", this.lit);
        return nbtCompound;
    }

    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public void clear() {
        this.itemsBeingCooked.clear();
    }
}
