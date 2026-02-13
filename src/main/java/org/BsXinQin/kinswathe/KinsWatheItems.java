package org.BsXinQin.kinswathe;

import dev.doctor4t.wathe.game.GameConstants;
import dev.doctor4t.wathe.game.GameFunctions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import org.BsXinQin.kinswathe.items.*;
import org.jetbrains.annotations.NotNull;

public class KinsWatheItems {

    /// 新增物品
    //吹矢
    public static final Item BLOWGUN = registerItem(
            new BlowgunItem(new Item.Settings().maxCount(1)),
            "blowgun"
    );
    //吹矢
    public static final Item HUNTING_KNIFE = registerItem(
            new HuntingKnifeItem(new Item.Settings().maxCount(1)),
            "hunting_knife"
    );
    //迷药
    public static final Item KNOCKOUT_DRUG = registerItem(
            new KnockoutDrugItem(new Item.Settings().maxCount(4)),
            "knockout_drug"
    );
    //医疗箱
    public static final Item MEDICAL_KIT = registerItem(
            new MedicalKitItem(new Item.Settings().maxCount(1)),
            "medical_kit"
    );
    //平底锅
    public static final Item PAN = registerItem(
            new PanItem(new Item.Settings().maxCount(1)),
            "pan"
    );
    //毒液注射器
    public static final Item PILL = registerItem(
            new PillItem(new Item.Settings().maxCount(1)),
            "pill"
    );
    //毒液注射器
    public static final Item POISON_INJECTOR = registerItem(
            new PoisonInjectorItem(new Item.Settings().maxCount(1)),
            "poison_injector"
    );
    //硫酸桶
    public static final Item SULFURIC_ACID_BARREL = registerItem(
            new SulfuricAcidBarrelItem(new Item.Settings().maxCount(1)),
            "sulfuric_acid_barrel"
    );

    /// 注册方法
    public static Item registerItem(Item item, String id) {
        Identifier itemID = Identifier.of(KinsWathe.MOD_ID, id);
        return Registry.register(Registries.ITEM, itemID, item);
    }

    /// 设置物品使用
    public static void setItemAfterUsing(@NotNull PlayerEntity player, @NotNull Item item, Hand hand) {
        if (GameFunctions.isPlayerAliveAndSurvival(player)) {
            player.getItemCooldownManager().set(item, GameConstants.ITEM_COOLDOWNS.get(item));
            if (hand != null) player.getStackInHand(hand).decrement(1);
        }
    }

    /// 添加物品冷却
    public static void addItemCooldowns() {
        GameConstants.ITEM_COOLDOWNS.put(BLOWGUN, GameConstants.getInTicks(1,0));
        GameConstants.ITEM_COOLDOWNS.put(HUNTING_KNIFE, GameConstants.getInTicks(0,45));
        GameConstants.ITEM_COOLDOWNS.put(KNOCKOUT_DRUG, GameConstants.getInTicks(0,60));
        GameConstants.ITEM_COOLDOWNS.put(MEDICAL_KIT, GameConstants.getInTicks(1,0));
        GameConstants.ITEM_COOLDOWNS.put(PAN, GameConstants.getInTicks(0,45));
        GameConstants.ITEM_COOLDOWNS.put(PILL, GameConstants.getInTicks(2,0));
        GameConstants.ITEM_COOLDOWNS.put(POISON_INJECTOR, GameConstants.getInTicks(1,0));
        GameConstants.ITEM_COOLDOWNS.put(SULFURIC_ACID_BARREL, GameConstants.getInTicks(1,0));
    }

    /// 初始化方法
    public static void init() {
        //添加物品冷却
        addItemCooldowns();
    }
}