package org.BsXinQin.kinswathe.mixin.roles.hunter;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import dev.doctor4t.wathe.cca.GameWorldComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import org.BsXinQin.kinswathe.KinsWatheRoles;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = PlayerEntity.class, priority = 2500)
public abstract class HunterSprintMixin extends LivingEntity {

    protected HunterSprintMixin(@NotNull EntityType<? extends @NotNull LivingEntity> entityType, @NotNull World world) {super(entityType, world);}

    @ModifyReturnValue(method = "getMovementSpeed", at = @At("RETURN"))
    private float setHunterSprint(float original) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(player.getWorld());
        if (gameWorld.isRole(player, KinsWatheRoles.HUNTER) && player.isUsingItem()) {
            ItemStack stack = player.getActiveItem();
            Item item = stack.getItem();
            if (item.getUseAction(stack) == UseAction.SPEAR && player.isSprinting()) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 1, 0, false, false, false));
                return 0.15f;
            }
        }
        return original;
    }
}