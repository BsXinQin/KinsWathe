package org.BsXinQin.kinswathe.client.mixin.modifiers.violator;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.client.WatheClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import org.BsXinQin.kinswathe.KinsWathe;
import org.agmas.harpymodloader.component.WorldModifierComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = KeyBinding.class, priority = 5000)
public abstract class ViolatorKeyBindingMixin {

    @Unique
    private void ViolatorUnLockKeys(CallbackInfoReturnable<Boolean> ci) {
        if (WatheClient.isPlayerAliveAndInSurvival()) {
            MinecraftClient client = MinecraftClient.getInstance();
            WorldModifierComponent modifier = WorldModifierComponent.KEY.get(client.player.getWorld());
            if (modifier.isModifier(client.player, KinsWathe.VIOLATOR)) {
                KeyBinding key = (KeyBinding) (Object) this;
                boolean jumpKey = key.equals(client.options.jumpKey);
                if (jumpKey) {
                    ci.setReturnValue(ViolatorUnLockKeyBinding());
                }
            }
        }
    }

    @Inject(method = "wasPressed", at = @At("RETURN"), cancellable = true)
    private void wasPressed(CallbackInfoReturnable<Boolean> ci) {
        if (WatheClient.isPlayerAliveAndInSurvival()) {
            MinecraftClient client = MinecraftClient.getInstance();
            GameWorldComponent gameWorld = GameWorldComponent.KEY.get(client.player.getWorld());
            WorldModifierComponent modifier = WorldModifierComponent.KEY.get(client.player.getWorld());
            if (modifier.isModifier(client.player, KinsWathe.VIOLATOR)) {
                if (gameWorld.isRunning() && !ci.getReturnValue()) {
                    ViolatorUnLockKeys(ci);
                }
            }
        }
    }

    @Inject(method = "isPressed", at = @At("RETURN"), cancellable = true)
    private void isPressed(CallbackInfoReturnable<Boolean> ci) {
        if (WatheClient.isPlayerAliveAndInSurvival()) {
            MinecraftClient client = MinecraftClient.getInstance();
            GameWorldComponent gameWorld = GameWorldComponent.KEY.get(client.player.getWorld());
            WorldModifierComponent modifier = WorldModifierComponent.KEY.get(client.player.getWorld());
            if (modifier.isModifier(client.player, KinsWathe.VIOLATOR)) {
                if (gameWorld.isRunning() && !ci.getReturnValue()) {
                    ViolatorUnLockKeys(ci);
                }
            }
        }
    }

    @Accessor("pressed")
    abstract boolean ViolatorUnLockKeyBinding();
}