package org.BsXinQin.kinswathe.items;

import dev.doctor4t.wathe.game.GameConstants;
import dev.doctor4t.wathe.game.GameFunctions;
import dev.doctor4t.wathe.index.WatheSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.BsXinQin.kinswathe.KinsWatheItems;
import org.BsXinQin.kinswathe.roles.hunter.HunterComponent;
import org.jetbrains.annotations.NotNull;

public class HuntingKnifeItem extends Item {

    public HuntingKnifeItem(@NotNull Settings settings) {super(settings);}

    @Override
    public @NotNull TypedActionResult<@NotNull ItemStack> use(@NotNull World world, @NotNull PlayerEntity player, @NotNull Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (player.getItemCooldownManager().isCoolingDown(this)) return TypedActionResult.fail(stack);
        HunterComponent playerHunter = HunterComponent.KEY.get(player);
        playerHunter.useHuntingKnife();
        player.setCurrentHand(hand);
        player.playSound(WatheSounds.ITEM_KNIFE_PREPARE, 1.0f, 1.0f);
        return TypedActionResult.consume(stack);
    }

    @Override
    public void onStoppedUsing(@NotNull ItemStack stack, @NotNull World world, @NotNull LivingEntity livingEntity, int remainingUseTicks) {
        if (!(livingEntity instanceof PlayerEntity player) || player.isSpectator() || remainingUseTicks >= this.getMaxUseTime(stack, player) - 10) return;
        HitResult hitResult = ProjectileUtil.getCollision(player, entity -> entity instanceof @NotNull PlayerEntity target && GameFunctions.isPlayerAliveAndSurvival(target), 3.0f);
        PlayerEntity targetPlayer = (hitResult instanceof @NotNull EntityHitResult entityHitResult) ? (PlayerEntity) entityHitResult.getEntity() : null;
        setTemporaryCooldown(world, player);
        if (!world.isClient && targetPlayer != null && remainingUseTicks > 5) {
            GameConstants.ITEM_COOLDOWNS.put(this, GameConstants.getInTicks(0,45));
            KinsWatheItems.setItemAfterUsing(player, this, null);
            GameFunctions.killPlayer(targetPlayer, true, player, GameConstants.DeathReasons.KNIFE);
            targetPlayer.playSound(WatheSounds.ITEM_KNIFE_STAB, 1.0f, 1.0f);
            player.swingHand(Hand.MAIN_HAND);
        }
    }

    @Override
    public void usageTick(@NotNull World world, @NotNull LivingEntity entity, ItemStack stack, int remainingUseTicks) {
        if (remainingUseTicks <= 5 && entity instanceof PlayerEntity player) {
            player.stopUsingItem();
        }
    }

    @Override
    public UseAction getUseAction(@NotNull ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity livingEntity) {
        return 100;
    }

    private void setTemporaryCooldown(@NotNull World world, @NotNull PlayerEntity player) {
        if (world.isClient) return;
        HunterComponent playerHunter = HunterComponent.KEY.get(player);
        GameConstants.ITEM_COOLDOWNS.put(this, GameConstants.getInTicks(0, playerHunter.knifeTicks * 4 / 20));
        playerHunter.reset();
        if (GameFunctions.isPlayerAliveAndSurvival(player)) {
            player.getItemCooldownManager().set(this, GameConstants.ITEM_COOLDOWNS.get(this));
        }
    }
}