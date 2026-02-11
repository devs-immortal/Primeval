package net.cr24.primeval.entity;

import com.google.common.collect.ImmutableList;
import net.cr24.primeval.initialization.PrimevalBlocks;
import net.cr24.primeval.initialization.PrimevalItems;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.TradedItem;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class PrimevalVillagerTrades {

    public static final List<Pair<TradeOffers.Factory[], Integer>> CUSTOM_WANDERING_TRADER_TRADES =
            ImmutableList.of(
                    Pair.of(
                            new TradeOffers.Factory[]{
                                    new PrimevalTradeFactory(
                                            PrimevalItems.ANIMAL_FAT, 1,
                                            new ItemStack(PrimevalItems.COPPER_COIN, 2),
                                            6, 2
                                    ),
                                    new PrimevalTradeFactory(
                                            PrimevalItems.BONE, 4,
                                            new ItemStack(PrimevalItems.COPPER_COIN, 1),
                                            6, 2
                                    ),
                                    new PrimevalTradeFactory(
                                            PrimevalItems.BOTCHED_ALLOY_INGOT, 1,
                                            new ItemStack(PrimevalItems.COPPER_COIN, 2),
                                            6, 2
                                    ),
                                    new PrimevalTradeFactory(
                                            PrimevalItems.GUNPOWDER, 1,
                                            new ItemStack(PrimevalItems.COPPER_COIN, 1),
                                            6, 2
                                    )
                            }, 2),
                    Pair.of(
                            new TradeOffers.Factory[]{
                                    new PrimevalTradeFactory(
                                            PrimevalItems.COPPER_COIN, 8,
                                            new ItemStack(PrimevalItems.CABBAGE_SEEDS, 1),
                                            12, 2
                                    ),
                                    new PrimevalTradeFactory(
                                            PrimevalItems.COPPER_COIN, 8,
                                            new ItemStack(PrimevalItems.WHEAT_SEEDS, 1),
                                            12, 2
                                    ),
                                    new PrimevalTradeFactory(
                                            PrimevalItems.COPPER_COIN, 8,
                                            new ItemStack(PrimevalBlocks.LARGE_FIRED_CLAY_POT, 1),
                                            12, 2
                                    ),
                                    new PrimevalTradeFactory(
                                            PrimevalItems.COPPER_COIN, 1,
                                            new ItemStack(PrimevalItems.STONE_BRICK, 16),
                                            12, 2
                                    ),
                                    new PrimevalTradeFactory(
                                            PrimevalItems.COPPER_COIN, 12,
                                            new ItemStack(PrimevalItems.BRONZE_INGOT, 1),
                                            12, 2
                                    ),
                                    new PrimevalTradeFactory(
                                            PrimevalItems.COPPER_COIN, 2,
                                            new ItemStack(PrimevalBlocks.DAUB, 8),
                                            16, 2
                                    )
                            }, 5),
                    Pair.of(
                            new TradeOffers.Factory[]{
                                    new PrimevalTradeFactory(
                                            PrimevalItems.COPPER_COIN, 4,
                                            new ItemStack(PrimevalBlocks.OAK_SAPLING, 1),
                                            12, 2
                                    ),
                                    new PrimevalTradeFactory(
                                            PrimevalItems.COPPER_COIN, 4,
                                            new ItemStack(PrimevalBlocks.BIRCH_SAPLING, 1),
                                            12, 2
                                    ),
                                    new PrimevalTradeFactory(
                                            PrimevalItems.COPPER_COIN, 4,
                                            new ItemStack(PrimevalBlocks.SPRUCE_SAPLING, 1),
                                            12, 2
                                    ),
                                    new PrimevalTradeFactory(
                                            PrimevalItems.COPPER_COIN, 8,
                                            new ItemStack(PrimevalItems.COPPER_TOOL_PARTS.axe_head(), 1),
                                            1, 6
                                    ),
                                    new PrimevalTradeFactory(
                                            PrimevalItems.COPPER_COIN, 8,
                                            new ItemStack(PrimevalItems.COPPER_TOOL_PARTS.chisel_head(), 1),
                                            1, 6
                                    ),
                                    new PrimevalTradeFactory(
                                            PrimevalItems.COPPER_COIN, 8,
                                            new ItemStack(PrimevalItems.COPPER_TOOL_PARTS.knife_blade(), 1),
                                            1, 6
                                    ),
                                    new PrimevalTradeFactory(
                                            PrimevalItems.COPPER_COIN, 8,
                                            new ItemStack(PrimevalItems.COPPER_TOOL_PARTS.pickaxe_head(), 1),
                                            1, 6
                                    ),
                                    new PrimevalTradeFactory(
                                            PrimevalItems.COPPER_COIN, 8,
                                            new ItemStack(PrimevalItems.COPPER_TOOL_PARTS.shovel_head(), 1),
                                            1, 6
                                    ),
                                    new PrimevalTradeFactory(
                                            PrimevalItems.COPPER_COIN, 8,
                                            new ItemStack(PrimevalItems.COPPER_TOOL_PARTS.sword_blade(), 1),
                                            1, 6
                                    ),
                                    new PrimevalTradeFactory(
                                            PrimevalItems.COPPER_COIN, 8,
                                            new ItemStack(PrimevalItems.COPPER_TOOL_PARTS.hoe_head(), 1),
                                            1, 6
                                    ),
                                    new PrimevalTradeFactory(
                                            PrimevalItems.COPPER_COIN, 8,
                                            new ItemStack(PrimevalItems.COPPER_TOOL_PARTS.prospecting_pickaxe_head(), 1),
                                            1, 6
                                    )
                            }, 2)
            );

    public static void init() {
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
