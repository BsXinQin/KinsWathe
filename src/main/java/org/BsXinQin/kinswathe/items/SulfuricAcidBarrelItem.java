package org.BsXinQin.kinswathe.items;

import dev.doctor4t.wathe.entity.PlayerBodyEntity;
import dev.doctor4t.wathe.game.GameConstants;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.NotNull;

public class SulfuricAcidBarrelItem extends Item {

    public SulfuricAcidBarrelItem(Settings settings) {super(settings);}

    @Override
    public ActionResult useOnEntity(ItemStack stack, @NotNull PlayerEntity player, @NotNull LivingEntity entity, Hand hand) {
        if (player.getItemCooldownManager().isCoolingDown(this)) return ActionResult.FAIL;
        if (entity instanceof PlayerBodyEntity body) {
            body.discard();
            if (!player.getWorld().isClient) {
                player.getWorld().playSound(null, body.getX(), body.getY() + .1f, body.getZ(), SoundEvents.ITEM_BUCKET_EMPTY_LAVA, SoundCategory.PLAYERS, 0.5f, 0.5f);
            }
            if (!player.isCreative()) {
                player.getItemCooldownManager().set(this, GameConstants.ITEM_COOLDOWNS.get(this));
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
}