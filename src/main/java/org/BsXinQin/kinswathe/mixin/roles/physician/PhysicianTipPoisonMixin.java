package org.BsXinQin.kinswathe.mixin.roles.physician;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerPoisonComponent;
import dev.doctor4t.wathe.game.GameFunctions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.BsXinQin.kinswathe.KinsWatheRoles;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.util.UUID;

@Mixin(PlayerPoisonComponent.class)
public abstract class PhysicianTipPoisonMixin {

    @Shadow @Final @NotNull private PlayerEntity player;

    @Inject(method = "setPoisonTicks", at = @At("HEAD"))
    private void tipPhysicianPoison(int ticks, @NotNull UUID poisoner, CallbackInfo ci) {
        if (ticks <= 0 || poisoner == null || GameFunctions.isPlayerSpectatingOrCreative(this.player)) return;
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(this.player.getWorld());
        if (gameWorld.isRole(this.player, KinsWatheRoles.ROBOT) || !(this.player instanceof ServerPlayerEntity)) return;
        for (ServerPlayerEntity serverPlayer : this.player.getServer().getPlayerManager().getPlayerList()) {
            if (serverPlayer == null) return;
            if (gameWorld.isRole(serverPlayer, KinsWatheRoles.PHYSICIAN) && GameFunctions.isPlayerAliveAndSurvival(serverPlayer)) {
                serverPlayer.sendMessage(Text.translatable("tip.kinswathe.physician.poisoned").withColor(Color.RED.getRGB()), true);
            }
        }
    }
}