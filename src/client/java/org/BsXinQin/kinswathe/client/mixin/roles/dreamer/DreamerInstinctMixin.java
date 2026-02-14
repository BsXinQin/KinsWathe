//package org.BsXinQin.kinswathe.client.mixin.roles.dreamer;
//
//import dev.doctor4t.wathe.client.WatheClient;
//import dev.doctor4t.wathe.game.GameFunctions;
//import net.minecraft.client.MinecraftClient;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.player.PlayerEntity;
//import org.BsXinQin.kinswathe.KinsWatheRoles;
//import org.BsXinQin.kinswathe.roles.dreamer.DreamerComponent;
//import org.jetbrains.annotations.NotNull;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//@Mixin(WatheClient.class)
//public abstract class DreamerInstinctMixin {
//
//    @Inject(method = "getInstinctHighlight", at = @At("HEAD"), cancellable = true)
//    private static void getInstinctHighlight(@NotNull Entity target, @NotNull CallbackInfoReturnable<Integer> cir) {
//        if (MinecraftClient.getInstance().player == null) return;
//        if (target instanceof @NotNull PlayerEntity targetPlayer) {
//            if (GameFunctions.isPlayerAliveAndSurvival(targetPlayer)) {
//                DreamerComponent targetDream = DreamerComponent.KEY.get(targetPlayer);
//                PlayerEntity dreamer = targetPlayer.getWorld().getPlayerByUuid(targetDream.dreamerUUID);
//                if ((MinecraftClient.getInstance().player == dreamer && !WatheClient.isKiller() && WatheClient.isPlayerAliveAndInSurvival() && targetDream.dreamArmor > 0) ||
//                    (MinecraftClient.getInstance().player == dreamer && WatheClient.isKiller() && WatheClient.isPlayerAliveAndInSurvival() && !WatheClient.isInstinctEnabled() && targetDream.dreamArmor > 0)) {
//                    cir.setReturnValue(KinsWatheRoles.DREAMER.color());
//                }
//            }
//        }
//    }
//}