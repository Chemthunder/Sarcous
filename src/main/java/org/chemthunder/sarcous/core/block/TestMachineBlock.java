package org.chemthunder.sarcous.core.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.chemthunder.sarcous.core.block.entity.TestMachineBlockEntity;
import org.chemthunder.sarcous.api.block.PonderedBlock;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chemthunder
 */
public class TestMachineBlock extends BlockWithEntity implements PonderedBlock {
    public TestMachineBlock(Settings settings) {
        super(settings);
    }

    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(TestMachineBlock::new);
    }

    public List<Text> getPonderedText(BlockState state, BlockPos pos, World world, PlayerEntity viewer) {
        if (world.getBlockEntity(pos) instanceof TestMachineBlockEntity entity) {
            return List.of(
                    Text.literal("This is a test message!"),
                    Text.literal("Hello World!")
            );
        }
        return new ArrayList<>();
    }

    @Nullable
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new TestMachineBlockEntity(pos, state);
    }
}
