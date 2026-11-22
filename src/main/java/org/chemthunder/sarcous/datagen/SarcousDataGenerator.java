package org.chemthunder.sarcous.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.chemthunder.sarcous.datagen.providers.client.SarcousLanguageProvider;
import org.chemthunder.sarcous.datagen.providers.client.SarcousModelProvider;
import org.chemthunder.sarcous.datagen.providers.server.SarcousAdvancementProvider;
import org.chemthunder.sarcous.datagen.providers.server.machinery.DrillOutputProvider;
import org.chemthunder.sarcous.datagen.providers.server.machinery.FuelInputProvider;
import org.chemthunder.sarcous.datagen.providers.server.tag.SarcousBlockEntityTypeTagProvider;
import org.chemthunder.sarcous.datagen.providers.server.tag.SarcousItemTagProvider;

/**
 * @author Chemthunder
 */
public class SarcousDataGenerator implements DataGeneratorEntrypoint {
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		var pack = fabricDataGenerator.createPack();

		/// CLIENT ===
		pack.addProvider(SarcousModelProvider::new);
		pack.addProvider(SarcousLanguageProvider::new);

		/// SERVER ===
		pack.addProvider(FuelInputProvider::new);
		pack.addProvider(DrillOutputProvider::new);

		pack.addProvider(SarcousAdvancementProvider::new);

		pack.addProvider(SarcousItemTagProvider::new);
		pack.addProvider(SarcousBlockEntityTypeTagProvider::new);
	}
}
