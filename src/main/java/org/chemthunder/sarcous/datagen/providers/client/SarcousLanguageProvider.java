package org.chemthunder.sarcous.datagen.providers.client;

import net.acoyt.acornlib.api.util.MiscUtils;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;
import org.chemthunder.sarcous.core.index.SarcousBlocks;
import org.chemthunder.sarcous.core.index.SarcousItems;
import org.chemthunder.sarcous.core.index.SarcousMobEffects;
import org.chemthunder.sarcous.core.infrastructure.EnergyTier;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author Chemthunder
 */
public class SarcousLanguageProvider extends FabricLanguageProvider {
    public SarcousLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(SarcousItems.COLLECTORS_CARVER, "Collector's Carver");
        translationBuilder.add(SarcousItems.ROLLIE, "Rollie");
        translationBuilder.add(SarcousItems.ASHTRAY, "Ashtray");

        translationBuilder.add(SarcousMobEffects.HYPOVOLEMIA.value(), "Hypovolemia");

        translateTiered(translationBuilder, "Drill", SarcousBlocks.DRILLS);
        translateTiered(translationBuilder, "Stockpile", SarcousBlocks.STOCKPILES);
    }

    public void translateTiered(TranslationBuilder translationBuilder, String suffix, Map<EnergyTier, Block> tiers) {
        for (EnergyTier tier : tiers.keySet()) {
            Block ref = tiers.get(tier);

            if (ref != null) {
                translationBuilder.add(ref, MiscUtils.formatString(tier.getSerializedName()) + " " + suffix);
            }
        }
    }
}
