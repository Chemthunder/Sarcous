package org.chemthunder.sarcous.core.block;

import com.mojang.serialization.MapCodec;
import org.chemthunder.sarcous.core.infrastructure.EnergyTier;
import org.chemthunder.sarcous.core.block.entity.DrillBlockEntity;
import org.chemthunder.sarcous.api.block.PonderedBlock;
import org.jspecify.annotations.Nullable;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

/**
 * @author Chemthunder
 */
public class DrillBlock extends BaseEntityBlock implements PonderedBlock {
    private final EnergyTier tier;

    public DrillBlock(Properties settings, EnergyTier tier) {
        super(settings);
        this.tier = tier;
        this.registerDefaultState(this.defaultBlockState()
                .setValue(EnergyTier.PROPERTY, tier)
        );
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(EnergyTier.PROPERTY);
    }

    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(impl -> new DrillBlock(impl, tier));
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DrillBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level w, BlockState s, BlockEntityType<T> type) {
        return ((world, pos, state, blockEntity) -> {
            if (blockEntity instanceof DrillBlockEntity drill) {
                drill.tick(state, pos, world, drill);
            }
        });
    }

    public List<Component> getPonderedText(BlockState state, BlockPos pos, Level world, Player viewer) {
        return List.of(
                Component.literal("Gradually mines nearby blocks,"),
                Component.literal("extracting their resources among"),
                Component.literal("other uses.")
        );
    }
}
