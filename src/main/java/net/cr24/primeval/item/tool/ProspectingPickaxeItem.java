package net.cr24.primeval.item.tool;

import net.cr24.primeval.initialization.PrimevalTags;
import net.cr24.primeval.util.Size;
import net.cr24.primeval.util.Weight;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.stats.Stats;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import java.util.*;

public class ProspectingPickaxeItem extends PrimevalPickaxeItem {

    private final int horizontalSearchRange;
    private final int verticalSearchRange;

    public ProspectingPickaxeItem(ToolMaterial material, float attackDamage, float attackSpeed, int horizontalSearchRange, int verticalSearchRange, Weight weight, Size size, Item.Properties settings) {
        super(material, attackDamage, attackSpeed, weight, size, settings);
        this.horizontalSearchRange = horizontalSearchRange;
        this.verticalSearchRange = verticalSearchRange;
    }

    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        if (!world.isClientSide()) return InteractionResult.SUCCESS;
        Player user = context.getPlayer();
        ItemStack stack = context.getItemInHand();
        BlockPos pos = context.getClickedPos();

        var scanMap = setupDict();

        for (int x = -horizontalSearchRange; x <= horizontalSearchRange; x++) {
            for (int z = -horizontalSearchRange; z <= horizontalSearchRange; z++) {
                for (int y = -verticalSearchRange*2; y <= verticalSearchRange; y++) {
                    BlockState blockAt = world.getBlockState(pos.offset(x, y, z));
                    for (var tag : scanMap.entrySet()) {
                        if (blockAt.is(tag.getKey())) {
                            scanMap.put(tag.getKey(), tag.getValue() + 1);
                        }
                    }
                }
            }
        }
        var found = false;
        for (var tag : scanMap.entrySet()) {
            int amount = tag.getValue();
            if (amount > 1200) {
                user.displayClientMessage(Component.translatable("text.primeval.ore.1200").append(Component.translatable("text." + tag.getKey().location().toLanguageKey())), false);
                found = true;
            } else if (amount > 600) {
                user.displayClientMessage(Component.translatable("text.primeval.ore.600").append(Component.translatable("text." + tag.getKey().location().toLanguageKey())).append(Component.translatable("text.primeval.ore.trailing", amount)), false);
                found = true;
            } else if (amount > 300) {
                user.displayClientMessage(Component.translatable("text.primeval.ore.300").append(Component.translatable("text." + tag.getKey().location().toLanguageKey())).append(Component.translatable("text.primeval.ore.trailing", amount)), false);
                found = true;
            } else if (amount > 80) {
                user.displayClientMessage(Component.translatable("text.primeval.ore.80").append(Component.translatable("text." + tag.getKey().location().toLanguageKey())).append(Component.translatable("text.primeval.ore.trailing", amount)), false);
                found = true;
            } else if (amount > 30) {
                user.displayClientMessage(Component.translatable("text.primeval.ore.30").append(Component.translatable("text." + tag.getKey().location().toLanguageKey())).append(Component.translatable("text.primeval.ore.trailing", amount)), false);
                found = true;
            } else if (amount > 0) {
                user.displayClientMessage(Component.translatable("text.primeval.ore.1").append(Component.translatable("text." + tag.getKey().location().toLanguageKey())).append(Component.translatable("text.primeval.ore.trailing", amount)), false);
                found = true;
            }
        }
        if (!found) {
            user.displayClientMessage(Component.translatable("text.primeval.ore.0"), false);
        }

        user.getCooldowns().addCooldown(stack, 20);
        user.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResult.SUCCESS;
    }

    private Map<TagKey<Block>, Integer> setupDict() {
        HashMap<TagKey<Block>, Integer> map = new HashMap<>();
        map.put(PrimevalTags.Blocks.COPPER_ORES, 0);
        map.put(PrimevalTags.Blocks.TIN_ORES, 0);
        map.put(PrimevalTags.Blocks.ZINC_ORES, 0);
        map.put(PrimevalTags.Blocks.GOLD_ORES, 0);
        map.put(PrimevalTags.Blocks.LAZURITE_ORES, 0);
        map.put(PrimevalTags.Blocks.IRON_ORES, 0);
        return map;
    }


}
