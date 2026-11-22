package org.chemthunder.sarcous.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import org.chemthunder.sarcous.core.index.SarcousAttachmentTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author Chemthunder
 */
@Mixin(value = CropBlock.class)
public abstract class CropBlockMixin {
//    @WrapOperation(
//            method = "randomTick",
//            at = @At(
//                    value = "INVOKE",
//                    target = "Lnet/minecraft/world/level/block/CropBlock;getGrowthSpeed(Lnet/minecraft/world/level/block/Block;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;)F"
//            )
//    )
//    private float sarcous$letitdieliketheloraxsaid(Block block, BlockGetter blockGetter, BlockPos blockPos, Operation<Float> original, @Local(name = "serverLevel", argsOnly = true) ServerLevel serverLevel) {
//        if (serverLevel.getChunk(blockPos).getAttachedOrCreate(SarcousAttachmentTypes.POLLUTION_LEVEL) >= 50) {
//            return 0.0F;
//        }
//        return original.call(block, blockGetter, blockPos);
//    }
}
