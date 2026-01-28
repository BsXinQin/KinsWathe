package org.BsXinQin.kinswathe.client.mixin.noellesroles.roles.bartender;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.client.gui.screen.ingame.LimitedHandledScreen;
import dev.doctor4t.wathe.client.gui.screen.ingame.LimitedInventoryScreen;
import dev.doctor4t.wathe.util.ShopEntry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import org.BsXinQin.kinswathe.KinsWathe;
import org.agmas.harpymodloader.component.WorldModifierComponent;
import org.agmas.noellesroles.ModItems;
import org.agmas.noellesroles.Noellesroles;
import org.agmas.noellesroles.client.ui.guesser.GuesserPlayerWidget;
import org.agmas.noellesroles.client.ui.guesser.GuesserRoleWidget;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Mixin(LimitedInventoryScreen.class)
public abstract class BartenderShopMixin extends LimitedHandledScreen<PlayerScreenHandler> {

    @Shadow @Final public ClientPlayerEntity player;

    public BartenderShopMixin(PlayerScreenHandler handler, PlayerInventory inventory, Text title) {super(handler, inventory, title);}

    @Inject(method = "init", at = @At("HEAD"), cancellable = true)
    void BartenderShop(CallbackInfo ci) {
        if (!KinsWathe.NOELLESROLES_LOADED || !KinsWathe.EnableNoellesRolesModify()) return;
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(player.getWorld());
        if (gameWorld.isRole(player,Noellesroles.BARTENDER)) {
            ci.cancel();
            super.init();

            List<ShopEntry> entries = new ArrayList<>();
            entries.add(new ShopEntry(ModItems.DEFENSE_VIAL.getDefaultStack(), KinsWathe.BartenderPriceModify(), ShopEntry.Type.POISON));
            int apart = 36;
            int x = width / 2 - (entries.size()) * apart / 2 + 9;
            int shouldBeY = (height - 32) / 2;
            int y = shouldBeY - 46;
            for(int i = 0; i < entries.size(); ++i) {
                addDrawableChild(new LimitedInventoryScreen.StoreItemWidget((LimitedInventoryScreen)(Object)this, x + apart * i, y, entries.get(i), i));
            }

            //兼容猜测者
            if (KinsWathe.NOELLESROLES_LOADED) {
                WorldModifierComponent modifier = WorldModifierComponent.KEY.get(player.getWorld());
                GuesserPlayerWidget.selectedPlayer = null;
                if (modifier.isRole(player, Noellesroles.GUESSER)) {
                    GuesserRoleWidget.stopClosing = false;
                    List<UUID> entriesGuesser = new ArrayList<>(MinecraftClient.getInstance().player.networkHandler.getPlayerUuids());
                    if (!gameWorld.isInnocent(player)) {
                        entriesGuesser.clear();
                        for (AbstractClientPlayerEntity worldPlayer : MinecraftClient.getInstance().world.getPlayers()) {
                            if (gameWorld.isInnocent(worldPlayer) && !gameWorld.isRole(player, Noellesroles.MIMIC)) entriesGuesser.add(worldPlayer.getUuid());
                        }
                    }
                    int xGuesser = width / 2 - (entriesGuesser.size()) * apart / 2 + 9;
                    int yGuesser = shouldBeY + 105;
                    for (int i = 0; i < entriesGuesser.size(); ++i) {
                        GuesserPlayerWidget child = new GuesserPlayerWidget(((LimitedInventoryScreen) (Object) this), xGuesser + apart * i, yGuesser, entriesGuesser.get(i), player.networkHandler.getPlayerListEntry(entriesGuesser.get(i)));
                        addDrawableChild(child);
                        child.visible = false;
                    }
                    GuesserRoleWidget child = new GuesserRoleWidget(((LimitedInventoryScreen) (Object) this), textRenderer, (width / 2) - 100, y);
                    addDrawableChild(child);
                    child.setVisible(false);
                }
            }
        }
    }
}