package org.chemthunder.sarcous.core.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.chemthunder.sarcous.api.block.entity.PoweredBlockEntity;
import org.chemthunder.sarcous.core.index.SarcousAttachmentTypes;
import org.chemthunder.sarcous.core.index.SarcousBlockEntityTypes;

import java.util.Random;

/**
 * @author Chemthunder
 */
public class StockpileBlockEntity extends PoweredBlockEntity {
    public StockpileBlockEntity(BlockPos pos, BlockState state) {
        super(SarcousBlockEntityTypes.STOCKPILE, pos, state);
        this.delayRerouteTime = 80;
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (this.isPowered()) {
            Box detect = new Box(pos).expand(15);

            for (ItemEntity droppedItem : world.getEntitiesByClass(ItemEntity.class, detect, (item -> true))) {
                if (true) {
                    if (droppedItem.getBlockX() != pos.getX() && droppedItem.getBlockZ() != pos.getY()) {
                        ItemStack filter = this.getAttached(SarcousAttachmentTypes.STOCKPILE_FILTER);

                        if (world.getBlockEntity(pos.down()) instanceof LootableContainerBlockEntity container) {
                            for (int i = 0; i < container.size(); i++) {
                                ItemStack slot = container.getStack(i);

                                if (slot.isEmpty()) {
                                    if (filter != null) {
                                        if (droppedItem.getStack().isOf(filter.getItem())) {
                                            absorb(container, world, droppedItem, i);
                                            return;
                                        } else {
                                            break;
                                        }
                                    }
                                    absorb(container, world, droppedItem, i);
                                    break;
                                }
                            }
                        } else {
                            droppedItem.setPosition(pos.down().toCenterPos());
                        }
                    }
                }
            }
        }
    }

    private static void absorb(LootableContainerBlockEntity container, World world, ItemEntity droppedItem, int slot) {
        container.setStack(slot, droppedItem.getStack());

        world.playSound(
                null,
                droppedItem.getBlockPos(),
                SoundEvents.ENTITY_ITEM_PICKUP,
                SoundCategory.BLOCKS,
                1,
                new Random().nextFloat(0.5F, 0.8F)
        );

        if (world instanceof ServerWorld serverWorld) {
            droppedItem.kill(serverWorld);
        }
    }
}
