package org.chemthunder.sarcous.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.Hopper;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.chemthunder.sarcous.core.index.SarcousGameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author Chemthunder
 */
@Mixin(value = HopperBlockEntity.class)
public abstract class HopperBlockEntityMixin extends LootableContainerBlockEntity implements Hopper {
    protected HopperBlockEntityMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @WrapOperation(method = "serverTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/HopperBlockEntity;needsCooldown()Z"))
    private static boolean overclock(HopperBlockEntity instance, Operation<Boolean> original) {
        if (instance.getWorld() instanceof ServerWorld serverWorld) {
            if (serverWorld.getGameRules().getValue(SarcousGameRules.OVERCLOCK_HOPPERS)) {
                return false;
            }
        }
        return original.call(instance);
    }
}
