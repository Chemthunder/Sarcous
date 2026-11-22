package org.chemthunder.sarcous.datagen.providers.server.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.chemthunder.sarcous.core.index.tag.SarcousBlockEntityTypeTags;

import java.util.concurrent.CompletableFuture;

/**
 * @author Chemthunder
 */
public class SarcousBlockEntityTypeTagProvider extends FabricTagProvider.BlockEntityTypeTagProvider {
    public SarcousBlockEntityTypeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    protected void addTags(HolderLookup.Provider registries) {
        this.valueLookupBuilder(SarcousBlockEntityTypeTags.ABLE_TO_BE_OVERCLOCKED)
                .add(BlockEntityType.HOPPER)
                .setReplace(false);
    }
}
