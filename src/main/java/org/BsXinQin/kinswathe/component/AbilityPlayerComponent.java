package org.BsXinQin.kinswathe.component;

import lombok.SneakyThrows;
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

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class AbilityPlayerComponent implements AutoSyncedComponent, ServerTickingComponent, ClientTickingComponent {

    public static final ComponentKey<AbilityPlayerComponent> KEY = ComponentRegistry.getOrCreate(Identifier.of(KinsWathe.MOD_ID, "ability"), AbilityPlayerComponent.class);
    private final PlayerEntity player;
    public int cooldown = 0;

    public AbilityPlayerComponent(@NotNull PlayerEntity player) {
        this.player = player;
    }

    @Override
    public void serverTick() {
        if (this.cooldown > 0) {
            -- this.cooldown;
            this.sync();
        }
    }

    @Override
    public void clientTick() {}

    public void setAbilityCooldown(int ticks) {
        setNoellesRolesAbilityCooldown(ticks);
        this.cooldown = ticks * 20;
        this.sync();
    }

    @SneakyThrows
    public void setNoellesRolesAbilityCooldown(int ticks) {
        if (FabricLoader.getInstance().isModLoaded("noellesroles")) {
            Class<?> abilityClass = Class.forName("org.agmas.noellesroles.AbilityPlayerComponent");
            Field keyField = abilityClass.getField("KEY");
            Object componentKey = keyField.get(null);
            Method getMethod = componentKey.getClass().getMethod("get", Object.class);
            Object playerAbility = getMethod.invoke(componentKey, this.player);
            Field cooldownField = abilityClass.getField("cooldown");
            if (ticks >= 120) {
                cooldownField.setInt(playerAbility, ticks * 20);
            } else {
                cooldownField.setInt(playerAbility, 2400);
            }
            Method syncMethod = abilityClass.getMethod("sync");
            syncMethod.invoke(playerAbility);
        }
    }

    public void reset() {
        this.cooldown = 0;
        this.sync();
    }

    public void sync() {
        KEY.sync(this.player);
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag, RegistryWrapper.@NotNull WrapperLookup registryLookup) {
        tag.putInt("cooldown", this.cooldown);

    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag, RegistryWrapper.@NotNull WrapperLookup registryLookup) {
        this.cooldown = tag.contains("cooldown") ? tag.getInt("cooldown") : 0;
    }
}