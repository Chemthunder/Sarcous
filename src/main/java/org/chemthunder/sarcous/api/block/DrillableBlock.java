package org.chemthunder.sarcous.api.block;

import net.minecraft.block.BlockState;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author Chemthunder
 */
public interface DrillableBlock {
    SoundEvent getDrillSound(BlockState state, BlockPos pos, World world);

    void onDrilled(BlockState state, BlockPos pos, World world);
}
