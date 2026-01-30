package org.BsXinQin.kinswathe.mixin.noellesroles.roles.jester;

import dev.doctor4t.wathe.api.Role;
import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerPsychoComponent;
import dev.doctor4t.wathe.game.GameConstants;
import dev.doctor4t.wathe.game.GameFunctions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.BsXinQin.kinswathe.KinsWathe;
import org.agmas.noellesroles.Noellesroles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//这里不需要肘赢，所以不加优先级
@Mixin(GameFunctions.class)
public abstract class JesterJestMixin {

    @Inject(method = "killPlayer(Lnet/minecraft/entity/player/PlayerEntity;ZLnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Identifier;)V", at = @At("HEAD"), cancellable = true)
    private static void JesterJest(PlayerEntity victim, boolean spawnBody, PlayerEntity killer, Identifier identifier, CallbackInfo ci) {
        if (KinsWathe.NOELLESROLES_LOADED) {
            if (killer != null) {
                GameWorldComponent gameWorld = GameWorldComponent.KEY.get(victim.getWorld());
                Role killerRole = gameWorld.getRole(killer);
                if (gameWorld.isRole(victim, Noellesroles.JESTER) && KinsWathe.NEUTRAL_ROLES.contains(killerRole)) {
                    PlayerPsychoComponent playerPsycho = PlayerPsychoComponent.KEY.get(victim);
                    if (playerPsycho.getPsychoTicks() <= 0) {
                        playerPsycho.startPsycho();
                        playerPsycho.psychoTicks = GameConstants.getInTicks(0, 45);
                        playerPsycho.armour = 0;
                        ci.cancel();
                    }
                }
            }
        }
    }
}