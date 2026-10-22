package org.chemthunder.sarcous.core;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import org.chemthunder.sarcous.core.event.PoweredBlockEntityInteractionEvents;
import org.chemthunder.sarcous.core.index.*;
import org.chemthunder.sarcous.core.infrastructure.data.DrillOutputCollector;
import org.chemthunder.sarcous.core.infrastructure.data.ItemInputCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Chemthunder
 */
public class Sarcous implements ModInitializer {
	public static final String MOD_ID = "sarcous";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public void onInitialize() {
		SarcousItems.init();
		SarcousBlocks.init();
		SarcousBlockEntityTypes.init();
		SarcousItemGroups.init();
		SarcousAttachmentTypes.init();
		SarcousStatusEffects.init();

		PoweredBlockEntityInteractionEvents.init();

		ResourceLoader.get(ResourceType.SERVER_DATA).registerReloader(id(ItemInputCollector.path), new ItemInputCollector());
		ResourceLoader.get(ResourceType.SERVER_DATA).registerReloader(id(DrillOutputCollector.path), new DrillOutputCollector());

		LOGGER.info("blood.... mmmm....");

		for (String input : ItemInputCollector.getEntries().keySet()) {
			System.out.println(input);
		}
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
