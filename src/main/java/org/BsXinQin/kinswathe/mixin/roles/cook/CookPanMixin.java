package org.BsXinQin.kinswathe.mixin.roles.cook;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.doctor4t.wathe.game.GameFunctions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.BsXinQin.kinswathe.roles.cook.CookComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = PlayerEntity.class, priority = 5000)
public abstract class CookPanMixin extends LivingEntity {

    protected CookPanMixin(EntityType<? extends LivingEntity> entityType, World world) {super(entityType, world);}

    @ModifyReturnValue(method = "getMovementSpeed", at = @At("RETURN"))
    public float PanStun(float original) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        CookComponent playerPanStun = CookComponent.KEY.get(player);
        if (GameFunctions.isPlayerAliveAndSurvival(player) && playerPanStun.panStunTicks > 0) {
            return 0.0f;
        }
        return original;
    }
}