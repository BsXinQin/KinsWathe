package org.BsXinQin.kinswathe.client;

import dev.doctor4t.wathe.api.Role;
import dev.doctor4t.wathe.cca.GameWorldComponent;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.BsXinQin.kinswathe.KinsWathe;
import org.BsXinQin.kinswathe.KinsWatheItems;
import org.BsXinQin.kinswathe.KinsWatheRoles;
import org.BsXinQin.kinswathe.client.component.ExtraModelComponent;
import org.BsXinQin.kinswathe.client.component.ItemTipComponent;
import org.BsXinQin.kinswathe.packet.AbilityC2SPacket;
import org.agmas.noellesroles.client.NoellesrolesClient;
import org.lwjgl.glfw.GLFW;

public class KinsWatheInitializeClient {

    public static KeyBinding abilityBind;

    /// 设置技能按键
    public static void registerKeyBinding() {
        if (FabricLoader.getInstance().isModLoaded("noellesroles")) {
            if (abilityBind == null) ClientTickEvents.START_CLIENT_TICK.register(client -> {
                abilityBind = NoellesrolesClient.abilityBind;
            });
        } else if (!FabricLoader.getInstance().isModLoaded("noellesroles") && FabricLoader.getInstance().isModLoaded("starexpress")) {
            abilityBind = KeyBindingHelper.registerKeyBinding(new KeyBinding("key." + KinsWathe.MOD_ID + ".ability", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_R, "category.wathe.keybinds"));
        } else {
            abilityBind = KeyBindingHelper.registerKeyBinding(new KeyBinding("key." + KinsWathe.MOD_ID + ".ability", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "category.wathe.keybinds"));
        }
    }

    /// 设置有技能的角色
    public static void setRoleAbilityPackets() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (abilityBind == null) return;
            if (abilityBind.isPressed()) {
                client.execute(() -> {
                    if (MinecraftClient.getInstance().player == null) return;
                    GameWorldComponent gameWorld = GameWorldComponent.KEY.get(MinecraftClient.getInstance().player.getWorld());
                    boolean sendAbilityPacket = false;
                    Role[] rolesWithAbility = new Role[]{
                            KinsWatheRoles.BELLRINGER,
                            KinsWatheRoles.DETECTIVE,
                            KinsWatheRoles.HUNTER,
                            KinsWatheRoles.JUDGE,
                            KinsWatheRoles.ROBOT,
                            KinsWatheRoles.CLEANER
                    };
                    for (Role role : rolesWithAbility) {
                        if (gameWorld.isRole(MinecraftClient.getInstance().player, role)) sendAbilityPacket = true;
                    }
                    if (!sendAbilityPacket) return;
                    ClientPlayNetworking.send(new AbilityC2SPacket());
                });
            }
        });
    }
    /// 添加物品描述和模型
    public static void addItemTipAndModel() {
        ItemTooltipCallback.EVENT.register(((itemStack, tooltipContext, tooltipType, list) -> {
            //添加KinsWathe物品描述
            ItemTipComponent.addItemtip(KinsWatheItems.BLOWGUN, itemStack, list);
            ItemTipComponent.addItemtip(KinsWatheItems.HUNTING_KNIFE, itemStack, list);
            ItemTipComponent.addItemtip(KinsWatheItems.KNOCKOUT_DRUG, itemStack, list);
            ItemTipComponent.addItemtip(KinsWatheItems.MEDICAL_KIT, itemStack, list);
            ItemTipComponent.addItemtip(KinsWatheItems.PAN, itemStack, list);
            ItemTipComponent.addItemtip(KinsWatheItems.PILL, itemStack, list);
            ItemTipComponent.addItemtip(KinsWatheItems.POISON_INJECTOR, itemStack, list);
            ItemTipComponent.addItemtip(KinsWatheItems.SULFURIC_ACID_BARREL, itemStack, list);
            //添加NoellreRoles物品冷却描述
            if (FabricLoader.getInstance().isModLoaded("noellesroles")) {
                ItemTipComponent.addCooldowntip(Registries.ITEM.get(Identifier.of("noellesroles", "fake_revolver")), itemStack, list);
            }
        }));
        //注册物品额外材质
        ExtraModelComponent.registerCooldownModel(KinsWatheItems.POISON_INJECTOR);
    }

    public static void init() {
        //设置技能按键
        registerKeyBinding();
        //添加有技能的角色
        setRoleAbilityPackets();
        //添加物品描述和模型
        addItemTipAndModel();
    }
}