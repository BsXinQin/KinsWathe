package org.BsXinQin.kinswathe.mixin.roles.hunter;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import dev.doctor4t.wathe.util.ShopEntry;
import net.minecraft.entity.player.PlayerEntity;
import org.BsXinQin.kinswathe.KinsWatheRoles;
import org.BsXinQin.kinswathe.KinsWatheShops;
import org.BsXinQin.kinswathe.component.PlayerPurchaseComponent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerShopComponent.class)
public abstract class HunterShopMixin {

    @Shadow public int balance;
    @Shadow public abstract void sync();
    @Shadow @Final @NotNull private PlayerEntity player;

    @Inject(method = "tryBuy", at = @At("HEAD"), cancellable = true)
    void tryBuy(int index, @NotNull CallbackInfo ci) {
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(this.player.getWorld());
        if (gameWorld.isRole(this.player, KinsWatheRoles.HUNTER)) {
            if (index < 0 || index >= KinsWatheShops.getHunterShop(this.player.getWorld()).size()) return;
            ShopEntry entries = KinsWatheShops.getHunterShop(this.player.getWorld()).get(index);
            if (PlayerPurchaseComponent.handlePurchase(this.player, this.balance, entries.stack().getItem(), entries.price())) {
                this.balance -= entries.price();
                this.sync();
            }
            ci.cancel();
        }
    }
}