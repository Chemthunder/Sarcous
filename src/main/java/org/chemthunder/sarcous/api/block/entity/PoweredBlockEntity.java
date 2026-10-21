package org.chemthunder.sarcous.api.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.BiConsumer;

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

    public void tick(World world, BlockPos pos, BlockState state) {}

    public void tickPowerConsumption() {
        if (this.delay > 0) {
            this.delay--;
            if (this.delay <= 0) {
                this.delay = this.delayRerouteTime;
                this.fuel--;
                this.markDirty();
            }
        }
    }

    protected void readData(ReadView view) {
        this.fuel = view.getInt("Fuel", 0);
        this.delay = view.getInt("Delay", this.delayRerouteTime);
    }

    protected void writeData(WriteView view) {
        view.putInt("Fuel", this.fuel);
        view.putInt("Delay", this.delay);
    }

    public void setFuel(int amount) {
        this.fuel = amount;
        this.markDirty();
    }

    public int getFuel() {
        return this.fuel;
    }

    public void incrementFuel() {
        this.fuel++;
        this.markDirty();
    }

    public boolean isPowered() {
        return this.fuel > 0;
    }

    public int getDelay() {
        return this.delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
        this.markDirty();
    }
}
