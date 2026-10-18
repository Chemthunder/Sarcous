package org.chemthunder.sarcous.core.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import org.chemthunder.sarcous.api.block.entity.PoweredBlockEntity;
import org.chemthunder.sarcous.core.index.SarcousBlockEntityTypes;

/**
 * @author Chemthunder
 */
public class TestMachineBlockEntity extends PoweredBlockEntity {
    public TestMachineBlockEntity(BlockPos pos, BlockState state) {
        super(SarcousBlockEntityTypes.TEST_MACHINE, pos, state);
    }
}
