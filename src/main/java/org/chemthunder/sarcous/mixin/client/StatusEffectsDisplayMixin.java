package org.chemthunder.sarcous.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.StatusEffectsDisplay;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.text.Text;
import org.chemthunder.sarcous.core.Sarcous;
import org.chemthunder.sarcous.core.index.SarcousStatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author Chemthunder
 */
@SuppressWarnings("MathClampMigration")
@Mixin(value = StatusEffectsDisplay.class)
public abstract class StatusEffectsDisplayMixin {

    @WrapOperation(
            method = "drawStatusEffects",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screen/ingame/StatusEffectsDisplay;drawStatusEffectBackgrounds(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;Lnet/minecraft/text/Text;IIZI)I"
            )
    )
    private int sarcous$background(StatusEffectsDisplay instance, DrawContext context, TextRenderer textRenderer, Text description, Text duration, int x, int y, boolean ambient, int width, Operation<Integer> original, @Local(name = "statusEffectInstance") StatusEffectInstance statusEffectInstance) {
        if (statusEffectInstance.getEffectType() == SarcousStatusEffects.HYPOVOLEMIA) {
            return sarcous$drawStatusEffectBackgrounds(context, textRenderer, description, duration, x, y, ambient, width);
        }
        return original.call(instance, context, textRenderer, description, duration, x, y, ambient, width);
    }

    @Unique
    private int sarcous$drawStatusEffectBackgrounds(DrawContext context, TextRenderer textRenderer, Text description, Text duration, int x, int y, boolean ambient, int width) {
        int i = 32 + textRenderer.getWidth(description) + 7;
        int j = 32 + textRenderer.getWidth(duration) + 7;
        int k = Math.min(width, Math.max(i, j));
        context.drawGuiTexture(RenderPipelines.GUI_TEXTURED, Sarcous.id("container/inventory/blood_background"), x, y, k, 32);
        return k;
    }
}
