package org.BsXinQin.kinswathe.component;

import dev.doctor4t.wathe.cca.PlayerShopComponent;
import dev.doctor4t.wathe.index.WatheItems;
import dev.doctor4t.wathe.index.WatheSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;

public class PlayerPurchaseComponent {

    public static boolean handlePurchase(@NotNull PlayerEntity player, int balance, @NotNull Item item, int price) {
        if (balance >= price && !player.getItemCooldownManager().isCoolingDown(item)) {
            if (item == WatheItems.NOTE) player.giveItemStack((new ItemStack(WatheItems.NOTE, 4)));
            else if (item == WatheItems.BLACKOUT) PlayerShopComponent.useBlackout(player);
            else if (item == WatheItems.PSYCHO_MODE) PlayerShopComponent.usePsychoMode(player);
            else player.giveItemStack(item.getDefaultStack());
            if (player instanceof @NotNull ServerPlayerEntity serverPlayer) {
                serverPlayer.networkHandler.sendPacket(new PlaySoundS2CPacket(Registries.SOUND_EVENT.getEntry(WatheSounds.UI_SHOP_BUY), SoundCategory.PLAYERS, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), 1.0F, 0.9F + player.getRandom().nextFloat() * 0.2F, serverPlayer.getRandom().nextLong()));
            }
            return true;
        } else {
            player.sendMessage(Text.translatable("shop.purchase_failed").withColor(0xAA0000), true);
            if (player instanceof @NotNull ServerPlayerEntity serverPlayer) {
                serverPlayer.networkHandler.sendPacket(new PlaySoundS2CPacket(Registries.SOUND_EVENT.getEntry(WatheSounds.UI_SHOP_BUY_FAIL), SoundCategory.PLAYERS, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), 1.0F, 0.9F + player.getRandom().nextFloat() * 0.2F, serverPlayer.getRandom().nextLong()));
            }
            return false;
        }
    }
}