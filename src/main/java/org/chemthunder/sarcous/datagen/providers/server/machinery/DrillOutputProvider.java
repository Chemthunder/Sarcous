package org.chemthunder.sarcous.datagen.providers.server.machinery;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataOutput;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.chemthunder.sarcous.api.data.DrillOutput;
import org.chemthunder.sarcous.core.Sarcous;
import org.chemthunder.sarcous.core.infrastructure.data.DrillOutputCollector;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

/**
 * @author Chemthunder
 */
public class DrillOutputProvider extends FabricCodecDataProvider<DrillOutput> {
    public DrillOutputProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(dataOutput, registriesFuture, DataOutput.OutputType.DATA_PACK, DrillOutputCollector.path, DrillOutput.CODEC);
    }

    protected void configure(BiConsumer<Identifier, DrillOutput> provider, RegistryWrapper.WrapperLookup lookup) {
        offerDrillOutput(provider, Blocks.GOLD_BLOCK, Items.GOLD_NUGGET, SoundEvents.BLOCK_NETHER_GOLD_ORE_BREAK);
    }

    private static void offerDrillOutput(BiConsumer<Identifier, DrillOutput> provider, Block block, Item output, SoundEvent event) {
        provider.accept(
                Sarcous.id(Registries.BLOCK.getId(block).getPath().toLowerCase()),
                new DrillOutput(
                        block,
                        output,
                        event
                )
        );
    }

    public String getName() {
        return "Drill Outputs";
    }
}
