package org.chemthunder.sarcous.core.index;

import net.acoyt.acornlib.api.registrants.BlockRegistrant;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.chemthunder.sarcous.core.Sarcous;
import org.chemthunder.sarcous.core.block.*;
import org.chemthunder.sarcous.core.infrastructure.EnergyTier;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * @author Chemthunder
 */
public interface SarcousBlocks {
    BlockRegistrant plugin = new BlockRegistrant(Sarcous.MOD_ID);

    Map<EnergyTier, Block> STOCKPILES = registerTiered("stockpile", StockpileBlock::new);
    Map<EnergyTier, Block> DRILLS = registerTiered("drill", DrillBlock::new);

    Block TEST_MACHINE = plugin.registerWithItem(
            "test_machine",
            TestMachineBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)
    );

    Block TEST_DEPOSIT = plugin.registerWithItem(
            "test_deposit",
            TestDepositBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.RAW_IRON_BLOCK)
    );

    Block GEOTHERMIC_GENERATOR = plugin.registerWithItem("geothermic_generator",
            GeneratorBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)
    );

    Block ASHTRAY = plugin.register("ashtray",
            AshtrayBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.ACACIA_TRAPDOOR)
                    .sound(SoundType.GLASS)
                    .instabreak()
    );

    Block AIR_PURIFIER = plugin.registerWithItem("air_purifier",
            AirPurifierBlock::new,
            BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)
                    .sound(SoundType.IRON)
                    .randomTicks()
    );

    static void init() {}

    @Environment(EnvType.CLIENT)
    static void clinit() {
        BlockRenderLayerMap.putBlock(ASHTRAY, ChunkSectionLayer.TRANSLUCENT);
        BlockRenderLayerMap.putBlock(ASHTRAY, ChunkSectionLayer.CUTOUT);
    }

    static Map<EnergyTier, Block> registerTiered(String name, BiFunction<BlockBehaviour.Properties, EnergyTier, Block> factory) {
        Map<EnergyTier, Block> list = new HashMap<>();
        for (EnergyTier tier : Arrays.stream(EnergyTier.values()).filter((tier -> !Sarcous.BLACKLISTED_TIERS.contains(tier))).toList()) {
            Block bl = plugin.registerWithItem(
                    tier.getSerializedName().toLowerCase() + "_" + name,
                    settings -> factory.apply(settings, tier),
                    BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)
            );
            list.put(tier, bl);
        }
        return list;
    }
}
