package org.chemthunder.sarcous.core.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.chemthunder.sarcous.core.index.SarcousBlockEntityTypes;

/**
 * @author Chemthunder
 */
public class GeneratorBlockEntity extends BlockEntity {
    public GeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(SarcousBlockEntityTypes.GENERATOR, pos, state);
    }
}
