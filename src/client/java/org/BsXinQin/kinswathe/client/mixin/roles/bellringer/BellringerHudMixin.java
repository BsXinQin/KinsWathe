package org.BsXinQin.kinswathe.client.mixin.roles.bellringer;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import dev.doctor4t.wathe.client.WatheClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import org.BsXinQin.kinswathe.KinsWatheRoles;
import org.BsXinQin.kinswathe.client.KinsWatheInitializeClient;
import org.BsXinQin.kinswathe.component.AbilityPlayerComponent;
import org.BsXinQin.kinswathe.component.ConfigWorldComponent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class BellringerHudMixin {

    @Shadow public abstract TextRenderer getTextRenderer();

    @Inject(method = "render", at = @At("TAIL"))
    public void getAbilityHud(@NotNull DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (MinecraftClient.getInstance().player == null) return;
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(MinecraftClient.getInstance().player.getWorld());
        AbilityPlayerComponent ability = AbilityPlayerComponent.KEY.get(MinecraftClient.getInstance().player);
        PlayerShopComponent playerShop = PlayerShopComponent.KEY.get(MinecraftClient.getInstance().player);
        if (gameWorld.isRole(MinecraftClient.getInstance().player, KinsWatheRoles.BELLRINGER) && WatheClient.isPlayerAliveAndInSurvival()) {
            int drawY = context.getScaledWindowHeight();

            Text line = Text.translatable("tip.kinswathe.ability.can_use", KinsWatheInitializeClient.abilityBind.getBoundKeyLocalizedText());

            if (playerShop.balance < ConfigWorldComponent.KEY.get(MinecraftClient.getInstance().player.getWorld()).BellringerAbilityPrice) {
                line = Text.translatable("tip.kinswathe.ability.not_enough_money", ConfigWorldComponent.KEY.get(MinecraftClient.getInstance().player.getWorld()).BellringerAbilityPrice);
            }
            if (ability.cooldown > 0) {
                line = Text.translatable("tip.kinswathe.cooldown", ability.cooldown / 20);
            }

            drawY -= getTextRenderer().getWrappedLinesHeight(line, 999999);
            context.drawTextWithShadow(getTextRenderer(), line, context.getScaledWindowWidth() - getTextRenderer().getWidth(line), drawY, KinsWatheRoles.BELLRINGER.color());
        }
    }
}