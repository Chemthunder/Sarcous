package org.chemthunder.sarcous.core.index.tag;

import net.acoyt.acornlib.api.builder.TagBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.chemthunder.sarcous.core.Sarcous;

/**
 * @author Chemthunder
 */
public interface SarcousItemTags {
    TagBuilder<Item> tag = new TagBuilder<>(Sarcous.MOD_ID, Registries.ITEM);

    TagKey<Item> GEOTHERMIC_SOURCES = tag.register("geothermic_sources");
}
