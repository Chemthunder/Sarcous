package org.chemthunder.sarcous.api.block;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author Chemthunder
 */
public interface PonderedBlock {
    List<Component> getPonderedText(BlockState state, BlockPos pos, Level world, Player viewer);
}
