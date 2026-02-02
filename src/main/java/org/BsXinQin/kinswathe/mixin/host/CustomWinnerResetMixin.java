package org.BsXinQin.kinswathe.mixin.host;

import dev.doctor4t.wathe.game.GameFunctions;
import net.minecraft.server.world.ServerWorld;
import org.BsXinQin.kinswathe.component.CustomWinnerComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameFunctions.class)
public class CustomWinnerResetMixin {

    @Inject(method = "initializeGame", at = @At("HEAD"))
    private static void CustomWinnerReset(ServerWorld serverWorld, CallbackInfo ci) {
        CustomWinnerComponent customWinner = CustomWinnerComponent.KEY.get(serverWorld);
        if (customWinner != null) {
            customWinner.reset();
        }
    }
}