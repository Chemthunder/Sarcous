package org.chemthunder.sarcous.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.Identifier;
import org.chemthunder.sarcous.core.Sarcous;
import org.chemthunder.sarcous.core.client.event.PonderEvents;
import org.chemthunder.sarcous.core.index.SarcousStatusEffects;
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

    @WrapOperation(
            method = "renderStatusEffectOverlay",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/util/Identifier;IIII)V",
                    ordinal = 1
            )
    )
    private void sarcous$swapStatusEffectBackground(DrawContext instance, RenderPipeline pipeline, Identifier sprite, int x, int y, int width, int height, Operation<Void> original, @Local(name = "statusEffectInstance") StatusEffectInstance statusEffectInstance) {
        if (statusEffectInstance.getEffectType() == SarcousStatusEffects.HYPOVOLEMIA) {
            original.call(instance, pipeline, Sarcous.id("container/inventory/blood_background"), x, y, width, height);
            return;
        }
        original.call(instance, pipeline, sprite, x, y, width, height);
    }
}
