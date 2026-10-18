package org.chemthunder.sarcous.core.index;

import net.acoyt.acornlib.api.registrants.BlockEntityTypeRegistrant;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import org.chemthunder.sarcous.core.Sarcous;
import org.chemthunder.sarcous.core.block.entity.DrillBlockEntity;
import org.chemthunder.sarcous.core.block.entity.StockpileBlockEntity;
import org.chemthunder.sarcous.core.block.entity.TestMachineBlockEntity;

/**
 * @author Chemthunder
 */
public interface SarcousBlockEntityTypes {
    BlockEntityTypeRegistrant plugin = new BlockEntityTypeRegistrant(Sarcous.MOD_ID);

    BlockEntityType<DrillBlockEntity> DRILL = plugin.register("drill", FabricBlockEntityTypeBuilder.create(
            DrillBlockEntity::new,
            SarcousBlocks.DRILL
    ).build());

    BlockEntityType<TestMachineBlockEntity> TEST_MACHINE = plugin.register("test_machine", FabricBlockEntityTypeBuilder.create(
            TestMachineBlockEntity::new,
            SarcousBlocks.TEST_MACHINE
    ).build());

    BlockEntityType<StockpileBlockEntity> STOCKPILE = plugin.register("stockpile", FabricBlockEntityTypeBuilder.create(
            StockpileBlockEntity::new,
            SarcousBlocks.STOCKPILE
    ).build());

    static void init() {}

    @Environment(EnvType.CLIENT)
    static void clinit() {}
}
