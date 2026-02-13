package org.BsXinQin.kinswathe;

import de.maxhenkel.voicechat.api.VoicechatApi;
import de.maxhenkel.voicechat.api.VoicechatPlugin;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import de.maxhenkel.voicechat.api.events.MicrophonePacketEvent;
import net.minecraft.server.network.ServerPlayerEntity;
import org.BsXinQin.kinswathe.roles.kidnapper.KidnapperComponent;

public class KinsWatheVoiceChatPlugin implements VoicechatPlugin {

    @Override public String getPluginId() {return KinsWathe.MOD_ID;}
    @Override public void initialize(VoicechatApi api) {VoicechatPlugin.super.initialize(api);}

    @Override
    public void registerEvents(EventRegistration registration) {
        registration.registerEvent(MicrophonePacketEvent.class, this::onMicrophonePacket);
        VoicechatPlugin.super.registerEvents(registration);
    }

    public void onMicrophonePacket(MicrophonePacketEvent ci) {
        if (ci.getSenderConnection() == null) return;
        Object playerObject = ci.getSenderConnection().getPlayer().getPlayer();
        if (playerObject instanceof ServerPlayerEntity serverPlayer) {
            KidnapperComponent targetControlled = KidnapperComponent.KEY.get(serverPlayer);
            if (targetControlled.controlTicks > 0) {
                ci.cancel();
            }
        }
    }
}