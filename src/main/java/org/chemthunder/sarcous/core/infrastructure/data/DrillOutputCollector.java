package org.chemthunder.sarcous.core.infrastructure.data;

import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.reloader.SimpleResourceReloader;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceFinder;
import net.minecraft.util.Identifier;
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

    protected Map<Identifier, DrillOutput> prepare(Store store) {
        Map<Identifier, DrillOutput> rawData = new HashMap<>();
        JsonDataLoader.load(store.getResourceManager(), ResourceFinder.json(path),
                store.getOrThrow(ResourceLoader.RELOADER_REGISTRY_LOOKUP_KEY).getOps(JsonOps.INSTANCE),
                DrillOutput.CODEC,
                rawData
        );

        Sarcous.LOGGER.info("Prepared incoming data: {}", rawData);
        return rawData;
    }

    protected void apply(Map<Identifier, DrillOutput> prepared, Store store) {
        LOADED_DATA.clear();
        LOADED_DATA.putAll(prepared);

        Sarcous.LOGGER.info("Applied data: {}", Arrays.toString(LOADED_DATA.keySet().toArray()));
    }
}
