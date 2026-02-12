package net.cr24.primeval.mixin.entity.mob;

import net.cr24.primeval.entity.PrimevalVillagerTrades;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.entity.npc.villager.VillagerTrades;
import net.minecraft.world.entity.npc.wanderingtrader.WanderingTrader;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WanderingTrader.class)
public abstract class WanderingTraderEntityMixin extends AbstractVillager {

    private WanderingTraderEntityMixin(EntityType<? extends AbstractVillager> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "updateTrades", at = @At("HEAD"), cancellable = true)
    private void fillRecipes(ServerLevel world, CallbackInfo info) {
        MerchantOffers tradeOfferList = this.getOffers();

        for(Pair<VillagerTrades.ItemListing[], Integer> pair : PrimevalVillagerTrades.CUSTOM_WANDERING_TRADER_TRADES) {
            VillagerTrades.ItemListing[] factories = pair.getLeft();
            this.addOffersFromItemListings(world, tradeOfferList, factories, pair.getRight());
        }
        info.cancel();
    }
}
