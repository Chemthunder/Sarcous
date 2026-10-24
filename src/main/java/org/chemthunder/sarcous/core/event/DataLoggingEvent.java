package org.chemthunder.sarcous.core.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.chemthunder.sarcous.api.data.FuelInput;
import org.chemthunder.sarcous.core.infrastructure.data.FuelInputCollector;

import java.util.Map;

/**
 * @author Chemthunder
 */
public class DataLoggingEvent implements ServerLifecycleEvents.ServerStarted {
    public void onServerStarted(MinecraftServer server) {
        for (Map.Entry<Identifier, FuelInput> entry : FuelInputCollector.LOADED_DATA.entrySet()) {
            server.getPlayerManager().broadcast(Text.of("Loaded data: " + entry.getKey()), false);
        }
        server.getPlayerManager().broadcast(Text.of("Loaded Sarcous data successfully!"), false);
    }
}
