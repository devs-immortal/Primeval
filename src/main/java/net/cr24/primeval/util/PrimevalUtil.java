package net.cr24.primeval.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.TagKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class PrimevalUtil {

    @SafeVarargs
    public static boolean itemEntitiesInBlock(World world, BlockPos pos, TagKey<Item>... tags) {
        boolean[] checks = new boolean[tags.length];
        List<Entity> itemEntities = world.getOtherEntities(null, Box.from(new Vec3d(pos.getX(), pos.getY(), pos.getZ())), entity -> entity instanceof ItemEntity);
        System.out.println(itemEntities);
        for (Entity ent : itemEntities) {
            ItemEntity itemEnt = (ItemEntity) ent;
            ItemStack stack = itemEnt.getStack();
            for (int itemStep = 0; itemStep < stack.getCount(); itemStep++) {
                for (int i = 0; i < checks.length; i++) {
                    if (checks[i]) continue;
                    if (stack.isIn(tags[i])) {
                        checks[i] = true;
                        i = 100;
                        ent.damage(DamageSource.IN_FIRE, 10);
                    }
                }
            }
        }
        System.out.println(Arrays.toString(checks));
        for (int i = 0; i < checks.length; i++) {
            if (!checks[i]) return false;
        }
        return true;
    }
}
