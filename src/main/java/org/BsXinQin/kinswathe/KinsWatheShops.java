package org.BsXinQin.kinswathe;

import dev.doctor4t.wathe.index.WatheItems;
import dev.doctor4t.wathe.util.ShopEntry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.World;
import org.BsXinQin.kinswathe.component.ConfigWorldComponent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class KinsWatheShops {

    /// 身份商店
    //制毒师商店
    public static List<ShopEntry> getDrugmakerShop(@NotNull World world) {
        return Util.make(new ArrayList<>(), (entries) -> {
            entries.add(new ShopEntry(KinsWatheItems.POISON_INJECTOR.getDefaultStack(), ConfigWorldComponent.KEY.get(world).DrugmakerPoisonInjectorPrice, ShopEntry.Type.WEAPON));
            entries.add(new ShopEntry(KinsWatheItems.BLOWGUN.getDefaultStack(), ConfigWorldComponent.KEY.get(world).DrugmakerBlowgunPrice, ShopEntry.Type.WEAPON));
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
    }
    //追猎者商店
    public static List<ShopEntry> getHunterShop(@NotNull World world) {
        return Util.make(new ArrayList<>(), (entries) -> {
            entries.add(new ShopEntry(KinsWatheItems.HUNTING_KNIFE.getDefaultStack(), 100, ShopEntry.Type.WEAPON));
            entries.add(new ShopEntry(WatheItems.REVOLVER.getDefaultStack(), 300, ShopEntry.Type.WEAPON));
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
    //绑匪商店
    public static List<ShopEntry> getKidnapperShop(@NotNull World world) {
        return Util.make(new ArrayList<>(), (entries) -> {
            entries.add(new ShopEntry(WatheItems.KNIFE.getDefaultStack(), 100, ShopEntry.Type.WEAPON));
            entries.add(new ShopEntry(KinsWatheItems.KNOCKOUT_DRUG.getDefaultStack(), ConfigWorldComponent.KEY.get(world).KidnapperKnockoutDrugPrice, ShopEntry.Type.WEAPON));
            entries.add(new ShopEntry(WatheItems.REVOLVER.getDefaultStack(), 300, ShopEntry.Type.WEAPON));
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
    //杀手方中立商店
    public static List<ShopEntry> getKillerNeutralRolesShop(@NotNull World world) {
        if (FabricLoader.getInstance().isModLoaded("noellesroles")) {
            return Util.make(new ArrayList<>(), (entries) -> {
                entries.add(new ShopEntry(WatheItems.LOCKPICK.getDefaultStack(), 50, ShopEntry.Type.TOOL));
                entries.add(new ShopEntry(Registries.ITEM.get(Identifier.of("noellesroles", "delusion_vial")).getDefaultStack(), 30, ShopEntry.Type.POISON));
                entries.add(new ShopEntry(WatheItems.FIRECRACKER.getDefaultStack(), 5, ShopEntry.Type.TOOL));
                entries.add(new ShopEntry(WatheItems.NOTE.getDefaultStack(), 5, ShopEntry.Type.TOOL));

            });
        }
        return null;
    }
}
