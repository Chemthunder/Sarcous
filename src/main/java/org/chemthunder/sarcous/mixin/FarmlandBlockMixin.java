package org.chemthunder.sarcous.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.FarmBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.chemthunder.sarcous.core.index.SarcousAttachmentTypes;
import org.spongepowered.asm.mixin.Mixin;

/**
 * @author Chemthunder
 */
@Mixin(value = FarmBlock.class)
public abstract class FarmlandBlockMixin {

    @WrapMethod(method = "randomTick")
    private void sarcous$letitdie(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource, Operation<Void> original) {
        if (serverLevel.getChunk(blockPos).getAttachedOrCreate(SarcousAttachmentTypes.POLLUTION_LEVEL) >= 50) {
            if (blockState.getValue(FarmBlock.MOISTURE) > 0) {
                blockState.setValue(FarmBlock.MOISTURE, 0);
            }
            return;
        }
        original.call(blockState, serverLevel, blockPos, randomSource);
    }
}
