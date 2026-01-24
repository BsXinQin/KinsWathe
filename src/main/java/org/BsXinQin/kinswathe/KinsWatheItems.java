package org.BsXinQin.kinswathe;

import dev.doctor4t.wathe.game.GameConstants;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.BsXinQin.kinswathe.items.MedicalKitItem;
import org.BsXinQin.kinswathe.items.SulfuricAcidBarrelItem;

public class KinsWatheItems {

    /// 设置物品冷却
    public static void setItemCooldown() {
        GameConstants.ITEM_COOLDOWNS.put(SULFURIC_ACID_BARREL, GameConstants.getInTicks(1,0));
        GameConstants.ITEM_COOLDOWNS.put(MEDICAL_KIT, GameConstants.getInTicks(1,0));
    }

    /// 新增物品
    //医疗箱
    public static final Item MEDICAL_KIT = registerItem(
            new MedicalKitItem(new Item.Settings().maxCount(1)),
            "medical_kit"
    );
    //硫酸桶
    public static final Item SULFURIC_ACID_BARREL = registerItem(
            new SulfuricAcidBarrelItem(new Item.Settings().maxCount(1)),
            "sulfuric_acid_barrel"
    );

    /// 注册物品
    public static Item registerItem(Item item, String id) {
        Identifier itemID = Identifier.of(KinsWathe.MOD_ID, id);
        return Registry.register(Registries.ITEM, itemID, item);
    }
}