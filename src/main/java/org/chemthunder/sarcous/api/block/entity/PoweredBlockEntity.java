package org.chemthunder.sarcous.api.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.chemthunder.sarcous.core.Sarcous;
import org.chemthunder.sarcous.core.index.SarcousAttachmentTypes;
import org.chemthunder.sarcous.core.infrastructure.EnergyTier;

import java.util.Random;

/**
 * @author Chemthunder
 */
public abstract class PoweredBlockEntity extends BlockEntity {
    private int fuel = 0;

    protected int delayRerouteTime = 20;
    private int delay = delayRerouteTime;

    public PoweredBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public void tickPowerConsumption() {
        if (this.delay > 0) {
            this.delay--;
            if (this.delay <= 0) {
                this.delay = this.delayRerouteTime;
                this.fuel--;

                if (this.level != null) {
                    if (this.getBlockState().getValue(EnergyTier.PROPERTY) == EnergyTier.COALBURNER) {
                        if (new Random().nextInt(1, 3) == 2) {
                            ChunkAccess chunk = level.getChunk(worldPosition);
                            double pollution = chunk.getAttachedOrCreate(SarcousAttachmentTypes.POLLUTION_LEVEL);
                            chunk.setAttached(SarcousAttachmentTypes.POLLUTION_LEVEL, pollution + 1);
                        }
                    }
                }

                this.setChanged();
            }
        }

        if (this.level != null) {
            BlockState state = level.getBlockState(worldPosition);

            if (state.getValue(EnergyTier.PROPERTY) == EnergyTier.COALBURNER) {
                if (level.getBlockState(worldPosition.above()).propagatesSkylightDown()) {
                    level.addParticle(
                            ParticleTypes.LARGE_SMOKE,
                            worldPosition.getCenter().x(),
                            worldPosition.getCenter().y(),
                            worldPosition.getCenter().z(),
                            0,
                            0.06F,
                            0
                    );
                }
            }
        }
    }

    protected void loadAdditional(ValueInput view) {
        this.fuel = view.getIntOr("Fuel", 0);
        this.delay = view.getIntOr("Delay", this.delayRerouteTime);
    }

    protected void saveAdditional(ValueOutput view) {
        view.putInt("Fuel", this.fuel);
        view.putInt("Delay", this.delay);
    }

    public void setFuel(int amount) {
        this.fuel = amount;
        this.setChanged();
    }

    public int getFuel() {
        return this.fuel;
    }

    public void incrementFuel() {
        this.fuel++;
        this.setChanged();
    }

    public void decrementFuel() {
        this.fuel--;
        this.setChanged();
    }

    public boolean isPowered() {
        return this.fuel > 0;
    }

    public int getDelay() {
        return this.delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
        this.setChanged();
    }
}




