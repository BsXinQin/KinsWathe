package org.BsXinQin.kinswathe.packet;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import org.BsXinQin.kinswathe.KinsWathe;

public record ClientS2CPacket() implements CustomPayload {

    public static final Identifier Client_ID = Identifier.of(KinsWathe.MOD_ID, "client");
    public static final Id<ClientS2CPacket> ID = new Id<>(Client_ID);
    public static final PacketCodec<RegistryByteBuf, ClientS2CPacket> CODEC;
    static {CODEC = PacketCodec.of(ClientS2CPacket::write, ClientS2CPacket::read);}
    public void write(PacketByteBuf buf) {}
    public static ClientS2CPacket read(PacketByteBuf buf) {
        return new ClientS2CPacket();
    }
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
