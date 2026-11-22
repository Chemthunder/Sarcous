package org.chemthunder.sarcous.core.block.entity;

import org.chemthunder.sarcous.api.block.DrillableBlock;
import org.chemthunder.sarcous.api.block.entity.PoweredBlockEntity;
import org.chemthunder.sarcous.api.data.DrillOutput;
import org.chemthunder.sarcous.core.index.SarcousBlockEntityTypes;
import org.chemthunder.sarcous.core.infrastructure.EnergyTier;
import org.chemthunder.sarcous.core.infrastructure.data.DrillOutputCollector;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

/**
 * @author Chemthunder
 */
public class DrillBlockEntity extends PoweredBlockEntity {
    private static final int REROUTE = 20;
    private int delay = REROUTE;

    public DrillBlockEntity(BlockPos pos, BlockState state) {
        super(SarcousBlockEntityTypes.DRILL, pos, state);
    }

    public void tick(BlockState state, BlockPos pos, Level world, DrillBlockEntity entity) {
        if (state.hasProperty(EnergyTier.PROPERTY)) {
            EnergyTier tier = state.getValue(EnergyTier.PROPERTY);

            if (this.isPowered()) {
                if (tier == EnergyTier.COALBURNER) {
                    if (world.getBlockState(pos.above()).propagatesSkylightDown()) {
                        world.addParticle(
                                ParticleTypes.LARGE_SMOKE,
                                pos.getCenter().x(),
                                pos.getCenter().y(),
                                pos.getCenter().z(),
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

                            for (DrillOutput output : DrillOutputCollector.LOADED_DATA.values()) {
                                if (tState.getBlock() == output.block()) {
                                    Containers.dropItemStack(
                                            world,
                                            t.getCenter().x,
                                            t.getCenter().y,
                                            t.getCenter().z,
                                            new ItemStack(output.output())
                                    );

                                    world.addDestroyBlockEffect(t, tState);

                                    world.playSound(
                                            null,
                                            pos.getX(),
                                            pos.getY(),
                                            pos.getZ(),
                                            output.sound(),
                                            SoundSource.BLOCKS,
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
                                        SoundSource.BLOCKS,
                                        1,
                                        1
                                );

                                world.addDestroyBlockEffect(t, tState);
                            }
                        }
                        delay = REROUTE;
                        this.setChanged();
                    }
                }
            }
        }
    }

    private static List<BlockPos> getTargetedPositions(BlockPos center) {
        List<BlockPos> list = new ArrayList<>();
        list.add(center.above());
        list.add(center.below());
        list.add(center.east());
        list.add(center.north());
        list.add(center.south());
        list.add(center.west());
        return list;
    }

    protected void loadAdditional(ValueInput view) {
        delay = view.getIntOr("Delay", REROUTE);
    }

    protected void saveAdditional(ValueOutput view) {
        view.putInt("Delay", delay);
    }
}
