package org.BsXinQin.kinswathe.roles.physician;

import dev.doctor4t.wathe.api.event.AllowPlayerDeath;
import dev.doctor4t.wathe.index.WatheSounds;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import org.BsXinQin.kinswathe.KinsWathe;
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class PhysicianComponent implements AutoSyncedComponent, ServerTickingComponent {

    public static final ComponentKey<PhysicianComponent> KEY = ComponentRegistry.getOrCreate(Identifier.of(KinsWathe.MOD_ID, "physician"), PhysicianComponent.class);

    @NotNull private final PlayerEntity player;
    public int armor = 0;

    public PhysicianComponent(@NotNull PlayerEntity player) {this.player = player;}

    @Override
    public void serverTick() {
        this.preventDeath();
    }

    public void giveArmor() {
        this.armor = 1;
        this.sync();
    }

    public void preventDeath() {
        AllowPlayerDeath.EVENT.register(((player, killer, identifier) -> {
            if (this.armor > 0) {
                player.getWorld().playSound(player, player.getBlockPos(), WatheSounds.ITEM_PSYCHO_ARMOUR, SoundCategory.PLAYERS, 5.0F, 1.0F);
                this.armor--;
                return false;
            }
            return true;
        }));
    }

    public void reset() {
        this.armor = 0;
    }

    public void sync() {
        KEY.sync(this.player);
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag, RegistryWrapper.@NotNull WrapperLookup registryLookup) {
        tag.putInt("armor", this.armor);
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag, RegistryWrapper.@NotNull WrapperLookup registryLookup) {
        this.armor = tag.contains("armor") ? tag.getInt("armor") : 0;
    }
}