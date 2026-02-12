package net.cr24.primeval.util;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class PrimevalUtil {

    @SafeVarargs
    public static boolean itemEntitiesInBlock(Level world, BlockPos pos, TagKey<Item>... tags) {
        boolean[] checks = new boolean[tags.length];
        List<Entity> itemEntities = world.getEntities((Entity) null, AABB.unitCubeFromLowerCorner(new Vec3(pos.getX(), pos.getY(), pos.getZ())), entity -> entity instanceof ItemEntity);
        for (Entity ent : itemEntities) {
            ItemEntity itemEnt = (ItemEntity) ent;
            ItemStack stack = itemEnt.getItem();
            for (int itemStep = 0; itemStep < stack.getCount(); itemStep++) {
                for (int i = 0; i < checks.length; i++) {
                    if (checks[i]) continue;
                    if (stack.is(tags[i])) {
                        checks[i] = true;
                        i = 100;
                        ent.hurt(world.damageSources().inFire(), 10);
                    }
                }
            }
        }
        for (int i = 0; i < checks.length; i++) {
            if (!checks[i]) return false;
        }
        return true;
    }
}
