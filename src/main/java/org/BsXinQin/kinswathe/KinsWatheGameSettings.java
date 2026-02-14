package org.BsXinQin.kinswathe;

import dev.doctor4t.wathe.api.event.GameEvents;
import dev.doctor4t.wathe.game.GameConstants;
import dev.doctor4t.wathe.index.WatheItems;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.BsXinQin.kinswathe.component.AbilityPlayerComponent;
import org.BsXinQin.kinswathe.component.GameSafeComponent;
import org.BsXinQin.kinswathe.packet.AbilityC2SPacket;
import org.agmas.harpymodloader.events.ResetPlayerEvent;
import org.jetbrains.annotations.NotNull;

public class KinsWatheGameSettings {

    private static boolean GAME_START = false;
    private static boolean GAME_STOP = false;

    /// 设置游戏开始和结束功能
    public static void betterGameSettings() {
        //注册游戏开始时事件
        GameEvents.ON_GAME_START.register((gameMode) -> {GAME_START = true;});
        ServerTickEvents.START_SERVER_TICK.register(server -> {
            if (GAME_START) {
                //指令
                server.getCommandManager().executeWithPrefix(server.getCommandSource().withSilent(), "kill @e[type=wathe:player_body]");
                server.getCommandManager().executeWithPrefix(server.getCommandSource().withSilent(), "kill @e[type=item]");
                server.getCommandManager().executeWithPrefix(server.getCommandSource().withSilent(), "effect clear @a");
                if (FabricLoader.getInstance().isModLoaded("noellesroles")) {
                    server.getCommandManager().executeWithPrefix(server.getCommandSource().withSilent(), "kill @e[type=noellesroles:cube]");
                }
                for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                    player.getInventory().selectedSlot = 8;
                }
                //游戏安全时间
                setGameSafeTime(server);
                GAME_START = false;
            }
        });
        //注册游戏结束时事件
        GameEvents.ON_GAME_STOP.register((gameMode) -> {GAME_STOP = true;});
        ServerTickEvents.START_SERVER_TICK.register(server -> {
            if (GAME_STOP) {
                //指令
                server.getCommandManager().executeWithPrefix(server.getCommandSource().withSilent(), "kill @e[type=wathe:player_body]");
                server.getCommandManager().executeWithPrefix(server.getCommandSource().withSilent(), "kill @e[type=item]");
                server.getCommandManager().executeWithPrefix(server.getCommandSource().withSilent(), "effect clear @a");
                if (FabricLoader.getInstance().isModLoaded("noellesroles")) {
                    server.getCommandManager().executeWithPrefix(server.getCommandSource().withSilent(), "kill @e[type=noellesroles:cube]");
                }
                GAME_STOP = false;
            }
        });
    }

    /// 设置游戏安全时间
    public static void setGameSafeTime(@NotNull MinecraftServer server) {
        if (!KinsWatheConfig.HANDLER.instance().EnableGameSafeTime) return;
        for (ServerPlayerEntity serverPlayer : server.getPlayerManager().getPlayerList()) {
            if (serverPlayer == null) return;
            serverPlayer.getItemCooldownManager().set(WatheItems.KNIFE, GameConstants.getInTicks(0, KinsWatheConfig.HANDLER.instance().StartingCooldown));
            serverPlayer.getItemCooldownManager().set(WatheItems.REVOLVER, GameConstants.getInTicks(0, KinsWatheConfig.HANDLER.instance().StartingCooldown));
            serverPlayer.getItemCooldownManager().set(WatheItems.GRENADE, GameConstants.getInTicks(0, KinsWatheConfig.HANDLER.instance().StartingCooldown));
            if (FabricLoader.getInstance().isModLoaded("noellesroles")) {
                serverPlayer.getItemCooldownManager().set(Registries.ITEM.get(Identifier.of("noellesroles", "bandit_revolver")), GameConstants.getInTicks(0, KinsWatheConfig.HANDLER.instance().StartingCooldown));
            }
            if (FabricLoader.getInstance().isModLoaded("stupid_express")) {
                serverPlayer.getItemCooldownManager().set(Registries.ITEM.get(Identifier.of("stupid_express", "jerry_can")), GameConstants.getInTicks(0, KinsWatheConfig.HANDLER.instance().StartingCooldown));
                serverPlayer.getItemCooldownManager().set(Registries.ITEM.get(Identifier.of("stupid_express", "lighter")), GameConstants.getInTicks(0, KinsWatheConfig.HANDLER.instance().StartingCooldown));
            }
            if (FabricLoader.getInstance().isModLoaded("harpysimpleroles")) {
                serverPlayer.getItemCooldownManager().set(Registries.ITEM.get(Identifier.of("harpysimpleroles", "bandit_revolver")), GameConstants.getInTicks(0, KinsWatheConfig.HANDLER.instance().StartingCooldown));
            }
            GameSafeComponent playerSafe = GameSafeComponent.KEY.get(serverPlayer);
            playerSafe.startGameSafe();
        }
    }

    /// 初始化配置文件
    public static void initializeConfig() {
        KinsWatheConfig.HANDLER.load();
    }

    /// 注册网络数据包
    public static void registerPackets() {
        PayloadTypeRegistry.playC2S().register(AbilityC2SPacket.ID, AbilityC2SPacket.CODEC);
    }

    /// 重置游戏事件
    public static void resetGameEvents() {
        ResetPlayerEvent.EVENT.register(player -> {
            GameSafeComponent.KEY.get(player).reset();
            AbilityPlayerComponent.KEY.get(player).reset();
        });
    }

    /// 初始化方法
    public static void init() {
        //设置游戏开始和结束功能
        betterGameSettings();
        //初始化配置文件
        initializeConfig();
        //注册网络数据包
        registerPackets();
        //重置游戏事件
        resetGameEvents();
    }
}
