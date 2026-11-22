package org.chemthunder.sarcous.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import org.chemthunder.sarcous.core.SarcousClient;
import org.chemthunder.sarcous.core.client.OverclockedBlockEntityRenderer;
import org.chemthunder.sarcous.core.index.SarcousAttachmentTypes;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author Chemthunder
 */
@Mixin(value = BlockEntityRenderDispatcher.class)
public abstract class BlockEntityRenderDispatcherMixin {
    @WrapOperation(
            method = "tryExtractRenderState",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/blockentity/BlockEntityRenderer;extractRenderState(Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/client/renderer/blockentity/state/BlockEntityRenderState;FLnet/minecraft/world/phys/Vec3;Lnet/minecraft/client/renderer/feature/ModelFeatureRenderer$CrumblingOverlay;)V"
            )
    )
    private <S extends BlockEntityRenderState, T extends BlockEntity> void a(BlockEntityRenderer<T, S> instance, T blockEntity, S blockEntityRenderState, float f, Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay, Operation<Void> original) {
        if (blockEntity.hasAttached(SarcousAttachmentTypes.OVERCLOCKED)) {
            blockEntityRenderState.setData(SarcousClient.OVERCLOCKED, true);
        }
        original.call(instance, blockEntity, blockEntityRenderState, f, vec3, crumblingOverlay);
    }

    @WrapOperation(
            method = "submit",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/blockentity/BlockEntityRenderer;submit(Lnet/minecraft/client/renderer/blockentity/state/BlockEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;Lnet/minecraft/client/renderer/state/CameraRenderState;)V"
            )
    )
    private <S extends BlockEntityRenderState, T extends BlockEntity> void b(BlockEntityRenderer<T, S> instance, S s, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState, Operation<Void> original) {
        original.call(instance, s, poseStack, submitNodeCollector, cameraRenderState);
        if (Boolean.TRUE.equals(s.getData(SarcousClient.OVERCLOCKED))) {
            OverclockedBlockEntityRenderer.render(s, poseStack, submitNodeCollector, cameraRenderState);
        }
    }
}
