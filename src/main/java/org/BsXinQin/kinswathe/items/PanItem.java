package org.BsXinQin.kinswathe.items;

import dev.doctor4t.wathe.game.GameConstants;
import dev.doctor4t.wathe.game.GameFunctions;
import dev.doctor4t.wathe.index.WatheSounds;
import net.minecraft.entity.Entity;
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
import org.BsXinQin.kinswathe.roles.cook.CookComponent;
import org.jetbrains.annotations.NotNull;

public class PanItem extends Item {

    public PanItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(@NotNull World world, @NotNull PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        player.setCurrentHand(hand);
        player.playSound(WatheSounds.ITEM_KNIFE_PREPARE, 1.0f, 0.1f);
        return TypedActionResult.consume(itemStack);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, @NotNull World world, @NotNull LivingEntity player, int remainingUseTicks) {
        if (player.isSpectator()) return;
        if (remainingUseTicks >= this.getMaxUseTime(stack, player) - 10 || !(player instanceof PlayerEntity attacker)) return;
        HitResult collision = getTarget(attacker);
        if (collision instanceof EntityHitResult entityHitResult) {
            Entity target = entityHitResult.getEntity();
            if (!world.isClient && target instanceof PlayerEntity playerTarget) {
                CookComponent targetPan = CookComponent.KEY.get(playerTarget);
                if (targetPan != null) {
                    targetPan.setPanStun(100);
                }
                playerTarget.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 5, false, true, false));
                playerTarget.playSound(SoundEvents.BLOCK_ANVIL_LAND, 0.8f, 0.8f);
                if (!attacker.isCreative()) {
                    attacker.getItemCooldownManager().set(this, GameConstants.ITEM_COOLDOWNS.get(this));
                }
            }
        }
    }

    public static HitResult getTarget(@NotNull PlayerEntity player) {return ProjectileUtil.getCollision(player, entity -> entity instanceof PlayerEntity target && GameFunctions.isPlayerAliveAndSurvival(target), 3f);}
    @Override public UseAction getUseAction(ItemStack stack) {return UseAction.SPEAR;}
    @Override public int getMaxUseTime(ItemStack stack, @NotNull LivingEntity player) {return 100;}
}