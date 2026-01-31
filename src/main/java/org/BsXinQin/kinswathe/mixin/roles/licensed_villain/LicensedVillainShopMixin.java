package org.BsXinQin.kinswathe.mixin.roles.licensed_villain;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import dev.doctor4t.wathe.index.WatheItems;
import dev.doctor4t.wathe.index.WatheSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import org.BsXinQin.kinswathe.KinsWathe;
import org.BsXinQin.kinswathe.component.ConfigWorldComponent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerShopComponent.class)
public abstract class LicensedVillainShopMixin {

    @Shadow public int balance;
    @Shadow @Final private PlayerEntity player;
    @Shadow public abstract void sync();

    @Inject(method = "tryBuy", at = @At("HEAD"), cancellable = true)
    void LicensedVillainShop(int index, CallbackInfo ci) {
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(player.getWorld());
        if (gameWorld.isRole(player, KinsWathe.LICENSED_VILLAIN)) {
            if (index == 0) {
                if (balance >= ConfigWorldComponent.KEY.get(player.getWorld()).LicensedVillainPrice && !this.player.getItemCooldownManager().isCoolingDown(WatheItems.REVOLVER)) {
                    this.balance -= ConfigWorldComponent.KEY.get(player.getWorld()).LicensedVillainPrice;
                    sync();
                    player.giveItemStack(WatheItems.REVOLVER.getDefaultStack());
                    PlayerEntity var6 = this.player;
                    if (var6 instanceof ServerPlayerEntity) {
                        ServerPlayerEntity player = (ServerPlayerEntity) var6;
                        player.networkHandler.sendPacket(new PlaySoundS2CPacket(Registries.SOUND_EVENT.getEntry(WatheSounds.UI_SHOP_BUY), SoundCategory.PLAYERS, player.getX(), player.getY(), player.getZ(), 1.0F, 0.9F + this.player.getRandom().nextFloat() * 0.2F, player.getRandom().nextLong()));
                    }
                } else {
                    this.player.sendMessage(Text.translatable("shop.purchase_failed").withColor(0xAA0000), true);
                    PlayerEntity var4 = this.player;
                    if (var4 instanceof ServerPlayerEntity) {
                        ServerPlayerEntity player = (ServerPlayerEntity) var4;
                        player.networkHandler.sendPacket(new PlaySoundS2CPacket(Registries.SOUND_EVENT.getEntry(WatheSounds.UI_SHOP_BUY_FAIL), SoundCategory.PLAYERS, player.getX(), player.getY(), player.getZ(), 1.0F, 0.9F + this.player.getRandom().nextFloat() * 0.2F, player.getRandom().nextLong()));
                    }
                }
            }
            ci.cancel();
        }
    }
}