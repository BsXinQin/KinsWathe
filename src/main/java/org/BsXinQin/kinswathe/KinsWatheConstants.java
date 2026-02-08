package org.BsXinQin.kinswathe;

import dev.doctor4t.wathe.index.WatheItems;
import dev.doctor4t.wathe.util.ShopEntry;
import net.minecraft.util.Util;

import java.util.ArrayList;
import java.util.List;

public interface KinsWatheConstants {
    /// 自定义杀手商店
    //制毒师
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
        entries.add(new ShopEntry(WatheItems.BLACKOUT.getDefaultStack(), 200, ShopEntry.Type.TOOL));
        entries.add(new ShopEntry(WatheItems.NOTE.getDefaultStack(), 10, ShopEntry.Type.TOOL));
    });
    //绑匪
    List<ShopEntry> KidnapperShop = Util.make(new ArrayList<>(), (entries) -> {
        entries.add(new ShopEntry(WatheItems.KNIFE.getDefaultStack(), 100, ShopEntry.Type.WEAPON));
        entries.add(new ShopEntry(KinsWatheItems.KNOCKOUT_DRUG.getDefaultStack(), 75, ShopEntry.Type.WEAPON));
        entries.add(new ShopEntry(WatheItems.GRENADE.getDefaultStack(), 350, ShopEntry.Type.WEAPON));
        entries.add(new ShopEntry(WatheItems.PSYCHO_MODE.getDefaultStack(), 300, ShopEntry.Type.WEAPON));
        entries.add(new ShopEntry(WatheItems.POISON_VIAL.getDefaultStack(), 100, ShopEntry.Type.POISON));
        entries.add(new ShopEntry(WatheItems.SCORPION.getDefaultStack(), 50, ShopEntry.Type.POISON));
        entries.add(new ShopEntry(WatheItems.FIRECRACKER.getDefaultStack(), 10, ShopEntry.Type.TOOL));
        entries.add(new ShopEntry(WatheItems.LOCKPICK.getDefaultStack(), 50, ShopEntry.Type.TOOL));
        entries.add(new ShopEntry(WatheItems.CROWBAR.getDefaultStack(), 25, ShopEntry.Type.TOOL));
        entries.add(new ShopEntry(WatheItems.BODY_BAG.getDefaultStack(), 200, ShopEntry.Type.TOOL));
        entries.add(new ShopEntry(WatheItems.BLACKOUT.getDefaultStack(), 200, ShopEntry.Type.TOOL));
        entries.add(new ShopEntry(WatheItems.NOTE.getDefaultStack(), 10, ShopEntry.Type.TOOL));
    });
}