package org.BsXinQin.kinswathe.items;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.BsXinQin.kinswathe.KinsWatheItems;
import org.BsXinQin.kinswathe.roles.physician.PhysicianComponent;
import org.agmas.noellesroles.bartender.BartenderPlayerComponent;
import org.jetbrains.annotations.NotNull;

public class PillItem extends Item {

    public PillItem(@NotNull Settings settings) {super(settings);}

    @Override
    public @NotNull TypedActionResult<@NotNull ItemStack> use(@NotNull World world, @NotNull PlayerEntity player, @NotNull Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (player.getItemCooldownManager().isCoolingDown(this)) return TypedActionResult.fail(stack);
        if (!world.isClient) {
            KinsWatheItems.setItemAfterUsing(player, this, hand);
            if (FabricLoader.getInstance().isModLoaded("noellesroles")) {
                BartenderPlayerComponent playerBartender = BartenderPlayerComponent.KEY.get(player);
                playerBartender.giveArmor();
            } else {
                PhysicianComponent playerPhysician = PhysicianComponent.KEY.get(player);
                playerPhysician.giveArmor();
            }
            player.playSoundToPlayer(SoundEvents.ITEM_HONEY_BOTTLE_DRINK, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }
        return TypedActionResult.pass(stack);
    }
}
