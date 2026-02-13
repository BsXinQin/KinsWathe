package org.BsXinQin.kinswathe.mixin.modifiers.taskmaster;

import dev.doctor4t.wathe.api.Role;
import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerMoodComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import net.minecraft.entity.player.PlayerEntity;
import org.BsXinQin.kinswathe.KinsWatheRoles;
import org.agmas.harpymodloader.component.WorldModifierComponent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerMoodComponent.class)
public abstract class TaskmasterGiveCoinsMixin {

    @Shadow public abstract float getMood();
    @Shadow @Final @NotNull private PlayerEntity player;

    @Inject(method = "setMood", at = @At("HEAD"))
    void giveTaskmasterCoins(float mood, CallbackInfo ci) {
        WorldModifierComponent modifier = WorldModifierComponent.KEY.get(this.player.getWorld());
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(this.player.getWorld());
        PlayerShopComponent playerShop = PlayerShopComponent.KEY.get(this.player);
        Role role = gameWorld.getRole(this.player);
        if (role != null && mood > getMood()) {
            if (modifier.isModifier(this.player, KinsWatheRoles.TASKMASTER)) {
                if (role.canUseKiller()) {
                    playerShop.balance += 50;
                    playerShop.sync();
                } else {
                    playerShop.balance += 25;
                    playerShop.sync();
                }
            }
        }
    }
}