package net.cr24.primeval.util

import net.minecraft.entity.Entity
import net.minecraft.entity.ItemEntity
import net.minecraft.entity.damage.DamageSource
import net.minecraft.item.Item
import net.minecraft.tag.TagKey
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World

class PrimevalUtil {
    companion object {
        @SafeVarargs
        fun itemEntitiesInBlock(world: World, pos: BlockPos, vararg tags: TagKey<Item?>?): Boolean {
            val checks = BooleanArray(tags.size)
            val itemEntities = world.getOtherEntities(null, Box.from(Vec3d(pos.x.toDouble(), pos.y.toDouble(), pos.z.toDouble()))) { entity: Entity? -> entity is ItemEntity }
            println(itemEntities)
            for (ent in itemEntities) {
                val itemEnt = ent as ItemEntity
                val stack = itemEnt.stack
                for (itemStep in 0 until stack.count) {
                    var i = 0
                    while (i < checks.size) {
                        if (checks[i]) {
                            i++
                            continue
                        }
                        if (stack.isIn(tags[i])) {
                            checks[i] = true
                            i = 100
                            ent.damage(DamageSource.IN_FIRE, 10f)
                        }
                        i++
                    }
                }
            }
            for (check in checks)
                if (!check) return false
            return true
        }
    }
}