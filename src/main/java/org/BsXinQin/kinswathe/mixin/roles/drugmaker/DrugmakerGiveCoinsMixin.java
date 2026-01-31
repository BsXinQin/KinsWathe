package org.BsXinQin.kinswathe.mixin.roles.drugmaker;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerPoisonComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import dev.doctor4t.wathe.game.GameFunctions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.BsXinQin.kinswathe.KinsWathe;
import org.BsXinQin.kinswathe.component.ConfigWorldComponent;
import org.agmas.noellesroles.Noellesroles;
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

    @Shadow @Final private PlayerEntity player;
    @Unique private static final UUID DELUSION_MARKER = UUID.fromString("00000000-0000-0000-dead-c0de00000000");

    @Inject(method = "setPoisonTicks", at = @At("HEAD"))
    private void DrugmakerGiveCoins(int ticks, UUID poisoner, CallbackInfo ci) {
        if (ticks <= 0 || poisoner == null) return;
        if (KinsWathe.NOELLESROLES_LOADED && poisoner.equals(DELUSION_MARKER)) return;
        if (!(player instanceof ServerPlayerEntity poisonedPlayer) || !GameFunctions.isPlayerAliveAndSurvival(poisonedPlayer)) return;
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(poisonedPlayer.getWorld());
        PlayerEntity poisonerEntity = poisonedPlayer.getServerWorld().getPlayerByUuid(poisoner);
        if (gameWorld.isRole(poisonedPlayer, KinsWathe.ROBOT)) return;
        if (poisonerEntity instanceof ServerPlayerEntity poisonerPlayer) {
            if (KinsWathe.NOELLESROLES_LOADED && gameWorld.isRole(poisonerPlayer, Noellesroles.BARTENDER)) return;
        }
        for (ServerPlayerEntity onlinePlayer : poisonedPlayer.getServer().getPlayerManager().getPlayerList()) {
            if (!GameFunctions.isPlayerAliveAndSurvival(onlinePlayer)) continue;
            if (gameWorld.isRole(onlinePlayer, KinsWathe.DRUGMAKER)) {
                PlayerShopComponent playerShop = PlayerShopComponent.KEY.get(onlinePlayer);
                onlinePlayer.sendMessage(Text.translatable("tip.kinswathe.drugmaker.posioned").withColor(KinsWathe.DRUGMAKER.color()), true);
                playerShop.addToBalance(ConfigWorldComponent.KEY.get(player.getWorld()).DrugmakerGetCoins);
                playerShop.sync();
            }
        }
    }
}