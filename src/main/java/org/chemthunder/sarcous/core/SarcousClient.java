package org.chemthunder.sarcous.core;

import net.fabricmc.api.ClientModInitializer;
import org.chemthunder.sarcous.core.client.ponder.PonderEvents;
import org.chemthunder.sarcous.core.index.SarcousBlockEntityTypes;

/**
 * @author Chemthunder
 */
public class SarcousClient implements ClientModInitializer {
    public void onInitializeClient() {
        PonderEvents.init();

        SarcousBlockEntityTypes.clinit();
    }
}
