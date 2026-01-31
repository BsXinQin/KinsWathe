package org.BsXinQin.kinswathe.client.mixin.host;

import dev.doctor4t.ratatouille.client.util.OptionLocker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OptionLocker.class)
public class UnlockDistanceMixin {

    @Inject(method = "overrideOption", at = @At("HEAD"), cancellable = true)
    private static void UnlockDistance(String option, Object value, CallbackInfo ci) {
        if ("renderDistance".equals(option) && value instanceof Integer) {
            int distanceValue = (Integer) value;
            if (distanceValue > 2) {
                ci.cancel();
            }
        }
    }
}