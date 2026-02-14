package org.BsXinQin.kinswathe;

import dev.doctor4t.wathe.api.Role;
import dev.doctor4t.wathe.api.WatheRoles;
import dev.doctor4t.wathe.index.WatheItems;
import lombok.SneakyThrows;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.BsXinQin.kinswathe.component.AbilityPlayerComponent;
import org.BsXinQin.kinswathe.packet.AbilityC2SPacket;
import org.BsXinQin.kinswathe.roles.bellringer.BellringerAbility;
import org.BsXinQin.kinswathe.roles.cleaner.CleanerAbility;
import org.BsXinQin.kinswathe.roles.cook.CookComponent;
import org.BsXinQin.kinswathe.roles.detective.DetectiveAbility;
import org.BsXinQin.kinswathe.roles.dreamer.DreamerComponent;
import org.BsXinQin.kinswathe.roles.hunter.HunterAbility;
import org.BsXinQin.kinswathe.roles.hunter.HunterComponent;
import org.BsXinQin.kinswathe.roles.judge.JudgeAbility;
import org.BsXinQin.kinswathe.roles.kidnapper.KidnapperComponent;
import org.BsXinQin.kinswathe.roles.physician.PhysicianComponent;
import org.BsXinQin.kinswathe.roles.robot.RobotAbility;
import org.BsXinQin.kinswathe.roles.robot.RobotComponent;
import org.agmas.harpymodloader.Harpymodloader;
import org.agmas.harpymodloader.config.HarpyModLoaderConfig;
import org.agmas.harpymodloader.events.ModdedRoleAssigned;
import org.agmas.harpymodloader.events.ResetPlayerEvent;
import org.agmas.harpymodloader.modifiers.HMLModifiers;
import org.agmas.harpymodloader.modifiers.Modifier;

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
//    //梦者
//    public static Role DREAMER = registerRole(new Role(
//            Identifier.of(KinsWathe.MOD_ID, "dreamer"),
//            0xE5CCFF,
//            true,
//            false,
//            Role.MoodType.FAKE,
//            -1,
//            true
//    ));
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
    //追猎者
    public static Role HUNTER = registerRole(new Role(
            Identifier.of(KinsWathe.MOD_ID, "hunter"),
            0x663300,
            false,
            true,
            Role.MoodType.FAKE,
            400,
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
            300,
            false
    ));
    //医师
    public static Role PHYSICIAN = registerRole(new Role(
            Identifier.of(KinsWathe.MOD_ID, "physician"),
            0xFFE5CC,
            true,
            false,
            Role.MoodType.REAL,
            300,
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
            new ArrayList<>(rolesHaveIncome()),
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
    //注册词条
    public static Modifier registerModifier(Modifier modifier) {
        HMLModifiers.registerModifier(modifier);
        MODIFIERS.put(modifier.identifier().getPath(), modifier);
        return modifier;
    }

    /// 引入NoellesRoles角色
    @SneakyThrows
    public static Role noellesrolesRoles(String role) {
        Class<?> roleClass = Class.forName("org.agmas.noellesroles.Noellesroles");
        Field roleField = roleClass.getField(role);
        return (Role) roleField.get(null);
    }

    /// 添加有收入的身份
    public static List<Role> rolesHaveIncome() {
        List<Role> roles = new ArrayList<>();
        roles.add(WatheRoles.KILLER);
        roles.add(BELLRINGER);
        roles.add(CLEANER);
        roles.add(COOK);
        roles.add(DETECTIVE);
//        roles.add(DREAMER);
        roles.add(DRUGMAKER);
        roles.add(JUDGE);
        roles.add(KIDNAPPER);
        roles.add(LICENSED_VILLAIN);
        roles.add(PHYSICIAN);
        if (FabricLoader.getInstance().isModLoaded("noellesroles")) {
            roles.add(noellesrolesRoles("MIMIC"));
            roles.add(noellesrolesRoles("JESTER"));
            roles.add(noellesrolesRoles("PHANTOM"));
            roles.add(noellesrolesRoles("SWAPPER"));
            roles.add(noellesrolesRoles("TRAPPER"));
            roles.add(noellesrolesRoles("RECALLER"));
            roles.add(noellesrolesRoles("BARTENDER"));
            roles.add(noellesrolesRoles("MORPHLING"));
            roles.add(noellesrolesRoles("NOISEMAKER"));
            roles.add(noellesrolesRoles("EXECUTIONER"));
            roles.add(noellesrolesRoles("THE_INSANE_DAMNED_PARANOID_KILLER_OF_DOOM_DEATH_DESTRUCTION_AND_WAFFLES"));
        }
        return List.copyOf(roles);
    }

    /// 添加有被动收入的身份
    public static List<Role> rolesHavePassiveIncome() {
        List<Role> roles = new ArrayList<>();
        roles.add(WatheRoles.KILLER);
        roles.add(COOK);
        roles.add(CLEANER);
//        roles.add(DREAMER);
        roles.add(DRUGMAKER);
        roles.add(JUDGE);
        roles.add(KIDNAPPER);
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
//    public static final ArrayList<Role> KILLER_NEUTRAL_ROLES = new ArrayList<>();
    //新增中立身份
    public static void addNeutralRoles() {
        NEUTRAL_ROLES.add(LICENSED_VILLAIN);
        Harpymodloader.setRoleMaximum(LICENSED_VILLAIN, 1);
    }
//    //新增杀手方中立身份
//    public static void addKillerNeutralRoles() {
//        KILLER_NEUTRAL_ROLES.add(DREAMER);
//        Harpymodloader.setRoleMaximum(DREAMER, 1);
//    }

    /// 限制身份生成人数
    public static void limitRolesGeneratePlayers() {
        ServerTickEvents.END_SERVER_TICK.register(((server) -> {
            //限制清道夫生成人数
            if (server.getPlayerManager().getCurrentPlayerCount() >= KinsWatheConfig.HANDLER.instance().CleanerPlayerLimit) {
                Harpymodloader.setRoleMaximum(CLEANER,1);} else {
                Harpymodloader.setRoleMaximum(CLEANER,0);
            }
            //限制执照恶棍生成人数
            if (server.getPlayerManager().getCurrentPlayerCount() >= KinsWatheConfig.HANDLER.instance().LicensedVillainPlayerLimit) {
                Harpymodloader.setRoleMaximum(LICENSED_VILLAIN,1);} else {
                Harpymodloader.setRoleMaximum(LICENSED_VILLAIN,0);
            }
        }));
    }

    /// 设置初始事件
    public static void setDefaultEvents() {
        ModdedRoleAssigned.EVENT.register((player, role)->{
            AbilityPlayerComponent ability = AbilityPlayerComponent.KEY.get(player);
            ability.cooldown = KinsWatheConfig.HANDLER.instance().StartingCooldown * 20;
            //清道夫初始物品
            if (role.equals(CLEANER)) {
                player.giveItemStack(KinsWatheItems.SULFURIC_ACID_BARREL.getDefaultStack());
            }
//            //梦者初始物品
//            if (role.equals(DREAMER)) {
//                player.giveItemStack(KinsWatheItems.DREAM_IMPRINT.getDefaultStack());
//            }
            //黑警初始物品
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
            JudgeAbility.register(context.player());
            RobotAbility.register(context.player());
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


    /// 重置身份事件
    public static void resetRolesEvents() {
        ResetPlayerEvent.EVENT.register(player -> {
            CookComponent.KEY.get(player).reset();
            DreamerComponent.KEY.get(player).reset();
            HunterComponent.KEY.get(player).reset();
            KidnapperComponent.KEY.get(player).reset();
            RobotComponent.KEY.get(player).reset();
            PhysicianComponent.KEY.get(player).reset();
        });
    }

    /// 初始化方法
    public static void init() {
        //新增中立阵营
        addNeutralRoles();
        //限制身份生成人数
        limitRolesGeneratePlayers();
        //设置初始事件
        setDefaultEvents();
        //注册身份技能
        registerRolesAbility();
        //限制词条自动启用配置
        limitModifiersGenerateConfig();
        //重置身份事件
        resetRolesEvents();
    }
}
