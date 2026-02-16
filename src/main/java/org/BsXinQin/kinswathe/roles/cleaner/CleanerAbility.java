package org.BsXinQin.kinswathe.roles.cleaner;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import dev.doctor4t.wathe.game.GameFunctions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import org.BsXinQin.kinswathe.KinsWatheConfig;
import org.BsXinQin.kinswathe.KinsWatheRoles;
import org.BsXinQin.kinswathe.component.AbilityPlayerComponent;
import org.jetbrains.annotations.NotNull;

public class CleanerAbility {

    public static void register(@NotNull PlayerEntity player) {
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(player.getWorld());
        AbilityPlayerComponent ability = AbilityPlayerComponent.KEY.get(player);
        PlayerShopComponent playerShop = PlayerShopComponent.KEY.get(player);
        if (gameWorld.isRole(player, KinsWatheRoles.CLEANER) && GameFunctions.isPlayerAliveAndSurvival(player) && ability.cooldown <= 0) {
            if (playerShop.balance < KinsWatheConfig.HANDLER.instance().CleanerAbilityPrice) return;
            playerShop.balance -= KinsWatheConfig.HANDLER.instance().CleanerAbilityPrice;
            playerShop.sync();
            player.getServer().getCommandManager().executeWithPrefix(player.getServer().getCommandSource().withSilent(), "kill @e[type=item]");
            player.playSoundToPlayer(SoundEvents.ENTITY_ENDER_DRAGON_FLAP, SoundCategory.PLAYERS, 1.0f, 1.0f);
            ability.setAbilityCooldown(KinsWatheConfig.HANDLER.instance().CleanerAbilityCooldown);
        }
    }
}