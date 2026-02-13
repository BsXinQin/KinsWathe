package org.BsXinQin.kinswathe.client.mixin.noellesroles.roles.conductor;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.client.WatheClient;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import org.BsXinQin.kinswathe.component.ConfigWorldComponent;
import org.agmas.noellesroles.Noellesroles;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WatheClient.class)
public abstract class ConductorInstinctMixin {

    @Inject(method = "getInstinctHighlight", at = @At("HEAD"), cancellable = true)
    private static void getInstinctHighlight(@NotNull Entity target, @NotNull CallbackInfoReturnable<Integer> cir) {
        if (!FabricLoader.getInstance().isModLoaded("noellesroles") || MinecraftClient.getInstance().player == null) return;
        if (!ConfigWorldComponent.KEY.get(MinecraftClient.getInstance().player.getWorld()).EnableNoellesRolesModify || !ConfigWorldComponent.KEY.get(MinecraftClient.getInstance().player.getWorld()).ConductorInstinctModify) return;
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(MinecraftClient.getInstance().player.getWorld());
        if (target instanceof @NotNull ItemEntity) {
            if (gameWorld.isRole(MinecraftClient.getInstance().player, Noellesroles.CONDUCTOR) && WatheClient.isPlayerAliveAndInSurvival()) {
                cir.setReturnValue(0xDB9D00);
            }
        }
    }
}