package net.cr24.primeval.mixin.entity.mob;

import net.cr24.primeval.entity.PrimevalVillagerTrades;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOfferList;
import net.minecraft.village.TradeOffers;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WanderingTraderEntity.class)
public abstract class WanderingTraderEntityMixin extends MerchantEntity {

    private WanderingTraderEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "fillRecipes", at = @At("HEAD"), cancellable = true)
    private void fillRecipes(ServerWorld world, CallbackInfo info) {
        TradeOfferList tradeOfferList = this.getOffers();

        for(Pair<TradeOffers.Factory[], Integer> pair : PrimevalVillagerTrades.CUSTOM_WANDERING_TRADER_TRADES) {
            TradeOffers.Factory[] factories = pair.getLeft();
            this.fillRecipesFromPool(world, tradeOfferList, factories, pair.getRight());
        }
        info.cancel();
    }
}
