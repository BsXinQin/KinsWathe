package org.BsXinQin.kinswathe.component;

import dev.doctor4t.wathe.game.GameConstants;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.BsXinQin.kinswathe.KinsWathe;
import org.BsXinQin.kinswathe.KinsWatheConfig;
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class ConfigWorldComponent implements AutoSyncedComponent, ServerTickingComponent {

    public static final ComponentKey<ConfigWorldComponent> KEY = ComponentRegistry.getOrCreate(Identifier.of(KinsWathe.MOD_ID, "config"), ConfigWorldComponent.class);

    /// 具体配置内容
    //全局配置
    public int StartingCooldown = GameConstants.getInTicks(0,30) / 20;
    public boolean EnableStaminaBar = true;
    public boolean EnableJumpNotInGame = true;
    //关于KinsWathe修改
    public int BellringerAbilityPrice = 200;
    public int BellringerAbilityCooldown = GameConstants.getInTicks(2,0) / 20;
    public int DetectiveAbilityPrice = 200;
    public int DetectiveAbilityCooldown = GameConstants.getInTicks(1,30) / 20;
    public int JudgeAbilityPrice = 300;
    public int JudgeAbilityGlowing = GameConstants.getInTicks(1,30) / 20;
    public int JudgeAbilityCooldown = GameConstants.getInTicks(3,0) / 20;
    public int RobotAbilityCooldown = GameConstants.getInTicks(1,30) / 20;
    public int CleanerAbilityPrice = 200;
    public int CleanerAbilityCooldown = GameConstants.getInTicks(2,30) / 20;
    public int DrugmakerGetCoins = 50;
    public int LicensedVillainRevolverPrice = 300;
    //关于NoellesRoles修改
    public boolean EnableNoellesRolesModify = true;
    public boolean ConductorInstinctModify = false;
    public boolean CoronerInstinctModify = false;
    public boolean RecallerAbilityModify = true;

    /// 同步服务端客户端配置
    public void writeToNbt(@NotNull NbtCompound tag, RegistryWrapper.@NotNull WrapperLookup registryLookup) {
        //全局配置
        StartingCooldown = KinsWatheConfig.HANDLER.instance().StartingCooldown; tag.putInt("StartingCooldown", this.StartingCooldown);
        EnableStaminaBar = KinsWatheConfig.HANDLER.instance().EnableStaminaBar; tag.putBoolean("EnableStaminaBar", this.EnableStaminaBar);
        EnableJumpNotInGame = KinsWatheConfig.HANDLER.instance().EnableJumpNotInGame; tag.putBoolean("EnableJumpNotInGame", this.EnableJumpNotInGame);
        //关于KinsWathe修改
        BellringerAbilityPrice = KinsWatheConfig.HANDLER.instance().BellringerAbilityPrice; tag.putInt("BellringerAbilityPrice", this.BellringerAbilityPrice);
        BellringerAbilityCooldown = KinsWatheConfig.HANDLER.instance().BellringerAbilityCooldown; tag.putInt("BellringerAbilityCooldown", this.BellringerAbilityCooldown);
        DetectiveAbilityPrice = KinsWatheConfig.HANDLER.instance().DetectiveAbilityPrice; tag.putInt("DetectiveAbilityPrice", this.DetectiveAbilityPrice);
        DetectiveAbilityCooldown = KinsWatheConfig.HANDLER.instance().DetectiveAbilityCooldown; tag.putInt("DetectiveAbilityCooldown", this.DetectiveAbilityCooldown);
        JudgeAbilityPrice = KinsWatheConfig.HANDLER.instance().JudgeAbilityPrice; tag.putInt("JudgeAbilityPrice", this.JudgeAbilityPrice);
        JudgeAbilityGlowing = KinsWatheConfig.HANDLER.instance().JudgeAbilityGlowing; tag.putInt("JudgeAbilityGlowing", this.JudgeAbilityGlowing);
        JudgeAbilityCooldown = KinsWatheConfig.HANDLER.instance().JudgeAbilityCooldown; tag.putInt("JudgeAbilityCooldown", this.JudgeAbilityCooldown);
        RobotAbilityCooldown = KinsWatheConfig.HANDLER.instance().RobotAbilityCooldown; tag.putInt("RobotAbilityCooldown", this.RobotAbilityCooldown);
        CleanerAbilityPrice = KinsWatheConfig.HANDLER.instance().CleanerAbilityPrice; tag.putInt("CleanerAbilityPrice", this.CleanerAbilityPrice);
        CleanerAbilityCooldown = KinsWatheConfig.HANDLER.instance().CleanerAbilityCooldown; tag.putInt("CleanerAbilityCooldown", this.CleanerAbilityCooldown);
        DrugmakerGetCoins = KinsWatheConfig.HANDLER.instance().DrugmakerGetCoins; tag.putInt("DrugmakerGetCoins", this.DrugmakerGetCoins);
        LicensedVillainRevolverPrice = KinsWatheConfig.HANDLER.instance().LicensedVillainRevolverPrice; tag.putInt("LicensedVillainRevolverPrice", this.LicensedVillainRevolverPrice);
        //关于NoellesRoles修改
        EnableNoellesRolesModify = KinsWatheConfig.HANDLER.instance().EnableNoellesRolesModify; tag.putBoolean("EnableNoellesRolesModify", this.EnableNoellesRolesModify);
        ConductorInstinctModify = KinsWatheConfig.HANDLER.instance().ConductorInstinctModify; tag.putBoolean("ConductorInstinctModify", this.ConductorInstinctModify);
        CoronerInstinctModify = KinsWatheConfig.HANDLER.instance().CoronerInstinctModify; tag.putBoolean("CoronerInstinctModify", this.CoronerInstinctModify);
        RecallerAbilityModify = KinsWatheConfig.HANDLER.instance().RecallerAbilityModify; tag.putBoolean("RecallerAbilityModify", this.RecallerAbilityModify);
    }
    public void readFromNbt(@NotNull NbtCompound tag, RegistryWrapper.@NotNull WrapperLookup registryLookup) {
        //全局配置
        if (tag.contains("StartingCooldown"))   this.StartingCooldown = tag.getInt("StartingCooldown");
        if (tag.contains("EnableStaminaBar"))   this.EnableStaminaBar = tag.getBoolean("EnableStaminaBar");
        if (tag.contains("EnableJumpNotInGame"))   this.EnableJumpNotInGame = tag.getBoolean("EnableJumpNotInGame");
        //关于KinsWathe修改
        if (tag.contains("BellringerAbilityPrice"))   this.BellringerAbilityPrice = tag.getInt("BellringerAbilityPrice");
        if (tag.contains("BellringerAbilityCooldown"))   this.BellringerAbilityCooldown = tag.getInt("BellringerAbilityCooldown");
        if (tag.contains("DetectiveAbilityPrice"))   this.DetectiveAbilityPrice = tag.getInt("DetectiveAbilityPrice");
        if (tag.contains("DetectiveAbilityCooldown"))   this.DetectiveAbilityCooldown = tag.getInt("DetectiveAbilityCooldown");
        if (tag.contains("JudgeAbilityPrice"))   this.JudgeAbilityPrice = tag.getInt("JudgeAbilityPrice");
        if (tag.contains("JudgeAbilityGlowing"))   this.JudgeAbilityGlowing = tag.getInt("JudgeAbilityGlowing");
        if (tag.contains("JudgeAbilityCooldown"))   this.JudgeAbilityCooldown = tag.getInt("JudgeAbilityCooldown");
        if (tag.contains("RobotAbilityCooldown"))   this.RobotAbilityCooldown = tag.getInt("RobotAbilityCooldown");
        if (tag.contains("CleanerAbilityPrice"))   this.CleanerAbilityPrice = tag.getInt("CleanerAbilityPrice");
        if (tag.contains("CleanerAbilityCooldown"))   this.CleanerAbilityCooldown = tag.getInt("CleanerAbilityCooldown");
        if (tag.contains("DrugmakerGetCoins"))   this.DrugmakerGetCoins = tag.getInt("DrugmakerGetCoins");
        if (tag.contains("LicensedVillainRevolverPrice"))   this.LicensedVillainRevolverPrice = tag.getInt("LicensedVillainRevolverPrice");
        //关于NoellesRoles修改
        if (tag.contains("EnableNoellesRolesModify"))   this.EnableNoellesRolesModify = tag.getBoolean("EnableNoellesRolesModify");
        if (tag.contains("ConductorInstinctModify"))   this.ConductorInstinctModify = tag.getBoolean("ConductorInstinctModify");
        if (tag.contains("CoronerInstinctModify"))   this.CoronerInstinctModify = tag.getBoolean("CoronerInstinctModify");
        if (tag.contains("RecallerAbilityModify"))   this.RecallerAbilityModify = tag.getBoolean("RecallerAbilityModify");
    }

    private final World world;
    public void sync() {
        KEY.sync(this.world);
    }
    public void reset() {this.sync();}
    public ConfigWorldComponent(World world) {this.world = world;}
    @Override public void serverTick() {this.sync();}
}