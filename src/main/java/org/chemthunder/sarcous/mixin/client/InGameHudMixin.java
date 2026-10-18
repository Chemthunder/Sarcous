package org.chemthunder.sarcous.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.Identifier;
import org.chemthunder.sarcous.core.Sarcous;
import org.chemthunder.sarcous.core.client.event.PonderEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author Chemthunder
 */
@Mixin(value = InGameHud.class)
public abstract class InGameHudMixin {
    @WrapOperation(
            method = "renderCrosshair",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/util/Identifier;IIII)V",
                    ordinal = 0
            )
    )
    private void sarcous$swapCrosshair(DrawContext instance, RenderPipeline pipeline, Identifier sprite, int x, int y, int width, int height, Operation<Void> original) {
        original.call(instance, pipeline, PonderEvents.pondering ? Sarcous.id("question_mark") : sprite, x, y, width, height);
    }
}
