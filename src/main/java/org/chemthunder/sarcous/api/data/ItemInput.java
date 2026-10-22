package org.chemthunder.sarcous.api.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import org.chemthunder.sarcous.core.infrastructure.EnergyTier;

/**
 * @author Chemthunder
 */
public record ItemInput(Item ingredient, EnergyTier tier, int value) {
    public static final Codec<ItemInput> CODEC = RecordCodecBuilder.create(codec -> codec.group(
            Registries.ITEM.getCodec().fieldOf("ingredient").forGetter(ItemInput::ingredient),
            EnergyTier.CODEC.fieldOf("tier").forGetter(ItemInput::tier),
            Codec.INT.fieldOf("value").forGetter(ItemInput::value)
    ).apply(codec, ItemInput::new));
}
