package org.chemthunder.sarcous.core.block;

import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.chemthunder.sarcous.api.block.PonderedBlock;
import org.chemthunder.sarcous.core.block.entity.GeneratorBlockEntity;
import org.jspecify.annotations.Nullable;

import java.util.List;

/**
 * @author Chemthunder
 */
public class GeneratorBlock extends BaseEntityBlock implements PonderedBlock {
    public GeneratorBlock(Properties settings) {
        super(settings);
    }

    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(GeneratorBlock::new);
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new GeneratorBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return ((world1, pos, state1, blockEntity) -> {
            if (blockEntity instanceof GeneratorBlockEntity generator) {
                generator.tick(world1, pos, state1);
            }
        });
    }

    public List<Component> getPonderedText(BlockState state, BlockPos pos, Level world, Player viewer) {
        return List.of(
                Component.literal("Can be fueled with lava buckets"),
                Component.literal("to power nearby geothermic"),
                Component.literal("machinery.")
        );
    }
}