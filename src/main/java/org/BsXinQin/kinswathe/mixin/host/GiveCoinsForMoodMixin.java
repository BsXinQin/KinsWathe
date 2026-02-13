package org.BsXinQin.kinswathe.mixin.host;

import dev.doctor4t.wathe.api.Role;
import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerMoodComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerMoodComponent.class)
public abstract class GiveCoinsForMoodMixin {

    @Shadow public abstract float getMood();
    @Shadow @Final @NotNull private PlayerEntity player;

    @Inject(method = "setMood", at = @At("HEAD"))
    void giveCoinsForMood(float mood, CallbackInfo ci) {
        if (FabricLoader.getInstance().isModLoaded("noellesroles")) return;
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(this.player.getWorld());
        PlayerShopComponent playerShop = PlayerShopComponent.KEY.get(this.player);
        Role role = gameWorld.getRole(this.player);
        if (role != null && mood > getMood()) {
            if (role.getMoodType().equals(Role.MoodType.REAL)) {
                playerShop.balance += 50;
                playerShop.sync();
            }
        }
    }
}