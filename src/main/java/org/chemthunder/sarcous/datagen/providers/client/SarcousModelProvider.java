package org.chemthunder.sarcous.datagen.providers.client;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;
import org.chemthunder.sarcous.core.index.SarcousBlocks;
import org.chemthunder.sarcous.core.index.SarcousItems;

/**
 * @author Chemthunder
 */
public class SarcousModelProvider extends FabricModelProvider {
    public SarcousModelProvider(FabricDataOutput output) {
        super(output);
    }

    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(SarcousBlocks.TEST_MACHINE);
        blockStateModelGenerator.registerSimpleCubeAll(SarcousBlocks.TEST_DEPOSIT);
        blockStateModelGenerator.registerSimpleCubeAll(SarcousBlocks.STOCKPILE);
    }

    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(SarcousItems.COLLECTORS_CARVER, Models.HANDHELD);
    }
}
