package org.BsXinQin.kinswathe.component;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.BsXinQin.kinswathe.KinsWathe;
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class CustomWinnerComponent implements AutoSyncedComponent {

    public static final ComponentKey<CustomWinnerComponent> KEY = ComponentRegistry.getOrCreate(Identifier.of(KinsWathe.MOD_ID, "winner"), CustomWinnerComponent.class);
    private final World world;

    @Getter @Setter private String winningTextId = null;
    @Getter @Setter private int color = 0x000000;
    @Getter @Setter private List<ServerPlayerEntity> winners = new ArrayList<>();

    public CustomWinnerComponent(@NotNull World world) {
        this.world = world;
    }

    public ArrayList<UUID> uuidListFromTag(@NotNull NbtCompound tag, String listName) {
        ArrayList<UUID> result = new ArrayList<>();
        if (tag.contains(listName)) {
            NbtList list = tag.getList(listName, NbtElement.INT_ARRAY_TYPE);
            for (NbtElement e : list) {
                Optional<UUID> uuidOptional = Optional.of(NbtHelper.toUuid(e));
                if (uuidOptional.isPresent()) {
                    result.add(uuidOptional.get());
                }
            }
        }
        return result;
    }

    public boolean hasCustomWinner() {
        return this.winningTextId != null;
    }

    public void sync() {CustomWinnerComponent.KEY.sync(this.world);}

    public void reset() {
        this.winningTextId = null;
        this.winners = new ArrayList<>();
        this.color = 0x000000;sync();
        this.sync();
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag, RegistryWrapper.@NotNull WrapperLookup registryLookup) {
        if (this.winningTextId != null) {
            tag.putString("winning_text", this.winningTextId);
        }
        NbtList winnersList = new NbtList();
        for (ServerPlayerEntity player : this.winners) {
            if (player != null) {
                winnersList.add(NbtHelper.fromUuid(player.getUuid()));
            }
        }
        tag.put("winners", winnersList);
        tag.putInt("color", this.color);
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag, RegistryWrapper.@NotNull WrapperLookup registryLookup) {
        this.winningTextId = tag.contains("winning_text") ? tag.getString("winning_text") : null;
        if (tag.contains("winners")) {
            List<ServerPlayerEntity> loadedWinners = new ArrayList<>();
            List<UUID> uuids = uuidListFromTag(tag, "winners");
            if (world instanceof ServerWorld serverWorld) {
                for (UUID uuid : uuids) {
                    ServerPlayerEntity player = serverWorld.getServer().getPlayerManager().getPlayer(uuid);
                    if (player != null) {
                        loadedWinners.add(player);
                    }
                }
            }
            this.winners = new ArrayList<>(loadedWinners);} else {
            this.winners = new ArrayList<>();
        }
        this.color = tag.contains("color") ? tag.getInt("color") : 0x000000;
    }
}