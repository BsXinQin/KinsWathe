package org.BsXinQin.kinswathe.client.mixin.host;

import dev.doctor4t.wathe.api.Role;
import dev.doctor4t.wathe.api.WatheRoles;
import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.client.WatheClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.BsXinQin.kinswathe.KinsWathe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WatheClient.class)
public abstract class InstinctMainMixin {

    @Inject(method = "getInstinctHighlight", at = @At("HEAD"), cancellable = true)
    private static void getInstinctHighlightColor(Entity target, CallbackInfoReturnable<Integer> ci) {
        if (target instanceof PlayerEntity) {
            if (!target.isSpectator() && WatheClient.isInstinctEnabled()) {
                if (MinecraftClient.getInstance().player != null) {
                    GameWorldComponent gameWorld = GameWorldComponent.KEY.get(MinecraftClient.getInstance().player.getWorld());
                    Role role = gameWorld.getRole((PlayerEntity) target);
                    if (role != null) {
                        if (WatheClient.isKiller() && WatheClient.isPlayerAliveAndInSurvival()) {
                            if (KinsWathe.NEUTRAL_ROLES.contains(role)) {
                                ci.setReturnValue(WatheRoles.CIVILIAN.color());
                            }
                        }
                    }
                }
            }
        }
    }
}