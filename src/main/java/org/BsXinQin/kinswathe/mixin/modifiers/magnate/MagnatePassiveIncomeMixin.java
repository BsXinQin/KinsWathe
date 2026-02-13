package org.BsXinQin.kinswathe.mixin.modifiers.magnate;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import dev.doctor4t.wathe.game.GameConstants;
import dev.doctor4t.wathe.game.gamemode.MurderGameMode;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.BsXinQin.kinswathe.KinsWatheRoles;
import org.agmas.harpymodloader.component.WorldModifierComponent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MurderGameMode.class)
public abstract class MagnatePassiveIncomeMixin {

    @Inject(method = "tickServerGameLoop", at = @At("TAIL"))
    public void setMagnatePassiveIncome(@NotNull ServerWorld serverWorld, @NotNull GameWorldComponent gameWorld, CallbackInfo ci) {
        if (!gameWorld.isRunning()) return;
        for (ServerPlayerEntity player : serverWorld.getPlayers()) {
            WorldModifierComponent modifier = WorldModifierComponent.KEY.get(serverWorld);
            PlayerShopComponent playerShop = PlayerShopComponent.KEY.get(player);
            if (modifier.isModifier(player, KinsWatheRoles.MAGNATE)) {
                playerShop.balance += GameConstants.PASSIVE_MONEY_TICKER.apply(serverWorld.getTime());
                playerShop.sync();
            }
        }
    }
}