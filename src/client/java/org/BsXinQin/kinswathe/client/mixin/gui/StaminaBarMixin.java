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
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class StaminaBarMixin {

    @Unique private static final Identifier STAMINA_BAR_TEXTURE = Identifier.of(KinsWathe.MOD_ID, "textures/gui/container/stamina_bar.png");

    @Inject(method = "render", at = @At("TAIL"))
    public void StaminaBar(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        if (WatheClient.isPlayerAliveAndInSurvival()) {
            GameWorldComponent gameWorld = GameWorldComponent.KEY.get(client.player.getWorld());
            Role role = gameWorld.getRole(client.player);
            if (ConfigWorldComponent.KEY.get(client.player.getWorld()).EnableStaminaBar && !client.options.hudHidden && role != null) {
                int maxSprintTime = role.getMaxSprintTime();
                if (maxSprintTime == -1) {
                    StaminaBarInfinite(context, 1.0f, 0xFF00FF00);
                } else {
                    try {
                        NbtCompound nbt = client.player.writeNbt(new NbtCompound());
                        if (nbt.contains("sprintingTicks")) {
                            float stamina = nbt.getFloat("sprintingTicks");
                            StaminaBarRequire(context, stamina, maxSprintTime);
                        }
                    } catch (Exception ignored) {}
                }
            }
        }
    }

    @Unique
    private void StaminaBarRequire(DrawContext context, float stamina, float maxSprintTime) {
        MinecraftClient client = MinecraftClient.getInstance();

        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        int textureWidth = 174;
        int textureHeight = 11;

        int innerWidth = 166;
        int innerHeight = 3;

        int horizontalBorder = (textureWidth - innerWidth) / 2;
        int verticalBorder = (textureHeight - innerHeight) / 2;

        int x = screenWidth / 2 - textureWidth / 2;
        int y = screenHeight - 38;

        float percent = Math.max(0, Math.min(1, stamina / maxSprintTime));
        context.drawTexture(STAMINA_BAR_TEXTURE, x, y, 0, 0, textureWidth, textureHeight, textureWidth, textureHeight);
        int fillWidth = (int)(innerWidth * percent);
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
    private void StaminaBarInfinite(DrawContext context, float percent, int barColor) {
        MinecraftClient client = MinecraftClient.getInstance();

        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();

        int textureWidth = 174;
        int textureHeight = 11;

        int innerWidth = 166;
        int innerHeight = 3;

        int horizontalBorder = (textureWidth - innerWidth) / 2;
        int verticalBorder = (textureHeight - innerHeight) / 2;

        int x = screenWidth / 2 - textureWidth / 2;
        int y = screenHeight - 38;

        context.drawTexture(STAMINA_BAR_TEXTURE, x, y, 0, 0, textureWidth, textureHeight, textureWidth, textureHeight);
        int fillWidth = (int)(innerWidth * percent);
        if (fillWidth <= 0) return;

        int barX = x + horizontalBorder;
        int barY = y + verticalBorder;

        context.fill(barX, barY, barX + fillWidth, barY + innerHeight, barColor);
    }
}