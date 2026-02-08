package org.BsXinQin.kinswathe.roles.cook;

import dev.doctor4t.wathe.game.GameConstants;
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

public class CookComponent implements AutoSyncedComponent, ServerTickingComponent {

    public static final ComponentKey<CookComponent> KEY = ComponentRegistry.getOrCreate(Identifier.of(KinsWathe.MOD_ID, "cook"), CookComponent.class);
    private final PlayerEntity player;
    public int eatTicks = 0;
    public int panStunTicks = 0;

    public CookComponent(@NotNull PlayerEntity player) {this.player = player;}
    public void sync() {KEY.sync(this.player);}

    @Override
    public void serverTick() {
        if (this.eatTicks > 0) {
            --this.eatTicks;
            this.sync();
        }
        if (this.panStunTicks > 0) {
            --this.panStunTicks;
            this.sync();
        }
    }

    public void startEating() {
        setEatTicks(GameConstants.getInTicks(0, 40));
        this.sync();
    }

    public void setEatTicks(int ticks) {
        this.eatTicks = ticks;
        this.sync();
    }

    public void setPanStun(int ticks) {
        this.panStunTicks = ticks;
        this.sync();
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag, RegistryWrapper.@NotNull WrapperLookup registryLookup) {
        tag.putInt("eatTicks", this.eatTicks);
        tag.putInt("panStunTicks", this.panStunTicks);
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag, RegistryWrapper.@NotNull WrapperLookup registryLookup) {
        this.eatTicks = tag.contains("eatTicks") ? tag.getInt("eatTicks") : 0;
        this.panStunTicks = tag.contains("panStunTicks") ? tag.getInt("panStunTicks") : 0;
    }
}