package org.BsXinQin.kinswathe.client.mixin.roles.licensed_villain;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.client.gui.MoodRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.BsXinQin.kinswathe.KinsWathe;
import org.BsXinQin.kinswathe.KinsWatheRoles;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(MoodRenderer.class)
public class LicensedVillainMoodMixin {

    @Shadow public static float moodOffset;
    @Shadow public static float moodTextWidth;
    @Shadow public static float moodRender;
    @Shadow public static float moodAlpha;
    @Shadow public static Random random;
    @Unique private static final Identifier LICENSED_VILLAIN_MOOD = Identifier.of(KinsWathe.MOD_ID, "hud/mood_licensed_villain");

    @Inject(method = "renderKiller", at = @At("HEAD"), cancellable = true)
    private static void getMoodHud(TextRenderer textRenderer, @NotNull DrawContext context, @NotNull CallbackInfo ci) {
        if (MinecraftClient.getInstance().player == null) return;
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(MinecraftClient.getInstance().player.getWorld());
        if (gameWorld.isRole(MinecraftClient.getInstance().player, KinsWatheRoles.LICENSED_VILLAIN)) {
            context.getMatrices().push();
            context.getMatrices().translate(0.0F, 3.0F * moodOffset, 0.0F);
            context.drawGuiTexture(LICENSED_VILLAIN_MOOD, 5, 6, 14, 17);
            context.getMatrices().pop();
            context.getMatrices().push();
            context.getMatrices().translate(0.0F, 10.0F * moodOffset, 0.0F);
            MatrixStack var10000 = context.getMatrices();
            var10000.translate(26.0F, (float)(8 + 9), 0.0F);
            context.getMatrices().scale((moodTextWidth - 8.0F) * moodRender, 1.0F, 1.0F);
            context.fill(0, 0, 1, 1, KinsWatheRoles.LICENSED_VILLAIN.color() | (int)(moodAlpha * 255.0F) << 24);
            context.getMatrices().pop();
            ci.cancel();
        }
    }
}