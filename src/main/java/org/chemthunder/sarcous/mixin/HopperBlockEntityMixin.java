package org.chemthunder.sarcous.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.block.entity.HopperBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author Chemthunder
 */
@Mixin(value = HopperBlockEntity.class)
public abstract class HopperBlockEntityMixin {

    @WrapOperation(method = "serverTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/entity/HopperBlockEntity;needsCooldown()Z"))
    private static boolean overclock(HopperBlockEntity instance, Operation<Boolean> original) {

        return false;
    }
}
