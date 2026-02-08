package org.BsXinQin.kinswathe.client.mixin.roles.drugmaker;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerPoisonComponent;
import dev.doctor4t.wathe.client.WatheClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.BsXinQin.kinswathe.KinsWathe;
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
    private static void getDrugmakerInstinctColor(Entity target, CallbackInfoReturnable<Integer> ci) {
        if (target instanceof PlayerEntity) {
            if (!target.isSpectator()) {
                if (MinecraftClient.getInstance().player != null) {
                    GameWorldComponent gameWorld = GameWorldComponent.KEY.get(MinecraftClient.getInstance().player.getWorld());
                    PlayerPoisonComponent targetPoison = PlayerPoisonComponent.KEY.get(target);
                    if (gameWorld.isRole(MinecraftClient.getInstance().player, KinsWathe.DRUGMAKER) && WatheClient.isPlayerAliveAndInSurvival() && !WatheClient.isInstinctEnabled() && targetPoison.poisonTicks > 0 && !((UUID) targetPoison.poisoner).equals(DELUSION_MARKER)) {
                        ci.setReturnValue(KinsWathe.DRUGMAKER.color());
                    }
                }
            }
        }
    }
}