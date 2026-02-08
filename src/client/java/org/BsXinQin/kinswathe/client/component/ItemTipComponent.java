package org.BsXinQin.kinswathe.client.component;

import dev.doctor4t.ratatouille.util.TextUtils;
import dev.doctor4t.wathe.client.util.WatheItemTooltips;
import dev.doctor4t.wathe.game.GameConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemTipComponent {

    private static final Map<Item, Integer> presetCooldowns = new HashMap<>();
    public static int getItemCooldownTicks(Item item) {return presetCooldowns.getOrDefault(item, 0);}

    public static void initItemCooldown() {
        if (GameConstants.ITEM_COOLDOWNS == null) return;
        presetCooldowns.putAll(GameConstants.ITEM_COOLDOWNS);
    }

    public static void addItemtip(Item item, ItemStack itemStack, List<Text> list) {
        if (itemStack.isOf(item)) {
            addCooldowntip(item, itemStack, list);
            addTooltip(item, itemStack, list);
        }
    }

    public static void addTooltip(Item item, ItemStack itemStack, List<Text> list) {
        if (itemStack.isOf(item)) {
            list.addAll(TextUtils.getTooltipForItem(item, Style.EMPTY.withColor(WatheItemTooltips.REGULAR_TOOLTIP_COLOR)));
        }
    }

    public static void addCooldowntip(Item item, ItemStack itemStack, List<Text> list) {
        if (itemStack.isOf(item)) {
            if (MinecraftClient.getInstance().player == null) return;
            initItemCooldown();
            ItemCooldownManager itemCooldown = MinecraftClient.getInstance().player.getItemCooldownManager();
            if (itemCooldown == null) return;
            if (itemCooldown.isCoolingDown(item)) {
                float progress = itemCooldown.getCooldownProgress(item, 0);
                int totalTicks = ItemTipComponent.getItemCooldownTicks(item);
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