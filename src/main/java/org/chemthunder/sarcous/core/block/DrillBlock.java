package org.chemthunder.sarcous.core.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.chemthunder.sarcous.core.infrastructure.EnergyTier;
import org.chemthunder.sarcous.core.block.entity.DrillBlockEntity;
import org.chemthunder.sarcous.api.block.PonderedBlock;
import org.jspecify.annotations.Nullable;

import java.util.List;

/**
 * @author Chemthunder
 */
public class DrillBlock extends BlockWithEntity implements PonderedBlock {
    private final EnergyTier tier;

    public DrillBlock(Settings settings, EnergyTier tier) {
        super(settings);
        this.tier = tier;
        this.setDefaultState(this.getDefaultState()
                .with(EnergyTier.PROPERTY, tier)
        );
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(EnergyTier.PROPERTY);
    }

    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return createCodec(impl -> new DrillBlock(impl, tier));
    }

    @Nullable
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DrillBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World w, BlockState s, BlockEntityType<T> type) {
        return ((world, pos, state, blockEntity) -> {
            if (blockEntity instanceof DrillBlockEntity drill) {
                drill.tick(state, pos, world, drill);
            }
        });
    }

    public List<Text> getPonderedText(BlockState state, BlockPos pos, World world, PlayerEntity viewer) {
        return List.of(
                Text.literal("Gradually mines nearby blocks,"),
                Text.literal("extracting their resources among"),
                Text.literal("other uses.")
        );
    }
}
