package org.chemthunder.sarcous.core.infrastructure.data;

import net.acoyt.acornlib.api.util.MiscUtils;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceFinder;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.chemthunder.sarcous.api.data.DrillOutput;
import org.chemthunder.sarcous.api.data.ItemInput;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Chemthunder
 */
public class DrillOutputCollector extends JsonDataLoader<DrillOutput> {
    public static final Map<Identifier, DrillOutput> data = new HashMap<>();
    public static final String path = "machinery/drill_output";

    public DrillOutputCollector() {
        super(DrillOutput.CODEC, ResourceFinder.json(path));
    }

    protected void apply(Map<Identifier, DrillOutput> prepared, ResourceManager manager, Profiler profiler) {
        data.clear();
        data.putAll(prepared);
    }

    public static Map<String, DrillOutput> getEntries() {
        Map<String, DrillOutput> values = new HashMap<>();
        data.forEach((id, data) -> {
            String formatted = MiscUtils.formatAfter(id.getPath(), '/').replace(".json", "");
            values.computeIfAbsent(formatted, st -> data);
        });

        return values;
    }
}
