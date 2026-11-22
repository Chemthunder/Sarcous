package org.chemthunder.sarcous.core.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.chemthunder.sarcous.api.block.entity.PoweredBlockEntity;
import org.chemthunder.sarcous.core.index.SarcousBlockEntityTypes;
import org.chemthunder.sarcous.core.infrastructure.EnergyTier;
import org.chemthunder.sarcous.core.infrastructure.MachineryUtil;

/**
 * @author Chemthunder
 */
public class GeneratorBlockEntity extends PoweredBlockEntity {
    public GeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(SarcousBlockEntityTypes.GENERATOR, pos, state);
    }

    public void tick(Level world, BlockPos pos, BlockState state) {
        if (this.isPowered()) {
            if (world.getGameTime() % 20 == 0) {
                for (BlockPos t : MachineryUtil.getDirectionalized(pos)) {
                    BlockState targetState = world.getBlockState(t);

                    if (world.getBlockEntity(t) instanceof PoweredBlockEntity power) {
                        if (targetState.hasProperty(EnergyTier.PROPERTY)) {
                            EnergyTier tier = targetState.getValue(EnergyTier.PROPERTY);

                            if (tier == EnergyTier.GEOTHERMIC) {
                                power.incrementFuel();
                            }
                        }
                    }
                }
            }
        }
    }
}
