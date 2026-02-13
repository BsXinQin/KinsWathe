package org.BsXinQin.kinswathe.items;

import dev.doctor4t.wathe.game.GameFunctions;
import dev.doctor4t.wathe.index.WatheSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.BsXinQin.kinswathe.KinsWatheItems;
import org.BsXinQin.kinswathe.roles.cook.CookComponent;
import org.jetbrains.annotations.NotNull;

public class PanItem extends Item {

    public PanItem(@NotNull Settings settings) {super(settings);}

    @Override
    public @NotNull TypedActionResult<@NotNull ItemStack> use(@NotNull World world, @NotNull PlayerEntity player, @NotNull Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (player.getItemCooldownManager().isCoolingDown(this)) return TypedActionResult.fail(stack);
        player.setCurrentHand(hand);
        player.playSound(WatheSounds.ITEM_KNIFE_PREPARE, 1.0f, 0.1f);
        return TypedActionResult.consume(stack);
    }

    @Override
    public void onStoppedUsing(@NotNull ItemStack stack, @NotNull World world, @NotNull LivingEntity livingEntity, int remainingUseTicks) {
        if (!(livingEntity instanceof PlayerEntity player) || player.isSpectator() || remainingUseTicks >= this.getMaxUseTime(stack, player) - 10) return;
        HitResult hitResult = ProjectileUtil.getCollision(player, entity -> entity instanceof @NotNull PlayerEntity target && GameFunctions.isPlayerAliveAndSurvival(target), 3.0f);
        PlayerEntity targetPlayer = (hitResult instanceof @NotNull EntityHitResult entityHitResult) ? (PlayerEntity) entityHitResult.getEntity() : null;
        if (!world.isClient && targetPlayer != null) {
            KinsWatheItems.setItemAfterUsing(player, this, null);
            CookComponent targetPan = CookComponent.KEY.get(targetPlayer);
            targetPan.setPanStun(100);
            targetPlayer.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 5, false, true, false));
            targetPlayer.playSound(SoundEvents.BLOCK_ANVIL_LAND, 0.8f, 0.8f);
        }
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity livingEntity) {
        return 100;
    }
}