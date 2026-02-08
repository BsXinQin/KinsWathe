package org.BsXinQin.kinswathe.roles.kidnapper;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.game.GameConstants;
import dev.doctor4t.wathe.game.GameFunctions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.BsXinQin.kinswathe.KinsWathe;
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

import java.util.Set;
import java.util.UUID;

public class KidnapperComponent implements AutoSyncedComponent, ServerTickingComponent {

    public static final ComponentKey<KidnapperComponent> KEY = ComponentRegistry.getOrCreate(Identifier.of(KinsWathe.MOD_ID, "kidnapper"), KidnapperComponent.class);
    private final PlayerEntity player;
    public UUID controllerUUID = null;
    public int controlTicks = 0;
    private int lastSecond = 0;

    public KidnapperComponent(@NotNull PlayerEntity player) {this.player = player;}
    public void sync() {KEY.sync(this.player);}

    @Override
    public void serverTick() {
        if (this.controlTicks > 0) {
            if (this.controllerUUID != null) {
                ServerWorld serverWorld = (ServerWorld) this.player.getWorld();
                PlayerEntity controller = serverWorld.getPlayerByUuid(this.controllerUUID);
                if (controller == null || (controller != null && !GameWorldComponent.KEY.get(this.player.getWorld()).isRunning() || !GameFunctions.isPlayerAliveAndSurvival(controller) || !GameFunctions.isPlayerAliveAndSurvival(this.player) || controller.isSneaking())) {
                    releaseControl();
                    return;
                }
            }
            this.controlTicks--;
            int currentSecond = this.controlTicks / 20;
            if (currentSecond != lastSecond && currentSecond >= 0) {
                notifyControllerRemainingTime(currentSecond);
                lastSecond = currentSecond;
            }
            teleportToController();
            if (controlTicks == 0) {
                releaseControl();
            }
            this.sync();
        }
    }

    public void startControl(PlayerEntity controller) {
        this.controllerUUID = controller.getUuid();
        this.controlTicks = GameConstants.getInTicks(0, 15);
        this.lastSecond = this.controlTicks / 20;
        this.sync();
    }

    public void releaseControl() {
        if (this.controllerUUID != null) {
            ServerWorld serverWorld = (ServerWorld) this.player.getWorld();
            PlayerEntity controller = serverWorld.getPlayerByUuid(this.controllerUUID);
            if (controller != null && GameWorldComponent.KEY.get(controller.getWorld()).isRunning() && GameFunctions.isPlayerAliveAndSurvival(controller)) {
                controller.sendMessage(Text.translatable("tip.kinswathe.kidnapper.release").withColor(KinsWathe.KIDNAPPER.color()), true);
                controller.playSoundToPlayer(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1.0f, 1.0f);
            }
        }
        this.controllerUUID = null;
        this.controlTicks = 0;
        this.lastSecond = 0;
        this.sync();
    }

    private void teleportToController() {
        if (this.controllerUUID == null) return;
        ServerWorld serverWorld = (ServerWorld) this.player.getWorld();
        PlayerEntity controller = serverWorld.getPlayerByUuid(this.controllerUUID);
        if (controller != null && this.player instanceof ServerPlayerEntity serverPlayer) {
            serverPlayer.teleport(serverWorld, controller.getX(), controller.getY(), controller.getZ(), Set.of(), controller.getYaw(), controller.getPitch());
        }
    }

    private void notifyControllerRemainingTime(int remainingSeconds) {
        if (this.controllerUUID == null) return;
        ServerWorld serverWorld = (ServerWorld) this.player.getWorld();
        PlayerEntity controller = serverWorld.getPlayerByUuid(this.controllerUUID);
        if (controller != null && GameWorldComponent.KEY.get(controller.getWorld()).isRunning() && GameFunctions.isPlayerAliveAndSurvival(controller) && remainingSeconds > 0) {
            controller.sendMessage(Text.translatable("tip.kinswathe.kidnapper.timeleft", remainingSeconds).withColor(KinsWathe.KIDNAPPER.color()), true);
        }
    }

    public void reset() {
        this.controllerUUID = null;
        this.controlTicks = 0;
        this.lastSecond = 0;
        this.sync();
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag, RegistryWrapper.@NotNull WrapperLookup registryLookup) {
        tag.putInt("controlTicks", this.controlTicks);
        if (this.controllerUUID != null) {
            tag.putUuid("controllerUUID", this.controllerUUID);
        }
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag, RegistryWrapper.@NotNull WrapperLookup registryLookup) {
        this.controlTicks = tag.getInt("controlTicks");
        if (tag.contains("controllerUUID")) {
            this.controllerUUID = tag.getUuid("controllerUUID");
        }
    }
}