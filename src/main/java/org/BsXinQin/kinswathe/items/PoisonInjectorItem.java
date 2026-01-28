package org.BsXinQin.kinswathe.items;

import dev.doctor4t.wathe.api.WatheRoles;
import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerPoisonComponent;
import dev.doctor4t.wathe.game.GameConstants;
import dev.doctor4t.wathe.game.GameFunctions;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.BsXinQin.kinswathe.KinsWathe;

import java.util.Random;

public class PoisonInjectorItem extends Item {

    public PoisonInjectorItem(Settings settings) {super(settings);}
    private static final Random random = new Random();

    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity player, LivingEntity entity, Hand hand) {
        if (player.getItemCooldownManager().isCoolingDown(this)) return ActionResult.FAIL;
        if (!(entity instanceof PlayerEntity targetPlayer)) return ActionResult.PASS;
        PlayerPoisonComponent targetPoison = PlayerPoisonComponent.KEY.get(targetPlayer);
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(player.getWorld());
        if (gameWorld.isRole(targetPlayer, KinsWathe.ROBOT)) {
            player.sendMessage(Text.translatable("tip.kinswathe.drugmaker.posion_failed").withColor(WatheRoles.KILLER.color()), true);
            player.playSoundToPlayer(SoundEvents.ENTITY_VILLAGER_AMBIENT, SoundCategory.PLAYERS, 1.0f, 1.0f);
            player.getItemCooldownManager().set(this, GameConstants.ITEM_COOLDOWNS.get(this));
            return ActionResult.SUCCESS;
        }
        if (targetPoison.poisonTicks > 0) {
            GameFunctions.killPlayer(targetPlayer, true, player, GameConstants.DeathReasons.POISON);
            player.playSoundToPlayer(SoundEvents.ENTITY_SPIDER_STEP, SoundCategory.PLAYERS, 1.0f, 1.0f);
            if (!player.isCreative()) {
                player.getItemCooldownManager().set(this, GameConstants.ITEM_COOLDOWNS.get(this));
                return ActionResult.SUCCESS;
            }
        } else {
            int poisonTicks = PlayerPoisonComponent.clampTime.getLeft() + random.nextInt(PlayerPoisonComponent.clampTime.getRight() - PlayerPoisonComponent.clampTime.getLeft());
            targetPoison.setPoisonTicks(poisonTicks, player.getUuid());
            player.playSoundToPlayer(SoundEvents.ENTITY_SPIDER_DEATH, SoundCategory.PLAYERS, 1.0f, 1.0f);
            if (!player.isCreative()) {
                player.getItemCooldownManager().set(this, GameConstants.ITEM_COOLDOWNS.get(this));
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }
}