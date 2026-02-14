//package org.BsXinQin.kinswathe.items;
//
//import dev.doctor4t.wathe.cca.GameWorldComponent;
//import net.minecraft.entity.LivingEntity;
//import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.item.Item;
//import net.minecraft.item.ItemStack;
//import net.minecraft.sound.SoundCategory;
//import net.minecraft.sound.SoundEvents;
//import net.minecraft.util.ActionResult;
//import net.minecraft.util.Hand;
//import org.BsXinQin.kinswathe.KinsWatheItems;
//import org.BsXinQin.kinswathe.KinsWatheRoles;
//import org.BsXinQin.kinswathe.roles.dreamer.DreamerComponent;
//import org.jetbrains.annotations.NotNull;
//
//public class DreamImprintItem extends Item {
//
//    public DreamImprintItem(@NotNull Settings settings) {super(settings);}
//
//    @Override
//    public ActionResult useOnEntity(ItemStack stack, @NotNull PlayerEntity player, @NotNull LivingEntity entity, @NotNull Hand hand) {
//        if (player.getItemCooldownManager().isCoolingDown(this)) return ActionResult.FAIL;
//        if (!player.getWorld().isClient && entity instanceof @NotNull PlayerEntity targetPlayer) {
//            KinsWatheItems.setItemAfterUsing(player, this, hand);
//            GameWorldComponent gameWorld = GameWorldComponent.KEY.get(player.getWorld());
//            DreamerComponent targetDream = DreamerComponent.KEY.get(targetPlayer);
//            player.playSoundToPlayer(SoundEvents.BLOCK_BEACON_POWER_SELECT, SoundCategory.PLAYERS, 1.0f, 1.0f);
//            targetPlayer.playSoundToPlayer(SoundEvents.BLOCK_BEACON_POWER_SELECT, SoundCategory.PLAYERS, 1.0f, 1.0f);
//            if (gameWorld.isRole(player, KinsWatheRoles.DREAMER)) {
//                targetDream.imprintDreamer(player);
//            }
//            return ActionResult.SUCCESS;
//        }
//        return ActionResult.PASS;
//    }
//}
