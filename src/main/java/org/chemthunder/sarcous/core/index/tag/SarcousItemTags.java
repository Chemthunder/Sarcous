package org.chemthunder.sarcous.core.index.tag;

import net.acoyt.acornlib.api.builder.TagBuilder;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import org.chemthunder.sarcous.core.Sarcous;

/**
 * @author Chemthunder
 */
public interface SarcousItemTags {
    TagBuilder<Item> tag = new TagBuilder<>(Sarcous.MOD_ID, RegistryKeys.ITEM);

    TagKey<Item> GEOTHERMIC_SOURCES = tag.register("geothermic_sources");
}
