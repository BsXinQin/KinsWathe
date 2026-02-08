package org.BsXinQin.kinswathe.client.mixin.roles.cook;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.client.WatheClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.BsXinQin.kinswathe.KinsWathe;
import org.BsXinQin.kinswathe.roles.cook.CookComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;

@Mixin(WatheClient.class)
public abstract class CookInstinctMixin {

    @Inject(method = "getInstinctHighlight", at = @At("HEAD"), cancellable = true)
    private static void getCookInstinctColor(Entity target, CallbackInfoReturnable<Integer> ci) {
        if (target instanceof PlayerEntity) {
            if (!target.isSpectator()) {
                if (MinecraftClient.getInstance().player != null) {
                    GameWorldComponent gameWorld = GameWorldComponent.KEY.get(MinecraftClient.getInstance().player.getWorld());
                    CookComponent targetEat = CookComponent.KEY.get(target);
                    if (gameWorld.isRole(MinecraftClient.getInstance().player, KinsWathe.COOK) && WatheClient.isPlayerAliveAndInSurvival() && targetEat.eatTicks > 0) {
                        ci.setReturnValue(Color.GREEN.getRGB());
                    }
                }
            }
        }
    }
}