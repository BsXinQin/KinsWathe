package org.BsXinQin.kinswathe.client;

import dev.doctor4t.wathe.api.Role;
import dev.doctor4t.wathe.cca.GameWorldComponent;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.BsXinQin.kinswathe.KinsWathe;
import org.BsXinQin.kinswathe.KinsWatheItems;
import org.BsXinQin.kinswathe.client.component.ExtraModelComponent;
import org.BsXinQin.kinswathe.client.component.ItemTipComponent;
import org.BsXinQin.kinswathe.packet.AbilityC2SPacket;
import org.agmas.noellesroles.ModItems;
import org.agmas.noellesroles.client.NoellesrolesClient;
import org.lwjgl.glfw.GLFW;

public class KinsWatheClient implements ClientModInitializer {

    public static KeyBinding abilityBind;

    @Override
    public void onInitializeClient() {

        /// 设置技能按键
        if (FabricLoader.getInstance().isModLoaded("noellesroles")) {
            if (abilityBind == null) ClientTickEvents.START_CLIENT_TICK.register(client -> {abilityBind = NoellesrolesClient.abilityBind;});
        } else {
            abilityBind = KeyBindingHelper.registerKeyBinding(new KeyBinding("key." + KinsWathe.MOD_ID + ".ability", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "category.wathe.keybinds"));
        }

        /// 添加有技能的角色
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (abilityBind == null) return;
            if (abilityBind.isPressed()) {
                client.execute(() -> {
                    if (MinecraftClient.getInstance().player == null) return;
                    GameWorldComponent gameWorld = GameWorldComponent.KEY.get(MinecraftClient.getInstance().player.getWorld());
                    boolean sendAbilityPacket = false;
                    Role[] rolesWithAbility = new Role[] {
                            KinsWathe.BELLRINGER,
                            KinsWathe.DETECTIVE,
                            KinsWathe.JUDGE,
                            KinsWathe.ROBOT,
                            KinsWathe.CLEANER
                    };
                    for (Role role : rolesWithAbility) {
                        if (gameWorld.isRole(MinecraftClient.getInstance().player, role)) sendAbilityPacket = true;
                    }
                    if (!sendAbilityPacket) return;
                    ClientPlayNetworking.send(new AbilityC2SPacket());
                });
            }
        });

        /// 添加物品描述
        ItemTooltipCallback.EVENT.register(((itemStack, tooltipContext, tooltipType, list) -> {
            //添加KinsWathe物品提示
            ItemTipComponent.addItemtip(KinsWatheItems.BLOWGUN, itemStack, list);
            ItemTipComponent.addItemtip(KinsWatheItems.KNOCKOUT_DRUG, itemStack, list);
            ItemTipComponent.addItemtip(KinsWatheItems.MEDICAL_KIT, itemStack, list);
            ItemTipComponent.addItemtip(KinsWatheItems.PAN, itemStack, list);
            ItemTipComponent.addItemtip(KinsWatheItems.POISON_INJECTOR, itemStack, list);
            ItemTipComponent.addItemtip(KinsWatheItems.SULFURIC_ACID_BARREL, itemStack, list);
            //添加NoellreRoles物品冷却提示
            if (FabricLoader.getInstance().isModLoaded("noellesroles")) {
                ItemTipComponent.addCooldowntip(ModItems.FAKE_REVOLVER, itemStack, list);
            }
        }));

        /// 注册物品额外材质
        ExtraModelComponent.registerCooldownModel(KinsWatheItems.POISON_INJECTOR);
    }
}