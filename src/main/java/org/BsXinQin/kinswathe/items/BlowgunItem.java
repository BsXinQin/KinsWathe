package org.BsXinQin.kinswathe.items;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerPoisonComponent;
import dev.doctor4t.wathe.game.GameFunctions;
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
import org.BsXinQin.kinswathe.KinsWatheItems;
import org.BsXinQin.kinswathe.KinsWatheRoles;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Random;

public class BlowgunItem extends Item {

    public BlowgunItem(@NotNull Settings settings) {super(settings);}
    private static final Random random = new Random();

    @Override
    public @NotNull TypedActionResult<@NotNull ItemStack> use(@NotNull World world, @NotNull PlayerEntity player, @NotNull Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (player.getItemCooldownManager().isCoolingDown(this)) return TypedActionResult.fail(stack);
        KinsWatheItems.setItemAfterUsing(player, this, null);
        player.playSound(SoundEvents.ENTITY_PUFFER_FISH_BLOW_OUT, 0.5f, 1.5f);
        HitResult hitResult = ProjectileUtil.getCollision(player, entity -> entity instanceof @NotNull PlayerEntity target && GameFunctions.isPlayerAliveAndSurvival(target), 15.0f);
        PlayerEntity targetPlayer = (hitResult instanceof @NotNull EntityHitResult entityHitResult) ? (PlayerEntity) entityHitResult.getEntity() : null;
        if (!world.isClient && targetPlayer != null) {
            GameWorldComponent gameWorld = GameWorldComponent.KEY.get(world);
            PlayerPoisonComponent targetPoison = PlayerPoisonComponent.KEY.get(targetPlayer);
            if (gameWorld.isRole(targetPlayer, KinsWatheRoles.ROBOT)) {
                player.sendMessage(Text.translatable("tip.kinswathe.drugmaker.poison_failed").withColor(Color.RED.getRGB()), true);
                return TypedActionResult.success(stack, false);
            }
            if (targetPoison.poisonTicks > 0) {
                int reduction = random.nextInt(200) + 100;
                int poisonTicks = Math.max(0, targetPoison.poisonTicks - reduction);
                targetPoison.setPoisonTicks(poisonTicks, player.getUuid());
            } else {
                int poisonTicks = PlayerPoisonComponent.clampTime.getLeft() + random.nextInt(PlayerPoisonComponent.clampTime.getRight() - PlayerPoisonComponent.clampTime.getLeft());
                targetPoison.setPoisonTicks(poisonTicks, player.getUuid());
            }
        }
        return TypedActionResult.success(stack, false);
    }
}