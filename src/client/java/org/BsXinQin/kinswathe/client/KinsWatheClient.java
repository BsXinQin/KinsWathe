package org.BsXinQin.kinswathe.client;

import com.google.common.collect.Maps;
import dev.doctor4t.ratatouille.util.TextUtils;
import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.client.WatheClient;
import dev.doctor4t.wathe.client.util.WatheItemTooltips;
import dev.doctor4t.wathe.entity.PlayerBodyEntity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.BsXinQin.kinswathe.KinsWathe;
import org.BsXinQin.kinswathe.KinsWatheItems;
import org.BsXinQin.kinswathe.client.host.ItemCooldownComponent;
import org.BsXinQin.kinswathe.packet.AbilityC2SPacket;
import org.agmas.noellesroles.ModItems;
import org.agmas.noellesroles.client.NoellesrolesClient;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.*;

public class KinsWatheClient implements ClientModInitializer {

    public static int insanityTime = 0;
    public static KeyBinding abilityBind;
    public static PlayerEntity target;
    public static PlayerBodyEntity targetBody;
    public static Map<UUID, UUID> SHUFFLED_PLAYER_ENTRIES_CACHE = Maps.newHashMap();

    @Override
    public void onInitializeClient() {

        /// 设置技能按键
        if (!KinsWathe.NOELLESROLES_LOADED) {
            abilityBind = KeyBindingHelper.registerKeyBinding(new KeyBinding("key." + KinsWathe.MOD_ID + ".ability", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_G, "category.wathe.keybinds"));
        } else {
            ClientTickEvents.START_CLIENT_TICK.register(client -> {
                abilityBind = NoellesrolesClient.abilityBind;
            });
        }
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (abilityBind.isPressed()) {
                PacketByteBuf data = PacketByteBufs.create();
                client.execute(() -> {
                    if (MinecraftClient.getInstance().player == null) return;
                    GameWorldComponent gameWorld = GameWorldComponent.KEY.get(MinecraftClient.getInstance().player.getWorld());
                    ClientPlayNetworking.send(new AbilityC2SPacket());
                });
            }
            if (!KinsWathe.NOELLESROLES_LOADED) {
                insanityTime++;
                if (insanityTime >= 20 * 6) {
                    insanityTime = 0;
                    List<UUID> keys = new ArrayList<UUID>(WatheClient.PLAYER_ENTRIES_CACHE.keySet());
                    List<UUID> originalkeys = new ArrayList<UUID>(WatheClient.PLAYER_ENTRIES_CACHE.keySet());
                    Collections.shuffle(keys);
                    int i = 0;
                    for (UUID o : originalkeys) {
                        SHUFFLED_PLAYER_ENTRIES_CACHE.put(o, keys.get(i));
                        i++;
                    }
                }
            }
        });

        /// 初始化物品冷却提示
        ItemCooldownComponent.initItemCooldown();

        /// 添加额外材质
        ModelPredicateProviderRegistry.register(
                KinsWatheItems.POISON_INJECTOR,
                Identifier.of(KinsWathe.MOD_ID, "cooling"),
                (stack, world, entity, seed) -> {
                    if (entity instanceof PlayerEntity player) return player.getItemCooldownManager().isCoolingDown(KinsWatheItems.POISON_INJECTOR) ? 1.0F : 0.0F;
                    return 0.0F;
                }
        );

        /// 添加物品描述和冷却提示
        ItemTooltipCallback.EVENT.register(((itemStack, tooltipContext, tooltipType, list) -> {
            //添加NoellreRoles物品冷却提示
            if (KinsWathe.NOELLESROLES_LOADED) {
                CooldownText(ModItems.FAKE_REVOLVER, list, itemStack);
            }
            //添加物品提示
            CooldownText(KinsWatheItems.BLOWGUN, list, itemStack);
            CooldownText(KinsWatheItems.MEDICAL_KIT, list, itemStack);
            CooldownText(KinsWatheItems.PAN, list, itemStack);
            CooldownText(KinsWatheItems.POISON_INJECTOR, list, itemStack);
            CooldownText(KinsWatheItems.SULFURIC_ACID_BARREL, list, itemStack);
            ToolTip(KinsWatheItems.BLOWGUN, itemStack, list);
            ToolTip(KinsWatheItems.MEDICAL_KIT, itemStack, list);
            ToolTip(KinsWatheItems.PAN, itemStack, list);
            ToolTip(KinsWatheItems.POISON_INJECTOR, itemStack, list);
            ToolTip(KinsWatheItems.SULFURIC_ACID_BARREL, itemStack, list);
        }));
    }

    /// 添加物品描述
    public void ToolTip(Item item, ItemStack itemStack, List<Text> list) {
        if (itemStack.isOf(item)) {
            list.addAll(TextUtils.getTooltipForItem(item, Style.EMPTY.withColor(WatheItemTooltips.REGULAR_TOOLTIP_COLOR)));
        }
    }

    /// 添加物品冷却提示
    private static void CooldownText(Item item, List<Text> list, @NotNull ItemStack itemStack) {
        if (itemStack.isOf(item)) {
            ItemCooldownManager itemCooldown = MinecraftClient.getInstance().player.getItemCooldownManager();
            if (itemCooldown.isCoolingDown(item)) {
                float progress = itemCooldown.getCooldownProgress(item, 0);
                int totalTicks = ItemCooldownComponent.getItemCooldownTicks(item);
                if (totalTicks > 0) {
                    int remainingTicks = (int) (totalTicks * progress);
                    int totalSeconds = (remainingTicks + 19) / 20;
                    int minutes = totalSeconds / 60;
                    int seconds = totalSeconds % 60;
                    String countdown = (minutes > 0 ? minutes + "m" : "") + (seconds > 0 ? seconds + "s" : "");
                    list.add(Text.translatable("tip.cooldown", countdown).withColor(WatheItemTooltips.COOLDOWN_COLOR));
                }
            }
        }
    }
}