package org.BsXinQin.kinswathe.items;

import dev.doctor4t.wathe.cca.PlayerPoisonComponent;
import dev.doctor4t.wathe.game.GameConstants;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class MedicalKitItem extends Item {

    public MedicalKitItem(Settings settings) {super(settings);}

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand) {
        if (player.getItemCooldownManager().isCoolingDown(this)) return ActionResult.FAIL;
        if (!(entity instanceof PlayerEntity targetPlayer)) return ActionResult.PASS;
        PlayerPoisonComponent targetPoison = PlayerPoisonComponent.KEY.get(targetPlayer);
        if (targetPoison.poisonTicks > -1) {
            targetPoison.reset();
            targetPlayer.sendMessage(Text.translatable("tip.kinswathe.physician.medical_kit").withColor(0x00AA00), true);
            entity.getWorld().playSound(null, targetPlayer.getX(), targetPlayer.getY(), targetPlayer.getZ(), SoundEvents.ENTITY_HORSE_ARMOR, SoundCategory.PLAYERS, 1.0f, 1.0f);
            if (!player.isCreative()) {
                player.getItemCooldownManager().set(this, GameConstants.ITEM_COOLDOWNS.get(this));
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
}