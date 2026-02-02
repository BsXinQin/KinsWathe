package org.BsXinQin.kinswathe.client.mixin.host;

import dev.doctor4t.wathe.client.gui.RoleAnnouncementTexts;
import dev.doctor4t.wathe.game.GameFunctions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.BsXinQin.kinswathe.component.CustomWinnerComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = RoleAnnouncementTexts.RoleAnnouncementText.class, priority = 500)
public class CustomWinnerAnnouncementMixin {

    @Inject(method = "getEndText", at = @At("HEAD"), cancellable = true)
    private void CustomWinnerAnnouncement(GameFunctions.WinStatus status, Text winner, CallbackInfoReturnable<Text> ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) return;
        var customWinner = CustomWinnerComponent.KEY.get(client.world);
        if (customWinner == null || !customWinner.hasCustomWinner()) return;
        String winningTextId = customWinner.getWinningTextId();
        int color = customWinner.getColor();
        if (winningTextId == null || winningTextId.isEmpty()) return;
        winningTextId = cleanWinningTextId(winningTextId);
        MutableText customText = Text.translatable("announcement.win.kinswathe." + winningTextId);
        if (color != 0x000000) customText = customText.withColor(color);
        ci.setReturnValue(customText);
        ci.cancel();
    }

    @Unique
    private String cleanWinningTextId(String textId) {
        if (textId.contains(":")) {
            String[] parts = textId.split(":");
            if (parts.length >= 2) {
                return parts[1];
            }
        }
        return textId;
    }
}