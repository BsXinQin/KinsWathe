package org.BsXinQin.kinswathe.mixin.roles.drugmaker;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import dev.doctor4t.wathe.index.WatheSounds;
import dev.doctor4t.wathe.util.ShopEntry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import org.BsXinQin.kinswathe.KinsWathe;
import org.BsXinQin.kinswathe.KinsWatheConstants;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerShopComponent.class)
public abstract class DrugmakerShopMixin {

    @Shadow public int balance;
    @Shadow @Final private PlayerEntity player;
    @Shadow public abstract void sync();

    @Inject(method = "tryBuy", at = @At("HEAD"), cancellable = true)
    void DrugmakerShop(int index, CallbackInfo ci) {
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(player.getWorld());
        if (gameWorld.isRole(player, KinsWathe.DRUGMAKER)) {
            if (index >= 0 && index < KinsWatheConstants.DrugmakerShop.size()) {
                ShopEntry entries = KinsWatheConstants.DrugmakerShop.get(index);
                if (FabricLoader.getInstance().isDevelopmentEnvironment() && this.balance < entries.price()) {
                    this.balance = entries.price() * 10;
                }
                if (this.balance >= entries.price()  && !this.player.getItemCooldownManager().isCoolingDown(entries.stack().getItem()) && entries.onBuy(this.player)) {
                    this.balance -= entries.price();
                    PlayerEntity playerBuy = this.player;
                    if (playerBuy instanceof ServerPlayerEntity) {
                        ServerPlayerEntity player = (ServerPlayerEntity) playerBuy;
                        player.networkHandler.sendPacket(new PlaySoundS2CPacket(Registries.SOUND_EVENT.getEntry(WatheSounds.UI_SHOP_BUY), SoundCategory.PLAYERS, player.getX(), player.getY(), player.getZ(), 1.0F, 0.9F + this.player.getRandom().nextFloat() * 0.2F, player.getRandom().nextLong()));
                    }
                } else {
                    this.player.sendMessage(Text.translatable("shop.purchase_failed").withColor(0xAA0000), true);
                    PlayerEntity playerBuy = this.player;
                    if (playerBuy instanceof ServerPlayerEntity) {
                        ServerPlayerEntity player = (ServerPlayerEntity) playerBuy;
                        player.networkHandler.sendPacket(new PlaySoundS2CPacket(Registries.SOUND_EVENT.getEntry(WatheSounds.UI_SHOP_BUY_FAIL), SoundCategory.PLAYERS, player.getX(), player.getY(), player.getZ(), 1.0F, 0.9F + this.player.getRandom().nextFloat() * 0.2F, player.getRandom().nextLong()));
                    }
                }
                this.sync();
            }
            ci.cancel();
        }
    }
}