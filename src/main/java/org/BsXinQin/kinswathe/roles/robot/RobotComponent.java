package org.BsXinQin.kinswathe.roles.robot;

import dev.doctor4t.wathe.api.event.AllowPlayerPunching;
import dev.doctor4t.wathe.cca.GameWorldComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.BsXinQin.kinswathe.KinsWathe;
import org.BsXinQin.kinswathe.KinsWatheRoles;
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class RobotComponent implements AutoSyncedComponent, ServerTickingComponent {

    public static final ComponentKey<RobotComponent> KEY = ComponentRegistry.getOrCreate(Identifier.of(KinsWathe.MOD_ID, "robot"), RobotComponent.class);

    @NotNull private final PlayerEntity player;
    public int robotTicks = 0;

    public RobotComponent(@NotNull PlayerEntity player) {this.player = player;}

    @Override
    public void serverTick() {
        if (this.robotTicks > 0) {
            durationRobotAbility();
            --this.robotTicks;
            this.sync();
        }
    }

    public void setRobotTicks(int ticks) {
        this.robotTicks = ticks;
        this.sync();
    }

    public void durationRobotAbility() {
        AllowPlayerPunching.EVENT.register(((attacker, victim) -> {
            GameWorldComponent gameWorld = GameWorldComponent.KEY.get(this.player.getWorld());
            return gameWorld.isRole(this.player, KinsWatheRoles.ROBOT) && this.robotTicks > 0;
        }));
    }

    public void reset() {
        this.robotTicks = 0;
        this.sync();
    }

    public void sync() {
        KEY.sync(this.player);
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag, RegistryWrapper.@NotNull WrapperLookup registryLookup) {
        tag.putInt("robotTicks", this.robotTicks);
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag, RegistryWrapper.@NotNull WrapperLookup registryLookup) {
        this.robotTicks = tag.contains("robotTicks") ? tag.getInt("robotTicks") : 0;
    }
}