package org.chemthunder.sarcous.datagen.providers.server.machinery;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.data.DataOutput;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.chemthunder.sarcous.api.data.FuelInput;
import org.chemthunder.sarcous.core.Sarcous;
import org.chemthunder.sarcous.core.infrastructure.EnergyTier;
import org.chemthunder.sarcous.core.infrastructure.data.FuelInputCollector;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

/**
 * @author Chemthunder
 */
public class FuelInputProvider extends FabricCodecDataProvider<FuelInput> {
    public FuelInputProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(dataOutput, registriesFuture, DataOutput.OutputType.DATA_PACK, FuelInputCollector.path, FuelInput.CODEC);
    }

    protected void configure(BiConsumer<Identifier, FuelInput> provider, RegistryWrapper.WrapperLookup lookup) {
        offerRecipe(provider, Items.COAL, EnergyTier.COALBURNER, 2, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE);
        offerRecipe(provider, Items.CHARCOAL, EnergyTier.COALBURNER, 1, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE);
        offerRecipe(provider, Items.COAL_BLOCK, EnergyTier.COALBURNER, 18, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE);
        offerRecipe(provider, Items.COAL_ORE, EnergyTier.COALBURNER, 2, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE);
        offerRecipe(provider, Items.DEEPSLATE_COAL_ORE, EnergyTier.COALBURNER, 2, SoundEvents.BLOCK_FURNACE_FIRE_CRACKLE);

        offerRecipe(provider, Items.ROTTEN_FLESH, EnergyTier.BILEFUEL, 1, SoundEvents.ENTITY_GENERIC_EAT.value());
    }

    public static void offerRecipe(BiConsumer<Identifier, FuelInput> provider, Item input, EnergyTier tier, int value, SoundEvent event) {
        provider.accept(
                Sarcous.id(Registries.ITEM.getId(input).getPath().toLowerCase()),
                new FuelInput(
                        input,
                        tier,
                        value,
                        event
                )
        );
    }

    public String getName() {
        return "Fuel Inputs";
    }
}
