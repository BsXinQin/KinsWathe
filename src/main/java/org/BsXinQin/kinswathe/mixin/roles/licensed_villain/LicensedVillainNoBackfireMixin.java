package org.BsXinQin.kinswathe.mixin.roles.licensed_villain;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.Item;
import net.minecraft.server.network.ServerPlayerEntity;
import org.BsXinQin.kinswathe.KinsWathe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "dev.doctor4t.wathe.util.GunShootPayload$Receiver", priority = 500)
public class LicensedVillainNoBackfireMixin {

    @Inject(method = "lambda$receive$2", at = @At("HEAD"), remap = false, cancellable = true)
    private static void LicensedVillainNoBackfire(ServerPlayNetworking.Context context, ServerPlayerEntity player, Item revolver, CallbackInfo ci) {
        if (player.isCreative()) return;
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(player.getWorld());
        if (gameWorld.isRole(player, KinsWathe.LICENSED_VILLAIN)) {
            ci.cancel();
        }
    }
}