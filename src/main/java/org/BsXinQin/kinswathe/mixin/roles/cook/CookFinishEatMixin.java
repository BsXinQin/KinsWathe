package org.BsXinQin.kinswathe.mixin.roles.cook;

import dev.doctor4t.wathe.game.GameConstants;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.BsXinQin.kinswathe.roles.cook.CookComponent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class CookFinishEatMixin {

    @Inject(method = "finishUsing", at = @At("HEAD"))
    private void playerFinishEat(@NotNull ItemStack stack, @NotNull World world, @NotNull LivingEntity entity, CallbackInfoReturnable<ItemStack> cir) {
        if (world.isClient) return;
        if (entity instanceof @NotNull PlayerEntity player) {
            Item item = stack.getItem();
            if (item.getUseAction(stack) == UseAction.EAT) {
                CookComponent playerEat = CookComponent.KEY.get(player);
                playerEat.setEatTicks(GameConstants.getInTicks(0, 40));
            }
        }
    }
}