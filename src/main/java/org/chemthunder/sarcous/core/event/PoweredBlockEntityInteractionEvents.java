package org.chemthunder.sarcous.core.event;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.chemthunder.sarcous.api.block.entity.PoweredBlockEntity;
import org.chemthunder.sarcous.api.data.FuelInput;
import org.chemthunder.sarcous.core.block.entity.StockpileBlockEntity;
import org.chemthunder.sarcous.core.index.SarcousAttachmentTypes;
import org.chemthunder.sarcous.core.index.SarcousBlocks;
import org.chemthunder.sarcous.core.infrastructure.EnergyTier;
import org.chemthunder.sarcous.core.infrastructure.data.FuelInputCollector;

import java.util.Random;

/**
 * @author Chemthunder
 */
public class PoweredBlockEntityInteractionEvents {
    public static void init() {
        UseBlockCallback.EVENT.register(new InputFuel());
        UseBlockCallback.EVENT.register(new FilterStockpile());
    }

    public static class FilterStockpile implements UseBlockCallback {
        public InteractionResult interact(Player player, Level world, InteractionHand hand, BlockHitResult hitResult) {
            BlockPos pos = hitResult.getBlockPos();
            ItemStack stack = player.getItemInHand(hand);

            if (player.isShiftKeyDown() && hand == InteractionHand.MAIN_HAND) {
                if (world.getBlockEntity(pos) instanceof StockpileBlockEntity stockpile) {
                    ItemStack filter = stockpile.getAttached(SarcousAttachmentTypes.STOCKPILE_FILTER);

                    if (stack.isEmpty()) {
                        if (filter != null) { // remove filtered item
                            player.setItemSlot(hand.asEquipmentSlot(), filter);
                            stockpile.setAttached(SarcousAttachmentTypes.STOCKPILE_FILTER, null);
                            playBlockSound(
                                    SoundEvents.IRON_TRAPDOOR_CLOSE,
                                    world,
                                    pos
                            );
                            return InteractionResult.SUCCESS;
                        }
                    } else {
                        if (filter == null) { // input filter
                            stockpile.setAttached(SarcousAttachmentTypes.STOCKPILE_FILTER, stack.split(1));
                            playBlockSound(
                                    SoundEvents.IRON_TRAPDOOR_OPEN,
                                    world,
                                    pos
                            );
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }
            return InteractionResult.PASS;
        }
    }

    public static class InputFuel implements UseBlockCallback {
        public InteractionResult interact(Player player, Level world, InteractionHand hand, BlockHitResult hitResult) {
            BlockPos pos = hitResult.getBlockPos();
            ItemStack stack = player.getItemInHand(hand);

            if (world.getBlockEntity(pos) instanceof PoweredBlockEntity power) {
                BlockState state = world.getBlockState(pos);

                if (state.hasProperty(EnergyTier.PROPERTY)) {
                    EnergyTier tier = state.getValue(EnergyTier.PROPERTY);

                    for (FuelInput compat : FuelInputCollector.LOADED_DATA.values()) {
                        if (tier == compat.tier()) {
                            if (compat.ingredient() == stack.getItem()) {
                                for (int i = 0; i < compat.value(); i++) {
                                    power.incrementFuel();
                                }
                                stack.split(1);
                                playBlockSound(
                                        SoundEvents.IRON_DOOR_CLOSE,
                                        world,
                                        pos
                                );
                                playBlockSound(
                                        compat.event(),
                                        world,
                                        pos
                                );
                                return InteractionResult.SUCCESS;
                            }
                        }
                    }
                } else {
                    if (state.is(SarcousBlocks.GEOTHERMIC_GENERATOR)) {
                        if (stack.is(Items.LAVA_BUCKET)) {
                            power.incrementFuel();
                            player.swing(hand);
                            stack.split(1);
                            player.addItem(new ItemStack(Items.BUCKET));
                            playBlockSound(
                                    SoundEvents.BUCKET_EMPTY_LAVA,
                                    world,
                                    pos
                            );
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }
            return InteractionResult.PASS;
        }
    }

    private static void playBlockSound(SoundEvent event, Level world, BlockPos pos) {
        world.playSound(
                null,
                pos.getCenter().x,
                pos.getCenter().y,
                pos.getCenter().z,
                event,
                SoundSource.BLOCKS,
                (float) 1,
                new Random().nextFloat(0.3F, 0.6F)
        );
    }
}
