package net.cr24.primeval.entity;

import net.cr24.primeval.initialization.PrimevalBlocks;
import net.cr24.primeval.initialization.PrimevalItems;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.TradedItem;

public class PrimevalVillagerTrades {

    public static void init() {
        // Clear vanilla trades
        TradeOffers.WANDERING_TRADER_TRADES.clear();

        TradeOfferHelper.registerWanderingTraderOffers(
                1,
                factories -> factories.add(new PrimevalTradeFactory(
                        PrimevalItems.COPPER_COIN, 8,
                        new ItemStack(PrimevalItems.CABBAGE_SEEDS, 1),
                        12, 2
                ))
        );

        TradeOfferHelper.registerWanderingTraderOffers(
                1,
                factories -> factories.add(new PrimevalTradeFactory(
                        PrimevalItems.COPPER_COIN, 8,
                        new ItemStack(PrimevalItems.WHEAT_SEEDS, 1),
                        12, 2
                ))
        );

        TradeOfferHelper.registerWanderingTraderOffers(
                1,
                factories -> factories.add(new PrimevalTradeFactory(
                        PrimevalItems.COPPER_COIN, 8,
                        new ItemStack(PrimevalBlocks.LARGE_FIRED_CLAY_POT, 1),
                        12, 2
                ))
        );

        TradeOfferHelper.registerWanderingTraderOffers(
                1,
                factories -> factories.add(new PrimevalTradeFactory(
                        PrimevalItems.COPPER_COIN, 1,
                        new ItemStack(PrimevalItems.STONE_BRICK, 16),
                        12, 2
                ))
        );

        TradeOfferHelper.registerWanderingTraderOffers(
                1,
                factories -> factories.add(new PrimevalTradeFactory(
                        PrimevalItems.COPPER_COIN, 12,
                        new ItemStack(PrimevalItems.BRONZE_INGOT, 1),
                        12, 2
                ))
        );

        TradeOfferHelper.registerWanderingTraderOffers(
                1,
                factories -> factories.add(new PrimevalTradeFactory(
                        PrimevalItems.COPPER_COIN, 2,
                        new ItemStack(PrimevalBlocks.DAUB, 8),
                        16, 2
                ))
        );

        TradeOfferHelper.registerWanderingTraderOffers(
                1,
                factories -> factories.add(new PrimevalTradeFactory(
                        PrimevalItems.ANIMAL_FAT, 1,
                        new ItemStack(PrimevalItems.COPPER_COIN, 2),
                        6, 2
                ))
        );

        TradeOfferHelper.registerWanderingTraderOffers(
                1,
                factories -> factories.add(new PrimevalTradeFactory(
                        PrimevalItems.BONE, 4,
                        new ItemStack(PrimevalItems.COPPER_COIN, 1),
                        6, 2
                ))
        );

        TradeOfferHelper.registerWanderingTraderOffers(
                1,
                factories -> factories.add(new PrimevalTradeFactory(
                        PrimevalItems.BOTCHED_ALLOY_INGOT, 1,
                        new ItemStack(PrimevalItems.COPPER_COIN, 2),
                        6, 2
                ))
        );

        TradeOfferHelper.registerWanderingTraderOffers(
                1,
                factories -> factories.add(new PrimevalTradeFactory(
                        PrimevalItems.GUNPOWDER, 1,
                        new ItemStack(PrimevalItems.COPPER_COIN, 1),
                        6, 2
                ))
        );

        // Final Trades

        for (Item item : PrimevalItems.COPPER_TOOL_PARTS) {
            TradeOfferHelper.registerWanderingTraderOffers(
                    2,
                    factories -> factories.add(new PrimevalTradeFactory(
                            PrimevalItems.COPPER_COIN, 8,
                            new ItemStack(item, 1),
                            1, 6
                    ))
            );
        }

        TradeOfferHelper.registerWanderingTraderOffers(
                2,
                factories -> factories.add(new PrimevalTradeFactory(
                        PrimevalItems.COPPER_COIN, 4,
                        new ItemStack(PrimevalBlocks.OAK_SAPLING, 1),
                        12, 2
                ))
        );

        TradeOfferHelper.registerWanderingTraderOffers(
                2,
                factories -> factories.add(new PrimevalTradeFactory(
                        PrimevalItems.COPPER_COIN, 4,
                        new ItemStack(PrimevalBlocks.BIRCH_SAPLING, 1),
                        12, 2
                ))
        );

        TradeOfferHelper.registerWanderingTraderOffers(
                2,
                factories -> factories.add(new PrimevalTradeFactory(
                        PrimevalItems.COPPER_COIN, 4,
                        new ItemStack(PrimevalBlocks.SPRUCE_SAPLING, 1),
                        12, 2
                ))
        );

    }

    private static class PrimevalTradeFactory implements TradeOffers.Factory {
        private final TradedItem buy;

        private final ItemStack sell;
        private final int maxUses;
        private final int experience;
        private final float multiplier;

        public PrimevalTradeFactory(Item buy, int buyCount, ItemStack sell, int maxUses, int experience) {
            this.buy = new TradedItem(buy, buyCount);
            this.sell = sell;
            this.maxUses = maxUses;
            this.experience = experience;
            this.multiplier = 0.05f;
        }

        @Override
        public TradeOffer create(Entity entity, Random random) {
            return new TradeOffer(buy, sell, this.maxUses, this.experience, this.multiplier);
        }
    }
}
