package org.BsXinQin.kinswathe.roles.kidnapper;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.game.GameConstants;
import dev.doctor4t.wathe.game.GameFunctions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.BsXinQin.kinswathe.KinsWathe;
import org.BsXinQin.kinswathe.KinsWatheRoles;
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

import java.util.Set;
import java.util.UUID;

public class KidnapperComponent implements AutoSyncedComponent, ServerTickingComponent {

    public static final ComponentKey<KidnapperComponent> KEY = ComponentRegistry.getOrCreate(Identifier.of(KinsWathe.MOD_ID, "kidnapper"), KidnapperComponent.class);

    @NotNull private final PlayerEntity player;
    public UUID controllerUUID = null;
    public int controlTicks = 0;

    public KidnapperComponent(@NotNull PlayerEntity player) {this.player = player;}

    @Override
    public void serverTick() {
        if (this.controlTicks > 0) {
            this.notInGameReset();
            this.connectWithController();
            this.teleportToController();
            this.notifyControllerRemainingTime();
            -- this.controlTicks;
            this.sync();
        }
    }

    public void notInGameReset() {
        if (GameWorldComponent.KEY.get(this.player.getWorld()).getRole(this.player) == null) {
            this.reset();
        }
    }

    public void startControl(@NotNull PlayerEntity controller) {
        this.controllerUUID = controller.getUuid();
        this.controlTicks = GameConstants.getInTicks(0,20);
        this.sync();
    }

    public void connectWithController() {
        if (this.controllerUUID == null) return;
        PlayerEntity controller = this.player.getWorld().getPlayerByUuid(this.controllerUUID);
        if (controller == null) {
            this.reset();
            return;
        }
        if (GameFunctions.isPlayerSpectatingOrCreative(controller) || GameFunctions.isPlayerSpectatingOrCreative(this.player) || controller.isSneaking()) {
            this.releaseControlTip();
            this.reset();
        }
    }

    public void teleportToController() {
        if (this.controllerUUID == null) return;
        PlayerEntity controller = this.player.getWorld().getPlayerByUuid(this.controllerUUID);
        if (controller != null) {
            this.player.teleport((ServerWorld) this.player.getWorld(), controller.getX(), controller.getY(), controller.getZ(), Set.of(), controller.getYaw(), controller.getPitch());
        }
    }

    public void notifyControllerRemainingTime() {
        if (this.controllerUUID == null) return;
        PlayerEntity controller = this.player.getWorld().getPlayerByUuid(this.controllerUUID);
        if (controller != null && this.controlTicks / 20 >= 0) {
            controller.sendMessage(Text.translatable("tip.kinswathe.kidnapper.timeleft", this.controlTicks / 20).withColor(KinsWatheRoles.KIDNAPPER.color()), true);
            if (this.controlTicks == 1) releaseControlTip();
        }
    }

    public void releaseControlTip() {
        if (this.controllerUUID == null) return;
        PlayerEntity controller = this.player.getWorld().getPlayerByUuid(this.controllerUUID);
        if (controller != null) {
            controller.sendMessage(Text.translatable("tip.kinswathe.kidnapper.release").withColor(KinsWatheRoles.KIDNAPPER.color()), true);
            controller.playSoundToPlayer(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }
    }

    public void reset() {
        this.controllerUUID = null;
        this.controlTicks = 0;
        this.sync();
    }

    public void sync() {
        KEY.sync(this.player);
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag, RegistryWrapper.@NotNull WrapperLookup registryLookup) {
        tag.putInt("controlTicks", this.controlTicks);
        if (this.controllerUUID != null) tag.putUuid("controllerUUID", this.controllerUUID);
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag, RegistryWrapper.@NotNull WrapperLookup registryLookup) {
        this.controlTicks = tag.contains("controlTicks") ? tag.getInt("controlTicks") : 0;
        this.controllerUUID = tag.contains("controllerUUID") ? tag.getUuid("controllerUUID") : null;
    }
}