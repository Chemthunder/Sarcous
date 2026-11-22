package org.chemthunder.sarcous.datagen.providers.server.machinery;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
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
    public FuelInputProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(dataOutput, registriesFuture, PackOutput.Target.DATA_PACK, FuelInputCollector.path, FuelInput.CODEC);
    }

    protected void configure(BiConsumer<Identifier, FuelInput> provider, HolderLookup.Provider lookup) {
        offerRecipe(provider, Items.COAL, EnergyTier.COALBURNER, 2, SoundEvents.FURNACE_FIRE_CRACKLE);
        offerRecipe(provider, Items.CHARCOAL, EnergyTier.COALBURNER, 1, SoundEvents.FURNACE_FIRE_CRACKLE);
        offerRecipe(provider, Items.COAL_BLOCK, EnergyTier.COALBURNER, 18, SoundEvents.FURNACE_FIRE_CRACKLE);
        offerRecipe(provider, Items.COAL_ORE, EnergyTier.COALBURNER, 2, SoundEvents.FURNACE_FIRE_CRACKLE);
        offerRecipe(provider, Items.DEEPSLATE_COAL_ORE, EnergyTier.COALBURNER, 2, SoundEvents.FURNACE_FIRE_CRACKLE);

        offerRecipe(provider, Items.ROTTEN_FLESH, EnergyTier.BILEFUEL, 1, SoundEvents.GENERIC_EAT.value());
    }

    public static void offerRecipe(BiConsumer<Identifier, FuelInput> provider, Item input, EnergyTier tier, int value, SoundEvent event) {
        provider.accept(
                Sarcous.id(BuiltInRegistries.ITEM.getKey(input).getPath().toLowerCase()),
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
