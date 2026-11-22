package org.chemthunder.sarcous.datagen.providers.server.machinery;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.chemthunder.sarcous.api.data.DrillOutput;
import org.chemthunder.sarcous.core.Sarcous;
import org.chemthunder.sarcous.core.infrastructure.data.DrillOutputCollector;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

/**
 * @author Chemthunder
 */
public class DrillOutputProvider extends FabricCodecDataProvider<DrillOutput> {
    public DrillOutputProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(dataOutput, registriesFuture, PackOutput.Target.DATA_PACK, DrillOutputCollector.path, DrillOutput.CODEC);
    }

    protected void configure(BiConsumer<Identifier, DrillOutput> provider, HolderLookup.Provider lookup) {
        offerDrillOutput(provider, Blocks.GOLD_BLOCK, Items.GOLD_NUGGET, SoundEvents.NETHER_GOLD_ORE_BREAK);
    }

    private static void offerDrillOutput(BiConsumer<Identifier, DrillOutput> provider, Block block, Item output, SoundEvent event) {
        provider.accept(
                Sarcous.id(BuiltInRegistries.BLOCK.getKey(block).getPath().toLowerCase()),
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
