package org.BsXinQin.kinswathe;

import dev.doctor4t.wathe.api.Role;
import dev.doctor4t.wathe.api.WatheRoles;
import dev.doctor4t.wathe.api.event.GameEvents;
import dev.doctor4t.wathe.cca.*;
import dev.doctor4t.wathe.game.GameConstants;
import dev.doctor4t.wathe.game.GameFunctions;
import dev.doctor4t.wathe.index.WatheItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.BsXinQin.kinswathe.component.AbilityPlayerComponent;
import org.BsXinQin.kinswathe.component.RolesHaveIncomeComponent;
import org.agmas.harpymodloader.Harpymodloader;
import org.agmas.harpymodloader.events.ModdedRoleAssigned;
import org.BsXinQin.kinswathe.packet.*;
import org.agmas.harpymodloader.modifiers.HMLModifiers;
import org.agmas.harpymodloader.modifiers.Modifier;
import org.agmas.noellesroles.Noellesroles;
import org.agmas.noellesroles.recaller.RecallerPlayerComponent;

import java.util.*;

public class KinsWathe implements ModInitializer {

    public static String MOD_ID = "kinswathe";

    /// 获取noellesroles注册前置
    public static boolean NOELLESROLES_LOADED = FabricLoader.getInstance().isModLoaded("noellesroles");

    /// 设置游戏开始和结束功能
    private boolean ResetPlayerSlots = false;
    private boolean ClearItems = false;

    /// 定义身份词条
    //定义身份
    //平民
    public static Identifier BELLRINGER_ID = Identifier.of(MOD_ID, "bellringer");
    public static Identifier COOK_ID = Identifier.of(MOD_ID, "cook");
    public static Identifier DETECTIVE_ID = Identifier.of(MOD_ID, "detective");
    public static Identifier PHYSICIAN_ID = Identifier.of(MOD_ID, "physician");
    public static Identifier ROBOT_ID = Identifier.of(MOD_ID, "robot");
    //杀手
    public static Identifier CLEANER_ID = Identifier.of(MOD_ID, "cleaner");
    public static Identifier DRUGMAKER_ID = Identifier.of(MOD_ID, "drugmaker");
    //中立
    public static Identifier LICENSED_VILLAIN_ID = Identifier.of(MOD_ID, "licensed_villain");
    //定义词条
    public static Identifier TASKMASTER_ID = Identifier.of(MOD_ID, "taskmaster");

    /// 新增身份词条
    //平民
    //敲钟人
    public static Role BELLRINGER = WatheRoles.registerRole(new Role(
            BELLRINGER_ID,
            0x66B2FF,
            true,
            false,
            Role.MoodType.REAL,
            WatheRoles.CIVILIAN.getMaxSprintTime(),
            true
    ));
    //厨师
    public static Role COOK = WatheRoles.registerRole(new Role(
            COOK_ID,
            0xCCFF99,
            true,
            false,
            Role.MoodType.REAL,
            WatheRoles.CIVILIAN.getMaxSprintTime(),
            false
    ));
    //侦探
    public static Role DETECTIVE = WatheRoles.registerRole(new Role(
            DETECTIVE_ID,
            0xFFFFCC,
            true,
            false,
            Role.MoodType.REAL,
            WatheRoles.CIVILIAN.getMaxSprintTime(),
            false
    ));
    //医师
    public static Role PHYSICIAN = WatheRoles.registerRole(new Role(
            PHYSICIAN_ID,
            0xFFE5CC,
            true,
            false,
            Role.MoodType.REAL,
            WatheRoles.CIVILIAN.getMaxSprintTime(),
            false
    ));
    //机器人
    public static Role ROBOT = WatheRoles.registerRole(new Role(
            ROBOT_ID,
            0xC0C0C0,
            true,
            false,
            Role.MoodType.FAKE,
            -1,
            false
    ));
    //杀手
    //清道夫
    public static Role CLEANER = WatheRoles.registerRole(new Role(
            CLEANER_ID,
            0x16582C,
            false,
            true,
            Role.MoodType.FAKE,
            -1,
            true
    ));
    //制毒师
    public static Role DRUGMAKER = WatheRoles.registerRole(new Role(
            DRUGMAKER_ID,
            0x4C0099,
            false,
            true,
            Role.MoodType.FAKE,
            -1,
            true
    ));
    //中立
    //执照恶棍
    public static Role LICENSED_VILLAIN = WatheRoles.registerRole(new Role(
            LICENSED_VILLAIN_ID,
            0x404040,
            false,
            false,
            Role.MoodType.FAKE,
            300,
            false
    ));
    //新增词条
    //任务大师
    public static Modifier TASKMASTER = HMLModifiers.registerModifier(new Modifier(
            TASKMASTER_ID,
            0xFF3399,
            null,
            new ArrayList<>(RolesHaveIncomeComponent.getRoles()),
            false,
            false
    ));

    /// 设置身份
    public static final ArrayList<Role> VANNILA_ROLES = new ArrayList<>();
    public static final ArrayList<Identifier> VANNILA_ROLE_IDS = new ArrayList<>();
    public static final ArrayList<Role> NEUTRAL_ROLES = new ArrayList<>();

    /// 设置数据包
    public static final CustomPayload.Id<AbilityC2SPacket> ABILITY_PACKET = AbilityC2SPacket.ID;
    public static final CustomPayload.Id<ConfigSyncS2CPacket> CONFIG_SYNC_PACKET = ConfigSyncS2CPacket.ID;

    /// 初始化设置
    @Override
    public void onInitialize() {
        //导入原版内容
        VANNILA_ROLES.add(WatheRoles.KILLER);
        VANNILA_ROLES.add(WatheRoles.CIVILIAN);
        VANNILA_ROLES.add(WatheRoles.VIGILANTE);
        VANNILA_ROLES.add(WatheRoles.LOOSE_END);
        VANNILA_ROLE_IDS.add(WatheRoles.KILLER.identifier());
        VANNILA_ROLE_IDS.add(WatheRoles.CIVILIAN.identifier());
        VANNILA_ROLE_IDS.add(WatheRoles.VIGILANTE.identifier());
        VANNILA_ROLE_IDS.add(WatheRoles.LOOSE_END.identifier());

        //新增中立身份
        NEUTRAL_ROLES.add(LICENSED_VILLAIN);

        //限制身份人数
        Harpymodloader.setRoleMaximum(LICENSED_VILLAIN, 1);

        //初始化数据包
        PayloadTypeRegistry.playC2S().register(AbilityC2SPacket.ID, AbilityC2SPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(ConfigSyncS2CPacket.ID, ConfigSyncS2CPacket.CODEC);

        //初始化配置文件
        KinsWatheConfig.HANDLER.load();
        setClientConfig();
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {sendConfigToClient(handler.player);});

        //初始化物品
        KinsWatheItems.setItemCooldown();

        //初始化游戏开始和结束设置
        ResetPlayerSlots = false;
        ClearItems = false;

        //初始化事件与网络数据包
        registerEvents();
        registerPackets();
    }

    /// 设置客户端Config
    public static final Map<String, Integer> CLIENT_INT_CONFIGS = new HashMap<>();
    public static final Map<String, Boolean> CLIENT_BOOL_CONFIGS = new HashMap<>();
    //具体内容
    public static final String StartingCooldown = "StartingCooldown";
    public static final String EnableStaminaBar = "EnableStaminaBar";
    public static final String EnableKeyNotInGame = "EnableKeyNotInGame";
    public static final String BellringerAbilityPrice = "BellringerAbilityPrice";
    public static final String DetectiveAbilityPrice = "DetectiveAbilityPrice";
    public static final String CleanerAbilityPrice = "CleanerAbilityPrice";
    public static final String LicensedVillainPrice = "LicensedVillainPrice";
    public static final String EnableNoellesRolesModify = "EnableNoellesRolesModify";
    public static final String BartenderPriceModify = "BartenderPriceModify";
    public static final String RecallerAbilityModify = "RecallerAbilityModify";
    public static final String TrapperPriceModify = "BartenderPriceModify";
    //设置客户端默认配置
    private static void setClientConfig() {
        CLIENT_INT_CONFIGS.put(StartingCooldown, 30);
        CLIENT_BOOL_CONFIGS.put(EnableStaminaBar, true);
        CLIENT_BOOL_CONFIGS.put(EnableKeyNotInGame, true);
        CLIENT_INT_CONFIGS.put(BellringerAbilityPrice, 200);
        CLIENT_INT_CONFIGS.put(DetectiveAbilityPrice, 200);
        CLIENT_INT_CONFIGS.put(CleanerAbilityPrice, 200);
        CLIENT_INT_CONFIGS.put(LicensedVillainPrice, 300);
        CLIENT_BOOL_CONFIGS.put(EnableNoellesRolesModify, true);
        CLIENT_INT_CONFIGS.put(BartenderPriceModify, 150);
        CLIENT_BOOL_CONFIGS.put(RecallerAbilityModify, true);
        CLIENT_INT_CONFIGS.put(TrapperPriceModify, 200);
    }
    //发送配置给客户端
    private void sendConfigToClient(ServerPlayerEntity player) {
        Map<String, Integer> intConfigs = new HashMap<>();
        Map<String, Boolean> boolConfigs = new HashMap<>();
        KinsWatheConfig config = KinsWatheConfig.HANDLER.instance();
        intConfigs.put(StartingCooldown, config.StartingCooldown);
        boolConfigs.put(EnableStaminaBar, config.EnableStaminaBar);
        boolConfigs.put(EnableKeyNotInGame, config.EnableKeyNotInGame);
        intConfigs.put(BellringerAbilityPrice, config.BellringerAbilityPrice);
        intConfigs.put(DetectiveAbilityPrice, config.DetectiveAbilityPrice);
        intConfigs.put(CleanerAbilityPrice, config.CleanerAbilityPrice);
        intConfigs.put(LicensedVillainPrice, config.LicensedVillainPrice);
        boolConfigs.put(EnableNoellesRolesModify, config.EnableNoellesRolesModify);
        intConfigs.put(BartenderPriceModify, config.BartenderPriceModify);
        boolConfigs.put(RecallerAbilityModify, config.RecallerAbilityModify);
        intConfigs.put(TrapperPriceModify, config.TrapperPriceModify);
        ServerPlayNetworking.send(player, new ConfigSyncS2CPacket(intConfigs, boolConfigs));
    }
    //设置配置接口
    public static int StartingCooldown() {if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) return KinsWatheConfig.HANDLER.instance().StartingCooldown; else return CLIENT_INT_CONFIGS.getOrDefault(StartingCooldown, 30);}
    public static boolean EnableStaminaBar() {if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) return KinsWatheConfig.HANDLER.instance().EnableStaminaBar; else return CLIENT_BOOL_CONFIGS.getOrDefault(EnableStaminaBar, true);}
    public static boolean EnableKeyNotInGame() {if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) return KinsWatheConfig.HANDLER.instance().EnableKeyNotInGame; else return CLIENT_BOOL_CONFIGS.getOrDefault(EnableKeyNotInGame, true);}
    public static int BellringerAbilityPrice() {if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) return KinsWatheConfig.HANDLER.instance().BellringerAbilityPrice; else return CLIENT_INT_CONFIGS.getOrDefault(BellringerAbilityPrice, 200);}
    public static int DetectiveAbilityPrice() {if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) return KinsWatheConfig.HANDLER.instance().DetectiveAbilityPrice; else return CLIENT_INT_CONFIGS.getOrDefault(DetectiveAbilityPrice, 200);}
    public static int CleanerAbilityPrice() {if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) return KinsWatheConfig.HANDLER.instance().CleanerAbilityPrice; else return CLIENT_INT_CONFIGS.getOrDefault(CleanerAbilityPrice, 200);}
    public static int LicensedVillainPrice() {if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) return KinsWatheConfig.HANDLER.instance().LicensedVillainPrice; else return CLIENT_INT_CONFIGS.getOrDefault(LicensedVillainPrice, 300);}
    public static boolean EnableNoellesRolesModify() {if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) return KinsWatheConfig.HANDLER.instance().EnableNoellesRolesModify; else return CLIENT_BOOL_CONFIGS.getOrDefault(EnableNoellesRolesModify, true);}
    public static int BartenderPriceModify() {if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) return KinsWatheConfig.HANDLER.instance().BartenderPriceModify; else return CLIENT_INT_CONFIGS.getOrDefault(BartenderPriceModify, 150);}
    public static boolean RecallerAbilityModify() {if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) return KinsWatheConfig.HANDLER.instance().RecallerAbilityModify; else return CLIENT_BOOL_CONFIGS.getOrDefault(RecallerAbilityModify, true);}
    public static int TrapperPriceModify() {if (FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) return KinsWatheConfig.HANDLER.instance().TrapperPriceModify; else return CLIENT_INT_CONFIGS.getOrDefault(TrapperPriceModify, 200);}

    /// 注册身份id
    public static Identifier id(String path) {return Identifier.of(MOD_ID, path);}

    /// 注册事件
    public void registerEvents() {
        //初始化身份(给予初始物品等)
        ModdedRoleAssigned.EVENT.register((player,role)->{
            AbilityPlayerComponent ability = AbilityPlayerComponent.KEY.get(player);
            ability.cooldown = StartingCooldown() * 20;
            //医师初始物品
            if (role.equals(PHYSICIAN)) {
                player.giveItemStack(KinsWatheItems.MEDICAL_KIT.getDefaultStack());
            }
            //黑警初始物品
            if (role.equals(LICENSED_VILLAIN)) {
                player.giveItemStack(WatheItems.LOCKPICK.getDefaultStack());
            }
            //清道夫初始物品
            if (role.equals(CLEANER)) {
                player.giveItemStack(KinsWatheItems.SULFURIC_ACID_BARREL.getDefaultStack());
            }
        });
        //限制身份生成人数
        ServerTickEvents.END_SERVER_TICK.register(((server) -> {
            if (server.getPlayerManager().getCurrentPlayerCount() >= 10) {
                Harpymodloader.setRoleMaximum(LICENSED_VILLAIN,1);} else {
                Harpymodloader.setRoleMaximum(LICENSED_VILLAIN,0);
            }
            if (server.getPlayerManager().getCurrentPlayerCount() >= 12) {
                Harpymodloader.setRoleMaximum(CLEANER,1);} else {
                Harpymodloader.setRoleMaximum(CLEANER,0);
            }
        }));
        //注册游戏开始时事件
        GameEvents.ON_GAME_START.register((gameMode) -> {ResetPlayerSlots = true;});
        ServerTickEvents.START_SERVER_TICK.register(server -> {
            if (ResetPlayerSlots) {
                for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                    player.getInventory().selectedSlot = 8;
                }
                ResetPlayerSlots = false;
            }
        });
        //注册游戏结束时事件
        GameEvents.ON_GAME_STOP.register((gameMode) -> {ClearItems = true;});
        ServerTickEvents.START_SERVER_TICK.register(server -> {
            if (ClearItems) {
                try {
                    server.getCommandManager().executeWithPrefix(
                            server.getCommandSource(),
                            "kill @e[type=item]"
                    );
                } catch (Exception ignored) {}
                ClearItems = false;
            }
        });
    }

    /// 注册网络数据包
    public void registerPackets() {
        /// 按键技能
        ServerPlayNetworking.registerGlobalReceiver(KinsWathe.ABILITY_PACKET, (payload, context) -> {
            ServerPlayerEntity player = context.player();
            ServerWorld world = player.getServerWorld();
            GameWorldComponent gameWorld = GameWorldComponent.KEY.get(player.getWorld());
            PlayerShopComponent playerShop = PlayerShopComponent.KEY.get(player);
            AbilityPlayerComponent ability = AbilityPlayerComponent.KEY.get(player);
            org.agmas.noellesroles.AbilityPlayerComponent noellesrolesAbility = null;
            if (KinsWathe.NOELLESROLES_LOADED) noellesrolesAbility = org.agmas.noellesroles.AbilityPlayerComponent.KEY.get(player);
            /// KinsWathe技能
            if (GameFunctions.isPlayerAliveAndSurvival(player) && ability.cooldown <= 0) {
                //敲钟人技能
                if (gameWorld.isRole(player, BELLRINGER)) {
                    GameTimeComponent time = GameTimeComponent.KEY.get(player.getWorld());
                    if (playerShop.balance < KinsWathe.BellringerAbilityPrice()) return;
                    playerShop.balance -= KinsWathe.BellringerAbilityPrice();
                    playerShop.sync();
                    int currentTime = time.getTime();
                    int newTime = Math.max(0, currentTime - 1200);
                    time.setTime(newTime);
                    world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BLOCK_BELL_USE, SoundCategory.PLAYERS, 1.0f, 1.0f);
                    ability.cooldown = GameConstants.getInTicks(2, 0);
                    ability.sync();
                    if (KinsWathe.NOELLESROLES_LOADED) {
                        noellesrolesAbility.cooldown = GameConstants.getInTicks(2, 0);
                        noellesrolesAbility.sync();
                    }
                }
                //机器人技能
                if (gameWorld.isRole(player, ROBOT)) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 200, 0, true, true, false));
                    world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_IRON_GOLEM_HURT, SoundCategory.PLAYERS, 1.0f, 1.0f);
                    ability.cooldown = GameConstants.getInTicks(2, 0);
                    ability.sync();
                    if (KinsWathe.NOELLESROLES_LOADED) {
                        noellesrolesAbility.cooldown = GameConstants.getInTicks(2, 0);
                        noellesrolesAbility.sync();
                    }
                }
                //侦探技能
                if (gameWorld.isRole(player, DETECTIVE)) {
                    ServerPlayerEntity targetPlayer = null;
                    if (playerShop.balance < KinsWathe.DetectiveAbilityPrice()) return;
                    HitResult targetHitResult = ProjectileUtil.getCollision(player, entity -> entity instanceof ServerPlayerEntity target && target != player && GameFunctions.isPlayerAliveAndSurvival(target), 2.0f);
                    if (targetHitResult instanceof EntityHitResult entityHitResult) {
                        Entity entity = entityHitResult.getEntity();
                        if (entity instanceof ServerPlayerEntity) {
                            targetPlayer = (ServerPlayerEntity) entity;
                        }
                    }
                    Role targetRole = gameWorld.getRole(targetPlayer);
                    if (targetPlayer == null || targetRole == null) return;
                    playerShop.balance -= KinsWathe.DetectiveAbilityPrice();
                    playerShop.sync();
                    if (targetRole.isInnocent()) {
                        player.sendMessage(Text.translatable("tip.kinswathe.detective.innocent", targetPlayer.getName().getString()).withColor(WatheRoles.CIVILIAN.color()), true);
                        player.playSoundToPlayer(SoundEvents.ENTITY_VILLAGER_YES, SoundCategory.PLAYERS, 1.0f, 1.0f);
                    } else {
                        player.sendMessage(Text.translatable("tip.kinswathe.detective.notinnocent", targetPlayer.getName().getString()).withColor(WatheRoles.KILLER.color()), true);
                        player.playSoundToPlayer(SoundEvents.ENTITY_VILLAGER_NO, SoundCategory.PLAYERS, 1.0f, 1.0f);
                    }
                    ability.cooldown = GameConstants.getInTicks(1, 30);
                    ability.sync();
                    if (KinsWathe.NOELLESROLES_LOADED) {
                        noellesrolesAbility.cooldown = GameConstants.getInTicks(1, 30);
                        noellesrolesAbility.sync();
                    }
                }
                //清道夫技能
                if (gameWorld.isRole(player, CLEANER)) {
                    ServerCommandSource cleanDrops = player.getCommandSource().withLevel(4).withSilent();
                    if (playerShop.balance < KinsWathe.CleanerAbilityPrice()) return;
                    playerShop.balance -= KinsWathe.CleanerAbilityPrice();
                    playerShop.sync();
                    try {
                        Objects.requireNonNull(player.getServer()).getCommandManager().executeWithPrefix(cleanDrops, "kill @e[type=item]");
                    } catch (Exception ignored) {
                    }
                    player.playSoundToPlayer(SoundEvents.ENTITY_ENDER_DRAGON_FLAP, SoundCategory.PLAYERS, 1.0f, 1.0f);
                    ability.cooldown = GameConstants.getInTicks(2, 30);
                    ability.sync();
                    if (KinsWathe.NOELLESROLES_LOADED) {
                        noellesrolesAbility.cooldown = GameConstants.getInTicks(2, 30);
                        noellesrolesAbility.sync();
                    }
                }
            }
            /// 修改NoellesRoles技能
            if (KinsWathe.NOELLESROLES_LOADED && KinsWathe.EnableNoellesRolesModify()) {
                if (GameFunctions.isPlayerAliveAndSurvival(player) && noellesrolesAbility.cooldown <= 0) {
                    //修改回溯者技能
                    if (gameWorld.isRole(player, Noellesroles.RECALLER)) {
                        if (!KinsWathe.RecallerAbilityModify()) return;
                        RecallerPlayerComponent recallerPlayerComponent = RecallerPlayerComponent.KEY.get(player);
                        if (!recallerPlayerComponent.placed) {
                            player.playSoundToPlayer(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1.0f, 1.0f);
                        } else if (playerShop.balance >= 100) {
                            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);
                            world.spawnParticles(ParticleTypes.PORTAL, player.getX(), player.getY(), player.getZ(), 75, 0.5, 1.5, 0.5, 0.1);
                        }
                    }
                }
            }
        });
    }
}