package org.BsXinQin.kinswathe.mixin.noellesroles.roles.bartender;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import dev.doctor4t.wathe.index.WatheSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import org.BsXinQin.kinswathe.KinsWathe;
import org.agmas.noellesroles.ModItems;
import org.agmas.noellesroles.Noellesroles;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//更高的注入优先级
@Mixin(value = PlayerShopComponent.class , priority = 999)
public abstract class BartenderPlayerShopComponentMixin {

    @Shadow public int balance;
    @Shadow @Final private PlayerEntity player;
    @Shadow public abstract void sync();

    @Inject(method = "tryBuy", at = @At("HEAD"), cancellable = true)
    void BartenderBuy(int index, CallbackInfo ci) {
        if (!KinsWathe.NOELLESROLES_LOADED || !KinsWathe.EnableNoellesRolesModify()) return;
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(player.getWorld());
        if (gameWorld.isRole(player, Noellesroles.BARTENDER)) {
            if (index == 0) {
                if (balance >= KinsWathe.BartenderPriceModify()) {
                    this.balance -= KinsWathe.BartenderPriceModify();
                    sync();
                    player.giveItemStack(ModItems.DEFENSE_VIAL.getDefaultStack());
                    if (player instanceof ServerPlayerEntity serverPlayer) serverPlayer.networkHandler.sendPacket(new PlaySoundS2CPacket(Registries.SOUND_EVENT.getEntry(WatheSounds.UI_SHOP_BUY), SoundCategory.PLAYERS, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), 1.0F, 0.9F + player.getRandom().nextFloat() * 0.2F, player.getRandom().nextLong()));
                } else {
                    this.player.sendMessage(Text.translatable("shop.purchase_failed").withColor(0xAA0000), true);
                    if (player instanceof ServerPlayerEntity serverPlayer) serverPlayer.networkHandler.sendPacket(new PlaySoundS2CPacket(Registries.SOUND_EVENT.getEntry(WatheSounds.UI_SHOP_BUY_FAIL), SoundCategory.PLAYERS, serverPlayer.getX(), serverPlayer.getY(), serverPlayer.getZ(), 1.0F, 0.9F + player.getRandom().nextFloat() * 0.2F, player.getRandom().nextLong()));
                }
            }
            ci.cancel();
        }
    }
}