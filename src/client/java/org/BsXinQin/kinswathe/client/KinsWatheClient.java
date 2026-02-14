package org.BsXinQin.kinswathe.client;

import net.fabricmc.api.ClientModInitializer;

public class KinsWatheClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        //客户端初始化
        KinsWatheInitializeClient.init();
    }
}