package org.BsXinQin.kinswathe.items;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.BsXinQin.kinswathe.KinsWatheItems;
import org.BsXinQin.kinswathe.KinsWatheRoles;
import org.BsXinQin.kinswathe.roles.kidnapper.KidnapperComponent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class KnockoutDrugItem extends Item {

    public KnockoutDrugItem(@NotNull Settings settings) {super(settings);}

    @Override
    public ActionResult useOnEntity(ItemStack stack, @NotNull PlayerEntity player, @NotNull LivingEntity entity, @NotNull Hand hand) {
        if (player.getItemCooldownManager().isCoolingDown(this)) return ActionResult.FAIL;
        if (player.isSneaking()) return ActionResult.FAIL;
        if (!player.getWorld().isClient && entity instanceof @NotNull PlayerEntity targetPlayer) {
            KinsWatheItems.setItemAfterUsing(player, this, hand);
            KidnapperComponent playerControlled = KidnapperComponent.KEY.get(targetPlayer);
            GameWorldComponent gameWorld = GameWorldComponent.KEY.get(player.getWorld());
            if (gameWorld.isRole(targetPlayer, KinsWatheRoles.ROBOT)) {
                player.sendMessage(Text.translatable("tip.kinswathe.kidnapper.daze_failed").withColor(Color.RED.getRGB()), true);
                player.playSoundToPlayer(SoundEvents.ENTITY_VILLAGER_AMBIENT, SoundCategory.PLAYERS, 1.0f, 1.0f);
                return ActionResult.SUCCESS;
            }
            if (playerControlled.controlTicks <= 0) {
                playerControlled.startControl(player);
                player.playSoundToPlayer(SoundEvents.ENTITY_SHEEP_AMBIENT, SoundCategory.PLAYERS, 1.0f, 1.0f);
                targetPlayer.playSoundToPlayer(SoundEvents.ENTITY_SHEEP_AMBIENT, SoundCategory.PLAYERS, 1.0f, 1.0f);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }
}