package org.BsXinQin.kinswathe.client.mixin.host;

import dev.doctor4t.wathe.api.Role;
import dev.doctor4t.wathe.api.WatheRoles;
import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerMoodComponent;
import dev.doctor4t.wathe.cca.PlayerPoisonComponent;
import dev.doctor4t.wathe.client.WatheClient;
import dev.doctor4t.wathe.entity.PlayerBodyEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.BsXinQin.kinswathe.KinsWathe;
import org.BsXinQin.kinswathe.component.ConfigWorldComponent;
import org.BsXinQin.kinswathe.roles.cook.CookPlayerEatComponent;
import org.agmas.noellesroles.Noellesroles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;
import java.util.UUID;

@Mixin(WatheClient.class)
public abstract class InstinctMixin {

    @Shadow public static KeyBinding instinctKeybind;
    @Unique private static final UUID DELUSION_MARKER = UUID.fromString("00000000-0000-0000-dead-c0de00000000");

    @Inject(method = "isInstinctEnabled", at = @At("HEAD"), cancellable = true)
    private static void Instinct(CallbackInfoReturnable<Boolean> ci) {
        if (WatheClient.isPlayerAliveAndInSurvival()) {
            MinecraftClient client = MinecraftClient.getInstance();
            GameWorldComponent gameWorld = GameWorldComponent.KEY.get(client.player.getWorld());
            if (gameWorld.isRole(client.player, KinsWathe.LICENSED_VILLAIN)) {
                if (instinctKeybind.isPressed()) {
                    ci.setReturnValue(true);
                    ci.cancel();
                }
            }
        }
    }

    @Inject(method = "getInstinctHighlight", at = @At("HEAD"), cancellable = true)
    private static void InstinctColor(Entity target, CallbackInfoReturnable<Integer> ci) {
        if (WatheClient.isPlayerAliveAndInSurvival()) {
            MinecraftClient client = MinecraftClient.getInstance();
            GameWorldComponent gameWorld = GameWorldComponent.KEY.get(client.player.getWorld());
            if (target instanceof PlayerEntity) {
                //厨师透视
                if (!target.isSpectator()) {
                    CookPlayerEatComponent targetEat = CookPlayerEatComponent.KEY.get(target);
                    if (gameWorld.isRole(client.player, KinsWathe.COOK) && targetEat.hasEaten()) {
                        ci.setReturnValue(WatheRoles.CIVILIAN.color());
                        ci.cancel();
                    }
                }
                //医师透视
                if (!target.isSpectator()) {
                    PlayerPoisonComponent targetPoison = PlayerPoisonComponent.KEY.get(target);
                    if (gameWorld.isRole(client.player, KinsWathe.PHYSICIAN) && targetPoison.poisonTicks > 0) {
                        UUID poisoner = targetPoison.poisoner;
                        if (poisoner == null) return;
                        if (KinsWathe.NOELLESROLES_LOADED) {
                            PlayerEntity poisonerPlayer = client.player.getWorld().getPlayerByUuid(poisoner);
                            if (poisoner.equals(DELUSION_MARKER)) return;
                            if (gameWorld.isRole(poisonerPlayer, Noellesroles.BARTENDER)) return;
                        }
                        ci.setReturnValue(WatheRoles.KILLER.color());
                    }
                }
                //制毒师透视
                if (!target.isSpectator() && !WatheClient.isInstinctEnabled()) {
                    PlayerPoisonComponent targetPoison = PlayerPoisonComponent.KEY.get(target);
                    if (gameWorld.isRole(client.player, KinsWathe.DRUGMAKER) && targetPoison.poisonTicks > 0) {
                        UUID poisoner = targetPoison.poisoner;
                        if (poisoner == null) return;
                        if (KinsWathe.NOELLESROLES_LOADED) {
                            PlayerEntity poisonerPlayer = client.player.getWorld().getPlayerByUuid(poisoner);
                            if (poisoner.equals(DELUSION_MARKER)) return;
                            if (gameWorld.isRole(poisonerPlayer, Noellesroles.BARTENDER)) return;
                        }
                        ci.setReturnValue(KinsWathe.DRUGMAKER.color());
                    }
                }
                //执照恶棍透视
                if (!target.isSpectator() && WatheClient.isInstinctEnabled()) {
                    if (gameWorld.isRole(client.player, KinsWathe.LICENSED_VILLAIN)) {
                        ci.setReturnValue(KinsWathe.LICENSED_VILLAIN.color());
                        ci.cancel();
                    }
                }
                //不同阵营透视效果
                if (!target.isSpectator() && WatheClient.isInstinctEnabled()) {
                    Role role = gameWorld.getRole((PlayerEntity) target);
                    if (role != null) {
                        if (WatheClient.isKiller() && WatheClient.isPlayerAliveAndInSurvival()) {
                            if (KinsWathe.NEUTRAL_ROLES.contains(role)) {
                                ci.setReturnValue(WatheRoles.CIVILIAN.color());
                                ci.cancel();
                            } else if (KinsWathe.NOELLESROLES_LOADED && Noellesroles.KILLER_SIDED_NEUTRALS.contains(role)) {
                                ci.setReturnValue(role.color());
                                ci.cancel();
                            }
                        }
                    }
                }
            }
            /// 修改NoellesRoles透视
            if (KinsWathe.NOELLESROLES_LOADED && ConfigWorldComponent.KEY.get(client.player.getWorld()).EnableNoellesRolesModify) {
                if (target instanceof ItemEntity) {
                    //修改列车长透视
                    if (ConfigWorldComponent.KEY.get(client.player.getWorld()).ConductorInstinctModify && gameWorld.isRole(client.player, Noellesroles.CONDUCTOR)) {
                        ci.setReturnValue(0xDB9D00);
                        ci.cancel();
                    }
                }
                if (target instanceof PlayerBodyEntity) {
                    //修改验尸官透视
                    if (ConfigWorldComponent.KEY.get(client.player.getWorld()).CoronerInstinctModify && gameWorld.isRole(client.player, Noellesroles.CORONER)) {
                        PlayerMoodComponent playerMood = PlayerMoodComponent.KEY.get(client.player);
                        if (playerMood != null) {
                            if (!playerMood.isLowerThanMid()) {
                                ci.setReturnValue(0x606060);
                                ci.cancel();
                            }
                        }
                    }
                }
            }
        }
    }
}
