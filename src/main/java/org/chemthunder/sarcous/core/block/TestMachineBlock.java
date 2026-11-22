package org.chemthunder.sarcous.core.block;

import com.mojang.serialization.MapCodec;
import org.chemthunder.sarcous.core.block.entity.TestMachineBlockEntity;
import org.chemthunder.sarcous.api.block.PonderedBlock;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * @author Chemthunder
 */
public class TestMachineBlock extends BaseEntityBlock implements PonderedBlock {
    public TestMachineBlock(Properties settings) {
        super(settings);
    }

    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(TestMachineBlock::new);
    }

    public List<Component> getPonderedText(BlockState state, BlockPos pos, Level world, Player viewer) {
        if (world.getBlockEntity(pos) instanceof TestMachineBlockEntity entity) {
            return List.of(
                    Component.literal("This is a test message!"),
                    Component.literal("Hello World!")
            );
        }
        return new ArrayList<>();
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TestMachineBlockEntity(pos, state);
    }
}
