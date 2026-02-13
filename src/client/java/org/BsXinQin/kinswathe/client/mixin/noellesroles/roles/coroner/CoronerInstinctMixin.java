package org.BsXinQin.kinswathe.client.mixin.noellesroles.roles.coroner;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerMoodComponent;
import dev.doctor4t.wathe.client.WatheClient;
import dev.doctor4t.wathe.entity.PlayerBodyEntity;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import org.BsXinQin.kinswathe.component.ConfigWorldComponent;
import org.agmas.noellesroles.Noellesroles;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WatheClient.class)
public abstract class CoronerInstinctMixin {

    @Inject(method = "getInstinctHighlight", at = @At("HEAD"), cancellable = true)
    private static void getInstinctHighlight(@NotNull Entity target, @NotNull CallbackInfoReturnable<Integer> cir) {
        if (!FabricLoader.getInstance().isModLoaded("noellesroles") || MinecraftClient.getInstance().player == null) return;
        if (!ConfigWorldComponent.KEY.get(MinecraftClient.getInstance().player.getWorld()).EnableNoellesRolesModify || !ConfigWorldComponent.KEY.get(MinecraftClient.getInstance().player.getWorld()).CoronerInstinctModify) return;
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(MinecraftClient.getInstance().player.getWorld());
        if (target instanceof @NotNull PlayerBodyEntity) {
            PlayerMoodComponent playerMood = PlayerMoodComponent.KEY.get(MinecraftClient.getInstance().player);
            if (gameWorld.isRole(MinecraftClient.getInstance().player, Noellesroles.CORONER) && WatheClient.isPlayerAliveAndInSurvival() && !playerMood.isLowerThanMid()) {
                cir.setReturnValue(0x606060);
            }
        }
    }
}