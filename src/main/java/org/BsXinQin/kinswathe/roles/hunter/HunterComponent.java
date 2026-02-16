package org.BsXinQin.kinswathe.roles.hunter;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.BsXinQin.kinswathe.KinsWathe;
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class HunterComponent implements AutoSyncedComponent, ServerTickingComponent {

    public static final ComponentKey<HunterComponent> KEY = ComponentRegistry.getOrCreate(Identifier.of(KinsWathe.MOD_ID, "hunter"), HunterComponent.class);

    @NotNull private final PlayerEntity player;
    public boolean isUseKnife = false;
    public int knifeTicks = 0;

    public HunterComponent(@NotNull PlayerEntity player) {this.player = player;}

    @Override
    public void serverTick() {
        if (this.isUseKnife && knifeTicks <= 100) {
            ++ this.knifeTicks;
            this.sync();
        }
    }

    public void useHuntingKnife() {
        this.isUseKnife = true;
        this.knifeTicks = 0;
        this.sync();
    }

    public void reset() {
        this.isUseKnife = false;
        this.knifeTicks = 0;
        this.sync();
    }

    public void sync() {
        KEY.sync(this.player);
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag, RegistryWrapper.@NotNull WrapperLookup registryLookup) {
        tag.putInt("knifeTicks", this.knifeTicks);
        tag.putBoolean("isUseKnife", this.isUseKnife);
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag, RegistryWrapper.@NotNull WrapperLookup registryLookup) {
        this.knifeTicks = tag.contains("knifeTicks") ? tag.getInt("knifeTicks") : 0;
        this.isUseKnife = tag.contains("isUseKnife") && tag.getBoolean("isUseKnife");
    }
}