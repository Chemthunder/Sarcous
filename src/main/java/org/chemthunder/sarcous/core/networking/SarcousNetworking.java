package org.chemthunder.sarcous.core.networking;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import org.chemthunder.sarcous.core.networking.clientbound.SyncOverclockedPayload;

/**
 * @author Chemthunder
 */
public interface SarcousNetworking {
    static void typePayloads() {
        PayloadTypeRegistry.playS2C().register(SyncOverclockedPayload.TYPE, SyncOverclockedPayload.CODEC);
    }

    @Environment(EnvType.CLIENT)
    static void clientbound() {
        ClientPlayNetworking.registerGlobalReceiver(SyncOverclockedPayload.TYPE, new SyncOverclockedPayload.Reciever());
    }

    static void serverbound() {

    }
}
