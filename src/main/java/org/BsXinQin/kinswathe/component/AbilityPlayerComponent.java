package org.BsXinQin.kinswathe.component;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.BsXinQin.kinswathe.KinsWathe;
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ClientTickingComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class AbilityPlayerComponent implements AutoSyncedComponent, ServerTickingComponent, ClientTickingComponent {

    public static final ComponentKey<AbilityPlayerComponent> KEY = ComponentRegistry.getOrCreate(Identifier.of(KinsWathe.MOD_ID, "ability"), AbilityPlayerComponent.class);
    private final PlayerEntity player;
    public int cooldown = 0;

    public AbilityPlayerComponent(@NotNull PlayerEntity player) {
        this.player = player;
    }
    public void sync() {KEY.sync(this.player);}
    public void clientTick() {}

    public void serverTick() {
        if (this.cooldown > 0) {
            --this.cooldown;this.sync();
        }
    }

    public void setAbilityCooldown(@NotNull PlayerEntity player, int ticks) {
        AbilityPlayerComponent ability = AbilityPlayerComponent.KEY.get(player);
        setNoellesRolesAbilityCooldown(player, ticks);
        ability.cooldown = ticks * 20;
        this.sync();
    }

    public void setNoellesRolesAbilityCooldown(@NotNull PlayerEntity player, int ticks) {
        if (FabricLoader.getInstance().isModLoaded("noellesroles")) {
            org.agmas.noellesroles.AbilityPlayerComponent noellesrolesAbility = org.agmas.noellesroles.AbilityPlayerComponent.KEY.get(player);
            noellesrolesAbility.cooldown = ticks * 20;
            noellesrolesAbility.sync();
        }
    }

    public void reset() {
        this.cooldown = 0;
        this.sync();
    }

    public void writeToNbt(@NotNull NbtCompound tag, RegistryWrapper.@NotNull WrapperLookup registryLookup) {
        tag.putInt("cooldown", this.cooldown);

    }

    public void readFromNbt(@NotNull NbtCompound tag, RegistryWrapper.@NotNull WrapperLookup registryLookup) {
        this.cooldown = tag.contains("cooldown") ? tag.getInt("cooldown") : 0;
    }
}