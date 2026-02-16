package org.BsXinQin.kinswathe.mixin.host;

import dev.doctor4t.wathe.game.GameFunctions;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.BsXinQin.kinswathe.component.CustomWinnerComponent;
import org.BsXinQin.kinswathe.component.GameSafeComponent;
import org.BsXinQin.kinswathe.roles.cook.CookComponent;
import org.BsXinQin.kinswathe.roles.dreamer.DreamerComponent;
import org.BsXinQin.kinswathe.roles.dreamer.DreamerKillerComponent;
import org.BsXinQin.kinswathe.roles.hunter.HunterComponent;
import org.BsXinQin.kinswathe.roles.kidnapper.KidnapperComponent;
import org.BsXinQin.kinswathe.roles.physician.PhysicianComponent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameFunctions.class)
public class GameFnctionsResetMixin {

    @Inject(method = "initializeGame", at = @At("HEAD"))
    private static void initializeGame(@NotNull ServerWorld serverWorld, CallbackInfo ci) {
        CustomWinnerComponent customWinner = CustomWinnerComponent.KEY.get(serverWorld);
        if (customWinner != null) customWinner.reset();
    }

    @Inject(method = "resetPlayer", at = @At("TAIL"))
    private static void resetPlayer(@NotNull ServerPlayerEntity player, CallbackInfo ci) {
        GameSafeComponent.KEY.get(player).reset();
        CookComponent.KEY.get(player).reset();
        DreamerComponent.KEY.get(player).reset();
        DreamerKillerComponent.KEY.get(player).reset();
        HunterComponent.KEY.get(player).reset();
        KidnapperComponent.KEY.get(player).reset();
        PhysicianComponent.KEY.get(player).reset();
    }
}