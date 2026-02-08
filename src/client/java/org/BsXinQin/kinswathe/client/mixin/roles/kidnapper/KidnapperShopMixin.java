package org.BsXinQin.kinswathe.client.mixin.roles.kidnapper;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.client.gui.screen.ingame.LimitedInventoryScreen;
import dev.doctor4t.wathe.util.ShopEntry;
import net.minecraft.client.network.ClientPlayerEntity;
import org.BsXinQin.kinswathe.KinsWathe;
import org.BsXinQin.kinswathe.KinsWatheConstants;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;

@Mixin(LimitedInventoryScreen.class)
public abstract class KidnapperShopMixin {

    @Shadow @Final public ClientPlayerEntity player;

    @ModifyVariable(method = "init", at = @At(value = "STORE"), name = "entries")
    private List<ShopEntry> getKidnapperShop(List<ShopEntry> entries) {
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(this.player.getWorld());
        if (gameWorld.isRole(this.player, KinsWathe.KIDNAPPER)) {
            entries = KinsWatheConstants.KidnapperShop;
        }
        return entries;
    }
}