package org.chemthunder.sarcous.datagen.providers.server.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import org.chemthunder.sarcous.core.index.tag.SarcousItemTags;

import java.util.concurrent.CompletableFuture;

/**
 * @author Chemthunder
 */
public class SarcousItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public SarcousItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    protected void configure(RegistryWrapper.WrapperLookup registries) {
        this.valueLookupBuilder(SarcousItemTags.GEOTHERMIC_SOURCES)
                .add(Items.LAVA_BUCKET)
                .setReplace(false);
    }
}
