package org.BsXinQin.kinswathe.mixin.roles.drugmaker;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import dev.doctor4t.wathe.util.ShopEntry;
import net.minecraft.entity.player.PlayerEntity;
import org.BsXinQin.kinswathe.KinsWathe;
import org.BsXinQin.kinswathe.KinsWatheConstants;
import org.BsXinQin.kinswathe.component.PlayerPurchaseComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerShopComponent.class)
public abstract class DrugmakerShopMixin {

    @Shadow public int balance;
    @Shadow @Final private PlayerEntity player;
    @Shadow public abstract void sync();

    @Inject(method = "tryBuy", at = @At("HEAD"), cancellable = true)
    void tryBuyDrugmakerShop(int index, CallbackInfo ci) {
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(this.player.getWorld());
        if (gameWorld.isRole(this.player, KinsWathe.DRUGMAKER)) {
            if (index < 0 || index >= KinsWatheConstants.DrugmakerShop.size()) return;
            ShopEntry entries = KinsWatheConstants.DrugmakerShop.get(index);
            if (PlayerPurchaseComponent.handlePurchase(this.player, this.balance, entries.stack().getItem(), entries.price())) {
                this.balance -= entries.price();
                this.sync();
            }
            ci.cancel();
        }
    }
}