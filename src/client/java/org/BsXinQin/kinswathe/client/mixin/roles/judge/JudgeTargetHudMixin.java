package org.BsXinQin.kinswathe.client.mixin.roles.judge;

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
import org.BsXinQin.kinswathe.client.KinsWatheClient;
import org.BsXinQin.kinswathe.component.AbilityPlayerComponent;
import org.BsXinQin.kinswathe.component.ConfigWorldComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RoleNameRenderer.class)
public class JudgeTargetHudMixin {

    @Unique private static PlayerEntity JudgeTarget = null;

    @Inject(method = "renderHud", at = @At("TAIL"))
    private static void JudgeTargetHud(TextRenderer renderer, ClientPlayerEntity player, DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        if (WatheClient.isPlayerAliveAndInSurvival()) {
            GameWorldComponent gameWorld = GameWorldComponent.KEY.get(player.getWorld());
            AbilityPlayerComponent ability = AbilityPlayerComponent.KEY.get(player);
            PlayerShopComponent playerShop = PlayerShopComponent.KEY.get(player);
            if (gameWorld.isRole(player, KinsWathe.JUDGE)) {
                if (ability.cooldown > 0 || playerShop.balance < ConfigWorldComponent.KEY.get(player.getWorld()).JudgeAbilityPrice) return;
                if (JudgeTarget != null && JudgeTarget != player) {
                    context.getMatrices().push();
                    context.getMatrices().translate((float) context.getScaledWindowWidth() / 2.0F, (float) context.getScaledWindowHeight() / 2.0F + 6.0F, 0.0F);
                    context.getMatrices().scale(0.6F, 0.6F, 1.0F);
                    Text targetInfo = Text.translatable("hud.kinswathe.judge.target", KinsWatheClient.abilityBind.getBoundKeyLocalizedText()).withColor(KinsWathe.JUDGE.color());
                    context.drawTextWithShadow(renderer, targetInfo, -renderer.getWidth(targetInfo) / 2, 32, KinsWathe.JUDGE.color());
                    context.getMatrices().pop();
                }
            }
        }
    }

    @Inject(method = "renderHud", at = @At(value = "INVOKE", target = "Ldev/doctor4t/wathe/game/GameFunctions;isPlayerSpectatingOrCreative(Lnet/minecraft/entity/player/PlayerEntity;)Z"))
    private static void getJudgeTarget(TextRenderer renderer, ClientPlayerEntity player, DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(player.getWorld());
        if (!WatheClient.isPlayerAliveAndInSurvival() || !gameWorld.isRole(player, KinsWathe.JUDGE)) {
            JudgeTarget = null;
            return;
        }
        float range = 2.0F;
        var hitResult = ProjectileUtil.getCollision(player, (entity) -> {
            return entity instanceof PlayerEntity target && target != player;
        }, (double) range);
        JudgeTarget = null;
        if (hitResult instanceof EntityHitResult entityHit) {
            var entity = entityHit.getEntity();
            if (entity instanceof PlayerEntity targetPlayer) {
                JudgeTarget = targetPlayer;
            }
        }
    }
}