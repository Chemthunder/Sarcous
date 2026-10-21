package org.chemthunder.sarcous.core.index;

import net.acoyt.acornlib.api.registrants.BlockRegistrant;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import org.chemthunder.sarcous.api.energy.EnergyTier;
import org.chemthunder.sarcous.core.Sarcous;
import org.chemthunder.sarcous.core.block.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chemthunder
 */
public interface SarcousBlocks {
    BlockRegistrant plugin = new BlockRegistrant(Sarcous.MOD_ID);

    List<Block> DRILL_TIERS = new ArrayList<>();

    Block TEST_MACHINE = plugin.registerWithItem("test_machine", TestMachineBlock::new, AbstractBlock.Settings.copy(Blocks.BEDROCK)
    );

    Block TEST_DEPOSIT = plugin.registerWithItem("test_deposit", TestDepositBlock::new, AbstractBlock.Settings.copy(Blocks.RAW_IRON_BLOCK)
    );

    Block GEOTHERMIC_GENERATOR = plugin.registerWithItem("geothermic_generator",
            GeneratorBlock::new,
            AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)
    );

    // TIERED
    Block DRILL_COALBURNER = plugin.registerWithItem(
            "coalburning_drill",
            settings -> new DrillBlock(settings, EnergyTier.COALBURNER),
            AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)
    );

    Block DRILL_GEOTHERMIC = plugin.registerWithItem(
            "geothermic_drill",
            settings -> new DrillBlock(settings, EnergyTier.GEOTHERMIC),
            AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)
    );

    Block DRILL_BILEFUELED = plugin.registerWithItem(
            "bilefueled_drill",
            settings -> new DrillBlock(settings, EnergyTier.BILEFUEL),
            AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)
    );

    Block STOCKPILE = plugin.registerWithItem("stockpile", StockpileBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)
    );

    static void init() {
        for (Block block : plugin.toRegister) {
            if (block instanceof DrillBlock) {
                DRILL_TIERS.add(block);
            }
        }
    }
}
