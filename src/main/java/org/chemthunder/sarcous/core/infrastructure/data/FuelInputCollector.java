package org.chemthunder.sarcous.core.infrastructure.data;

import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.reloader.SimpleResourceReloader;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceFinder;
import net.minecraft.util.Identifier;
import org.chemthunder.sarcous.api.data.FuelInput;
import org.chemthunder.sarcous.core.Sarcous;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Chemthunder
 */
public class FuelInputCollector extends SimpleResourceReloader<Map<Identifier, FuelInput>> {
    public static final Map<Identifier, FuelInput> LOADED_DATA = new HashMap<>();
    public static final String path = "machinery/fuel_input";

    protected Map<Identifier, FuelInput> prepare(Store store) {
        Map<Identifier, FuelInput> rawData = new HashMap<>();
        JsonDataLoader.load(store.getResourceManager(), ResourceFinder.json(path),
                store.getOrThrow(ResourceLoader.RELOADER_REGISTRY_LOOKUP_KEY).getOps(JsonOps.INSTANCE),
                FuelInput.CODEC,
                rawData
        );

        Sarcous.LOGGER.info("Prepared incoming data: {}", rawData);
        return rawData;
    }

    protected void apply(Map<Identifier, FuelInput> prepared, Store store) {
        LOADED_DATA.clear();
        LOADED_DATA.putAll(prepared);

        Sarcous.LOGGER.info("Applied data: {}", Arrays.toString(LOADED_DATA.keySet().toArray()));
    }
}
