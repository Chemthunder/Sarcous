package org.chemthunder.sarcous.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import org.chemthunder.sarcous.datagen.providers.client.SarcousLanguageProvider;
import org.chemthunder.sarcous.datagen.providers.client.SarcousModelProvider;
import org.chemthunder.sarcous.datagen.providers.server.SarcousDrillOutputProvider;
import org.chemthunder.sarcous.datagen.providers.server.SarcousItemInputProvider;

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
		pack.addProvider(SarcousItemInputProvider::new);
		pack.addProvider(SarcousDrillOutputProvider::new);
	}
}
