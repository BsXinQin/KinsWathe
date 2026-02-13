package org.BsXinQin.kinswathe.packet;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import org.BsXinQin.kinswathe.KinsWathe;
import org.jetbrains.annotations.NotNull;

public record AbilityC2SPacket() implements CustomPayload {

    public static final Identifier ABILITY_PAYLOAD_ID = Identifier.of(KinsWathe.MOD_ID, "ability");
    public static final Id<AbilityC2SPacket> ID = new Id<>(ABILITY_PAYLOAD_ID);
    public static final PacketCodec<RegistryByteBuf, AbilityC2SPacket> CODEC;
    static {CODEC = PacketCodec.of(AbilityC2SPacket::write, AbilityC2SPacket::read);}
    public void write(PacketByteBuf buf) {}
    public static AbilityC2SPacket read(PacketByteBuf buf) {
        return new AbilityC2SPacket();
    }
    public @NotNull Id<? extends CustomPayload> getId() {return ID;}
}