package org.chemthunder.sarcous.core.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.chemthunder.sarcous.core.cca.entity.ExhaleComponent;
import org.chemthunder.sarcous.core.index.SarcousAttachmentTypes;
import org.chemthunder.sarcous.core.index.SarcousBlocks;
import org.chemthunder.sarcous.core.index.SarcousDataComponentTypes;
import org.chemthunder.sarcous.core.index.SarcousPlayerTriggers;

import java.util.Random;

/**
 * @author Chemthunder
 */
public class RollieItem extends Item {
    public static final int MAX_TIME = 8 * 20;

    public RollieItem(Properties properties) {
        super(properties);
    }

    public InteractionResult use(Level level, Player player, InteractionHand interactionHand) {
        if (player.getActiveItem().getOrDefault(SarcousDataComponentTypes.BLACKNESS, 0) < MAX_TIME) {
            player.startUsingItem(interactionHand);
            player.playSound(SoundEvents.HORSE_BREATHE, 1, 1F);
        }
        return super.use(level, player, interactionHand);
    }

    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack itemStack, int i) {
        super.onUseTick(level, livingEntity, itemStack, i);

        int time = itemStack.getOrDefault(SarcousDataComponentTypes.BLACKNESS, 0);

        if (time < MAX_TIME) {
            itemStack.set(SarcousDataComponentTypes.BLACKNESS, time + 1);
        }
    }

    public boolean releaseUsing(ItemStack itemStack, Level level, LivingEntity livingEntity, int i) {
        livingEntity.getComponent(ExhaleComponent.KEY).setExhaleTime(2 * 20);
        livingEntity.playSound(SoundEvents.CANDLE_EXTINGUISH, 1, 0.39F);

        ChunkAccess chunk = level.getChunk(livingEntity.blockPosition());

        double po = chunk.getAttachedOrCreate(SarcousAttachmentTypes.POLLUTION_LEVEL);

        chunk.setAttached(SarcousAttachmentTypes.POLLUTION_LEVEL, po + 0.02);

        if (itemStack.getOrDefault(SarcousDataComponentTypes.BLACKNESS, 0) >= MAX_TIME) {
            if (livingEntity instanceof ServerPlayer serverPlayer) {
                SarcousPlayerTriggers.FULL_SMOKE.trigger(serverPlayer);
            }
        }
        return super.releaseUsing(itemStack, level, livingEntity, i);
    }

    public InteractionResult useOn(UseOnContext useOnContext) {
        Level level = useOnContext.getLevel();
        BlockPos pos = useOnContext.getClickedPos();
        Player player = useOnContext.getPlayer();

        if (player != null) {
            if (level.getBlockState(pos).is(SarcousBlocks.ASHTRAY)) {
                player.swing(useOnContext.getHand());

                for (int i = 0; i < level.random.nextInt(2, 4); i++) {
                    Random rand = new Random();

                    level.addParticle(
                            ParticleTypes.SMOKE,
                            pos.getCenter().x + rand.nextFloat(-0.5F, 0.5F),
                            pos.getCenter().y,
                            pos.getCenter().z + rand.nextFloat(-0.5F, 0.5F),
                            0,
                            0,
                            0
                    );
                }
                return InteractionResult.SUCCESS;
            }
        }
        return super.useOn(useOnContext);
    }

    public boolean allowComponentsUpdateAnimation(Player player, InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
        return oldStack.getItem() != newStack.getItem();
    }

    public ItemUseAnimation getUseAnimation(ItemStack itemStack) {
        return ItemUseAnimation.TOOT_HORN;
    }
}
