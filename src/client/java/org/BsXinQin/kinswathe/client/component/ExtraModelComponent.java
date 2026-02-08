package org.BsXinQin.kinswathe.client.component;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.BsXinQin.kinswathe.KinsWathe;

public class ExtraModelComponent {

    public static Identifier getCooldownId() {
        return Identifier.of(KinsWathe.MOD_ID, "item_cooldown");
    }

    public static void registerCooldownModel(Item item) {
        ModelPredicateProviderRegistry.register(item, getCooldownId(), (itemStack, world, entity, seed) -> {
                    if (MinecraftClient.getInstance().player != null) {
                        return MinecraftClient.getInstance().player.getItemCooldownManager().isCoolingDown(item) ? 1.0F : 0.0F;
                    }
                    return 0.0F;
                }
        );
    }
}