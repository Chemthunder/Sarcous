package org.chemthunder.sarcous.core.index;

import net.acoyt.acornlib.api.registrants.BlockEntityTypeRegistrant;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import org.chemthunder.sarcous.core.Sarcous;
import org.chemthunder.sarcous.core.block.entity.DrillBlockEntity;

/**
 * @author Chemthunder
 */
public interface SarcousBlockEntityTypes {
    BlockEntityTypeRegistrant plugin = new BlockEntityTypeRegistrant(Sarcous.MOD_ID);

    BlockEntityType<DrillBlockEntity> DRILL = plugin.register("drill", FabricBlockEntityTypeBuilder.create(
            DrillBlockEntity::new,
            SarcousBlocks.DRILL
    ).build());

    static void init() {}

    @Environment(EnvType.CLIENT)
    static void clinit() {}
}
