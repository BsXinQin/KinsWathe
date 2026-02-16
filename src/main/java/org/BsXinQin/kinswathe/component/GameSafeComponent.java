package org.BsXinQin.kinswathe.component;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.BsXinQin.kinswathe.KinsWathe;
import org.BsXinQin.kinswathe.KinsWatheConfig;
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class GameSafeComponent implements AutoSyncedComponent, ServerTickingComponent {

    public static final ComponentKey<GameSafeComponent> KEY = ComponentRegistry.getOrCreate(Identifier.of(KinsWathe.MOD_ID, "safe"), GameSafeComponent.class);

    @NotNull private final PlayerEntity player;
    public boolean isGameSafe = false;
    public int safeTicks = 0;

    public GameSafeComponent(@NotNull PlayerEntity player) {this.player = player;}

    @Override
    public void serverTick() {
        if (this.isGameSafe && this.safeTicks <= KinsWatheConfig.HANDLER.instance().StartingCooldown * 20) {
            this.notInGameReset();
            if (this.safeTicks == KinsWatheConfig.HANDLER.instance().StartingCooldown * 20) {
                this.isGameSafe = false;
            }
            ++ this.safeTicks;
            this.sync();
        }
    }

    public void notInGameReset() {
        if (GameWorldComponent.KEY.get(this.player.getWorld()).getRole(this.player) == null) {
            this.reset();
        }
    }

    public void startGameSafe() {
        this.isGameSafe = true;
        this.sync();
    }

    public void reset() {
        this.isGameSafe = false;
        this.safeTicks = 0;
        this.sync();
    }

    public void sync() {
        KEY.sync(this.player);
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag, RegistryWrapper.@NotNull WrapperLookup registryLookup) {
        tag.putInt("safeTicks", this.safeTicks);
        tag.putBoolean("isGameSafe", this.isGameSafe);
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag, RegistryWrapper.@NotNull WrapperLookup registryLookup) {
        this.safeTicks = tag.contains("safeTicks") ? tag.getInt("safeTicks") : 0;
        this.isGameSafe = tag.contains("isGameSafe") && tag.getBoolean("isGameSafe");
    }
}