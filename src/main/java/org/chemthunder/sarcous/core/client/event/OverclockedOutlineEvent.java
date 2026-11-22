package org.chemthunder.sarcous.core.client.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class OverclockedOutlineEvent implements WorldRenderEvents.EndMain {
    public void endMain(WorldRenderContext context) {

    }
}

