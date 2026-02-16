package org.BsXinQin.kinswathe.roles.judge;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import dev.doctor4t.wathe.game.GameFunctions;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.text.Text;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.BsXinQin.kinswathe.KinsWatheConfig;
import org.BsXinQin.kinswathe.KinsWatheRoles;
import org.BsXinQin.kinswathe.component.AbilityPlayerComponent;
import org.jetbrains.annotations.NotNull;

public class JudgeAbility {

    public static void register(@NotNull PlayerEntity player) {
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(player.getWorld());
        AbilityPlayerComponent ability = AbilityPlayerComponent.KEY.get(player);
        PlayerShopComponent playerShop = PlayerShopComponent.KEY.get(player);
        if (gameWorld.isRole(player, KinsWatheRoles.JUDGE) && GameFunctions.isPlayerAliveAndSurvival(player) && ability.cooldown <= 0) {
            if (playerShop.balance < KinsWatheConfig.HANDLER.instance().JudgeAbilityPrice) return;
            HitResult hitResult = ProjectileUtil.getCollision(player, entity -> entity instanceof @NotNull PlayerEntity target && GameFunctions.isPlayerAliveAndSurvival(target), 2.0f);
            PlayerEntity targetPlayer = (hitResult instanceof @NotNull EntityHitResult entityHitResult) ? (PlayerEntity) entityHitResult.getEntity() : null;
            playerShop.balance -= KinsWatheConfig.HANDLER.instance().JudgeAbilityPrice;
            playerShop.sync();
            if (targetPlayer == null) return;
            targetPlayer.sendMessage(Text.translatable("tip.kinswathe.judge.notification").withColor(KinsWatheRoles.JUDGE.color()), true);
            targetPlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, KinsWatheConfig.HANDLER.instance().JudgeAbilityGlowing * 20, 0, false, true, true));
            var lightning = new LightningEntity(net.minecraft.entity.EntityType.LIGHTNING_BOLT, targetPlayer.getWorld());
            lightning.refreshPositionAfterTeleport(targetPlayer.getX(), targetPlayer.getY(), targetPlayer.getZ());
            lightning.setCosmetic(true);
            targetPlayer.getWorld().spawnEntity(lightning);
            ability.setAbilityCooldown(KinsWatheConfig.HANDLER.instance().JudgeAbilityCooldown);
        }
    }
}