package org.BsXinQin.kinswathe.mixin.noellesroles.roles.jester;

import dev.doctor4t.wathe.api.Role;
import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerPsychoComponent;
import dev.doctor4t.wathe.game.GameConstants;
import dev.doctor4t.wathe.game.GameFunctions;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.BsXinQin.kinswathe.KinsWatheRoles;
import org.agmas.noellesroles.Noellesroles;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameFunctions.class)
public abstract class JesterJestMixin {

    @Inject(method = "killPlayer(Lnet/minecraft/entity/player/PlayerEntity;ZLnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Identifier;)V", at = @At("HEAD"), cancellable = true)
    private static void setJesterJest(@NotNull PlayerEntity victim, boolean spawnBody, @NotNull PlayerEntity killer, Identifier identifier, @NotNull CallbackInfo ci) {
        if (!FabricLoader.getInstance().isModLoaded("noellesroles")) return;
        if (killer != null) {
            GameWorldComponent gameWorld = GameWorldComponent.KEY.get(victim.getWorld());
            Role killerRole = gameWorld.getRole(killer);
            if (gameWorld.isRole(victim, Noellesroles.JESTER) && KinsWatheRoles.NEUTRAL_ROLES.contains(killerRole)) {
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