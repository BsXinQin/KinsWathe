package org.BsXinQin.kinswathe.client.mixin.host;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.client.WatheClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import org.BsXinQin.kinswathe.KinsWathe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//高一点的优先级，以免后手注入
@Mixin(value = KeyBinding.class, priority = 999)
public abstract class KeyBindingMixin {
    //新建类，方便后续可能的键覆盖，如果有要覆盖的键配置项就启用
    @Unique
    private boolean Enable() {
        return KinsWathe.EnableJumpNotInGame();
    }
    @Unique
    private void UnLockKeys(CallbackInfoReturnable<Boolean> ci) {
        if (!Enable()) return;
        if (WatheClient.isPlayerAliveAndInSurvival()) {
            MinecraftClient client = MinecraftClient.getInstance();
            KeyBinding key = (KeyBinding) (Object) this;
            PlayerEntity player = client.player;
            if (player == null) return;
            GameWorldComponent gameWorld = GameWorldComponent.KEY.get(player.getWorld());
            if (gameWorld.isRunning()) return;
            if (KinsWathe.EnableJumpNotInGame() || key.equals(client.options.jumpKey)) {
                ci.setReturnValue(UnLockKeyBinding());
                ci.cancel();
            }
        }
    }

    @Inject(method = "wasPressed", at = @At("RETURN"), cancellable = true)
    private void wasPressed(CallbackInfoReturnable<Boolean> ci) {
        UnLockKeys(ci);
    }

    @Inject(method = "isPressed", at = @At("RETURN"), cancellable = true)
    private void isPressed(CallbackInfoReturnable<Boolean> ci) {
        UnLockKeys(ci);
    }

    @Accessor("pressed")
    abstract boolean UnLockKeyBinding();
}