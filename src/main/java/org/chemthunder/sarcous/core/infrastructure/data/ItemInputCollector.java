package org.chemthunder.sarcous.core.infrastructure.data;

import net.acoyt.acornlib.api.util.MiscUtils;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceFinder;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.chemthunder.sarcous.api.data.ItemInput;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Chemthunder
 */
public class ItemInputCollector extends JsonDataLoader<ItemInput> {
    public static final Map<Identifier, ItemInput> data = new HashMap<>();
    public static final String path = "machinery/item_input";

    public ItemInputCollector() {
        super(ItemInput.CODEC, ResourceFinder.json(path));
    }

    protected void apply(Map<Identifier, ItemInput> prepared, ResourceManager manager, Profiler profiler) {
        data.clear();
        data.putAll(prepared);
    }

    public static List<ItemInput> collect() {
        return data.values().stream().toList();
    }

    public static Map<String, ItemInput> getEntries() {
        Map<String, ItemInput> values = new HashMap<>();
        data.forEach((id, data) -> {
            String formatted = MiscUtils.formatAfter(id.getPath(), '/').replace(".json", "");
            values.computeIfAbsent(formatted, st -> data);
        });

        return values;
    }
}
