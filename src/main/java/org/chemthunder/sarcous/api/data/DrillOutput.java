package org.chemthunder.sarcous.api.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;

/**
 * @author Chemthunder
 */
public record DrillOutput(Block block, Item output, SoundEvent sound) {
    public static final Codec<DrillOutput> CODEC = RecordCodecBuilder.create(codec -> codec.group(
            Registries.BLOCK.getCodec().fieldOf("block").forGetter(DrillOutput::block),
            Registries.ITEM.getCodec().fieldOf("output").forGetter(DrillOutput::output),
            SoundEvent.CODEC.fieldOf("sound").forGetter(DrillOutput::sound)
    ).apply(codec, DrillOutput::new));
}
