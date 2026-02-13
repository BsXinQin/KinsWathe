package org.BsXinQin.kinswathe.mixin.roles.cook;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
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
public abstract class CookShopMixin {

    @Unique public int price;
    @Shadow public int balance;
    @Unique @NotNull public Item item;
    @Shadow public abstract void sync();
    @Shadow @Final @NotNull private PlayerEntity player;

    @Inject(method = "tryBuy", at = @At("HEAD"), cancellable = true)
    void tryBuy(int index, @NotNull CallbackInfo ci) {
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(this.player.getWorld());
        if (gameWorld.isRole(this.player, KinsWatheRoles.COOK)) {
            switch (index) {
                case 0:
                    this.item = KinsWatheItems.PAN;
                    this.price = KinsWatheConfig.HANDLER.instance().CookPanPrice;
                    break;
                case 1:
                    this.item = Items.COOKED_BEEF;
                    this.price = 75;
                    break;
                case 2:
                    this.item = Items.COOKED_CHICKEN;
                    this.price = 75;
                    break;
                case 3:
                    this.item = Items.COOKED_PORKCHOP;
                    this.price = 75;
                    break;
                default:
                    return;
            }
            if (index < 0 || index > 3) return;
            if (PlayerPurchaseComponent.handlePurchase(this.player, this.balance, this.item, this.price)) {
                this.balance -= this.price;
                this.sync();
            }
            ci.cancel();
        }
    }
}