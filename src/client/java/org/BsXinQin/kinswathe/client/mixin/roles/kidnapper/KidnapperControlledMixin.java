package org.BsXinQin.kinswathe.client.mixin.roles.kidnapper;

import dev.doctor4t.wathe.client.WatheClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import org.BsXinQin.kinswathe.KinsWatheRoles;
import org.BsXinQin.kinswathe.roles.kidnapper.KidnapperComponent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class KidnapperControlledMixin {

    @Inject(method = "render", at = @At("HEAD"))
    private void getControlledHud(@NotNull DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (MinecraftClient.getInstance().player == null) return;
        KidnapperComponent playerControlled = KidnapperComponent.KEY.get(MinecraftClient.getInstance().player);
        if (playerControlled.controlTicks > 0) {
            if (WatheClient.isPlayerAliveAndInSurvival()) {
                int width = context.getScaledWindowWidth();
                int height = context.getScaledWindowHeight();
                context.fill(0, 0, width, height, 0xFF000000);
                Text warningText = Text.translatable("tip.kinswathe.kidnapper.warning");
                context.drawCenteredTextWithShadow(MinecraftClient.getInstance().textRenderer, warningText, width / 2, height / 2 - 10, KinsWatheRoles.KIDNAPPER.color());
                Text countdownText = Text.translatable("tip.kinswathe.kidnapper.timeleft", playerControlled.controlTicks / 20);
                context.drawCenteredTextWithShadow(MinecraftClient.getInstance().textRenderer, countdownText, width / 2, height / 2 + 10, KinsWatheRoles.KIDNAPPER.color());
            }
        }
    }
}