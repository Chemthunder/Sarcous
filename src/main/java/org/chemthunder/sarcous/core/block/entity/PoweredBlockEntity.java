package org.chemthunder.sarcous.core.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.math.BlockPos;

/**
 * @author Chemthunder
 */
public class PoweredBlockEntity extends BlockEntity {
    private int fuel = 0;

    public PoweredBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    protected void readData(ReadView view) {
        fuel = view.getInt("Fuel", 0);
    }

    protected void writeData(WriteView view) {
        view.putInt("Fuel", fuel);
    }

    public void setFuel(int amount) {
        fuel = amount;
        this.markDirty();
    }

    public int getFuel() {
        return this.fuel;
    }
}
