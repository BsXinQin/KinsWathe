package org.BsXinQin.kinswathe.mixin.host;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.game.gamemode.MurderGameMode;
import net.minecraft.entity.player.PlayerEntity;
import org.BsXinQin.kinswathe.KinsWathe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MurderGameMode.class)
public abstract class PassiveIncomeMixin {

    /// 设置被动收入
    @WrapOperation(method = "tickServerGameLoop", at = @At(value = "INVOKE", target = "Ldev/doctor4t/wathe/cca/GameWorldComponent;canUseKillerFeatures(Lnet/minecraft/entity/player/PlayerEntity;)Z"))
    public boolean PassiveIncome(GameWorldComponent gameWorld, PlayerEntity player, Operation<Boolean> original) {
        if (gameWorld.isRole(player, KinsWathe.COOK) ||
            gameWorld.isRole(player, KinsWathe.JUDGE)) return true;
        return original.call(gameWorld,player);
    }
}