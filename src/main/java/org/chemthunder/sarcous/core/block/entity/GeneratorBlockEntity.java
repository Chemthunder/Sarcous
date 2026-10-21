package org.chemthunder.sarcous.core.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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

    public void tick(World world, BlockPos pos, BlockState state) {
        if (this.isPowered()) {
            if (world.getTime() % 20 == 0) {
                for (BlockPos t : MachineryUtil.getDirectionalized(pos)) {
                    BlockState targetState = world.getBlockState(t);

                    if (world.getBlockEntity(t) instanceof PoweredBlockEntity power) {
                        if (targetState.contains(EnergyTier.PROPERTY)) {
                            EnergyTier tier = targetState.get(EnergyTier.PROPERTY);

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
