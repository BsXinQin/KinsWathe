package org.BsXinQin.kinswathe.items;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerPoisonComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.BsXinQin.kinswathe.KinsWatheItems;
import org.BsXinQin.kinswathe.KinsWatheRoles;
import org.agmas.harpymodloader.component.WorldModifierComponent;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class MedicalKitItem extends Item {

    public MedicalKitItem(@NotNull Settings settings) {super(settings);}

    @Override
    public ActionResult useOnEntity(ItemStack stack, @NotNull PlayerEntity player, @NotNull LivingEntity entity, Hand hand) {
        if (player.getItemCooldownManager().isCoolingDown(this)) return ActionResult.FAIL;
        if (!player.getWorld().isClient && entity instanceof @NotNull PlayerEntity targetPlayer) {
            WorldModifierComponent modifier = WorldModifierComponent.KEY.get(player.getWorld());
            PlayerPoisonComponent targetPoison = PlayerPoisonComponent.KEY.get(targetPlayer);
            GameWorldComponent gameWorld = GameWorldComponent.KEY.get(player.getWorld());
            PlayerShopComponent playerShop = PlayerShopComponent.KEY.get(player);
            if (targetPoison.poisonTicks > 0) {
                KinsWatheItems.setItemAfterUsing(player, this, null);
                targetPoison.reset();
                targetPlayer.sendMessage(Text.translatable("tip.kinswathe.physician.medical_kit").withColor(Color.GREEN.getRGB()), true);
                targetPlayer.playSound(SoundEvents.ENTITY_HORSE_ARMOR, 1.0f, 1.0f);
                if (gameWorld.isRole(player, KinsWatheRoles.PHYSICIAN)) {
                    if (modifier.isModifier(player, KinsWatheRoles.TASKMASTER)) playerShop.balance += 75;
                    else playerShop.balance += 50;
                    playerShop.sync();
                }
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }
}