package org.BsXinQin.kinswathe.roles.detective;

import dev.doctor4t.wathe.api.Role;
import dev.doctor4t.wathe.api.WatheRoles;
import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import dev.doctor4t.wathe.game.GameFunctions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.BsXinQin.kinswathe.KinsWatheConfig;
import org.BsXinQin.kinswathe.KinsWatheRoles;
import org.BsXinQin.kinswathe.component.AbilityPlayerComponent;
import org.jetbrains.annotations.NotNull;

public class DetectiveAbility {

    public static void register(@NotNull PlayerEntity player) {
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(player.getWorld());
        AbilityPlayerComponent ability = AbilityPlayerComponent.KEY.get(player);
        PlayerShopComponent playerShop = PlayerShopComponent.KEY.get(player);
        if (gameWorld.isRole(player, KinsWatheRoles.DETECTIVE) && GameFunctions.isPlayerAliveAndSurvival(player) && ability.cooldown <= 0) {
            if (playerShop.balance < KinsWatheConfig.HANDLER.instance().DetectiveAbilityPrice) return;
            HitResult hitResult = ProjectileUtil.getCollision(player, entity -> entity instanceof @NotNull PlayerEntity target && GameFunctions.isPlayerAliveAndSurvival(target), 2.0f);
            PlayerEntity targetPlayer = (hitResult instanceof @NotNull EntityHitResult entityHitResult) ? (PlayerEntity) entityHitResult.getEntity() : null;
            if (targetPlayer == null) return;
            Role targetRole = gameWorld.getRole(targetPlayer);
            if (targetRole != null) {
                playerShop.balance -= KinsWatheConfig.HANDLER.instance().DetectiveAbilityPrice;
                playerShop.sync();
                if (targetRole.isInnocent()) {
                    player.sendMessage(Text.translatable("tip.kinswathe.detective.innocent", targetPlayer.getName().getString()).withColor(WatheRoles.CIVILIAN.color()), true);
                    player.playSoundToPlayer(SoundEvents.ENTITY_VILLAGER_YES, SoundCategory.PLAYERS, 1.0f, 1.0f);
                } else {
                    player.sendMessage(Text.translatable("tip.kinswathe.detective.notinnocent", targetPlayer.getName().getString()).withColor(WatheRoles.KILLER.color()), true);
                    player.playSoundToPlayer(SoundEvents.ENTITY_VILLAGER_NO, SoundCategory.PLAYERS, 1.0f, 1.0f);
                }
                ability.setAbilityCooldown(player, KinsWatheConfig.HANDLER.instance().DetectiveAbilityCooldown);
            }
        }
    }
}