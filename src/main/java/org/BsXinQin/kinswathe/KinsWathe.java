package org.BsXinQin.kinswathe;

import net.fabricmc.api.ModInitializer;

public class KinsWathe implements ModInitializer {

    public static String MOD_ID = "kinswathe";

    @Override
    public void onInitialize() {
        //初始化游戏设置
        KinsWatheGameSettings.init();
        //初始化角色
        KinsWatheRoles.init();
        //初始化物品
        KinsWatheItems.init();
    }
}