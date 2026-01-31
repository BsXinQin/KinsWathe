package org.BsXinQin.kinswathe.client.mixin.roles.drugmaker;

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
import org.BsXinQin.kinswathe.KinsWatheItems;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;

@Mixin(LimitedInventoryScreen.class)
public abstract class DrugmakerShopMixin extends LimitedHandledScreen<PlayerScreenHandler> {

    public DrugmakerShopMixin(PlayerScreenHandler handler, PlayerInventory inventory, Text title) {super(handler, inventory, title);}
    @Shadow @Final public ClientPlayerEntity player;

    @ModifyVariable(method = "init", at = @At(value = "STORE"), name = "entries")
    private List<ShopEntry> DrugmakerShop(List<ShopEntry> entries) {
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(player.getWorld());
        if (gameWorld.isRole(player, KinsWathe.DRUGMAKER)) {
            entries.clear();
            entries.add(new ShopEntry(KinsWatheItems.POISON_INJECTOR.getDefaultStack(), 125, ShopEntry.Type.WEAPON));
            entries.add(new ShopEntry(KinsWatheItems.BLOWGUN.getDefaultStack(), 175, ShopEntry.Type.WEAPON));
            entries.add(new ShopEntry(WatheItems.KNIFE.getDefaultStack(), 200, ShopEntry.Type.WEAPON));
            entries.add(new ShopEntry(WatheItems.POISON_VIAL.getDefaultStack(), 50, ShopEntry.Type.POISON));
            entries.add(new ShopEntry(WatheItems.SCORPION.getDefaultStack(), 25, ShopEntry.Type.POISON));
            entries.add(new ShopEntry(WatheItems.FIRECRACKER.getDefaultStack(), 10, ShopEntry.Type.TOOL));
            entries.add(new ShopEntry(WatheItems.LOCKPICK.getDefaultStack(), 50, ShopEntry.Type.TOOL));
            entries.add(new ShopEntry(WatheItems.CROWBAR.getDefaultStack(), 25, ShopEntry.Type.TOOL));
            entries.add(new ShopEntry(WatheItems.BODY_BAG.getDefaultStack(), 200, ShopEntry.Type.TOOL));
            entries.add(new ShopEntry(WatheItems.BLACKOUT.getDefaultStack(), 200, ShopEntry.Type.TOOL));
            entries.add(new ShopEntry(WatheItems.NOTE.getDefaultStack(), 10, ShopEntry.Type.TOOL));
            }
        return entries;
    }
}