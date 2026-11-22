package org.chemthunder.sarcous.mixin.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.HumanoidArm;
import org.chemthunder.sarcous.core.index.SarcousItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @author Chemthunder
 */
@Mixin(value = AvatarRenderer.class)
public abstract class AvatarRendererMixin {

    @Inject(method = "getArmPose(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/world/entity/HumanoidArm;)Lnet/minecraft/client/model/HumanoidModel$ArmPose;", at = @At(value = "HEAD"), cancellable = true)
    private static void sarcous$smokeThatShit(Avatar avatar, HumanoidArm humanoidArm, CallbackInfoReturnable<HumanoidModel.ArmPose> cir) {
        if (avatar.getActiveItem().is(SarcousItems.ROLLIE)) {
            if (avatar.isUsingItem()) {
                cir.setReturnValue(HumanoidModel.ArmPose.TOOT_HORN);
            }
        }
    }
}
