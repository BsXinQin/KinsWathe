package org.BsXinQin.kinswathe.client.mixin.roles.detective;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import dev.doctor4t.wathe.client.WatheClient;
import dev.doctor4t.wathe.client.gui.RoleNameRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.text.Text;
import net.minecraft.util.hit.EntityHitResult;
import org.BsXinQin.kinswathe.KinsWathe;
import org.BsXinQin.kinswathe.component.AbilityPlayerComponent;
import org.BsXinQin.kinswathe.client.KinsWatheClient;
import org.BsXinQin.kinswathe.component.ConfigWorldComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RoleNameRenderer.class)
public class DetectiveTargetHudMixin {

    @Unique private static PlayerEntity DetectiveTarget = null;

    @Inject(method = "renderHud", at = @At("TAIL"))
    private static void DetectiveTargetHud(TextRenderer renderer, ClientPlayerEntity player, DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (WatheClient.isPlayerAliveAndInSurvival()) {
            GameWorldComponent gameWorld = GameWorldComponent.KEY.get(player.getWorld());
            AbilityPlayerComponent ability = AbilityPlayerComponent.KEY.get(player);
            PlayerShopComponent playerShop = PlayerShopComponent.KEY.get(player);
            if (gameWorld.isRole(player, KinsWathe.DETECTIVE)) {
                if (ability.cooldown > 0 || playerShop.balance < ConfigWorldComponent.KEY.get(player.getWorld()).DetectiveAbilityPrice) return;
                if (DetectiveTarget != null && DetectiveTarget != player) {
                    context.getMatrices().push();
                    context.getMatrices().translate((float) context.getScaledWindowWidth() / 2.0F, (float) context.getScaledWindowHeight() / 2.0F + 6.0F, 0.0F);
                    context.getMatrices().scale(0.6F, 0.6F, 1.0F);
                    Text targetInfo = Text.translatable("hud.kinswathe.detective.target", KinsWatheClient.abilityBind.getBoundKeyLocalizedText()).withColor(KinsWathe.DETECTIVE.color());
                    context.drawTextWithShadow(renderer, targetInfo, -renderer.getWidth(targetInfo) / 2, 32, KinsWathe.DETECTIVE.color());
                    context.getMatrices().pop();
                }
            }
        }
    }

    @Inject(method = "renderHud", at = @At(value = "INVOKE", target = "Ldev/doctor4t/wathe/game/GameFunctions;isPlayerSpectatingOrCreative(Lnet/minecraft/entity/player/PlayerEntity;)Z"))
    private static void getDetectiveTarget(TextRenderer renderer, ClientPlayerEntity player, DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(player.getWorld());
        if (!WatheClient.isPlayerAliveAndInSurvival() || !gameWorld.isRole(MinecraftClient.getInstance().player, KinsWathe.DETECTIVE)) {
            DetectiveTarget = null;
            return;
        }
        float range = 2.0F;
        var hitResult = ProjectileUtil.getCollision(player, (entity) -> {
            return entity instanceof PlayerEntity target && target != player;
        }, (double) range);
        DetectiveTarget = null;
        if (hitResult instanceof EntityHitResult entityHit) {
            var entity = entityHit.getEntity();
            if (entity instanceof PlayerEntity targetPlayer) {
                DetectiveTarget = targetPlayer;
            }
        }
    }
}