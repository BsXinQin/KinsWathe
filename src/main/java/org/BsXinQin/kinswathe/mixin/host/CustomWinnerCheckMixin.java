package org.BsXinQin.kinswathe.mixin.host;

import com.llamalad7.mixinextras.sugar.Local;
import dev.doctor4t.wathe.cca.GameRoundEndComponent;
import net.minecraft.world.World;
import org.BsXinQin.kinswathe.component.CustomWinnerComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(GameRoundEndComponent.class)
public class CustomWinnerCheckMixin {

    @Shadow @Final private World world;

    @Inject(method = "didWin", at = @At(value = "RETURN", ordinal = 1), cancellable = true)
    private void CustomWinnerCheck(UUID uuid, CallbackInfoReturnable<Boolean> ci, @Local(name = "detail") GameRoundEndComponent.RoundEndData detail) {
        var customWinner = CustomWinnerComponent.KEY.get(world);
        if (customWinner == null || !customWinner.hasCustomWinner()) return;
        boolean isWinner = customWinner.getWinners().stream().anyMatch(player -> player != null && player.getUuid().equals(uuid));
        if (isWinner) {
            ci.setReturnValue(true);
        }
    }
}