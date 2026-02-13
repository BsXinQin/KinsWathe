package org.BsXinQin.kinswathe.items;

import dev.doctor4t.wathe.entity.PlayerBodyEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.BsXinQin.kinswathe.KinsWatheItems;
import org.jetbrains.annotations.NotNull;

public class SulfuricAcidBarrelItem extends Item {

    public SulfuricAcidBarrelItem(Settings settings) {super(settings);}

    @Override
    public ActionResult useOnEntity(ItemStack stack, @NotNull PlayerEntity player, @NotNull LivingEntity entity, Hand hand) {
        if (player.getItemCooldownManager().isCoolingDown(this)) return ActionResult.FAIL;
        if (!player.getWorld().isClient && entity instanceof @NotNull PlayerBodyEntity playerBody) {
            KinsWatheItems.setItemAfterUsing(player, this, null);
            playerBody.discard();
            player.getWorld().playSound(null, playerBody.getX(), playerBody.getY() + .1f, playerBody.getZ(), SoundEvents.ITEM_BUCKET_EMPTY_LAVA, SoundCategory.PLAYERS, 1.0f, 0.5f);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
}