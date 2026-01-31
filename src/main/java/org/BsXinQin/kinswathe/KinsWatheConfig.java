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

    @SerialEntry(comment = "Starting cooldown.")
    public int StartingCooldown = GameConstants.getInTicks(0,30) / 20;

    @SerialEntry(comment = "Whether to enable staminabar.")
    public boolean EnableStaminaBar = true;

    @SerialEntry(comment = "Whether to enable jump when not in game.")
    public boolean EnableJumpNotInGame = true;

    /// 关于KinsWathe修改
    @SerialEntry(comment = "\n\n[Kin's Wathe] Modify:\nBellringer: modify the price of ability.")
    public int BellringerAbilityPrice = 200;

    @SerialEntry(comment = "Detective: modify the price of ability.")
    public int DetectiveAbilityPrice = 200;

    @SerialEntry(comment = "Cleaner: modify the price of ability.")
    public int CleanerAbilityPrice = 200;

    @SerialEntry(comment = "Drugmaker: modify get coins when someone was poisoned.")
    public int DrugmakerGetCoins = 50;

    @SerialEntry(comment = "Licensed Villain: modify the price of Revolver in shop.")
    public int LicensedVillainPrice = 300;

    /// 关于NoellesRoles修改
    @SerialEntry(comment = "\n\n[Noelle's Roles] Modify:\nWhether to enable Noelle's Roles modify.")
    public boolean EnableNoellesRolesModify = true;

    @SerialEntry(comment = "Conductor: whether to enable instinct of seeing dropped items.")
    public boolean ConductorInstinctModify = false;

    @SerialEntry(comment = "Coroner: whether to enable instinct of seeing player bodies.")
    public boolean CoronerInstinctModify = false;

    @SerialEntry(comment = "Recaller: whether to enable the special effects of ability.")
    public boolean RecallerAbilityModify = true;
}