package org.BsXinQin.kinswathe.mixin.roles.hunter;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.UseAction;
import org.BsXinQin.kinswathe.items.HuntingKnifeItem;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PlayerInventory.class)
public class HunterLockSlotMixin {

    @Shadow @Final @NotNull public PlayerEntity player;

    @WrapMethod(method = "scrollInHotbar")
    private void lockHunterSlot(double scrollAmount, @NotNull Operation<Void> original) {
        int lockSlot = this.player.getInventory().selectedSlot;
        original.call(scrollAmount);
        if (this.player.isUsingItem()) {
            ItemStack stack = this.player.getActiveItem();
            Item item = stack.getItem();
            if (item instanceof @NotNull HuntingKnifeItem knife && knife.getUseAction(stack) == UseAction.SPEAR && (this.player.getInventory().getStack(lockSlot).isOf(knife)) && (!this.player.getInventory().getStack(this.player.getInventory().selectedSlot).isOf(knife))) {
                this.player.getInventory().selectedSlot = lockSlot;
            }
        }
    }
}
