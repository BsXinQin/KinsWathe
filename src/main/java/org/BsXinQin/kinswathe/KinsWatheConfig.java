package org.BsXinQin.kinswathe;

import dev.doctor4t.wathe.game.GameConstants;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

public class KinsWatheConfig {
    /// 配置文件设置
    public static ConfigClassHandler<KinsWatheConfig> HANDLER = ConfigClassHandler.createBuilder(KinsWatheConfig.class).id(Identifier.of(KinsWathe.MOD_ID, "config")).serializer(config -> GsonConfigSerializerBuilder.create(config).setPath(FabricLoader.getInstance().getConfigDir().resolve(KinsWathe.MOD_ID + ".json5")).setJson5(true).build()).build();

    @SerialEntry(comment = "Starting cooldown and game safe time (in seconds).")
    public int StartingCooldown = GameConstants.getInTicks(0,30) / 20;

    @SerialEntry(comment = "Whether to enable staminabar.")
    public boolean EnableStaminaBar = true;

    @SerialEntry(comment = "Whether to enable jump when not in game.")
    public boolean EnableJumpNotInGame = false;

    @SerialEntry(comment = "Whether to enable safe time when game starts.")
    public boolean EnableStartSafeTime = false;

    @SerialEntry(comment = "Whether to enable auto join voice chat group for spectators.")
    public boolean EnableAutoJoinVoiceChat = true;

    @SerialEntry(comment = "Whether to enable better Blackout visual effect.")
    public boolean EnableBetterBlackout = true;

    @SerialEntry(comment = "Whether to enable instinct when in Psycho Mode.")
    public boolean EnableAutoPsychoInstinct = true;

    /// 关于Wathe修改
    @SerialEntry(comment = "\n\n[Wathe] Modify (default settings are original settings of Wathe):")
    public boolean EnableWatheModify = false;

    @SerialEntry(comment = "Initial civilians income.")
    public int InitialCivilianIncome = 0;

    @SerialEntry(comment = "Initial neutrals income.")
    public int InitialNeutralIncome = 0;

    @SerialEntry(comment = "Initial killers income.")
    public int InitialKillerIncome = 100;

    @SerialEntry(comment = "Increase money when kill player.")
    public int IncreaseMoneyWhenKill = 100;

    @SerialEntry(comment = "Whether Revolver drop after killer kills civilian.")
    public boolean PreventKillerDropRevolver = false;

    /// 关于KinsWathe修改
    @SerialEntry(comment = "\n\n[Kin's Wathe] Modify:\nBellringer: modify price and cooldown of ability.")
    public int BellringerAbilityPrice = 200;
    @SerialEntry
    public int BellringerAbilityCooldown = GameConstants.getInTicks(2,0) / 20;

    @SerialEntry(comment = "Bodymaker: modify cooldown of ability and whether to fake role of body.")
    public int BodymakerAbilityCooldown = GameConstants.getInTicks(1,30) / 20;
    @SerialEntry
    public boolean BodymakerAbilityFakeRole = true;

    @SerialEntry(comment = "Cleaner: modify get coins when dissolve a body, price and cooldown of ability.")
    public int CleanerGetCoins = 50;

    public int CleanerAbilityPrice = 200;
    @SerialEntry
    public int CleanerAbilityCooldown = GameConstants.getInTicks(2,30) / 20;

    @SerialEntry(comment = "Cook: modify price of Pan.")
    public int CookPanPrice = 250;

    @SerialEntry(comment = "Detective: modify price and cooldown of ability.")
    public int DetectiveAbilityPrice = 200;
    @SerialEntry
    public int DetectiveAbilityCooldown = GameConstants.getInTicks(1,30) / 20;

    @SerialEntry(comment = "Dreamer: modify initial quantity of Dream Imprint.")
    public int DreamerInitialItemQuantity = 1;

    @SerialEntry(comment = "Drugmaker: modify generation player limit, get coins when someone was poisoned and price of Poison Injector and Blowgun.")
    public int DrugmakerPlayerLimit = 10;
    @SerialEntry
    public int DrugmakerGetCoins = 50;
    @SerialEntry
    public int DrugmakerPoisonInjectorPrice = 100;
    @SerialEntry
    public int DrugmakerBlowgunPrice = 175;

    @SerialEntry(comment = "Hacker: modify generation limit, hacking time, whether to enable shop and price of props.")
    public int HackerPlayerLimit = 10;
    @SerialEntry
    public boolean HackerGenerateWithMimic = false;
    @SerialEntry
    public int HackerHackingTime = GameConstants.getInTicks(0,30) / 20;
    @SerialEntry
    public boolean HackerHasShop = true;
    @SerialEntry
    public int HackerRefreshWeaponCooldownPrice = 300;
    @SerialEntry
    public int HackerRefreshAbilityCooldownPrice = 400;
    @SerialEntry
    public int HackerRefreshPotionEffectPrice = 200;

    @SerialEntry(comment = "Hunter: modify price and cooldown of ability.")
    public int HunterAbilityPrice = 125;
    @SerialEntry
    public int HunterAbilityCooldown = GameConstants.getInTicks(0,5) / 20;

    @SerialEntry(comment = "Judge: modify price and cooldown of ability.")
    public int JudgeAbilityPrice = 300;
    @SerialEntry
    public int JudgeAbilityGlowing = GameConstants.getInTicks(1,30) / 20;
    @SerialEntry
    public int JudgeAbilityCooldown = GameConstants.getInTicks(3,0) / 20;

    @SerialEntry(comment = "Kidnapper: modify price of Knockout Drug and get additional coins when kill dazed player.")
    public int KidnapperKnockoutDrugPrice = 75;
    @SerialEntry
    public int KidnapperGetAdditionalCoins = 100;

    @SerialEntry(comment = "Licensed Villain: modify generation player limit and price of Revolver.")
    public int LicensedVillainPlayerLimit = 10;
    @SerialEntry
    public int LicensedVillainRevolverPrice = 300;

    @SerialEntry(comment = "Physician: modify price of Pill.")
    public int PhysicianPillPrice = 300;

    @SerialEntry(comment = "Robot: modify duration and cooldown of ability.")
    public int RobotAbilityDuration = GameConstants.getInTicks(0,10) / 20;
    @SerialEntry
    public int RobotAbilityCooldown = GameConstants.getInTicks(1,30) / 20;

    @SerialEntry(comment = "Technician: modify price of props and Capture Device parameter.")
    public int TechnicianWrenchPrice = 100;
    @SerialEntry
    public int TechnicianCaptureDevicePrice = 100;
    @SerialEntry
    public int TechnicianPowerRestorationPrice = 300;
    @SerialEntry
    public int TechnicianCaptureDeviceStunTime = GameConstants.getInTicks(0,5) / 20;
    @SerialEntry
    public int TechnicianCaptureDeviceLifetimeSeconds = GameConstants.getInTicks(3,0) / 20;

    @SerialEntry(comment = "Violator: whether to allow Violator to retain disable/enable state after a server restart.")
    public boolean ViolatorEnabled = false;

    /// 关于NoellesRoles修改
    @SerialEntry(comment = "\n\n[Noelle's Roles] Modify (default settings are original settings of Noelle's Roles):")
    public boolean EnableNoellesRolesModify = false;

    @SerialEntry(comment = "Conductor: whether to enable instinct of seeing dropped items.")
    public boolean ConductorInstinctModify = false;

    @SerialEntry(comment = "Coroner: whether to enable instinct of seeing player bodies.")
    public boolean CoronerInstinctModify = false;

    @SerialEntry(comment = "Jester: whether to enable prevent attack killer in Psycho Mode.")
    public boolean JesterAttackKillerModify = false;
}