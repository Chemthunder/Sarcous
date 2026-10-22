package org.chemthunder.sarcous.datagen.providers.server;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.data.DataOutput;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import org.chemthunder.sarcous.api.energy.ItemInput;
import org.chemthunder.sarcous.core.Sarcous;
import org.chemthunder.sarcous.core.infrastructure.EnergyTier;
import org.chemthunder.sarcous.core.infrastructure.data.ItemInputCollector;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

/**
 * @author Chemthunder
 */
public class SarcousItemInputProvider extends FabricCodecDataProvider<ItemInput> {
    public SarcousItemInputProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(dataOutput, registriesFuture, DataOutput.OutputType.DATA_PACK, ItemInputCollector.path, ItemInput.CODEC);
    }

    protected void configure(BiConsumer<Identifier, ItemInput> provider, RegistryWrapper.WrapperLookup lookup) {
        provider.accept(
                Sarcous.id("test"),
                new ItemInput(
                        Ingredient.ofItem(
                                Items.BEDROCK
                        ),
                        EnergyTier.GEOTHERMIC,
                        4
                )
        );
    }

    public String getName() {
        return "Item Inputs";
    }
}
