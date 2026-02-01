package org.BsXinQin.kinswathe;

import dev.doctor4t.wathe.cca.PlayerShopComponent;
import dev.doctor4t.wathe.index.WatheItems;
import dev.doctor4t.wathe.util.ShopEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public interface KinsWatheConstants {
    /// 自定义杀手角色商店
    List<ShopEntry> DrugmakerShop = Util.make(new ArrayList<>(), (entries) -> {
        entries.add(new ShopEntry(KinsWatheItems.POISON_INJECTOR.getDefaultStack(), 125, ShopEntry.Type.WEAPON));
        entries.add(new ShopEntry(KinsWatheItems.BLOWGUN.getDefaultStack(), 175, ShopEntry.Type.WEAPON));
        entries.add(new ShopEntry(WatheItems.KNIFE.getDefaultStack(), 200, ShopEntry.Type.WEAPON));
        entries.add(new ShopEntry(WatheItems.POISON_VIAL.getDefaultStack(), 50, ShopEntry.Type.POISON));
        entries.add(new ShopEntry(WatheItems.SCORPION.getDefaultStack(), 25, ShopEntry.Type.POISON));
        entries.add(new ShopEntry(WatheItems.FIRECRACKER.getDefaultStack(), 10, ShopEntry.Type.TOOL));
        entries.add(new ShopEntry(WatheItems.LOCKPICK.getDefaultStack(), 50, ShopEntry.Type.TOOL));
        entries.add(new ShopEntry(WatheItems.CROWBAR.getDefaultStack(), 25, ShopEntry.Type.TOOL));
        entries.add(new ShopEntry(WatheItems.BODY_BAG.getDefaultStack(), 200, ShopEntry.Type.TOOL));
        entries.add(new ShopEntry(WatheItems.BLACKOUT.getDefaultStack(), 200, ShopEntry.Type.TOOL) {@Override public boolean onBuy(@NotNull PlayerEntity player) {return PlayerShopComponent.useBlackout(player);}});
        entries.add(new ShopEntry(new ItemStack(WatheItems.NOTE, 4), 10, ShopEntry.Type.TOOL));
    });
}
