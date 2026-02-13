package org.BsXinQin.kinswathe.client.mixin.gui;

import dev.doctor4t.wathe.api.Role;
import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.client.WatheClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import org.BsXinQin.kinswathe.KinsWathe;
import org.BsXinQin.kinswathe.component.ConfigWorldComponent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class StaminaBarMixin {

    @Unique private static final Identifier STAMINA_BAR_TEXTURE = Identifier.of(KinsWathe.MOD_ID, "textures/gui/container/stamina_bar.png");

    @Inject(method = "render", at = @At("TAIL"))
    public void staminaBar(@NotNull DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (MinecraftClient.getInstance().player == null) return;
        if (WatheClient.isPlayerAliveAndInSurvival()) {
            GameWorldComponent gameWorld = GameWorldComponent.KEY.get(MinecraftClient.getInstance().player.getWorld());
            Role role = gameWorld.getRole(MinecraftClient.getInstance().player);
            if (ConfigWorldComponent.KEY.get(MinecraftClient.getInstance().player.getWorld()).EnableStaminaBar && !MinecraftClient.getInstance().options.hudHidden && role != null) {
                int maxSprintTime = role.getMaxSprintTime();
                if (maxSprintTime == -1) {
                    getStaminaBarInfinite(context, 1.0f, 0xFF00FF00);
                } else {
                    NbtCompound nbt = MinecraftClient.getInstance().player.writeNbt(new NbtCompound());
                    getStaminaBarRequire(context, nbt.getFloat("sprintingTicks"), maxSprintTime);
                }
            }
        }
    }

    @Unique
    private void getStaminaBarRequire(@NotNull DrawContext context, float sprintTime, float maxSprintTime) {

        int screenWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
        int screenHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();

        int textureWidth = 174;
        int textureHeight = 11;

        int innerWidth = 166;
        int innerHeight = 3;

        int horizontalBorder = (textureWidth - innerWidth) / 2;
        int verticalBorder = (textureHeight - innerHeight) / 2;

        int x = screenWidth / 2 - textureWidth / 2;
        int y = screenHeight - 38;

        float percent = Math.max(0, Math.min(1, sprintTime / maxSprintTime));
        context.drawTexture(STAMINA_BAR_TEXTURE, x, y, 0, 0, textureWidth, textureHeight, textureWidth, textureHeight);
        int fillWidth = (int) (innerWidth * percent);
        if (fillWidth <= 0) return;

        int barX = x + horizontalBorder;
        int barY = y + verticalBorder;

        int barColor;
        if (percent > 0.7f) {
            barColor = 0xFF00FF00;
        } else if (percent > 0.3f) {
            barColor = 0xFFFFFF00;
        } else {
            barColor = 0xFFFF0000;
        }
        context.fill(barX, barY, barX + fillWidth, barY + innerHeight, barColor);
    }

    @Unique
    private void getStaminaBarInfinite(@NotNull DrawContext context, float sprintTime, int barColor) {

        int screenWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
        int screenHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();

        int textureWidth = 174;
        int textureHeight = 11;

        int innerWidth = 166;
        int innerHeight = 3;

        int horizontalBorder = (textureWidth - innerWidth) / 2;
        int verticalBorder = (textureHeight - innerHeight) / 2;

        int x = screenWidth / 2 - textureWidth / 2;
        int y = screenHeight - 38;

        context.drawTexture(STAMINA_BAR_TEXTURE, x, y, 0, 0, textureWidth, textureHeight, textureWidth, textureHeight);
        int fillWidth = (int) (innerWidth * sprintTime);
        if (fillWidth <= 0) return;

        int barX = x + horizontalBorder;
        int barY = y + verticalBorder;

        context.fill(barX, barY, barX + fillWidth, barY + innerHeight, barColor);
    }
}