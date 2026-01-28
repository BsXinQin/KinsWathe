package org.BsXinQin.kinswathe.mixin.roles.drugmaker;

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
import org.BsXinQin.kinswathe.KinsWatheItems;
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
            if (index == 0) {
                if (balance >= 125 && !this.player.getItemCooldownManager().isCoolingDown(KinsWatheItems.POISON_INJECTOR)) {
                    this.balance -= 125;
                    sync();
                    player.giveItemStack(KinsWatheItems.POISON_INJECTOR.getDefaultStack());
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
                ci.cancel();
            }
            if (index == 1) {
                if (balance >= 175 && !this.player.getItemCooldownManager().isCoolingDown(KinsWatheItems.BLOWGUN)) {
                    this.balance -= 175;
                    sync();
                    player.giveItemStack(KinsWatheItems.BLOWGUN.getDefaultStack());
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
                ci.cancel();
            }
            if (index == 2) {
                if (balance >= 200 && !this.player.getItemCooldownManager().isCoolingDown(WatheItems.KNIFE)) {
                    this.balance -= 200;
                    sync();
                    player.giveItemStack(WatheItems.KNIFE.getDefaultStack());
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
                ci.cancel();
            }
            if (index == 3) {
                if (balance >= 50) {
                    this.balance -= 50;
                    sync();
                    player.giveItemStack(WatheItems.POISON_VIAL.getDefaultStack());
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
                ci.cancel();
            }
            if (index == 4) {
                if (balance >= 25) {
                    this.balance -= 25;
                    sync();
                    player.giveItemStack(WatheItems.SCORPION.getDefaultStack());
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
                ci.cancel();
            }
            if (index == 5) {
                if (balance >= 10) {
                    this.balance -= 10;
                    sync();
                    player.giveItemStack(WatheItems.FIRECRACKER.getDefaultStack());
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
                ci.cancel();
            }
            if (index == 6 && !this.player.getItemCooldownManager().isCoolingDown(WatheItems.LOCKPICK)) {
                if (balance >= 50) {
                    this.balance -= 50;
                    sync();
                    player.giveItemStack(WatheItems.LOCKPICK.getDefaultStack());
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
                ci.cancel();
            }
            if (index == 7 && !this.player.getItemCooldownManager().isCoolingDown(WatheItems.CROWBAR)) {
                if (balance >= 25) {
                    this.balance -= 25;
                    sync();
                    player.giveItemStack(WatheItems.CROWBAR.getDefaultStack());
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
                ci.cancel();
            }
            if (index == 8) {
                if (balance >= 200) {
                    this.balance -= 200;
                    sync();
                    player.giveItemStack(WatheItems.BODY_BAG.getDefaultStack());
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
                ci.cancel();
            }
            if (index == 9) {
                if (balance >= 200 && !this.player.getItemCooldownManager().isCoolingDown(WatheItems.BLACKOUT)) {
                    this.balance -= 200;
                    sync();
                    PlayerShopComponent.useBlackout(this.player);
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
                ci.cancel();
            }
            if (index == 10) {
                if (balance >= 10) {
                    this.balance -= 10;
                    sync();
                    player.giveItemStack(WatheItems.NOTE.getDefaultStack());
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
                ci.cancel();
            }
            ci.cancel();
        }
    }
}