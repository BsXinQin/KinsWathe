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
    public boolean EnableJumpNotInGame = true;

    @SerialEntry(comment = "Whether to enable safe time when game starts.")
    public boolean EnableGameSafeTime = true;

    /// 关于KinsWathe修改
    @SerialEntry(comment = "\n\n[Kin's Wathe] Modify:\nBellringer: modify price and cooldown of ability.")
    public int BellringerAbilityPrice = 200;
    @SerialEntry
    public int BellringerAbilityCooldown = GameConstants.getInTicks(2,0) / 20;

    @SerialEntry(comment = "Cleaner: modify generation player limit, price and cooldown of ability.")
    public int CleanerPlayerLimit = 12;
    @SerialEntry
    public int CleanerAbilityPrice = 200;
    @SerialEntry
    public int CleanerAbilityCooldown = GameConstants.getInTicks(2,30) / 20;

    @SerialEntry(comment = "Cook: modify price of Pan.")
    public int CookPanPrice = 250;

    @SerialEntry(comment = "Detective: modify price and cooldown of ability.")
    public int DetectiveAbilityPrice = 200;
    @SerialEntry
    public int DetectiveAbilityCooldown = GameConstants.getInTicks(1,30) / 20;

    @SerialEntry(comment = "Drugmaker: modify get coins when someone was poisoned, price of Poison Injector and Blowgun.")
    public int DrugmakerGetCoins = 50;
    @SerialEntry
    public int DrugmakerPoisonInjectorPrice = 125;
    @SerialEntry
    public int DrugmakerBlowgunPrice = 175;

    @SerialEntry(comment = "Hunter: modify price and cooldown of ability.")
    public int HunterAbilityPrice = 150;
    @SerialEntry
    public int HunterAbilityCooldown = GameConstants.getInTicks(0,5) / 20;

    @SerialEntry(comment = "Judge: modify price and cooldown of ability.")
    public int JudgeAbilityPrice = 300;
    @SerialEntry
    public int JudgeAbilityGlowing = GameConstants.getInTicks(1,30) / 20;
    @SerialEntry
    public int JudgeAbilityCooldown = GameConstants.getInTicks(3,0) / 20;

    @SerialEntry(comment = "Kidnapper: modify price of Knockout Drug.")
    public int KidnapperKnockoutDrugPrice = 75;

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

    @SerialEntry(comment = "Violator: whether to allow Violator to retain disable/enable state after a server restart.")
    public boolean ViolatorEnabled = false;

    /// 关于NoellesRoles修改
    @SerialEntry(comment = "\n\n[Noelle's Roles] Modify:\nWhether to enable Noelle's Roles modify.")
    public boolean EnableNoellesRolesModify = true;

    @SerialEntry(comment = "Conductor: whether to enable instinct of seeing dropped items.")
    public boolean ConductorInstinctModify = false;

    @SerialEntry(comment = "Coroner: whether to enable instinct of seeing player bodies.")
    public boolean CoronerInstinctModify = false;
}