package org.chemthunder.sarcous.api.energy;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.recipe.Ingredient;
import org.chemthunder.sarcous.core.infrastructure.EnergyTier;

/**
 * @author Chemthunder
 */
public record ItemInput(Ingredient ingredient, EnergyTier tier, int value) {
    public static final Codec<ItemInput> CODEC = RecordCodecBuilder.create(codec -> codec.group(
            Ingredient.CODEC.fieldOf("ingredient").forGetter(ItemInput::ingredient),
            EnergyTier.CODEC.fieldOf("tier").forGetter(ItemInput::tier),
            Codec.INT.fieldOf("value").forGetter(ItemInput::value)
    ).apply(codec, ItemInput::new));
}
