package org.chemthunder.sarcous.datagen.providers.server;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataOutput;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
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
public class SarcousDrillOutputProvider extends FabricCodecDataProvider<DrillOutput> {
    public SarcousDrillOutputProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(dataOutput, registriesFuture, DataOutput.OutputType.DATA_PACK, DrillOutputCollector.path, DrillOutput.CODEC);
    }

    protected void configure(BiConsumer<Identifier, DrillOutput> provider, RegistryWrapper.WrapperLookup lookup) {
        provider.accept(
                Sarcous.id("test"),
                new DrillOutput(
                        Blocks.GOLD_BLOCK,
                        Items.GOLD_NUGGET,
                        SoundEvents.BLOCK_NETHER_GOLD_ORE_BREAK
                )
        );
    }

    public String getName() {
        return "Drill Outputs";
    }
}
