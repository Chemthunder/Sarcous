package org.chemthunder.sarcous.core.index;

import net.acoyt.acornlib.api.registrants.BlockEntityTypeRegistrant;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import org.chemthunder.sarcous.core.Sarcous;
import org.chemthunder.sarcous.core.block.entity.DrillBlockEntity;
import org.chemthunder.sarcous.core.block.entity.GeneratorBlockEntity;
import org.chemthunder.sarcous.core.block.entity.StockpileBlockEntity;
import org.chemthunder.sarcous.core.block.entity.TestMachineBlockEntity;

/**
 * @author Chemthunder
 */
public interface SarcousBlockEntityTypes {
    BlockEntityTypeRegistrant plugin = new BlockEntityTypeRegistrant(Sarcous.MOD_ID);

    BlockEntityType<DrillBlockEntity> DRILL = plugin.register("drill", FabricBlockEntityTypeBuilder.create(
            DrillBlockEntity::new
    ).addBlocks(SarcousBlocks.DRILLS.values()).build());

    BlockEntityType<StockpileBlockEntity> STOCKPILE = plugin.register("stockpile", FabricBlockEntityTypeBuilder.create(
            StockpileBlockEntity::new
    ).addBlocks(SarcousBlocks.STOCKPILES.values()).build());


    //
    BlockEntityType<TestMachineBlockEntity> TEST_MACHINE = plugin.register("test_machine", FabricBlockEntityTypeBuilder.create(
            TestMachineBlockEntity::new,
            SarcousBlocks.TEST_MACHINE
    ).build());

    BlockEntityType<GeneratorBlockEntity> GENERATOR = plugin.register("generator", FabricBlockEntityTypeBuilder.create(
            GeneratorBlockEntity::new,
            SarcousBlocks.GEOTHERMIC_GENERATOR
    ).build());

    static void init() {}

    @Environment(EnvType.CLIENT)
    static void clinit() {}
}
