package org.chemthunder.sarcous.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffectInstance;
import org.chemthunder.sarcous.core.Sarcous;
import org.chemthunder.sarcous.core.client.event.PonderEvents;
import org.chemthunder.sarcous.core.index.SarcousMobEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author Chemthunder
 */
@Mixin(value = Gui.class)
public abstract class GuiMixin {
    @WrapOperation(
            method = "renderCrosshair",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V",
                    ordinal = 0
            )
    )
    private void sarcous$swapCrosshair(GuiGraphics instance, RenderPipeline pipeline, Identifier sprite, int x, int y, int width, int height, Operation<Void> original) {
        original.call(instance, pipeline, PonderEvents.pondering ? Sarcous.id("question_mark") : sprite, x, y, width, height);
    }

    @WrapOperation(
            method = "renderEffects",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/GuiGraphics;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V",
                    ordinal = 1
            )
    )
    private void sarcous$swapStatusEffectBackground(GuiGraphics instance, RenderPipeline pipeline, Identifier sprite, int x, int y, int width, int height, Operation<Void> original, @Local(name = "mobEffectInstance") MobEffectInstance statusEffectInstance) {
        if (statusEffectInstance.getEffect() == SarcousMobEffects.HYPOVOLEMIA) {
            original.call(instance, pipeline, Sarcous.id("container/inventory/blood_background"), x, y, width, height);
            return;
        }
        original.call(instance, pipeline, sprite, x, y, width, height);
    }
}
