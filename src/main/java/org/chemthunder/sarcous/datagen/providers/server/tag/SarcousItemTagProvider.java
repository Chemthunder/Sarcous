package org.chemthunder.sarcous.datagen.providers.server.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.Items;
import org.chemthunder.sarcous.core.index.tag.SarcousItemTags;

import java.util.concurrent.CompletableFuture;

/**
 * @author Chemthunder
 */
public class SarcousItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public SarcousItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    protected void addTags(HolderLookup.Provider registries) {
        this.valueLookupBuilder(SarcousItemTags.GEOTHERMIC_SOURCES)
                .add(Items.LAVA_BUCKET)
                .setReplace(false);
    }
}
