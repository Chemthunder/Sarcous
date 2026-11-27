package org.chemthunder.sarcous.core.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.chemthunder.sarcous.api.block.PonderedBlock;
import org.chemthunder.sarcous.core.block.entity.AirPurifierBlockEntity;
import org.jspecify.annotations.Nullable;

import java.util.List;

/**
 * @author Chemthunder
 */
public class AirPurifierBlock extends BaseEntityBlock implements PonderedBlock {
    public AirPurifierBlock(Properties properties) {
        super(properties);
    }

    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(AirPurifierBlock::new);
    }

    protected RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new AirPurifierBlockEntity(blockPos, blockState);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level w, BlockState s, BlockEntityType<T> type) {
        return ((world, pos, state, blockEntity) -> {
            if (blockEntity instanceof AirPurifierBlockEntity b) {
                b.tick(world, pos, state);
            }
        });
    }

    public List<Component> getPonderedText(BlockState state, BlockPos pos, Level world, Player viewer) {
        return List.of(
                Component.literal("Cleans the hosted chunk of pollution."),
                Component.literal("Works in conjunction with other purifiers.")
        );
    }
}
