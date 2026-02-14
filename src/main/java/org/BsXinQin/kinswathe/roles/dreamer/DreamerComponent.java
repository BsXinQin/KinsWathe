package org.BsXinQin.kinswathe.roles.dreamer;

import dev.doctor4t.wathe.api.event.AllowPlayerDeath;
import dev.doctor4t.wathe.game.GameFunctions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.BsXinQin.kinswathe.KinsWathe;
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

import java.util.Set;
import java.util.UUID;

public class DreamerComponent implements AutoSyncedComponent, ServerTickingComponent {

    public static final ComponentKey<DreamerComponent> KEY = ComponentRegistry.getOrCreate(Identifier.of(KinsWathe.MOD_ID, "dreamer"), DreamerComponent.class);

    @NotNull private final PlayerEntity player;
    public UUID dreamerUUID = null;
    public int dreamArmor = 0;

    public DreamerComponent(@NotNull PlayerEntity player) {this.player = player;}

    @Override
    public void serverTick() {
        this.preventDeath();
    }

    public void preventDeath() {
        AllowPlayerDeath.EVENT.register(((player, killer, identifier) -> {
            DreamerComponent playerArmor = DreamerComponent.KEY.get(player);
            if (playerArmor.dreamArmor > 0) {
                player.getWorld().playSound(null, player.getBlockPos(), SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0F, 1.0F);
                playerArmor.teleportToDreamer();
                return false;
            }
            return true;
        }));
    }

    public void imprintDreamer(@NotNull PlayerEntity dreamer) {
        this.dreamerUUID = dreamer.getUuid();
        this.dreamArmor = 1;
        this.sync();
    }

    private void teleportToDreamer() {
        if (this.dreamerUUID == null) return;
        PlayerEntity dreamer = this.player.getWorld().getPlayerByUuid(this.dreamerUUID);
        if (GameFunctions.isPlayerAliveAndSurvival(dreamer) && GameFunctions.isPlayerAliveAndSurvival(this.player)) {
            this.player.teleport((ServerWorld) this.player.getWorld(), dreamer.getX(), dreamer.getY(), dreamer.getZ(), Set.of(), dreamer.getYaw(), dreamer.getPitch());
            ServerWorld serverWorld = (ServerWorld) dreamer.getWorld();
            serverWorld.spawnParticles(ParticleTypes.PORTAL, dreamer.getX(), dreamer.getY(), dreamer.getZ(), 75, 0.5, 1.5, 0.5, 0.1);
            dreamer.playSoundToPlayer(SoundEvents.ENTITY_ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }
        this.reset();
    }

    public void reset() {
        this.dreamerUUID = null;
        this.dreamArmor = 0;
        this.sync();
    }

    public void sync() {
        KEY.sync(this.player);
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag, RegistryWrapper.@NotNull WrapperLookup registryLookup) {
        tag.putInt("dreamArmor", this.dreamArmor);
        if (this.dreamerUUID != null) tag.putUuid("dreamerUUID", this.dreamerUUID);
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag, RegistryWrapper.@NotNull WrapperLookup registryLookup) {
        this.dreamArmor = tag.contains("dreamArmor") ? tag.getInt("dreamArmor") : 0;
        this.dreamerUUID = tag.contains("dreamerUUID") ? tag.getUuid("dreamerUUID") : null;
    }
}