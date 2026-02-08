package org.BsXinQin.kinswathe.mixin.roles.cook;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.BsXinQin.kinswathe.KinsWathe;
import org.BsXinQin.kinswathe.KinsWatheItems;
import org.BsXinQin.kinswathe.component.PlayerPurchaseComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerShopComponent.class)
public abstract class CookShopMixin {

    @Shadow public int balance;
    @Shadow @Final private PlayerEntity player;
    @Shadow public abstract void sync();

    @Inject(method = "tryBuy", at = @At("HEAD"), cancellable = true)
    void tryBuyCookShop(int index, CallbackInfo ci) {
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(this.player.getWorld());
        if (gameWorld.isRole(this.player, KinsWathe.COOK)) {
            Item item;
            int price;
            switch (index) {
                case 0:
                    item = KinsWatheItems.PAN;
                    price = 250;
                    break;
                case 1:
                    item = Items.COOKED_BEEF;
                    price = 75;
                    break;
                case 2:
                    item = Items.COOKED_CHICKEN;
                    price = 75;
                    break;
                case 3:
                    item = Items.COOKED_PORKCHOP;
                    price = 75;
                    break;
                default:
                    return;
            }
            if (index < 0 || index >= 4) return;
            if (PlayerPurchaseComponent.handlePurchase(this.player, this.balance, item, price)) {
                this.balance -= price;
                this.sync();
            }
            ci.cancel();
        }
    }
}