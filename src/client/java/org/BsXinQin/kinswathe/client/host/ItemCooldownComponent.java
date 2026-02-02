package org.BsXinQin.kinswathe.client.host;

import dev.doctor4t.wathe.game.GameConstants;
import net.minecraft.item.Item;

import java.util.HashMap;
import java.util.Map;

public class ItemCooldownComponent {

    private static final Map<Item, Integer> presetCooldowns = new HashMap<>();

    public static void initItemCooldown() {
        if (GameConstants.ITEM_COOLDOWNS == null) return;
        presetCooldowns.putAll(GameConstants.ITEM_COOLDOWNS);
    }

    public static int getItemCooldownTicks(Item item) {
        return presetCooldowns.getOrDefault(item, 0);
    }
}