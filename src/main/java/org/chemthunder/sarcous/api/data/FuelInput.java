package org.chemthunder.sarcous.api.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import org.chemthunder.sarcous.core.infrastructure.EnergyTier;

/**
 * @author Chemthunder
 */
public record FuelInput(Item ingredient, EnergyTier tier, int value, SoundEvent event) {
    public static final Codec<FuelInput> CODEC = RecordCodecBuilder.create(codec -> codec.group(
            Registries.ITEM.getCodec().fieldOf("ingredient").forGetter(FuelInput::ingredient),
            EnergyTier.CODEC.fieldOf("tier").forGetter(FuelInput::tier),
            Codec.INT.fieldOf("value").forGetter(FuelInput::value),
            SoundEvent.CODEC.fieldOf("sound").forGetter(FuelInput::event)
    ).apply(codec, FuelInput::new));
}
