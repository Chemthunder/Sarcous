package org.chemthunder.sarcous.core.event;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.chemthunder.sarcous.api.block.entity.PoweredBlockEntity;
import org.chemthunder.sarcous.api.data.ItemInput;
import org.chemthunder.sarcous.core.index.SarcousBlocks;
import org.chemthunder.sarcous.core.infrastructure.EnergyTier;
import org.chemthunder.sarcous.core.block.entity.StockpileBlockEntity;
import org.chemthunder.sarcous.core.index.SarcousAttachmentTypes;
import org.chemthunder.sarcous.core.infrastructure.data.ItemInputCollector;

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
        public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
            BlockPos pos = hitResult.getBlockPos();
            ItemStack stack = player.getStackInHand(hand);

            if (player.isSneaking() && hand == Hand.MAIN_HAND) {
                if (world.getBlockEntity(pos) instanceof StockpileBlockEntity stockpile) {
                    ItemStack filter = stockpile.getAttached(SarcousAttachmentTypes.STOCKPILE_FILTER);

                    if (stack.isEmpty()) {
                        if (filter != null) { // remove filtered item
                            player.equipStack(hand.getEquipmentSlot(), filter);
                            stockpile.setAttached(SarcousAttachmentTypes.STOCKPILE_FILTER, null);
                            playBlockSound(
                                    SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE,
                                    world,
                                    pos,
                                    1
                            );
                            return ActionResult.SUCCESS;
                        }
                    } else {
                        if (filter == null) { // input filter
                            stockpile.setAttached(SarcousAttachmentTypes.STOCKPILE_FILTER, stack.split(1));
                            playBlockSound(
                                    SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN,
                                    world,
                                    pos,
                                    1
                            );
                            return ActionResult.SUCCESS;
                        }
                    }
                }
            }
            return ActionResult.PASS;
        }
    }

    public static class InputFuel implements UseBlockCallback {
        public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
            BlockPos pos = hitResult.getBlockPos();
            ItemStack stack = player.getStackInHand(hand);

            if (world.getBlockEntity(pos) instanceof PoweredBlockEntity power) {
                BlockState state = world.getBlockState(pos);

                if (state.contains(EnergyTier.PROPERTY)) {
                    EnergyTier tier = state.get(EnergyTier.PROPERTY);

                    for (ItemInput compat : ItemInputCollector.getEntries().values()) {
                        if (tier == compat.tier()) {
                            if (compat.ingredient() == stack.getItem()) {
                                for (int i = 0; i < compat.value(); i++) {
                                    power.incrementFuel();
                                }
                                stack.split(1);
                                playBlockSound(
                                        SoundEvents.BLOCK_IRON_DOOR_CLOSE,
                                        world,
                                        pos,
                                        1
                                );
                                return ActionResult.SUCCESS;
                            }
                        }
                    }

                    if (tier == EnergyTier.COALBURNER) {
                        if (stack.isOf(Items.COAL) || stack.isOf(Items.CHARCOAL)) {
                            power.incrementFuel();
                            player.swingHand(hand);
                            stack.split(1);
                            playBlockSound(
                                    SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE,
                                    world,
                                    pos,
                                    0.5F
                            );
                            playBlockSound(
                                    SoundEvents.BLOCK_IRON_DOOR_CLOSE,
                                    world,
                                    pos,
                                    1
                            );
                            return ActionResult.SUCCESS;
                        }

                        if (stack.isOf(Items.COAL_BLOCK)) {
                            for (int i = 0; i < 9; i++) {
                                power.incrementFuel();
                            }

                            player.swingHand(hand);
                            stack.split(1);
                            playBlockSound(
                                    SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE,
                                    world,
                                    pos,
                                    0.5F
                            );
                            playBlockSound(
                                    SoundEvents.BLOCK_IRON_DOOR_CLOSE,
                                    world,
                                    pos,
                                    1
                            );
                            return ActionResult.SUCCESS;
                        }
                    } else if (tier == EnergyTier.BILEFUEL) {
                        if (stack.isOf(Items.ROTTEN_FLESH)) {
                            power.incrementFuel();
                            player.swingHand(hand);
                            stack.split(1);
                            playBlockSound(
                                    SoundEvents.ENTITY_GENERIC_EAT.value(),
                                    world,
                                    pos,
                                    1
                            );
                            return ActionResult.SUCCESS;
                        }
                    }
                } else {
                    if (state.isOf(SarcousBlocks.GEOTHERMIC_GENERATOR)) {
                        if (stack.isOf(Items.LAVA_BUCKET)) {
                            power.incrementFuel();
                            player.swingHand(hand);
                            stack.split(1);
                            player.giveItemStack(new ItemStack(Items.BUCKET));
                            playBlockSound(
                                    SoundEvents.ITEM_BUCKET_EMPTY_LAVA,
                                    world,
                                    pos,
                                    1
                            );
                            return ActionResult.SUCCESS;
                        }
                    }

                    if (stack.isOf(Items.ROTTEN_FLESH)) {
                        power.incrementFuel();
                        player.swingHand(hand);
                        stack.split(1);
                        playBlockSound(
                                SoundEvents.ENTITY_GENERIC_EAT.value(),
                                world,
                                pos,
                                1
                        );
                        return ActionResult.SUCCESS;
                    }
                }
            }
            return ActionResult.PASS;
        }
    }

    private static void playBlockSound(SoundEvent event, World world, BlockPos pos, float volume) {
        world.playSound(
                null,
                pos.toCenterPos().x,
                pos.toCenterPos().y,
                pos.toCenterPos().z,
                event,
                SoundCategory.BLOCKS,
                volume,
                new Random().nextFloat(0.3F, 0.6F)
        );
    }
}
