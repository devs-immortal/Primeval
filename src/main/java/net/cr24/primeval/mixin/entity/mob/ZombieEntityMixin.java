package net.cr24.primeval.mixin.entity.mob;

import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ZombieEntity.class)
public class ZombieEntityMixin {

    @Inject(method = "initEquipment", at = @At("HEAD"), cancellable = true)
    private void initEquipment(Random random, LocalDifficulty localDifficulty, CallbackInfo info) {
        info.cancel();
    }

}
