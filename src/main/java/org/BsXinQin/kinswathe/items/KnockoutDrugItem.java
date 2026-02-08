package org.BsXinQin.kinswathe.items;

import dev.doctor4t.wathe.game.GameConstants;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.BsXinQin.kinswathe.roles.kidnapper.KidnapperComponent;
import org.jetbrains.annotations.NotNull;

public class KnockoutDrugItem extends Item {

    public KnockoutDrugItem(Settings settings) {super(settings);}

    @Override
    public ActionResult useOnEntity(ItemStack stack, @NotNull PlayerEntity player, LivingEntity entity, Hand hand) {
        if (player.getItemCooldownManager().isCoolingDown(this)) return ActionResult.FAIL;
        if (entity instanceof PlayerEntity targetPlayer) {
            KidnapperComponent playerControlled = KidnapperComponent.KEY.get(targetPlayer);
            if (playerControlled.controlTicks <= 0) {
                playerControlled.startControl(player);
                player.playSoundToPlayer(SoundEvents.ENTITY_SHEEP_AMBIENT, SoundCategory.PLAYERS, 1.0f, 1.0f);
                targetPlayer.playSoundToPlayer(SoundEvents.ENTITY_SHEEP_AMBIENT, SoundCategory.PLAYERS, 1.0f, 1.0f);
                if (!player.isCreative()) {
                    player.getStackInHand(hand).decrement(1);
                    player.getItemCooldownManager().set(this, GameConstants.ITEM_COOLDOWNS.get(this));
                }
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }
}