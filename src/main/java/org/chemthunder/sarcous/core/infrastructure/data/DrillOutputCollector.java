package org.chemthunder.sarcous.core.infrastructure.data;

import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.reloader.SimpleResourceReloader;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import org.chemthunder.sarcous.api.data.DrillOutput;
import org.chemthunder.sarcous.core.Sarcous;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Chemthunder
 */
public class DrillOutputCollector extends SimpleResourceReloader<Map<Identifier, DrillOutput>> {
    public static final Map<Identifier, DrillOutput> LOADED_DATA = new HashMap<>();
    public static final String path = "machinery/drill_output";

    protected Map<Identifier, DrillOutput> prepare(SharedState store) {
        Map<Identifier, DrillOutput> rawData = new HashMap<>();
        SimpleJsonResourceReloadListener.scanDirectory(store.resourceManager(), FileToIdConverter.json(path),
                store.get(ResourceLoader.RELOADER_REGISTRY_LOOKUP_KEY).createSerializationContext(JsonOps.INSTANCE),
                DrillOutput.CODEC,
                rawData
        );

        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            Sarcous.LOGGER.info("Prepared incoming data: {}", rawData);
        }
        return rawData;
    }

    protected void apply(Map<Identifier, DrillOutput> prepared, SharedState store) {
        LOADED_DATA.clear();
        LOADED_DATA.putAll(prepared);

        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            Sarcous.LOGGER.info("Applied data: {}", Arrays.toString(LOADED_DATA.keySet().toArray()));
        }
    }
}
