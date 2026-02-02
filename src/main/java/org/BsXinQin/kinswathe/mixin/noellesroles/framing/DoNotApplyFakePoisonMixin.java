package org.BsXinQin.kinswathe.mixin.noellesroles.framing;

import dev.doctor4t.wathe.cca.PlayerPoisonComponent;
import dev.doctor4t.wathe.game.GameConstants;
import dev.doctor4t.wathe.game.GameFunctions;
import net.minecraft.entity.player.PlayerEntity;
import org.BsXinQin.kinswathe.KinsWathe;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(PlayerPoisonComponent.class)
public abstract class DoNotApplyFakePoisonMixin {

    @Unique private static final UUID DELUSION_MARKER = UUID.fromString("00000000-0000-0000-dead-c0de00000000");
    @Shadow @Final private PlayerEntity player;
    @Shadow public int poisonTicks;
    @Shadow public UUID poisoner;
    @Shadow public abstract void reset();
    @Shadow public abstract void sync();
    @Unique private boolean isDelusionPoison = false;

    @Inject(method = "setPoisonTicks", at = @At("HEAD"))
    private void checkDelusionPoison(int ticks, UUID poisoner, CallbackInfo ci) {
        if (!KinsWathe.NOELLESROLES_LOADED) return;
        this.isDelusionPoison = poisoner != null && poisoner.equals(DELUSION_MARKER);
    }

    @Inject(method = "serverTick", at = @At("HEAD"), cancellable = true)
    private void checkPoisonTicks(CallbackInfo ci) {
        if (!KinsWathe.NOELLESROLES_LOADED) return;
        ci.cancel();
        if (this.poisonTicks > -1) {
            this.poisonTicks--;
            if (this.isDelusionPoison) {
                if (this.poisonTicks <= 5) {
                    this.reset();
                } return;
            }
            if (this.poisonTicks == 0) {
                this.poisonTicks = -1;
                GameFunctions.killPlayer(this.player, true,
                this.poisoner == null ? null : this.player.getWorld().getPlayerByUuid(this.poisoner), GameConstants.DeathReasons.POISON);
                this.poisoner = null;
                this.sync();
            }
        }
    }

    @Inject(method = "reset", at = @At("HEAD"))
    private void clearDelusionFlag(CallbackInfo ci) {
        if (!KinsWathe.NOELLESROLES_LOADED) return;
        this.isDelusionPoison = false;
    }
}