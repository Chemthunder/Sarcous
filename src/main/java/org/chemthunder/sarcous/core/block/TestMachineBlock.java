package org.chemthunder.sarcous.core.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.chemthunder.sarcous.core.client.api.PonderedBlock;

import java.util.List;

/**
 * @author Chemthunder
 */
public class TestMachineBlock extends Block implements PonderedBlock {
    public TestMachineBlock(Settings settings) {
        super(settings);
    }

    public List<Text> getPonderedText(BlockState state, BlockPos pos, World world, PlayerEntity viewer) {
        return List.of(
                Text.literal("This is a test message!"),
                Text.literal("Hello World!"),
                Text.literal("dwwwwwwwwwwwwwwwwwwwwwww")
        );
    }
}
