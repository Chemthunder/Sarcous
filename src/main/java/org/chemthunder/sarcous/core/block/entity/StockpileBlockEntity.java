package org.chemthunder.sarcous.core.block.entity;

import org.chemthunder.sarcous.api.block.entity.PoweredBlockEntity;
import org.chemthunder.sarcous.core.index.SarcousAttachmentTypes;
import org.chemthunder.sarcous.core.index.SarcousBlockEntityTypes;

import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

/**
 * @author Chemthunder
 */
public class StockpileBlockEntity extends PoweredBlockEntity {
    public StockpileBlockEntity(BlockPos pos, BlockState state) {
        super(SarcousBlockEntityTypes.STOCKPILE, pos, state);
        this.delayRerouteTime = (3 * 20);
    }

    public void tick(Level world, BlockPos pos, BlockState state) {
        if (this.isPowered()) {
            AABB detect = new AABB(pos).inflate(15);

            this.tickPowerConsumption();

            for (ItemEntity droppedItem : world.getEntitiesOfClass(ItemEntity.class, detect, (item -> true))) {
                if (droppedItem.getBlockX() != pos.getX() && droppedItem.getBlockZ() != pos.getY()) {
                    ItemStack filter = this.getAttached(SarcousAttachmentTypes.STOCKPILE_FILTER);

                    if (world.getBlockEntity(pos.below()) instanceof RandomizableContainerBlockEntity container) {
                        for (int i = 0; i < container.getContainerSize(); i++) {
                            ItemStack slot = container.getItem(i);

                            if (slot.isEmpty()) {
                                if (filter != null) {
                                    if (droppedItem.getItem().is(filter.getItem())) {
                                        absorb(container, world, droppedItem, i);
                                        return;
                                    }
                                }
                                absorb(container, world, droppedItem, i);
                                break;
                            }
                        }
                    } else {
                        droppedItem.setPos(pos.below().getCenter());
                    }
                }
            }
        }
    }

    private static void absorb(RandomizableContainerBlockEntity container, Level world, ItemEntity droppedItem, int slot) {
        container.setItem(slot, droppedItem.getItem());

        world.playSound(
                null,
                droppedItem.blockPosition(),
                SoundEvents.ITEM_PICKUP,
                SoundSource.BLOCKS,
                1,
                new Random().nextFloat(0.5F, 0.8F)
        );

        if (world instanceof ServerLevel serverWorld) {
            droppedItem.kill(serverWorld);
        }
    }
}

