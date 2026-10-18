package org.chemthunder.sarcous.api.block;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chemthunder
 */
public interface PonderedBlock {
    List<Text> getPonderedText(BlockState state, BlockPos pos, World world, PlayerEntity viewer);
}
