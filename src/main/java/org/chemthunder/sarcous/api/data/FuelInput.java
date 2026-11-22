package org.chemthunder.sarcous.api.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import org.chemthunder.sarcous.core.infrastructure.EnergyTier;

/**
 * @author Chemthunder
 */
public record FuelInput(Item ingredient, EnergyTier tier, int value, SoundEvent event) {
    public static final Codec<FuelInput> CODEC = RecordCodecBuilder.create(codec -> codec.group(
            BuiltInRegistries.ITEM.byNameCodec().fieldOf("ingredient").forGetter(FuelInput::ingredient),
            EnergyTier.CODEC.fieldOf("tier").forGetter(FuelInput::tier),
            Codec.INT.fieldOf("value").forGetter(FuelInput::value),
            SoundEvent.DIRECT_CODEC.fieldOf("sound").forGetter(FuelInput::event)
    ).apply(codec, FuelInput::new));
}
