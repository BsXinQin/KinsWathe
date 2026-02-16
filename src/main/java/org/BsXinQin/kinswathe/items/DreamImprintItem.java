package org.BsXinQin.kinswathe.items;

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
import org.BsXinQin.kinswathe.roles.dreamer.DreamerComponent;
import org.jetbrains.annotations.NotNull;

public class DreamImprintItem extends Item {

    public DreamImprintItem(@NotNull Settings settings) {super(settings);}

    @Override
    public ActionResult useOnEntity(ItemStack stack, @NotNull PlayerEntity player, @NotNull LivingEntity entity, @NotNull Hand hand) {
        if (player.getItemCooldownManager().isCoolingDown(this)) return ActionResult.FAIL;
        if (!player.getWorld().isClient && entity instanceof @NotNull PlayerEntity targetPlayer) {
            DreamerComponent targetDream = DreamerComponent.KEY.get(targetPlayer);
            if (targetDream.dreamArmor == 0) {
                KinsWatheItems.setItemAfterUsing(player, this, hand);
                targetDream.imprintDreamer(player);
                player.sendMessage(Text.translatable("tip.kinswathe.dreamer.imprint", targetPlayer.getName().getString()).withColor(KinsWatheRoles.DREAMER.color()), true);
                player.playSoundToPlayer(SoundEvents.BLOCK_BEACON_POWER_SELECT, SoundCategory.PLAYERS, 1.0f, 1.0f);
                targetPlayer.sendMessage(Text.translatable("tip.kinswathe.dreamer.imprint", player.getName().getString()).withColor(KinsWatheRoles.DREAMER.color()), true);
                targetPlayer.playSoundToPlayer(SoundEvents.BLOCK_BEACON_POWER_SELECT, SoundCategory.PLAYERS, 1.0f, 1.0f);
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }
}
