package org.chemthunder.sarcous.datagen.providers.client;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.properties.conditional.IsUsingItem;
import net.minecraft.resources.Identifier;
import org.chemthunder.sarcous.core.client.item.RollieBlackeningSource;
import org.chemthunder.sarcous.core.index.SarcousBlocks;
import org.chemthunder.sarcous.core.index.SarcousItems;

/**
 * @author Chemthunder
 */
public class SarcousModelProvider extends FabricModelProvider {
    public SarcousModelProvider(FabricDataOutput output) {
        super(output);
    }

    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        blockStateModelGenerator.createTrivialCube(SarcousBlocks.TEST_MACHINE);
        blockStateModelGenerator.createTrivialCube(SarcousBlocks.TEST_DEPOSIT);

        blockStateModelGenerator.createTrivialBlock(SarcousBlocks.AIR_PURIFIER, TexturedModel.CUBE_TOP_BOTTOM);

        SarcousBlocks.DRILLS.forEach((tier, block) -> blockStateModelGenerator.createTrivialCube(block));
        SarcousBlocks.STOCKPILES.forEach((tier, block) -> blockStateModelGenerator.createTrivialCube(block));
    }

    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        itemModelGenerator.generateFlatItem(SarcousItems.COLLECTORS_CARVER, ModelTemplates.FLAT_HANDHELD_ITEM);

        itemModelGenerator.generateFlatItem(SarcousItems.AMELIORATE, ModelTemplates.FLAT_ITEM);

        itemModelGenerator.generateFlatItem(SarcousItems.ASHTRAY, ModelTemplates.FLAT_ITEM);

        generateRollie(itemModelGenerator);
    }

    private void generateRollie(ItemModelGenerators generators) {
        Identifier loc = ModelLocationUtils.getModelLocation(SarcousItems.ROLLIE);

        generators.itemModelOutput.accept(SarcousItems.ROLLIE,
                ItemModelUtils.tintedModel(
                        ModelTemplates.FLAT_ITEM.create(
                                loc,
                                TextureMapping.layer0(loc),
                                generators.modelOutput
                        ),
                        new RollieBlackeningSource()
                )
        );
    }
}
