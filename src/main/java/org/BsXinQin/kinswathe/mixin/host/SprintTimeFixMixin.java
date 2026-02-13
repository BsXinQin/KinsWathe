package org.BsXinQin.kinswathe.mixin.host;

import dev.doctor4t.wathe.api.Role;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;

@Mixin(Role.class)
public class SprintTimeFixMixin {

    @Inject(method = "<init>", at = @At("TAIL"))
    private void fixSprintTime(Identifier identifier, int color, boolean isInnocent, boolean canUseKiller, @NotNull Role.MoodType moodType, int maxSprintTime, boolean invincibleToPoison, CallbackInfo ci) throws NoSuchFieldException, IllegalAccessException {
        if (maxSprintTime == Integer.MAX_VALUE) {
            Role role = (Role) (Object) this;
            Class<?> roleClass = role.getClass();
            Field maxSprintTimeField = roleClass.getDeclaredField("maxSprintTime");
            maxSprintTimeField.setAccessible(true);
            maxSprintTimeField.set(role, -1);
        }
    }
}