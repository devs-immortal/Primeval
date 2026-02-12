package net.cr24.primeval.block.entity;

import net.cr24.primeval.Primeval;
import net.cr24.primeval.initialization.PrimevalBlocks;
import net.cr24.primeval.initialization.PrimevalItems;
import net.cr24.primeval.item.tool.VesselItem;
import net.cr24.primeval.recipe.AlloyingRecipe;
import net.cr24.primeval.recipe.MeltingRecipe;
import net.cr24.primeval.recipe.PitKilnFiringRecipe;
import net.cr24.primeval.recipe.QuernRecipe;
import net.cr24.primeval.util.FluidInput;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Clearable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import java.util.Optional;
import java.util.Stack;

public class PitKilnBlockEntity extends BlockEntity implements Clearable {

    public static final int[] FIRING_TIMES = new int[] {-1, 9600, 13200, 16800, 19200};
    //public static final int[] FIRING_TIMES = new int[] {-1, 100, 100, 100, 100};

    private Stack<ItemStack> logs;
    private ItemStack[] inventory;
    private int burnTimer;

    public PitKilnBlockEntity(BlockPos pos, BlockState state) {
        super(PrimevalBlocks.PIT_KILN_BLOCK_ENTITY, pos, state);
        this.logs = new Stack<>();
        this.inventory = new ItemStack[] {ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY};
        this.burnTimer = -1;
    }

    protected void loadAdditional(ValueInput view) {
        super.loadAdditional(view);
        for (int i = 0; i < 4; i++) {
            this.logs.clear();
            this.logs.push(view.read("Log"+i, ItemStack.CODEC).orElse(ItemStack.EMPTY));
            this.inventory[i] = view.read("Item"+i, ItemStack.CODEC).orElse(ItemStack.EMPTY);
        }
        this.burnTimer = view.getIntOr("burnTimer", -1);
    }

    protected void saveAdditional(ValueOutput view) {
        super.saveAdditional(view);
        for (int i = 0; i < 4; i++) {
            if (i < this.logs.size() && !this.logs.get(i).isEmpty()) {
                view.store("Log"+i, ItemStack.CODEC, this.logs.get(i));
            }
            if (!this.inventory[i].isEmpty()) {
                view.store("Item"+i, ItemStack.CODEC, this.inventory[i]);
            }
        }
        view.putInt("burnTimer", this.burnTimer);
    }

    public static void serverTick(ServerLevel serverWorld, BlockPos pos, BlockState state, PitKilnBlockEntity blockEntity, RecipeManager.CachedCheck<SingleRecipeInput, PitKilnFiringRecipe> recipeMatchGetter, RecipeManager.CachedCheck<SingleRecipeInput, MeltingRecipe> vesselRecipeMatchGetter, RecipeManager.CachedCheck<FluidInput, AlloyingRecipe> alloyMatchGetter) {
        if (blockEntity.burnTimer > 0) {
            blockEntity.burnTimer--;
        } else if (blockEntity.burnTimer == 0) { // WHEN FINISHES FIRING
            ItemStack[] results = blockEntity.processItems(serverWorld, recipeMatchGetter, vesselRecipeMatchGetter, alloyMatchGetter);
            serverWorld.removeBlockEntity(pos);
            serverWorld.setBlockAndUpdate(pos, PrimevalBlocks.ASH_PILE.defaultBlockState());
            BlockEntity newBlockEntity = serverWorld.getBlockEntity(pos);
            if (newBlockEntity instanceof AshPileBlockEntity) {
                ((AshPileBlockEntity) newBlockEntity).setItems(results);
            }
        }
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

    public void addLog(ItemStack stack) {
        this.logs.push(stack);
        this.setChanged();
    }

    public ItemStack[] getLogs() {
        return this.logs.toArray(new ItemStack[4]);
    }

    public ItemStack removeLog() {
        this.setChanged();
        return this.logs.pop();
    }

    public boolean addItem(ItemStack stack, int slot) {
        if (this.inventory[slot].isEmpty()) {
            this.inventory[slot] = stack;
            this.setChanged();
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
        this.setChanged();
        return ret;
    }

    public ItemStack[] processItems(ServerLevel serverWorld, RecipeManager.CachedCheck<SingleRecipeInput, PitKilnFiringRecipe> kilnRecipeMatchGetter, RecipeManager.CachedCheck<SingleRecipeInput, MeltingRecipe> vesselRecipeMatchGetter, RecipeManager.CachedCheck<FluidInput, AlloyingRecipe> alloyMatchGetter) {
        for (int i = 0; i < 4; i++) {
            if (this.inventory[i].getItem() instanceof VesselItem) {
                this.inventory[i] = VesselItem.processItem(this.inventory[i], serverWorld, vesselRecipeMatchGetter, alloyMatchGetter);
            } else {
                SingleRecipeInput singleStackRecipeInput = new SingleRecipeInput(this.inventory[i]);
                Optional<ItemStack> result = kilnRecipeMatchGetter.getRecipeFor(singleStackRecipeInput, serverWorld).map((recipe) -> (recipe.value()).assemble(singleStackRecipeInput, level.registryAccess()));
                if (result.isPresent()) {
                    this.inventory[i] = result.get();
                }
            }
        }
        this.setChanged();
        if (level != null) level.sendBlockUpdated(worldPosition, this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
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
        this.setChanged();
    }

    @Override
    public void clearContent() {
        this.logs.clear();
        this.inventory = new ItemStack[] {ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY};
        this.burnTimer = -1;
    }
}
