package net.cr24.primeval.block.entity;

import net.cr24.primeval.block.PrimevalBlocks;
import net.cr24.primeval.block.functional.PrimevalCampfireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Clearable;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class PrimevalCampfireBlockEntity extends BlockEntity implements Clearable {

    private final DefaultedList<ItemStack> itemsBeingCooked = DefaultedList.ofSize(4, ItemStack.EMPTY);
    private final int[] cookingTimes = new int[4];
    private final int[] cookingTotalTimes = new int[4];
    private int fuel = 0;
    private boolean lit = false;

    public PrimevalCampfireBlockEntity(BlockPos pos, BlockState state) {
        super(PrimevalBlocks.CAMPFIRE_BLOCK_ENTITY, pos, state);
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        int[] is;
        this.itemsBeingCooked.clear();
        Inventories.readNbt(nbt, this.itemsBeingCooked);
        if (nbt.contains("CookingTimes", 11)) {
            is = nbt.getIntArray("CookingTimes");
            System.arraycopy(is, 0, this.cookingTimes, 0, Math.min(this.cookingTotalTimes.length, is.length));
        }
        if (nbt.contains("CookingTotalTimes", 11)) {
            is = nbt.getIntArray("CookingTotalTimes");
            System.arraycopy(is, 0, this.cookingTotalTimes, 0, Math.min(this.cookingTotalTimes.length, is.length));
        }
        this.fuel = nbt.getInt("Fuel");
        this.lit = nbt.getBoolean("Lit");
    }

    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.itemsBeingCooked, true);
        nbt.putIntArray("CookingTimes", this.cookingTimes);
        nbt.putIntArray("CookingTotalTimes", this.cookingTotalTimes);
        nbt.putInt("Fuel", this.fuel);
        nbt.putBoolean("Lit", this.lit);
    }

    public DefaultedList<ItemStack> getItemsBeingCooked() {
        return this.itemsBeingCooked;
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
        this.markDirty();
        return arr;
    }

    public boolean addItem(ItemStack item, int cookTime) {
        for (int i = 0; i < this.itemsBeingCooked.size(); ++i) {
            ItemStack itemStack = this.itemsBeingCooked.get(i);
            if (!itemStack.isEmpty()) continue;
            this.cookingTotalTimes[i] = cookTime;
            this.cookingTimes[i] = 0;
            this.itemsBeingCooked.set(i, item.split(1));
            this.markDirty();
            return true;
        }
        return false;
    }

    public static void tick(World world, BlockPos pos, BlockState state, PrimevalCampfireBlockEntity blockEntity) {
        if (blockEntity.lit) {
            boolean bl = false;
            blockEntity.fuel--;
            for (int i = 0; i < blockEntity.itemsBeingCooked.size(); ++i) {
                ItemStack itemStack = blockEntity.itemsBeingCooked.get(i);
                if (itemStack.isEmpty() || blockEntity.cookingTimes[i] == -1) continue;
                blockEntity.cookingTimes[i]++;
                if (blockEntity.cookingTimes[i] >= blockEntity.cookingTotalTimes[i]) {
                    SimpleInventory inventory = new SimpleInventory(itemStack);
                    ItemStack craftingResult = world.getRecipeManager().getFirstMatch(RecipeType.CAMPFIRE_COOKING, inventory, world).map(recipe -> recipe.craft(inventory)).orElse(itemStack);
                    blockEntity.itemsBeingCooked.set(i, craftingResult);
                    blockEntity.cookingTimes[i] = -1;
                    bl = true;
                }
            }
            if (bl) {
                CampfireBlockEntity.markDirty(world, pos, state);
            }
        }
    }


    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbtCompound = new NbtCompound();
        Inventories.writeNbt(nbtCompound, this.itemsBeingCooked, true);
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
