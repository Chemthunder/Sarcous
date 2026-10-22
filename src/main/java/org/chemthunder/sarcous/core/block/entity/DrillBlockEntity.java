package org.chemthunder.sarcous.core.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.chemthunder.sarcous.api.block.DrillableBlock;
import org.chemthunder.sarcous.api.block.entity.PoweredBlockEntity;
import org.chemthunder.sarcous.api.data.DrillOutput;
import org.chemthunder.sarcous.core.infrastructure.EnergyTier;
import org.chemthunder.sarcous.core.index.SarcousBlockEntityTypes;
import org.chemthunder.sarcous.core.infrastructure.data.DrillOutputCollector;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chemthunder
 */
public class DrillBlockEntity extends PoweredBlockEntity {
    private static final int REROUTE = 20;
    private int delay = REROUTE;

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

                            for (DrillOutput output : DrillOutputCollector.getEntries().values()) {
                                if (tState.getBlock() == output.block()) {
                                    ItemScatterer.spawn(
                                            world,
                                            pos.toCenterPos().x,
                                            pos.toCenterPos().y,
                                            pos.toCenterPos().z,
                                            new ItemStack(output.output())
                                    );

                                    world.addBlockBreakParticles(t, tState);

                                    world.playSound(
                                            null,
                                            pos.getX(),
                                            pos.getY(),
                                            pos.getZ(),
                                            output.sound(),
                                            SoundCategory.BLOCKS,
                                            1,
                                            1
                                    );

                                    break;
                                }
                            }

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
                        delay = REROUTE;
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
        delay = view.getInt("Delay", REROUTE);
    }

    protected void writeData(WriteView view) {
        view.putInt("Delay", delay);
    }
}
