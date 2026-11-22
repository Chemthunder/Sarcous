package org.chemthunder.sarcous.api.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author Chemthunder
 */
public interface DrillableBlock {
    SoundEvent getDrillSound(BlockState state, BlockPos pos, Level world);

    void onDrilled(BlockState state, BlockPos pos, Level world);
}
