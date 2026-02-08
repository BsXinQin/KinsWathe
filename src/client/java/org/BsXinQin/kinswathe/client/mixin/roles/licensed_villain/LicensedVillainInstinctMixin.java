package org.BsXinQin.kinswathe.client.mixin.roles.licensed_villain;

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
public abstract class LicensedVillainInstinctMixin {

    @Inject(method = "isInstinctEnabled", at = @At("HEAD"), cancellable = true)
    private static void isLicensedVillainInstinctEnabled(CallbackInfoReturnable<Boolean> ci) {
        if (MinecraftClient.getInstance().player != null) {
            GameWorldComponent gameWorld = GameWorldComponent.KEY.get(MinecraftClient.getInstance().player.getWorld());
            if (gameWorld.isRole(MinecraftClient.getInstance().player, KinsWathe.LICENSED_VILLAIN) && WatheClient.instinctKeybind.isPressed()) {
                ci.setReturnValue(true);
                ci.cancel();
            }
        }
    }

    @Inject(method = "getInstinctHighlight", at = @At("HEAD"), cancellable = true)
    private static void getLicensedVillainInstinctColor(Entity target, CallbackInfoReturnable<Integer> ci) {
        if (target instanceof PlayerEntity) {
            if (!target.isSpectator()) {
                if (MinecraftClient.getInstance().player != null) {
                    GameWorldComponent gameWorld = GameWorldComponent.KEY.get(MinecraftClient.getInstance().player.getWorld());
                    if (gameWorld.isRole(MinecraftClient.getInstance().player, KinsWathe.LICENSED_VILLAIN) && WatheClient.isInstinctEnabled()) {
                        ci.setReturnValue(KinsWathe.LICENSED_VILLAIN.color());
                    }
                }
            }
        }
    }
}