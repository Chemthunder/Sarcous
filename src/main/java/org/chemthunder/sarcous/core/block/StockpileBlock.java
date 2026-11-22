package org.chemthunder.sarcous.core.block;

import com.mojang.serialization.MapCodec;
import org.chemthunder.sarcous.core.block.entity.StockpileBlockEntity;
import org.chemthunder.sarcous.api.block.PonderedBlock;
import org.chemthunder.sarcous.core.index.SarcousAttachmentTypes;
import org.chemthunder.sarcous.core.infrastructure.EnergyTier;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
public class StockpileBlock extends BaseEntityBlock implements PonderedBlock {
    private final EnergyTier tier;

    public StockpileBlock(Properties settings, EnergyTier tier) {
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
        return simpleCodec(settings1 -> new StockpileBlock(settings1, tier));
    }

    @Nullable
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new StockpileBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level w, BlockState s, BlockEntityType<T> t) {
        return ((world, pos, state, blockEntity) -> {
            if (blockEntity instanceof StockpileBlockEntity entity) {
                entity.tick(world, pos, state);
            }
        });
    }

    public List<Component> getPonderedText(BlockState state, BlockPos pos, Level world, Player viewer) {
        if (world.getBlockEntity(pos) instanceof StockpileBlockEntity entity) {
            List<Component> texts = new ArrayList<>();
            if (entity.getAttached(SarcousAttachmentTypes.STOCKPILE_FILTER) != null) {
                ItemStack filter = entity.getAttached(SarcousAttachmentTypes.STOCKPILE_FILTER);
                if (filter != null) {
                    texts.add(Component.empty()
                            .append(Component.literal("Filter: ").withStyle(ChatFormatting.DARK_GRAY))
                            .append(Component.literal(filter.getHoverName().getString()).withStyle(ChatFormatting.RED, ChatFormatting.BOLD))
                    );
                }
            }

            texts.addAll(List.of(
                    Component.literal("Picks up nearby dropped items and"),
                    Component.literal("funnels them into a container placed"),
                    Component.literal("below.")
            ));
            return texts;
        }
        return List.of();
    }
}
