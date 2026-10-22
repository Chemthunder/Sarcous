package org.chemthunder.sarcous.datagen.providers.client;

import net.acoyt.acornlib.api.util.MiscUtils;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryWrapper;
import org.chemthunder.sarcous.core.block.DrillBlock;
import org.chemthunder.sarcous.core.block.StockpileBlock;
import org.chemthunder.sarcous.core.index.SarcousBlocks;
import org.chemthunder.sarcous.core.index.SarcousItems;
import org.chemthunder.sarcous.core.index.SarcousStatusEffects;
import org.chemthunder.sarcous.core.infrastructure.EnergyTier;

import java.util.Map;
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

        translationBuilder.add(SarcousStatusEffects.HYPOVOLEMIA.value(), "Hypovolemia");

        translateTiered(translationBuilder, "Drill", SarcousBlocks.DRILLS);
        translateTiered(translationBuilder, "Stockpile", SarcousBlocks.STOCKPILES);
    }

    public void translateTiered(TranslationBuilder translationBuilder, String suffix, Map<EnergyTier, Block> tiers) {
        for (EnergyTier tier : tiers.keySet()) {
            Block ref = tiers.get(tier);

            if (ref != null) {
                translationBuilder.add(ref, MiscUtils.formatString(tier.asString()) + " " + suffix);
            }
        }
    }
}
