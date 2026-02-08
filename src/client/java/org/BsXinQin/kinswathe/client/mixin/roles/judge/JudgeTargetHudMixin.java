package org.BsXinQin.kinswathe.client.mixin.roles.judge;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import dev.doctor4t.wathe.client.WatheClient;
import dev.doctor4t.wathe.client.gui.RoleNameRenderer;
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
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RoleNameRenderer.class)
public class JudgeTargetHudMixin {

    @Unique private static PlayerEntity JudgeTarget = null;

    @Inject(method = "renderHud", at = @At("TAIL"))
    private static void getJudgeTargetHud(TextRenderer renderer, @NotNull ClientPlayerEntity player, DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        if (JudgeTarget == null) return;
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(player.getWorld());
        AbilityPlayerComponent ability = AbilityPlayerComponent.KEY.get(player);
        PlayerShopComponent playerShop = PlayerShopComponent.KEY.get(player);
        if (gameWorld.isRole(player, KinsWathe.JUDGE) && WatheClient.isPlayerAliveAndInSurvival()) {
            if (ability.cooldown > 0 || playerShop.balance < ConfigWorldComponent.KEY.get(player.getWorld()).JudgeAbilityPrice) return;
            context.getMatrices().push();
            context.getMatrices().translate((float) context.getScaledWindowWidth() / 2.0F, (float) context.getScaledWindowHeight() / 2.0F + 6.0F, 0.0F);
            context.getMatrices().scale(0.6F, 0.6F, 1.0F);
            Text targetInfo = Text.translatable("hud.kinswathe.judge.target", KinsWatheClient.abilityBind.getBoundKeyLocalizedText()).withColor(KinsWathe.JUDGE.color());
            context.drawTextWithShadow(renderer, targetInfo, -renderer.getWidth(targetInfo) / 2, 32, KinsWathe.JUDGE.color());
            context.getMatrices().pop();
        }
    }

    @Inject(method = "renderHud", at = @At(value = "INVOKE", target = "Ldev/doctor4t/wathe/game/GameFunctions;isPlayerSpectatingOrCreative(Lnet/minecraft/entity/player/PlayerEntity;)Z"))
    private static void getJudgeTarget(TextRenderer renderer, @NotNull ClientPlayerEntity player, DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(player.getWorld());
        if (!gameWorld.isRole(player, KinsWathe.JUDGE)) {JudgeTarget = null;return;}
        var hitResult = ProjectileUtil.getCollision(player, entity -> entity instanceof PlayerEntity, 2.0);
        JudgeTarget = (hitResult instanceof EntityHitResult entityHit && entityHit.getEntity() instanceof PlayerEntity target) ? target : null;
    }
}