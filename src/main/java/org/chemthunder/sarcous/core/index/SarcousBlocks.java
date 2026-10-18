package org.chemthunder.sarcous.core.index;

import net.acoyt.acornlib.api.registrants.BlockRegistrant;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import org.chemthunder.sarcous.core.Sarcous;
import org.chemthunder.sarcous.core.block.DrillBlock;
import org.chemthunder.sarcous.core.block.StockpileBlock;
import org.chemthunder.sarcous.core.block.TestDepositBlock;
import org.chemthunder.sarcous.core.block.TestMachineBlock;

/**
 * @author Chemthunder
 */
public interface SarcousBlocks {
    BlockRegistrant plugin = new BlockRegistrant(Sarcous.MOD_ID);

    Block TEST_MACHINE = plugin.registerWithItem("test_machine", TestMachineBlock::new, AbstractBlock.Settings.copy(Blocks.BEDROCK)
    );

    Block TEST_DEPOSIT = plugin.registerWithItem("test_deposit", TestDepositBlock::new, AbstractBlock.Settings.copy(Blocks.RAW_IRON_BLOCK)
    );

    Block DRILL = plugin.registerWithItem("drill", DrillBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)
    );

    Block STOCKPILE = plugin.registerWithItem("stockpile", StockpileBlock::new, AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)
    );

    static void init() {}
}
