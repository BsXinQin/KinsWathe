package org.BsXinQin.kinswathe.mixin.roles.cook;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.BsXinQin.kinswathe.roles.cook.CookComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(PlayerEntity.class)
public class CookSeePlayerEatMixin {

    @Inject(method = "eatFood", at = @At("HEAD"))
    private void CookSeePlayerEat(World world, ItemStack stack, FoodComponent foodComponent, CallbackInfoReturnable<ItemStack> ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (!world.isClient) {
            UseAction eatMethod = stack.getItem().getUseAction(stack);
            if (eatMethod == UseAction.EAT) {
                CookComponent playerEat = CookComponent.KEY.get(player);
                if (playerEat != null) {
                    playerEat.startEating();
                }
            }
        }
    }
}