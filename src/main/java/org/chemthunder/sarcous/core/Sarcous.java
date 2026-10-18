package org.chemthunder.sarcous.core;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;

import org.chemthunder.sarcous.core.index.SarcousBlockEntityTypes;
import org.chemthunder.sarcous.core.index.SarcousBlocks;
import org.chemthunder.sarcous.core.index.SarcousItemGroups;
import org.chemthunder.sarcous.core.index.SarcousItems;
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

		LOGGER.info("Hello Fabric world!");
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
