package org.BsXinQin.kinswathe.client.mixin.modifiers.violator;

import dev.doctor4t.wathe.client.WatheClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import org.BsXinQin.kinswathe.KinsWatheRoles;
import org.agmas.harpymodloader.component.WorldModifierComponent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = KeyBinding.class, priority = 5000)
public abstract class ViolatorKeyBindingMixin {

    @Unique
    private void violatorUnLockKeys(@NotNull CallbackInfoReturnable<Boolean> cir) {
        if (MinecraftClient.getInstance().player == null) return;
        WorldModifierComponent modifier = WorldModifierComponent.KEY.get(MinecraftClient.getInstance().player.getWorld());
        if (modifier.isModifier(MinecraftClient.getInstance().player, KinsWatheRoles.VIOLATOR) && WatheClient.isPlayerAliveAndInSurvival()) {
            KeyBinding key = (KeyBinding) (Object) this;
            boolean jumpKey = key.equals(MinecraftClient.getInstance().options.jumpKey);
            if (jumpKey) {
                cir.setReturnValue(keyPressed());
            }
        }
    }

    @Inject(method = "wasPressed", at = @At("RETURN"), cancellable = true)
    private void wasPressed(@NotNull CallbackInfoReturnable<Boolean> cir) {violatorUnLockKeys(cir);}

    @Inject(method = "isPressed", at = @At("RETURN"), cancellable = true)
    private void isPressed(@NotNull CallbackInfoReturnable<Boolean> cir) {violatorUnLockKeys(cir);}

    @Accessor("pressed")
    abstract boolean keyPressed();
}