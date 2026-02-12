package net.cr24.primeval.block.entity;

import net.cr24.primeval.Primeval;
import net.cr24.primeval.block.functional.PrimevalCampfireBlock;
import net.cr24.primeval.initialization.PrimevalBlocks;
import net.cr24.primeval.recipe.OpenFireRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Clearable;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PrimevalCampfireBlockEntity extends BlockEntity implements Clearable {

    private final NonNullList<ItemStack> itemsBeingCooked = NonNullList.withSize(4, ItemStack.EMPTY);
    private final int[] cookingTimes = new int[4];
    private final int[] cookingTotalTimes = new int[4];
    private int burnTime = 0;
    private int fuel = 0;
    private boolean lit = false;
    private static final int MAX_FUEL = 12000;

    public PrimevalCampfireBlockEntity(BlockPos pos, BlockState state) {
        super(PrimevalBlocks.CAMPFIRE_BLOCK_ENTITY, pos, state);
    }

    protected void loadAdditional(ValueInput view) {
        super.loadAdditional(view);
        this.itemsBeingCooked.clear();
        ContainerHelper.loadAllItems(view, this.itemsBeingCooked);
        var cookingTime = view.getIntArray("CookingTimes");
        if (cookingTime.isPresent()) {
            System.arraycopy(cookingTime.get(), 0, this.cookingTimes, 0, Math.min(this.cookingTotalTimes.length, cookingTime.get().length));
        }
        var cookingTotalTime = view.getIntArray("CookingTotalTimes");
        if (cookingTotalTime.isPresent()) {
            System.arraycopy(cookingTotalTime.get(), 0, this.cookingTotalTimes, 0, Math.min(this.cookingTotalTimes.length, cookingTotalTime.get().length));
        }
        this.burnTime = view.getIntOr("BurnTime", 0);
        this.fuel = view.getIntOr("Fuel", 0);
        this.lit = view.getBooleanOr("Lit", false);
    }

    protected void saveAdditional(ValueOutput view) {
        super.saveAdditional(view);
        ContainerHelper.saveAllItems(view, this.itemsBeingCooked);
        view.putIntArray("CookingTimes", this.cookingTimes);
        view.putIntArray("CookingTotalTimes", this.cookingTotalTimes);
        view.putInt("BurnTime", this.burnTime);
        view.putInt("Fuel", this.fuel);
        view.putBoolean("Lit", this.lit);
    }

    public NonNullList<ItemStack> getItemsBeingCooked() {
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

    public boolean addFuel(BlockState state, Level world, BlockPos pos, int amount) {
        if (this.fuel + amount < MAX_FUEL) {
            this.fuel += amount;
            this.updateListeners();
            updateKindling(state, world, pos, this.fuel);
            return true;
        }
        return false;
    }

    private void updateKindling(BlockState state, Level world, BlockPos pos, int fuelAmount) {
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
        world.setBlockAndUpdate(pos, state.setValue(PrimevalCampfireBlock.KINDLING, kState));
    }

    private int updateFireHeight(BlockState state, Level world, BlockPos pos, int fireLevel) {
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
        world.setBlockAndUpdate(pos, state.setValue(PrimevalCampfireBlock.FIRE_SCALE, fState));
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

    public static void tick(Level world, BlockPos pos, BlockState state, PrimevalCampfireBlockEntity blockEntity, RecipeManager.CachedCheck<SingleRecipeInput, OpenFireRecipe> recipeMatchGetter) {
        // If client, just make particles
        if (world.isClientSide()) {
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
                if (blockEntity.cookingTimes[i] >= blockEntity.cookingTotalTimes[i] && world instanceof ServerLevel) {
                    SingleRecipeInput singleStackRecipeInput = new SingleRecipeInput(itemStack);
                    Optional<ItemStack> result = recipeMatchGetter.getRecipeFor(singleStackRecipeInput, (ServerLevel) world).map((recipe) -> (recipe.value()).assemble(singleStackRecipeInput, world.registryAccess()));
                    if (result.isPresent()) {
                        blockEntity.itemsBeingCooked.set(i, result.get());
                    }
                    blockEntity.cookingTimes[i] = -1;
                    bl = true;
                }
            }
            blockEntity.updateKindling(state, world, pos, blockEntity.fuel);
            blockEntity.updateFireHeight(state, world, pos, blockEntity.burnTime);
            world.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
        } else if (blockEntity.lit){
            blockEntity.setLit(false);
            world.setBlockAndUpdate(pos, world.getBlockState(pos).setValue(PrimevalCampfireBlock.LIT, false).setValue(PrimevalCampfireBlock.KINDLING, 0));
            world.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL);
        }
        if (bl) {
            CampfireBlockEntity.setChanged(world, pos, state);
        }
    }

    private static void clientParticles(Level world, BlockPos pos, BlockState state, PrimevalCampfireBlockEntity blockEntity) {
        if (!state.getValue(PrimevalCampfireBlock.LIT)) return;
        RandomSource random = world.random;
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
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        var writeView = TagValueOutput.createWithContext(Primeval.errorReporter(this), registries);
        saveAdditional(writeView);
        return writeView.buildResult();
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void clearContent() {
        this.itemsBeingCooked.clear();
    }
}
