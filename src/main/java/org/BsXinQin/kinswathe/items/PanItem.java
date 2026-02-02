package org.BsXinQin.kinswathe.items;

import dev.doctor4t.wathe.game.GameFunctions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class PanItem extends Item {

    public PanItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, @NotNull PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        // 添加准备音效
        user.playSound(SoundEvents.BLOCK_METAL_PLACE, 1.0f, 1.0f);
        return TypedActionResult.consume(itemStack);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user.isSpectator()) {
            return;
        }

        if (remainingUseTicks >= this.getMaxUseTime(stack, user) - 10 || !(user instanceof PlayerEntity attacker))
            return;

        HitResult collision = getPanTarget(attacker);
        if (collision instanceof EntityHitResult entityHitResult) {
            Entity target = entityHitResult.getEntity();

            // 直接在服务器端处理攻击逻辑
            if (!world.isClient && target instanceof LivingEntity livingTarget) {
                // 对目标造成伤害
                livingTarget.damage(world.getDamageSources().playerAttack(attacker), 5.0f);

                // 播放打击音效
                world.playSound(null, target.getX(), target.getY(), target.getZ(),
                        SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.PLAYERS, 0.7f, 1.0f);

                // 添加击退效果
                double knockback = 0.4;
                double dx = target.getX() - attacker.getX();
                double dz = target.getZ() - attacker.getZ();
                double distance = Math.sqrt(dx * dx + dz * dz);

                if (distance > 0.0) {
                    double strength = knockback * (1.0 - distance / 10.0);
                    target.addVelocity(
                            dx / distance * strength,
                            0.1,
                            dz / distance * strength
                    );
                }
            }
        }
    }

    public static HitResult getPanTarget(PlayerEntity user) {
        return ProjectileUtil.getCollision(user, entity -> entity instanceof PlayerEntity player && GameFunctions.isPlayerAliveAndSurvival(player), 3f);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 100;
    }
}