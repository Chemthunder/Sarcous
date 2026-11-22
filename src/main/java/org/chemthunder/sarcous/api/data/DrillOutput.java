package org.chemthunder.sarcous.api.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

/**
 * @author Chemthunder
 */
public record DrillOutput(Block block, Item output, SoundEvent sound) {
    public static final Codec<DrillOutput> CODEC = RecordCodecBuilder.create(codec -> codec.group(
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("block").forGetter(DrillOutput::block),
            BuiltInRegistries.ITEM.byNameCodec().fieldOf("output").forGetter(DrillOutput::output),
            SoundEvent.DIRECT_CODEC.fieldOf("sound").forGetter(DrillOutput::sound)
    ).apply(codec, DrillOutput::new));
}
