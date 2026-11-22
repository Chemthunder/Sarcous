package org.chemthunder.sarcous.core;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import org.chemthunder.sarcous.core.event.DataLoggingEvent;
import org.chemthunder.sarcous.core.event.PoweredBlockEntityInteractionEvents;
import org.chemthunder.sarcous.core.index.*;
import org.chemthunder.sarcous.core.infrastructure.EnergyTier;
import org.chemthunder.sarcous.core.infrastructure.data.DrillOutputCollector;
import org.chemthunder.sarcous.core.infrastructure.data.FuelInputCollector;
import org.chemthunder.sarcous.core.networking.SarcousNetworking;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Chemthunder
 */
public class Sarcous implements ModInitializer {
	public static final String MOD_ID = "sarcous";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final List<EnergyTier> BLACKLISTED_TIERS = List.of(EnergyTier.GEOTHERMIC, EnergyTier.BILEFUEL);

	public void onInitialize() {
		SarcousNetworking.typePayloads();
		SarcousNetworking.serverbound();

		SarcousItems.init();
		SarcousBlocks.init();
		SarcousBlockEntityTypes.init();
		SarcousCreativeModeTabs.init();
		SarcousAttachmentTypes.init();
		SarcousMobEffects.init();
		SarcousGameRules.init();
		SarcousDataComponentTypes.init();
		SarcousPlayerTriggers.init();

		PoweredBlockEntityInteractionEvents.init();

		ServerLifecycleEvents.SERVER_STARTED.register(new DataLoggingEvent());

		ResourceLoader.get(PackType.SERVER_DATA).registerReloader(id(FuelInputCollector.path), new FuelInputCollector());
		ResourceLoader.get(PackType.SERVER_DATA).registerReloader(id(DrillOutputCollector.path), new DrillOutputCollector());

		LOGGER.info("blood.... mmmm....");
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
