package org.BsXinQin.kinswathe.mixin.roles.cook;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.BsXinQin.kinswathe.roles.cook.CookComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class CookFinishEatMixin {

    @Inject(method = "finishUsing", at = @At("HEAD"))
    private void CookFinishEat(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> ci) {
        if (!world.isClient && user instanceof PlayerEntity player) {
            Item item = stack.getItem();
            if (item.getUseAction(stack) == UseAction.EAT) {
                CookComponent playerEat = CookComponent.KEY.get(player);
                if (playerEat != null) {
                    playerEat.startEating();
                }
            }
        }
    }
}