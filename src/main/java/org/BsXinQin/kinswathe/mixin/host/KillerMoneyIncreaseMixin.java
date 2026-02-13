package org.BsXinQin.kinswathe.mixin.host;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import dev.doctor4t.wathe.game.GameFunctions;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameFunctions.class)
public class KillerMoneyIncreaseMixin {

    @Inject(method = "killPlayer(Lnet/minecraft/entity/player/PlayerEntity;ZLnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Identifier;)V", at = @At("HEAD"))
    private static void increaseKillerMoney(@NotNull PlayerEntity victim, boolean spawnBody, @NotNull PlayerEntity player, Identifier identifier, CallbackInfo ci) {
        if (FabricLoader.getInstance().isModLoaded("noellesroles")) return;
        if (player != null) {
            GameWorldComponent gameWorld = GameWorldComponent.KEY.get(victim.getWorld());
            if (!gameWorld.isInnocent(player)) {
                PlayerShopComponent playerShop = PlayerShopComponent.KEY.get(player);
                playerShop.addToBalance(100);
            }
        }
    }
}