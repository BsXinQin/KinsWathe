package org.BsXinQin.kinswathe.client.mixin.gui;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import dev.doctor4t.wathe.client.WatheClient;
import dev.doctor4t.wathe.client.gui.StoreRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.math.MathHelper;
import org.BsXinQin.kinswathe.KinsWatheRoles;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StoreRenderer.class)
public abstract class IncomeIconMixin {

    @Shadow public static float offsetDelta;
    @Shadow public static StoreRenderer.MoneyNumberRenderer view;

    /// 添加收入图标
    @Inject(method = "renderHud", at = @At("HEAD"))
    private static void incomeIcon(@NotNull TextRenderer renderer, @NotNull ClientPlayerEntity player, @NotNull DrawContext context, float delta, @NotNull CallbackInfo ci) {
        if (MinecraftClient.getInstance().player == null) return;
        if (WatheClient.isPlayerAliveAndInSurvival()) {
            if (GameWorldComponent.KEY.get(player.getWorld()).isRole(player, KinsWatheRoles.BELLRINGER) ||
                GameWorldComponent.KEY.get(player.getWorld()).isRole(player, KinsWatheRoles.COOK) ||
                GameWorldComponent.KEY.get(player.getWorld()).isRole(player, KinsWatheRoles.DETECTIVE) ||
                GameWorldComponent.KEY.get(player.getWorld()).isRole(player, KinsWatheRoles.JUDGE) ||
                GameWorldComponent.KEY.get(player.getWorld()).isRole(player, KinsWatheRoles.LICENSED_VILLAIN) ||
                GameWorldComponent.KEY.get(player.getWorld()).isRole(player, KinsWatheRoles.PHYSICIAN)) {
                int balance = PlayerShopComponent.KEY.get(player).balance;
                if (view.getTarget() != (float) balance) {
                    offsetDelta = (float) balance > view.getTarget() ? 0.6F : -0.6F;
                    view.setTarget((float) balance);
                }
                float r = offsetDelta > 0.0F ? 1.0F - offsetDelta : 1.0F;
                float g = offsetDelta < 0.0F ? 1.0F + offsetDelta : 1.0F;
                float b = 1.0F - Math.abs(offsetDelta);
                int colour = MathHelper.packRgb(r, g, b) | -16777216;
                context.getMatrices().push();
                context.getMatrices().translate((float) (context.getScaledWindowWidth() - 12), 6.0F, 0.0F);
                view.render(renderer, context, 0, 0, colour, delta);
                context.getMatrices().pop();
                offsetDelta = MathHelper.lerp(delta / 16.0F, offsetDelta, 0.0F);
            }
        }
    }
}