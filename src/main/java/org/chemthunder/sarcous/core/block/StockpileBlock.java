package org.chemthunder.sarcous.core.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.chemthunder.sarcous.core.block.entity.StockpileBlockEntity;
import org.chemthunder.sarcous.api.block.PonderedBlock;
import org.chemthunder.sarcous.core.index.SarcousAttachmentTypes;
import org.chemthunder.sarcous.core.infrastructure.EnergyTier;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chemthunder
 */
public class StockpileBlock extends BlockWithEntity implements PonderedBlock {
    private final EnergyTier tier;

    public StockpileBlock(Settings settings, EnergyTier tier) {
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
        return createCodec(settings1 -> new StockpileBlock(settings1, tier));
    }

    @Nullable
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new StockpileBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World w, BlockState s, BlockEntityType<T> t) {
        return ((world, pos, state, blockEntity) -> {
            if (blockEntity instanceof StockpileBlockEntity entity) {
                entity.tick(world, pos, state);
            }
        });
    }

    public List<Text> getPonderedText(BlockState state, BlockPos pos, World world, PlayerEntity viewer) {
        if (world.getBlockEntity(pos) instanceof StockpileBlockEntity entity) {
            List<Text> texts = new ArrayList<>();
            if (entity.getAttached(SarcousAttachmentTypes.STOCKPILE_FILTER) != null) {
                ItemStack filter = entity.getAttached(SarcousAttachmentTypes.STOCKPILE_FILTER);
                if (filter != null) {
                    texts.add(Text.empty()
                            .append(Text.literal("Filter: ").formatted(Formatting.DARK_GRAY))
                            .append(Text.literal(filter.getName().getString()).formatted(Formatting.RED, Formatting.BOLD))
                    );
                }
            }

            texts.addAll(List.of(
                    Text.literal("Picks up nearby dropped items and"),
                    Text.literal("funnels them into a container placed"),
                    Text.literal("below.")
            ));
            return texts;
        }
        return List.of();
    }
}
