package org.BsXinQin.kinswathe.client.mixin.host;

import dev.doctor4t.wathe.client.gui.RoundTextRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.BsXinQin.kinswathe.component.CustomWinnerComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(RoundTextRenderer.class)
public class CustomWinnerTextMixin {

    @ModifyVariable(method = "renderHud", at = @At(value = "STORE"), name = "winMessage")
    private static MutableText CustomWinnerText(MutableText winMessage) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) return winMessage;
        CustomWinnerComponent customWinner = CustomWinnerComponent.KEY.get(client.world);
        if (customWinner == null || !customWinner.hasCustomWinner()) return winMessage;
        String winningTextId = customWinner.getWinningTextId();
        if (winningTextId == null || winningTextId.isEmpty()) return winMessage;
        winningTextId = cleanWinningTextId(winningTextId);
        return Text.translatable("game.win.kinswathe." + winningTextId);
    }

    @Unique
    private static String cleanWinningTextId(String textId) {
        if (textId.contains(":")) {
            String[] parts = textId.split(":");
            if (parts.length >= 2) {
                return parts[1];
            }
        }
        return textId;
    }
}