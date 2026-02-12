package net.cr24.primeval.item.tool;

import net.cr24.primeval.initialization.PrimevalTags;
import net.cr24.primeval.util.Size;
import net.cr24.primeval.util.Weight;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

public class ProspectingPickaxeItem extends PrimevalPickaxeItem {

    private final int horizontalSearchRange;
    private final int verticalSearchRange;

    public ProspectingPickaxeItem(ToolMaterial material, float attackDamage, float attackSpeed, int horizontalSearchRange, int verticalSearchRange, Weight weight, Size size, net.minecraft.item.Item.Settings settings) {
        super(material, attackDamage, attackSpeed, weight, size, settings);
        this.horizontalSearchRange = horizontalSearchRange;
        this.verticalSearchRange = verticalSearchRange;
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        if (!world.isClient()) return ActionResult.SUCCESS;
        PlayerEntity user = context.getPlayer();
        ItemStack stack = context.getStack();
        BlockPos pos = context.getBlockPos();

        var scanMap = setupDict();

        for (int x = -horizontalSearchRange; x <= horizontalSearchRange; x++) {
            for (int z = -horizontalSearchRange; z <= horizontalSearchRange; z++) {
                for (int y = -verticalSearchRange*2; y <= verticalSearchRange; y++) {
                    BlockState blockAt = world.getBlockState(pos.add(x, y, z));
                    for (var tag : scanMap.entrySet()) {
                        if (blockAt.isIn(tag.getKey())) {
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
                user.sendMessage(Text.translatable("text.primeval.ore.1200").append(Text.translatable("text." + tag.getKey().id().toTranslationKey())), false);
                found = true;
            } else if (amount > 600) {
                user.sendMessage(Text.translatable("text.primeval.ore.600").append(Text.translatable("text." + tag.getKey().id().toTranslationKey())).append(Text.translatable("text.primeval.ore.trailing", amount)), false);
                found = true;
            } else if (amount > 300) {
                user.sendMessage(Text.translatable("text.primeval.ore.300").append(Text.translatable("text." + tag.getKey().id().toTranslationKey())).append(Text.translatable("text.primeval.ore.trailing", amount)), false);
                found = true;
            } else if (amount > 80) {
                user.sendMessage(Text.translatable("text.primeval.ore.80").append(Text.translatable("text." + tag.getKey().id().toTranslationKey())).append(Text.translatable("text.primeval.ore.trailing", amount)), false);
                found = true;
            } else if (amount > 30) {
                user.sendMessage(Text.translatable("text.primeval.ore.30").append(Text.translatable("text." + tag.getKey().id().toTranslationKey())).append(Text.translatable("text.primeval.ore.trailing", amount)), false);
                found = true;
            } else if (amount > 0) {
                user.sendMessage(Text.translatable("text.primeval.ore.1").append(Text.translatable("text." + tag.getKey().id().toTranslationKey())).append(Text.translatable("text.primeval.ore.trailing", amount)), false);
                found = true;
            }
        }
        if (!found) {
            user.sendMessage(Text.translatable("text.primeval.ore.0"), false);
        }

        user.getItemCooldownManager().set(stack, 20);
        user.incrementStat(Stats.USED.getOrCreateStat(this));
        return ActionResult.SUCCESS;
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
