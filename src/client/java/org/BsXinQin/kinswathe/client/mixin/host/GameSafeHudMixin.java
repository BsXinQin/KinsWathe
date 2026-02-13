package org.BsXinQin.kinswathe.client.mixin.host;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.client.WatheClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import org.BsXinQin.kinswathe.component.ConfigWorldComponent;
import org.BsXinQin.kinswathe.component.GameSafeComponent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(InGameHud.class)
public abstract class GameSafeHudMixin {

    @Inject(method = "render", at = @At("HEAD"))
    private void getGameSafeHudMixin(@NotNull DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (MinecraftClient.getInstance().player == null) return;
        if (!ConfigWorldComponent.KEY.get(MinecraftClient.getInstance().player.getWorld()).EnableGameSafeTime) return;
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(MinecraftClient.getInstance().player.getWorld());
        GameSafeComponent playerSafe = GameSafeComponent.KEY.get(MinecraftClient.getInstance().player);
        if (gameWorld.isRunning() && WatheClient.isPlayerAliveAndInSurvival() && playerSafe.isGameSafe) {
            int width = context.getScaledWindowWidth();
            int height = context.getScaledWindowHeight();
            int safeTime = ConfigWorldComponent.KEY.get(MinecraftClient.getInstance().player.getWorld()).StartingCooldown - playerSafe.safeTicks / 20 - 1;
            Text safeTimeText;
            if (safeTime <= 0) {
                safeTimeText = Text.translatable("tip.kinswathe.game_no_safe_time", safeTime);
            } else {
                safeTimeText = Text.translatable("tip.kinswathe.game_safe_time", safeTime);
            }
            context.drawCenteredTextWithShadow(MinecraftClient.getInstance().textRenderer, safeTimeText, width / 2, 20, Color.GREEN.getRGB());
        }
    }
}