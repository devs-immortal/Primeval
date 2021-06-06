package net.cr24.primeval.entity.player;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;

public class HydrationManager {
    private int waterLevel = 20;
    private float waterSaturationLevel = 5.0F;
    private float exhaustion;
    private int waterDehydrationTimer;
    private int prevWaterLevel = 20;

    public HydrationManager() {
    }

    public void add(int food, float f) {
        this.waterLevel = Math.min(food + this.waterLevel, 20);
        this.waterSaturationLevel = Math.min(this.waterSaturationLevel + (float)food * f * 2.0F, (float)this.waterLevel);
    }

    public void drink(Item item, ItemStack stack) {
        if (item.isFood()) {
            FoodComponent foodComponent = item.getFoodComponent();
            this.add(foodComponent.getHunger(), foodComponent.getSaturationModifier());
        }

    }

    public void update(PlayerEntity player) {
        Difficulty difficulty = player.world.getDifficulty();
        this.prevWaterLevel = this.waterLevel;
        if (this.exhaustion > 4.0F) {
            this.exhaustion -= 4.0F;
            if (this.waterSaturationLevel > 0.0F) {
                this.waterSaturationLevel = Math.max(this.waterSaturationLevel - 1.0F, 0.0F);
            } else if (difficulty != Difficulty.PEACEFUL) {
                this.waterLevel = Math.max(this.waterLevel - 1, 0);
            }
        }

        boolean bl = player.world.getGameRules().getBoolean(GameRules.NATURAL_REGENERATION);
        if (bl && this.waterSaturationLevel > 0.0F && player.canFoodHeal() && this.waterLevel >= 20) {
            ++this.waterDehydrationTimer;
            if (this.waterDehydrationTimer >= 10) {
                float f = Math.min(this.waterSaturationLevel, 6.0F);
                player.heal(f / 6.0F);
                this.addExhaustion(f);
                this.waterDehydrationTimer = 0;
            }
        } else if (bl && this.waterLevel >= 18 && player.canFoodHeal()) {
            ++this.waterDehydrationTimer;
            if (this.waterDehydrationTimer >= 80) {
                player.heal(1.0F);
                this.addExhaustion(6.0F);
                this.waterDehydrationTimer = 0;
            }
        } else if (this.waterLevel <= 0) {
            ++this.waterDehydrationTimer;
            if (this.waterDehydrationTimer >= 80) {
                if (player.getHealth() > 10.0F || difficulty == Difficulty.HARD || player.getHealth() > 1.0F && difficulty == Difficulty.NORMAL) {
                    player.damage(DamageSource.STARVE, 1.0F);
                }

                this.waterDehydrationTimer = 0;
            }
        } else {
            this.waterDehydrationTimer = 0;
        }

    }

    public void fromTag(NbtCompound tag) {
        if (tag.contains("waterLevel", 99)) {
            this.waterLevel = tag.getInt("waterLevel");
            this.waterDehydrationTimer = tag.getInt("waterTickTimer");
            this.waterSaturationLevel = tag.getFloat("waterSaturationLevel");
            this.exhaustion = tag.getFloat("waterExhaustionLevel");
        }

    }

    public void toTag(NbtCompound tag) {
        tag.putInt("waterLevel", this.waterLevel);
        tag.putInt("waterTickTimer", this.waterDehydrationTimer);
        tag.putFloat("waterSaturationLevel", this.waterSaturationLevel);
        tag.putFloat("waterExhaustionLevel", this.exhaustion);
    }

    public int getWaterLevel() {
        return this.waterLevel;
    }

    public boolean isNotFull() {
        return this.waterLevel < 20;
    }

    public void addExhaustion(float exhaustion) {
        this.exhaustion = Math.min(this.exhaustion + exhaustion, 40.0F);
    }

    public float getSaturationLevel() {
        return this.waterSaturationLevel;
    }

    public void setWaterLevel(int waterLevel) {
        this.waterLevel = waterLevel;
    }

    @Environment(EnvType.CLIENT)
    public void setSaturationLevelClient(float saturationLevel) {
        this.waterSaturationLevel = saturationLevel;
    }
}
