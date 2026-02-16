package org.BsXinQin.kinswathe.client.mixin.roles.drugmaker;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerPoisonComponent;
import dev.doctor4t.wathe.client.WatheClient;
import dev.doctor4t.wathe.game.GameFunctions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.BsXinQin.kinswathe.KinsWatheRoles;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(WatheClient.class)
public abstract class DrugmakerInstinctMixin {

    @Unique private static final UUID DELUSION_MARKER = UUID.fromString("00000000-0000-0000-dead-c0de00000000");

    @Inject(method = "getInstinctHighlight", at = @At("HEAD"), cancellable = true)
    private static void getInstinctHighlight(@NotNull Entity target, @NotNull CallbackInfoReturnable<Integer> cir) {
        if (MinecraftClient.getInstance().player == null) return;
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(MinecraftClient.getInstance().player.getWorld());
        if (target instanceof @NotNull PlayerEntity targetPlayer) {
            if (GameFunctions.isPlayerAliveAndSurvival(targetPlayer)) {
                PlayerPoisonComponent targetPoison = PlayerPoisonComponent.KEY.get(targetPlayer);
                if (targetPoison.poisoner == null) return;
                if (gameWorld.isRole(MinecraftClient.getInstance().player, KinsWatheRoles.DRUGMAKER) && WatheClient.isPlayerAliveAndInSurvival() && !WatheClient.isInstinctEnabled() && targetPoison.poisonTicks > 0 && !targetPoison.poisoner.equals(DELUSION_MARKER)) {
                    cir.setReturnValue(KinsWatheRoles.DRUGMAKER.color());
                }
            }
        }
    }
}