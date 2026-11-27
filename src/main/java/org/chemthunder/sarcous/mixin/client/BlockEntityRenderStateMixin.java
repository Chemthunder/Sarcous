package org.chemthunder.sarcous.mixin.client;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.chemthunder.sarcous.core.SarcousClient;
import org.chemthunder.sarcous.core.index.SarcousAttachmentTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Chemthunder
 */
@Mixin(value = BlockEntityRenderState.class)
public abstract class BlockEntityRenderStateMixin {
    @Inject(method = "extractBase", at = @At(value = "TAIL"))
    private static void sarcous$updateKey(BlockEntity blockEntity, BlockEntityRenderState state, ModelFeatureRenderer.CrumblingOverlay crumblingOverlay, CallbackInfo ci) {
        if (blockEntity.hasAttached(SarcousAttachmentTypes.OVERCLOCKED)) {
            state.setData(SarcousClient.OVERCLOCKED, true);
        }
    }
}
