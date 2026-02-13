package org.BsXinQin.kinswathe.mixin.noellesroles.framing;

import dev.doctor4t.wathe.block.FoodPlatterBlock;
import dev.doctor4t.wathe.block_entity.BeveragePlateBlockEntity;
import dev.doctor4t.wathe.game.GameFunctions;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.agmas.noellesroles.ModItems;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(FoodPlatterBlock.class)
public abstract class DelusionVialApplyMixin {

    @Unique private static final UUID DELUSION_MARKER = UUID.fromString("00000000-0000-0000-dead-c0de00000000");

    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    private void applyDefenseVial(BlockState state, @NotNull World world, @NotNull BlockPos pos, @NotNull PlayerEntity player, BlockHitResult hit, @NotNull CallbackInfoReturnable<ActionResult> cir) {
        if (!FabricLoader.getInstance().isModLoaded("noellesroles")) return;
        if (world.isClient || GameFunctions.isPlayerSpectatingOrCreative(player)) return;
        BlockEntity platter = world.getBlockEntity(pos);
        if (platter instanceof @NotNull BeveragePlateBlockEntity blockEntity) {
            if (player.getStackInHand(Hand.MAIN_HAND).isOf(ModItems.DELUSION_VIAL) && blockEntity.getPoisoner() == null) {
                blockEntity.setPoisoner(DELUSION_MARKER.toString());
                player.getStackInHand(Hand.MAIN_HAND).decrement(1);
                player.playSoundToPlayer(SoundEvents.BLOCK_BREWING_STAND_BREW, SoundCategory.BLOCKS, 0.5F, 1.0F);
                cir.setReturnValue(ActionResult.SUCCESS);
                cir.cancel();
            }
        }
    }
}