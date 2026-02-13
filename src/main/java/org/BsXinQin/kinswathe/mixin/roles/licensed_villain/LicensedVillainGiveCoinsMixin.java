package org.BsXinQin.kinswathe.mixin.roles.licensed_villain;

import dev.doctor4t.wathe.api.Role;
import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerMoodComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import net.minecraft.entity.player.PlayerEntity;
import org.BsXinQin.kinswathe.KinsWatheRoles;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerMoodComponent.class)
public abstract class LicensedVillainGiveCoinsMixin {

    @Shadow public abstract float getMood();
    @Shadow @Final @NotNull private PlayerEntity player;

    @Inject(method = "setMood", at = @At("HEAD"))
    void giveLicensedVillainCoins(float mood, CallbackInfo ci) {
        GameWorldComponent gameWorld = (GameWorldComponent)GameWorldComponent.KEY.get(this.player.getWorld());
        Role role = gameWorld.getRole(this.player);
        if (role != null && mood > getMood()) {
            if (gameWorld.isRole(this.player, KinsWatheRoles.LICENSED_VILLAIN)) {
                PlayerShopComponent playerShop = PlayerShopComponent.KEY.get(this.player);
                playerShop.addToBalance(50);
            }
        }
    }
}