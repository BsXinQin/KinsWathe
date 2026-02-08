package org.BsXinQin.kinswathe.items;

import dev.doctor4t.wathe.api.WatheRoles;
import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerPoisonComponent;
import dev.doctor4t.wathe.game.GameConstants;
import dev.doctor4t.wathe.game.GameFunctions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.BsXinQin.kinswathe.KinsWathe;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class BlowgunItem extends Item {

    public BlowgunItem(Settings settings) {super(settings);}
    private static final Random random = new Random();

    @Override
    public TypedActionResult<ItemStack> use(@NotNull World world, @NotNull PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (player.getItemCooldownManager().isCoolingDown(this)) return TypedActionResult.fail(stack);
        player.playSound(SoundEvents.ENTITY_PUFFER_FISH_BLOW_OUT, 0.5f, 1.5f);
        HitResult targetHitResult = ProjectileUtil.getCollision(player, entity -> entity instanceof PlayerEntity target && target != player && GameFunctions.isPlayerAliveAndSurvival(target), 15f);
        PlayerEntity targetPlayer = null;
        if (targetHitResult instanceof EntityHitResult entityHitResult) {
            Entity entity = entityHitResult.getEntity();
            if (entity instanceof PlayerEntity) {
                targetPlayer = (PlayerEntity) entity;
            }
        }
        if (targetPlayer == null) {
            if (!player.isCreative()) {
                player.getItemCooldownManager().set(this, GameConstants.ITEM_COOLDOWNS.get(this));
            }
            return TypedActionResult.success(stack, false);
        }
        if (player.distanceTo(targetPlayer) > 15f) {
            if (!player.isCreative()) {
                player.getItemCooldownManager().set(this, GameConstants.ITEM_COOLDOWNS.get(this));
            }
            return TypedActionResult.success(stack, false);
        }
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(world);
        PlayerPoisonComponent targetPoison = PlayerPoisonComponent.KEY.get(targetPlayer);
        if (KinsWathe.ROBOT != null && gameWorld.isRole(targetPlayer, KinsWathe.ROBOT)) {
            player.sendMessage(Text.translatable("tip.kinswathe.drugmaker.posion_failed").withColor(WatheRoles.KILLER.color()), true);
            if (!player.isCreative()) {
                player.getItemCooldownManager().set(this, GameConstants.ITEM_COOLDOWNS.get(this));
            }
            return TypedActionResult.success(stack, false);
        }
        if (targetPoison.poisonTicks > 0) {
            int reduction = random.nextInt(200) + 100;
            int newTicks = Math.max(0, targetPoison.poisonTicks - reduction);
            targetPoison.setPoisonTicks(newTicks, player.getUuid());
        } else {
            int poisonTicks = PlayerPoisonComponent.clampTime.getLeft() + random.nextInt(PlayerPoisonComponent.clampTime.getRight() - PlayerPoisonComponent.clampTime.getLeft());
            targetPoison.setPoisonTicks(poisonTicks, player.getUuid());
        }
        if (!player.isCreative()) {
            player.getItemCooldownManager().set(this, GameConstants.ITEM_COOLDOWNS.get(this));
        }
        return TypedActionResult.success(stack, false);
    }
}