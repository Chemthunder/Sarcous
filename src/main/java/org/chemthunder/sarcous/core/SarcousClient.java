package org.chemthunder.sarcous.core;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.color.item.ItemTintSources;
import org.chemthunder.sarcous.core.client.event.BarometricPollutionEVent;
import org.chemthunder.sarcous.core.client.event.NoSmokingEvents;
import org.chemthunder.sarcous.core.client.event.PollutionFadeInEvent;
import org.chemthunder.sarcous.core.client.event.PonderEvents;
import org.chemthunder.sarcous.core.client.item.RollieBlackeningSource;
import org.chemthunder.sarcous.core.index.SarcousBlockEntityTypes;
import org.chemthunder.sarcous.core.index.SarcousBlocks;
import org.chemthunder.sarcous.core.networking.SarcousNetworking;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class SarcousClient implements ClientModInitializer {
    public static final RenderStateDataKey<Boolean> OVERCLOCKED = RenderStateDataKey.create();

    public void onInitializeClient() {
        SarcousNetworking.clientbound();

        PonderEvents.init();
        NoSmokingEvents.init();

        SarcousBlockEntityTypes.clinit();
        SarcousBlocks.clinit();

        HudElementRegistry.addFirst(Sarcous.id("pollution"), new BarometricPollutionEVent());

        ClientTickEvents.START_CLIENT_TICK.register(new PollutionFadeInEvent());

        ItemTintSources.ID_MAPPER.put(RollieBlackeningSource.ID, RollieBlackeningSource.CODEC);
    }
}
