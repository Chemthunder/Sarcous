package org.chemthunder.sarcous.datagen.providers.client;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;
import org.chemthunder.sarcous.core.index.SarcousBlocks;
import org.chemthunder.sarcous.core.index.SarcousItems;
import org.chemthunder.sarcous.core.index.SarcousStatusEffects;

import java.util.concurrent.CompletableFuture;

/**
 * @author Chemthunder
 */
public class SarcousLanguageProvider extends FabricLanguageProvider {
    public SarcousLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(SarcousItems.COLLECTORS_CARVER, "Collector's Carver");

        translationBuilder.add(SarcousBlocks.DRILL_COALBURNER, "Drill");
        translationBuilder.add(SarcousBlocks.STOCKPILE, "Stockpile");

        translationBuilder.add(SarcousStatusEffects.HYPOVOLEMIA.value(), "Hypovolemia");
    }
}
