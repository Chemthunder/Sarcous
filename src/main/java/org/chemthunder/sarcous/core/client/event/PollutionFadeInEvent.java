package org.chemthunder.sarcous.core.client.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.chemthunder.sarcous.core.index.SarcousAttachmentTypes;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class PollutionFadeInEvent implements ClientTickEvents.StartTick {
    public static float value = 0.0F;

    public void onStartTick(Minecraft client) {
        LocalPlayer localPlayer = client.player;
        if (localPlayer == null) return;

        value = Math.clamp(value, 0, 1);

        ChunkAccess chunk = localPlayer.level().getChunk(localPlayer.blockPosition());

        if (chunk.getAttachedOrCreate(SarcousAttachmentTypes.POLLUTION_LEVEL) > 20) {
            value += 0.0025F;
            return;
        }

        value -= 0.015F;
    }
}
