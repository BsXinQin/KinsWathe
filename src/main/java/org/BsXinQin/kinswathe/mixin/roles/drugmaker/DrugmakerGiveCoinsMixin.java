package org.BsXinQin.kinswathe.mixin.roles.drugmaker;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerPoisonComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import dev.doctor4t.wathe.game.GameFunctions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.BsXinQin.kinswathe.KinsWatheConfig;
import org.BsXinQin.kinswathe.KinsWatheRoles;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(PlayerPoisonComponent.class)
public abstract class DrugmakerGiveCoinsMixin {

    @Shadow @Final @NotNull private PlayerEntity player;
    @Unique private static final UUID DELUSION_MARKER = UUID.fromString("00000000-0000-0000-dead-c0de00000000");

    @Inject(method = "setPoisonTicks", at = @At("HEAD"))
    private void giveDrugmakerCoins(int ticks, @NotNull UUID poisoner, CallbackInfo ci) {
        if (ticks <= 0 || poisoner == null || GameFunctions.isPlayerSpectatingOrCreative(this.player)) return;
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(this.player.getWorld());
        if (gameWorld.isRole(this.player, KinsWatheRoles.ROBOT) || !(this.player instanceof ServerPlayerEntity) || poisoner.equals(DELUSION_MARKER)) return;
        for (ServerPlayerEntity serverPlayer : this.player.getServer().getPlayerManager().getPlayerList()) {
            if (serverPlayer == null) return;
            if (gameWorld.isRole(serverPlayer, KinsWatheRoles.DRUGMAKER) && GameFunctions.isPlayerAliveAndSurvival(serverPlayer)) {
                PlayerShopComponent playerShop = PlayerShopComponent.KEY.get(serverPlayer);
                serverPlayer.sendMessage(Text.translatable("tip.kinswathe.drugmaker.poisoned").withColor(KinsWatheRoles.DRUGMAKER.color()), true);
                playerShop.balance += KinsWatheConfig.HANDLER.instance().DrugmakerGetCoins;
                playerShop.sync();
            }
        }
    }
}