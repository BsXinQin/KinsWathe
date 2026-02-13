package org.BsXinQin.kinswathe.roles.robot;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.game.GameFunctions;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import org.BsXinQin.kinswathe.KinsWatheConfig;
import org.BsXinQin.kinswathe.KinsWatheRoles;
import org.BsXinQin.kinswathe.component.AbilityPlayerComponent;
import org.jetbrains.annotations.NotNull;

public class RobotAbility {

    public static void register(@NotNull PlayerEntity player) {
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(player.getWorld());
        AbilityPlayerComponent ability = AbilityPlayerComponent.KEY.get(player);
        if (gameWorld.isRole(player, KinsWatheRoles.ROBOT) && GameFunctions.isPlayerAliveAndSurvival(player) && ability.cooldown <= 0) {
            RobotComponent playerRobot = RobotComponent.KEY.get(player);
            playerRobot.setRobotTicks(KinsWatheConfig.HANDLER.instance().RobotAbilityDuration * 20);
            player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, KinsWatheConfig.HANDLER.instance().RobotAbilityDuration * 20, 0, true, true, false));
            player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.ENTITY_IRON_GOLEM_HURT, SoundCategory.PLAYERS, 1.0f, 1.0f);
            ability.setAbilityCooldown(player, KinsWatheConfig.HANDLER.instance().RobotAbilityCooldown);
        }
    }
}