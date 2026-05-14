package org.BsXinQin.kinswathe;

import dev.doctor4t.wathe.api.Role;
import dev.doctor4t.wathe.api.WatheRoles;
import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import dev.doctor4t.wathe.index.WatheItems;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import org.BsXinQin.kinswathe.component.AbilityPlayerComponent;
import org.BsXinQin.kinswathe.packet.host.AbilityC2SPacket;
import org.BsXinQin.kinswathe.packet.roles.BodymakerC2SPacket;
import org.BsXinQin.kinswathe.packet.roles.JudgeC2SPacket;
import org.BsXinQin.kinswathe.roles.bellringer.BellringerAbility;
import org.BsXinQin.kinswathe.roles.bodymaker.BodymakerAbility;
import org.BsXinQin.kinswathe.roles.cleaner.CleanerAbility;
import org.BsXinQin.kinswathe.roles.detective.DetectiveAbility;
import org.BsXinQin.kinswathe.roles.dreamer.DreamerKillerComponent;
import org.BsXinQin.kinswathe.roles.hacker.HackerPhoneComponent;
import org.BsXinQin.kinswathe.roles.hunter.HunterAbility;
import org.BsXinQin.kinswathe.roles.judge.JudgeAbility;
import org.BsXinQin.kinswathe.roles.robot.RobotAbility;
import org.agmas.harpymodloader.Harpymodloader;
import org.agmas.harpymodloader.config.HarpyModLoaderConfig;
import org.agmas.harpymodloader.events.ModdedRoleAssigned;
import org.agmas.harpymodloader.modifiers.HMLModifiers;
import org.agmas.harpymodloader.modifiers.Modifier;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KinsWatheRoles {

    private static final HashMap<String, Role> ROLES = new HashMap<>();
    public static HashMap<String, Role> getRoles() {return ROLES;}
    private static final HashMap<String, Modifier> MODIFIERS = new HashMap<>();
    public static HashMap<String, Modifier> getModifiers() {return MODIFIERS;}

    /// 新增身份
    //敲钟人
    public static Role BELLRINGER = registerRole(new Role(
            Identifier.of(KinsWathe.MOD_ID, "bellringer"),
            0x66B2FF,
            true,
            false,
            Role.MoodType.REAL,
            WatheRoles.CIVILIAN.getMaxSprintTime(),
            true
    ));
    //造尸怪
    public static Role BODYMAKER = registerRole(new Role(
            Identifier.of(KinsWathe.MOD_ID,"bodymaker"),
            0x2148d1,
            false,
            true,
            Role.MoodType.FAKE,
            -1,
            true
    ));
    //清道夫
    public static Role CLEANER = registerRole(new Role(
            Identifier.of(KinsWathe.MOD_ID, "cleaner"),
            0x16582C,
            false,
            true,
            Role.MoodType.FAKE,
            -1,
            true
    ));
    //厨师
    public static Role COOK = registerRole(new Role(
            Identifier.of(KinsWathe.MOD_ID, "cook"),
            0xCCFF99,
            true,
            false,
            Role.MoodType.REAL,
            WatheRoles.CIVILIAN.getMaxSprintTime(),
            false
    ));
    //侦探
    public static Role DETECTIVE = registerRole(new Role(
            Identifier.of(KinsWathe.MOD_ID, "detective"),
            0xFFFFCC,
            true,
            false,
            Role.MoodType.REAL,
            WatheRoles.CIVILIAN.getMaxSprintTime(),
            false
    ));
    //梦者
    public static Role DREAMER = registerNoellesRole(new Role(
            Identifier.of(KinsWathe.MOD_ID, "dreamer"),
            0xE5CCFF,
            false,
            false,
            Role.MoodType.FAKE,
            -1,
            true
    ));
    //制毒师
    public static Role DRUGMAKER = registerRole(new Role(
            Identifier.of(KinsWathe.MOD_ID, "drugmaker"),
            0x4C0099,
            false,
            true,
            Role.MoodType.FAKE,
            -1,
            true
    ));
    //黑客
    public static Role HACKER = registerRole(new Role(
            Identifier.of(KinsWathe.MOD_ID, "hacker"),
            0x808080,
            false,
            false,
            Role.MoodType.FAKE,
            WatheRoles.CIVILIAN.getMaxSprintTime(),
            true
    ));
    //追猎者
    public static Role HUNTER = registerRole(new Role(
            Identifier.of(KinsWathe.MOD_ID, "hunter"),
            0x663300,
            false,
            true,
            Role.MoodType.FAKE,
            -1,
            true
    ));
    //大法官
    public static Role JUDGE = registerRole(new Role(
            Identifier.of(KinsWathe.MOD_ID, "judge"),
            0xECECF7,
            true,
            false,
            Role.MoodType.REAL,
            WatheRoles.CIVILIAN.getMaxSprintTime(),
            false
    ));
    //绑匪
    public static Role KIDNAPPER = registerRole(new Role(
            Identifier.of(KinsWathe.MOD_ID, "kidnapper"),
            0xCC0066,
            false,
            true,
            Role.MoodType.FAKE,
            -1,
            true
    ));
    //执照恶棍
    public static Role LICENSED_VILLAIN = registerRole(new Role(
            Identifier.of(KinsWathe.MOD_ID, "licensed_villain"),
            0x404040,
            false,
            false,
            Role.MoodType.FAKE,
            WatheRoles.CIVILIAN.getMaxSprintTime() * 3 / 2,
            false
    ));
    //医师
    public static Role PHYSICIAN = registerRole(new Role(
            Identifier.of(KinsWathe.MOD_ID, "physician"),
            0xFFE5CC,
            true,
            false,
            Role.MoodType.REAL,
            WatheRoles.CIVILIAN.getMaxSprintTime() * 3 / 2,
            false
    ));
    //机器人
    public static Role ROBOT = registerRole(new Role(
            Identifier.of(KinsWathe.MOD_ID, "robot"),
            0xC0C0C0,
            true,
            false,
            Role.MoodType.FAKE,
            -1,
            false
    ));
    //技术员
    public static Role TECHNICIAN = registerRole(new Role(
            Identifier.of(KinsWathe.MOD_ID, "technician"),
            0x003366,
            true,
            false,
            Role.MoodType.REAL,
            WatheRoles.CIVILIAN.getMaxSprintTime(),
            false
    ));

    /// 新增词条
    //富豪
    public static Modifier MAGNATE = registerModifier(new Modifier(
            Identifier.of(KinsWathe.MOD_ID, "magnate"),
            0xFFFF00,
            null,
            new ArrayList<>(rolesHavePassiveIncome()),
            false,
            false
    ));
    //任务大师
    public static Modifier TASKMASTER = registerModifier(new Modifier(
            Identifier.of(KinsWathe.MOD_ID, "taskmaster"),
            0xFF3399,
            null,
            new ArrayList<>(rolesHaveTaskIncome()),
            false,
            false
    ));
    //违禁者
    public static Modifier VIOLATOR = registerModifier(new Modifier(
            Identifier.of(KinsWathe.MOD_ID, "violator"),
            0x660000,
            null,
            null,
            false,
            false
    ));

    /// 注册方法
    //注册身份
    public static Role registerRole(Role role) {
        WatheRoles.registerRole(role);
        ROLES.put(role.identifier().getPath(), role);
        return role;
    }
    public static Role registerNoellesRole(Role role) {
        if (FabricLoader.getInstance().isModLoaded("noellesroles")) {
            WatheRoles.registerRole(role);
            ROLES.put(role.identifier().getPath(), role);
        }
        return role;
    }
    //注册词条
    public static Modifier registerModifier(Modifier modifier) {
        HMLModifiers.registerModifier(modifier);
        MODIFIERS.put(modifier.identifier().getPath(), modifier);
        return modifier;
    }

    /// 引入其他模组角色
    //引入NoellesRoles角色
    public static Role noellesrolesRoles(@NotNull String role) {
        try {
            Class<?> roleClass = Class.forName("org.agmas.noellesroles.Noellesroles");
            Field roleField = roleClass.getField(role);
            return (Role) roleField.get(null);
        } catch (NoSuchFieldException | ClassNotFoundException | IllegalAccessException ignored) {}
        return null;
    }
    public static boolean noellesrolesKillerSidedNeutrals(@NotNull Object role) {
        try {
            Class<?> noellesrolesClass = Class.forName("org.agmas.noellesroles.Noellesroles");
            Field field = noellesrolesClass.getDeclaredField("KILLER_SIDED_NEUTRALS");
            field.setAccessible(true);
            ArrayList<?> neutralList = (ArrayList<?>) field.get(null);
            return neutralList.contains(role);
        } catch (NoSuchFieldException | ClassNotFoundException | IllegalAccessException ignored) {}
        return false;
    }
    public static boolean isKillerSidedNeutral(@NotNull PlayerEntity player) {
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(player.getWorld());
        return FabricLoader.getInstance().isModLoaded("noellesroles") && gameWorld.getRole(player) != null && (KinsWatheRoles.noellesrolesKillerSidedNeutrals(gameWorld.getRole(player)) || gameWorld.isRole(player, noellesrolesRoles("MIMIC")));
    }

    /// 添加有任务收入的身份
    public static List<Role> rolesHaveTaskIncome() {
        List<Role> roles = new ArrayList<>();
        roles.add(WatheRoles.KILLER);
        roles.add(BELLRINGER);
        roles.add(BODYMAKER);
        roles.add(CLEANER);
        roles.add(COOK);
        roles.add(DETECTIVE);
        roles.add(DRUGMAKER);
        roles.add(HUNTER);
        roles.add(JUDGE);
        roles.add(KIDNAPPER);
        roles.add(LICENSED_VILLAIN);
        roles.add(PHYSICIAN);
        roles.add(TECHNICIAN);
        if (KinsWatheConfig.HANDLER.instance().HackerHasShop) roles.add(HACKER);
        if (FabricLoader.getInstance().isModLoaded("noellesroles")) {
            roles.add(noellesrolesRoles("PHANTOM"));
            roles.add(noellesrolesRoles("SWAPPER"));
            roles.add(noellesrolesRoles("TRAPPER"));
            roles.add(noellesrolesRoles("RECALLER"));
            roles.add(noellesrolesRoles("BARTENDER"));
            roles.add(noellesrolesRoles("MORPHLING"));
            roles.add(noellesrolesRoles("NOISEMAKER"));
            roles.add(noellesrolesRoles("THE_INSANE_DAMNED_PARANOID_KILLER_OF_DOOM_DEATH_DESTRUCTION_AND_WAFFLES"));
        }
        return List.copyOf(roles);
    }

    /// 添加有被动收入的身份
    public static List<Role> rolesHavePassiveIncome() {
        List<Role> roles = new ArrayList<>();
        roles.add(WatheRoles.KILLER);
        roles.add(BODYMAKER);
        roles.add(CLEANER);
        roles.add(COOK);
        roles.add(DREAMER);
        roles.add(DRUGMAKER);
        roles.add(HUNTER);
        roles.add(JUDGE);
        roles.add(KIDNAPPER);
        if (KinsWatheConfig.HANDLER.instance().HackerHasShop) roles.add(HACKER);
        if (FabricLoader.getInstance().isModLoaded("noellesroles")) {
            roles.add(noellesrolesRoles("MIMIC"));
            roles.add(noellesrolesRoles("JESTER"));
            roles.add(noellesrolesRoles("PHANTOM"));
            roles.add(noellesrolesRoles("SWAPPER"));
            roles.add(noellesrolesRoles("MORPHLING"));
            roles.add(noellesrolesRoles("NOISEMAKER"));
            roles.add(noellesrolesRoles("EXECUTIONER"));
            roles.add(noellesrolesRoles("THE_INSANE_DAMNED_PARANOID_KILLER_OF_DOOM_DEATH_DESTRUCTION_AND_WAFFLES"));
        }
        return List.copyOf(roles);
    }

    /// 新增阵营
    //新增阵营
    public static final ArrayList<Role> NEUTRAL_ROLES = new ArrayList<>();
    public static final ArrayList<Role> KILLER_NEUTRAL_ROLES = new ArrayList<>();
    public static void addNewRoleCamps() {
        addNeutralRoles();
        addKillerNeutralRoles();
    }
    //新增中立身份
    public static void addNeutralRoles() {
        NEUTRAL_ROLES.add(LICENSED_VILLAIN);
    }
    //新增杀手方中立身份
    public static void addKillerNeutralRoles() {
        KILLER_NEUTRAL_ROLES.add(HACKER);
        if (FabricLoader.getInstance().isModLoaded("noellesroles")) {
            KILLER_NEUTRAL_ROLES.add(DREAMER);
            Harpymodloader.setRoleMaximum(DREAMER, 1);
        }
    }

    /// 限制身份生成人数
    public static void limitRolesGeneratePlayers() {
        ServerTickEvents.END_SERVER_TICK.register(((server) -> {
            //限制制毒师生成人数
            if (server.getPlayerManager().getCurrentPlayerCount() >= KinsWatheConfig.HANDLER.instance().DrugmakerPlayerLimit) {
                Harpymodloader.setRoleMaximum(DRUGMAKER,1);} else {
                Harpymodloader.setRoleMaximum(DRUGMAKER,0);
            }
            //限制黑客生成人数
            if (server.getPlayerManager().getCurrentPlayerCount() >= KinsWatheConfig.HANDLER.instance().HackerPlayerLimit) {
                Harpymodloader.setRoleMaximum(HACKER,1);} else {
                Harpymodloader.setRoleMaximum(HACKER,0);
            }
            //限制执照恶棍生成人数
            if (server.getPlayerManager().getCurrentPlayerCount() >= KinsWatheConfig.HANDLER.instance().LicensedVillainPlayerLimit) {
                Harpymodloader.setRoleMaximum(LICENSED_VILLAIN,1);} else {
                Harpymodloader.setRoleMaximum(LICENSED_VILLAIN,0);
            }
        }));
    }

    /// 限制冲突身份同时生成
    public static boolean conflictRolesGenerate(@NotNull Role role1, @NotNull Role role2) {
        if (!FabricLoader.getInstance().isModLoaded("noellesroles")) return false;
        if (KinsWatheConfig.HANDLER.instance().HackerGenerateWithMimic) return false;
        return (role1 == HACKER && role2 == noellesrolesRoles("MIMIC")) || (role1 == noellesrolesRoles("MIMIC") && role2 == HACKER);
    }

    /// 设置初始事件
    public static void setDefaultEvents() {
        ModdedRoleAssigned.EVENT.register((player, role)->{
            AbilityPlayerComponent ability = AbilityPlayerComponent.KEY.get(player);
            ability.cooldown = KinsWatheConfig.HANDLER.instance().StartingCooldown * 20;
            GameWorldComponent gameWorld = GameWorldComponent.KEY.get(player.getWorld());
            PlayerShopComponent playerShop = PlayerShopComponent.KEY.get(player);
            DreamerKillerComponent playerDreamer = DreamerKillerComponent.KEY.get(player);
            HackerPhoneComponent phone = HackerPhoneComponent.KEY.get(player);
            //阵营初始收入
            if (KinsWatheConfig.HANDLER.instance().EnableWatheModify) {
                if (gameWorld.isInnocent(player)) playerShop.addToBalance(KinsWatheConfig.HANDLER.instance().InitialCivilianIncome);
                if (!gameWorld.isInnocent(player) && !gameWorld.canUseKillerFeatures(player)) playerShop.addToBalance(KinsWatheConfig.HANDLER.instance().InitialNeutralIncome);
                if (gameWorld.canUseKillerFeatures(player)) playerShop.addToBalance(KinsWatheConfig.HANDLER.instance().InitialKillerIncome - 100);
            }
            //清道夫初始物品
            if (role.equals(CLEANER)) {
                player.giveItemStack(KinsWatheItems.SULFURIC_ACID_BARREL.getDefaultStack());
            }
            //梦者初始物品
            if (role.equals(DREAMER)) {
                player.giveItemStack(new ItemStack(KinsWatheItems.DREAM_IMPRINT, KinsWatheConfig.HANDLER.instance().DreamerInitialItemQuantity));
                playerDreamer.setDreamerRequired();
            }
            //黑客初始物品
            if (role.equals(HACKER)) {
                player.giveItemStack(phone.getPhone());
                for (ServerPlayerEntity serverPlayer : player.getServer().getPlayerManager().getPlayerList()) {
                    if (serverPlayer == null) continue;
                    if (gameWorld.canUseKillerFeatures(serverPlayer)) {
                        serverPlayer.giveItemStack(phone.getPhone());
                    }
                }
            }
            //绑匪初始物品
            if (role.equals(KIDNAPPER)) {
                player.giveItemStack(KinsWatheItems.KNOCKOUT_DRUG.getDefaultStack());
            }
            //执照恶棍初始物品
            if (role.equals(LICENSED_VILLAIN)) {
                player.giveItemStack(WatheItems.LOCKPICK.getDefaultStack());
            }
            //医师初始物品
            if (role.equals(PHYSICIAN)) {
                player.giveItemStack(KinsWatheItems.MEDICAL_KIT.getDefaultStack());
            }
        });
    }

    /// 注册身份技能
    public static void registerRolesAbility() {
        ServerPlayNetworking.registerGlobalReceiver(AbilityC2SPacket.ID, (payload, context) -> {
            BellringerAbility.register(context.player());
            CleanerAbility.register(context.player());
            DetectiveAbility.register(context.player());
            HunterAbility.register(context.player());
            RobotAbility.register(context.player());
        });
        ServerPlayNetworking.registerGlobalReceiver(BodymakerC2SPacket.ID, (payload, context) -> {
            BodymakerAbility.register(payload, context.player());
        });
        ServerPlayNetworking.registerGlobalReceiver(JudgeC2SPacket.ID, (payload, context) -> {
            JudgeAbility.register(payload, context.player());
        });
    }

    /// 限制词条自动启用配置
    public static void limitModifiersGenerateConfig() {
        if (!KinsWatheConfig.HANDLER.instance().ViolatorEnabled) {
            HarpyModLoaderConfig.HANDLER.load();
            //限制违禁者自动启用配置
            if (!HarpyModLoaderConfig.HANDLER.instance().disabledModifiers.contains(Identifier.of(KinsWathe.MOD_ID, "violator").toString())) {
                HarpyModLoaderConfig.HANDLER.instance().disabledModifiers.add(Identifier.of(KinsWathe.MOD_ID, "violator").toString());
            }
            HarpyModLoaderConfig.HANDLER.save();
        }
    }

    /// 初始化方法
    public static void init() {
        //新增阵营
        addNewRoleCamps();
        //限制身份生成人数
        limitRolesGeneratePlayers();
        //设置初始事件
        setDefaultEvents();
        //注册身份技能
        registerRolesAbility();
        //限制词条自动启用配置
        limitModifiersGenerateConfig();
    }
}