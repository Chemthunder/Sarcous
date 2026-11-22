package org.chemthunder.sarcous.core.index.tag;

import net.acoyt.acornlib.api.builder.TagBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.chemthunder.sarcous.core.Sarcous;

/**
 * @author Chemthunder
 */
public interface SarcousBlockEntityTypeTags {
    TagBuilder<BlockEntityType<?>> tag = new TagBuilder<>(Sarcous.MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    TagKey<BlockEntityType<?>> ABLE_TO_BE_OVERCLOCKED = tag.register("able_to_be_overclocked");
}
