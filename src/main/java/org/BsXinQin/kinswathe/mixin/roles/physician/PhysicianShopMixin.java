package org.BsXinQin.kinswathe.mixin.roles.physician;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import org.BsXinQin.kinswathe.KinsWatheConfig;
import org.BsXinQin.kinswathe.KinsWatheItems;
import org.BsXinQin.kinswathe.KinsWatheRoles;
import org.BsXinQin.kinswathe.component.PlayerPurchaseComponent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerShopComponent.class)
public abstract class PhysicianShopMixin {

    @Unique public int price;
    @Shadow public int balance;
    @Unique @NotNull public Item item;
    @Shadow public abstract void sync();
    @Shadow @Final @NotNull private PlayerEntity player;


    @Inject(method = "tryBuy", at = @At("HEAD"), cancellable = true)
    void tryBuy(int index, CallbackInfo ci) {
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(this.player.getWorld());
        if (gameWorld.isRole(this.player, KinsWatheRoles.PHYSICIAN)) {
            switch (index) {
                case 0:
                    this.item = KinsWatheItems.PILL;
                    this.price = KinsWatheConfig.HANDLER.instance().PhysicianPillPrice;
                    break;
                default:
                    return;
            }
            if (index != 0) return;
            if (PlayerPurchaseComponent.handlePurchase(this.player, this.balance, this.item, this.price)) {
                this.balance -= this.price;
                this.sync();
            }
            ci.cancel();
        }
    }
}