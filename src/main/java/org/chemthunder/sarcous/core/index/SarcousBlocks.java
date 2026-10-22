package org.chemthunder.sarcous.core.index;

import net.acoyt.acornlib.api.registrants.BlockRegistrant;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import org.chemthunder.sarcous.core.Sarcous;
import org.chemthunder.sarcous.core.block.*;
import org.chemthunder.sarcous.core.infrastructure.EnergyTier;

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
            AbstractBlock.Settings.copy(Blocks.BEDROCK)
    );

    Block TEST_DEPOSIT = plugin.registerWithItem(
            "test_deposit",
            TestDepositBlock::new,
            AbstractBlock.Settings.copy(Blocks.RAW_IRON_BLOCK)
    );

    Block GEOTHERMIC_GENERATOR = plugin.registerWithItem("geothermic_generator",
            GeneratorBlock::new,
            AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)
    );

    static void init() {}

    static Map<EnergyTier, Block> registerTiered(String name, BiFunction<AbstractBlock.Settings, EnergyTier, Block> factory) {
        Map<EnergyTier, Block> list = new HashMap<>();
        for (EnergyTier tier : EnergyTier.values()) {
            Block bl = plugin.registerWithItem(
                    tier.asString().toLowerCase() + "_" + name,
                    settings -> factory.apply(settings, tier),
                    AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)
            );
            list.put(tier, bl);
        }
        return list;
    }
}
