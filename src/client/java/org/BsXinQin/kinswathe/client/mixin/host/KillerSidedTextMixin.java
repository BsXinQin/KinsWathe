package org.BsXinQin.kinswathe.client.mixin.host;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.client.gui.RoleNameRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import org.BsXinQin.kinswathe.KinsWatheRoles;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RoleNameRenderer.class)
public class KillerSidedTextMixin {

    @WrapOperation(method = "renderHud", at = @At(value = "INVOKE", target = "Ldev/doctor4t/wathe/cca/GameWorldComponent;canUseKillerFeatures(Lnet/minecraft/entity/player/PlayerEntity;)Z", ordinal = 0))
    private static boolean rolesKillerNeutralText(@NotNull GameWorldComponent gameWorld, @NotNull PlayerEntity player, @NotNull Operation<Boolean> original) {
        if (MinecraftClient.getInstance().player != null) {
            if (gameWorld.getRole(player) != null) {
                if (KinsWatheRoles.KILLER_NEUTRAL_ROLES.contains(gameWorld.getRole(player))) {
                    return true;
                }
            }
        }
        return original.call(gameWorld, player);
    }
}
