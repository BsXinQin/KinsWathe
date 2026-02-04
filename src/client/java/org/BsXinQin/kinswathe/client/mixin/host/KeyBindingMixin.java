package org.BsXinQin.kinswathe.client.mixin.host;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.client.WatheClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import org.BsXinQin.kinswathe.component.ConfigWorldComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = KeyBinding.class, priority = 5000)
public abstract class KeyBindingMixin {

    @Unique
    private void UnLockKeys(CallbackInfoReturnable<Boolean> ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        if (WatheClient.isPlayerAliveAndInSurvival()) {
            if (ConfigWorldComponent.KEY.get(client.player.getWorld()).EnableJumpNotInGame) {
                KeyBinding key = (KeyBinding) (Object) this;
                boolean jumpKey = key.equals(client.options.jumpKey);
                if (jumpKey) {
                    ci.setReturnValue(UnLockKeyBinding());
                }
            }
        }
    }

    @Inject(method = "wasPressed", at = @At("RETURN"), cancellable = true)
    private void wasPressed(CallbackInfoReturnable<Boolean> ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        if (WatheClient.isPlayerAliveAndInSurvival()) {
            if (ConfigWorldComponent.KEY.get(client.player.getWorld()).EnableJumpNotInGame) {
                GameWorldComponent gameWorld = GameWorldComponent.KEY.get(client.player.getWorld());
                if (!gameWorld.isRunning() && !ci.getReturnValue()) {
                    UnLockKeys(ci);
                }
            }
        }
    }

    @Inject(method = "isPressed", at = @At("RETURN"), cancellable = true)
    private void isPressed(CallbackInfoReturnable<Boolean> ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        if (WatheClient.isPlayerAliveAndInSurvival()) {
            if (ConfigWorldComponent.KEY.get(client.player.getWorld()).EnableJumpNotInGame) {
                GameWorldComponent gameWorld = GameWorldComponent.KEY.get(client.player.getWorld());
                if (!gameWorld.isRunning() && !ci.getReturnValue()) {
                    UnLockKeys(ci);
                }
            }
        }
    }

    @Accessor("pressed")
    abstract boolean UnLockKeyBinding();
}