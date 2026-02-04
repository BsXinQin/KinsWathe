package org.BsXinQin.kinswathe.client.mixin.roles.licensed_villain;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.client.gui.screen.ingame.LimitedHandledScreen;
import dev.doctor4t.wathe.client.gui.screen.ingame.LimitedInventoryScreen;
import dev.doctor4t.wathe.index.WatheItems;
import dev.doctor4t.wathe.util.ShopEntry;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import org.BsXinQin.kinswathe.KinsWathe;
import org.BsXinQin.kinswathe.component.ConfigWorldComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(LimitedInventoryScreen.class)
public abstract class LicensedVillainShopMixin extends LimitedHandledScreen<PlayerScreenHandler> {

    public LicensedVillainShopMixin(PlayerScreenHandler handler, PlayerInventory inventory, Text title) {super(handler, inventory, title);}
    @Shadow @Final public ClientPlayerEntity player;

    @Inject(method = "init", at = @At("HEAD"))
    void LicensedVillainShop(CallbackInfo ci) {
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(this.player.getWorld());
        if (gameWorld.isRole(this.player, KinsWathe.LICENSED_VILLAIN)) {
            List<ShopEntry> entries = new ArrayList<>();
            entries.add(new ShopEntry(WatheItems.REVOLVER.getDefaultStack(), ConfigWorldComponent.KEY.get(this.player.getWorld()).LicensedVillainRevolverPrice, ShopEntry.Type.WEAPON));
            int apart = 36;
            int x = width / 2 - (entries.size()) * apart / 2 + 9;
            int shouldBeY = (height - 32) / 2;
            int y = shouldBeY - 46;
            for(int i = 0; i < entries.size(); ++i) {
                addDrawableChild(new LimitedInventoryScreen.StoreItemWidget((LimitedInventoryScreen) (Object)this, x + apart * i, y, (ShopEntry)entries.get(i), i));
            }
        }
    }

}