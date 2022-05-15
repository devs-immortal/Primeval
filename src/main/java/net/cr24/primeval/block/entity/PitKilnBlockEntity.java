package net.cr24.primeval.block.entity;

import net.cr24.primeval.block.PrimevalBlocks;
import net.cr24.primeval.item.VesselItem;
import net.cr24.primeval.recipe.PitKilnFiringRecipe;
import net.cr24.primeval.recipe.PrimevalRecipes;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.util.Clearable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.Stack;

public class PitKilnBlockEntity extends BlockEntity implements Clearable {

    //public static final int[] FIRING_TIMES = new int[] {-1, 9600, 13200, 16800, 19200};
    public static final int[] FIRING_TIMES = new int[] {-1, 100, 100, 100, 100};

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
            this.logs.clear();
            if (nbt.contains(("Log"+i), 10)) {
                this.logs.push(ItemStack.fromNbt(nbt.getCompound(("Log"+i))));
            }
            if (nbt.contains(("Item"+i), 10)) {
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
        } else if (blockEntity.burnTimer == 0) { // WHEN FINISHES FIRING
            ItemStack[] results = blockEntity.processItems();
            world.removeBlockEntity(pos);
            world.setBlockState(pos, PrimevalBlocks.ASH_PILE.getDefaultState());
            BlockEntity newBlockEntity = world.getBlockEntity(pos);
            if (newBlockEntity instanceof AshPileBlockEntity) {
                ((AshPileBlockEntity) newBlockEntity).setItems(results);
            }
        }
    }

    public BlockEntityUpdateS2CPacket toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbtCompound = new NbtCompound();
        for (int i = 0; i < 4; i++) {
            nbtCompound.put(("Item"+i), this.inventory[i].writeNbt(new NbtCompound()));
        }
        nbtCompound.putInt("burnTimer", this.burnTimer);
        return nbtCompound;
    }

    public void addLog(ItemStack stack) {
        this.logs.push(stack);
        this.markDirty();
    }

    public ItemStack[] getLogs() {
        return this.logs.toArray(new ItemStack[4]);
    }

    public ItemStack removeLog() {
        this.markDirty();
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
        this.markDirty();
        return ret;
    }

    public ItemStack[] processItems() {
        for (int i = 0; i < 4; i++) {
            if (this.inventory[i].getItem() instanceof VesselItem) {
                this.inventory[i] = VesselItem.processItems(this.inventory[i], world);
            } else {
                Optional<PitKilnFiringRecipe> result = world.getRecipeManager().getFirstMatch(PrimevalRecipes.PIT_KILN_FIRING, new SimpleInventory(this.inventory[i]), world);
                if (result.isPresent()) {
                    this.inventory[i] = result.get().getOutput();
                }
            }
        }
        this.markDirty();
        return this.inventory;
    }

    public void startFiring() {
        int itemCount = 0;
        for (ItemStack item : this.inventory) {
            if (!item.isEmpty()) itemCount+=1;
        }
        this.burnTimer = FIRING_TIMES[itemCount];
    }

    public void stopFiring() {
        this.burnTimer = FIRING_TIMES[0];
        this.markDirty();
    }

    @Override
    public void clear() {
        this.logs.clear();
        this.inventory = new ItemStack[] {ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY};
        this.burnTimer = -1;
    }
}
