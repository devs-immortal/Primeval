package net.cr24.primeval.block.entity;

import net.cr24.primeval.block.PitKilnBlock;
import net.cr24.primeval.block.PrimevalBlocks;
import net.cr24.primeval.recipe.PitKilnFiringRecipe;
import net.cr24.primeval.recipe.PrimevalRecipes;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Clearable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.Stack;

public class PitKilnBlockEntity extends BlockEntity implements Clearable {
    private Stack<ItemStack> logs;
    private ItemStack[] inventory;
    private int burnTimer;

    public PitKilnBlockEntity(BlockPos pos, BlockState state) {
        super(PrimevalBlocks.PIT_KILN_BLOCK_ENTITY, pos, state);
        this.logs = new Stack<>();
        this.inventory = new ItemStack[] {ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY};
        this.burnTimer = -1;
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        for (int i = 0; i < 4; i++) {
            if (nbt.contains(("Log"+i), 10)) {
                this.clear();
                this.logs.push(ItemStack.fromNbt(nbt.getCompound(("Log"+i))));
            }
            if (nbt.contains(("Item"+i), 10)) {
                this.clear();
                this.inventory[i] = ItemStack.fromNbt(nbt.getCompound(("Item"+i)));
            }
        }
        this.burnTimer = nbt.getInt("burnTimer");
    }

    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        for (int i = 0; i < 4; i++) {
            if (i < this.logs.size() && !this.logs.get(i).isEmpty()) {
                nbt.put(("Log"+i), this.logs.get(i).writeNbt(new NbtCompound()));
            }
            nbt.put(("Item"+i), this.inventory[i].writeNbt(new NbtCompound()));
        }
        nbt.putInt("burnTimer", this.burnTimer);
    }

    public static void tick(World world, BlockPos pos, BlockState state, PitKilnBlockEntity blockEntity) {
        if (blockEntity.burnTimer > 0) {
            blockEntity.burnTimer--;
        } else if (blockEntity.burnTimer == 0) {
            blockEntity.processItems();
            world.setBlockState(pos.up(), Blocks.AIR.getDefaultState());
        }
    }

    public void addLog(ItemStack stack) {
        this.logs.push(stack);
        this.markDirty();
    }

    public ItemStack[] getLogs() {
        return this.logs.toArray(new ItemStack[4]);
    }

    public ItemStack removeLog() {
        return this.logs.pop();
    }

    public boolean addItem(ItemStack stack, int slot) {
        if (this.inventory[slot].isEmpty()) {
            this.inventory[slot] = stack;
            this.markDirty();
            return true;
        } else {
            return false;
        }
    }

    public ItemStack[] getItems() {
        return this.inventory;
    }

    public ItemStack removeItem(int slot) {
        ItemStack ret = this.inventory[slot];
        this.inventory[slot] = ItemStack.EMPTY;
        return ret;
    }

    public void processItems() {
        for (int i = 0; i < 4; i++) {
            Optional<PitKilnFiringRecipe> result = world.getRecipeManager().getFirstMatch(PrimevalRecipes.PIT_KILN_FIRING, new SimpleInventory(this.inventory[i]), world);
            if (result.isPresent()) {
                this.inventory[i] = result.get().getOutput();
            }
        }
    }

    public void startFiring(int time) {
        this.burnTimer = time;
    }

    @Override
    public void clear() {
        this.logs.clear();
        this.inventory = new ItemStack[] {ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY};
        this.burnTimer = -1;
    }
}
