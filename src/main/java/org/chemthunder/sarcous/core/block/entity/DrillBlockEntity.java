package org.chemthunder.sarcous.core.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.chemthunder.sarcous.api.block.DrillableBlock;
import org.chemthunder.sarcous.api.block.entity.PoweredBlockEntity;
import org.chemthunder.sarcous.core.infrastructure.EnergyTier;
import org.chemthunder.sarcous.core.index.SarcousBlockEntityTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chemthunder
 */
public class DrillBlockEntity extends PoweredBlockEntity {
    private static final int DELAY_BETWEEN_STRIKES = 20;
    private int delay = DELAY_BETWEEN_STRIKES;

    public DrillBlockEntity(BlockPos pos, BlockState state) {
        super(SarcousBlockEntityTypes.DRILL, pos, state);
    }

    public void tick(BlockState state, BlockPos pos, World world, DrillBlockEntity entity) {
        if (state.contains(EnergyTier.PROPERTY)) {
            EnergyTier tier = state.get(EnergyTier.PROPERTY);
            if (this.isPowered()) {
                if (tier == EnergyTier.COALBURNER) {
                    if (world.getBlockState(pos.up()).isTransparent()) {
                        world.addParticleClient(
                                ParticleTypes.LARGE_SMOKE,
                                pos.toCenterPos().getX(),
                                pos.toCenterPos().getY(),
                                pos.toCenterPos().getZ(),
                                0,
                                0.06F,
                                0
                        );
                    }
                }

                if (delay > 0) {
                    delay--;
                    if (delay == 0) {
                        for (BlockPos t : getTargetedPositions(pos)) {
                            BlockState tState = world.getBlockState(t);

                            if (tState.getBlock() instanceof DrillableBlock drillable) {
                                drillable.onDrilled(tState, t, world);

                                world.playSound(
                                        null,
                                        pos.getX(),
                                        pos.getY(),
                                        pos.getZ(),
                                        drillable.getDrillSound(state, pos, world),
                                        SoundCategory.BLOCKS,
                                        1,
                                        1
                                );

                                world.addBlockBreakParticles(t, tState);
                            }
                        }
                        delay = DELAY_BETWEEN_STRIKES;
                        this.markDirty();
                    }
                }
            }
        }
    }

    private static List<BlockPos> getTargetedPositions(BlockPos center) {
        List<BlockPos> list = new ArrayList<>();
        list.add(center.up());
        list.add(center.down());
        list.add(center.east());
        list.add(center.north());
        list.add(center.south());
        list.add(center.west());
        return list;
    }

    protected void readData(ReadView view) {
        delay = view.getInt("Delay", DELAY_BETWEEN_STRIKES);
    }

    protected void writeData(WriteView view) {
        view.putInt("Delay", delay);
    }
}
