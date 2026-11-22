package org.chemthunder.sarcous.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.EffectsInInventory;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import org.chemthunder.sarcous.core.Sarcous;
import org.chemthunder.sarcous.core.index.SarcousMobEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author Chemthunder
 */
@SuppressWarnings("MathClampMigration")
@Mixin(value = EffectsInInventory.class)
public abstract class EffectsInInventoryMixin {

    @WrapOperation(
            method = "renderEffects",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/screens/inventory/EffectsInInventory;renderBackground(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/Component;IIZI)I"
            )
    )
    private int sarcous$background(EffectsInInventory instance, GuiGraphics context, Font textRenderer, Component description, Component duration, int x, int y, boolean ambient, int width, Operation<Integer> original, @Local(name = "mobEffectInstance") MobEffectInstance statusEffectInstance) {
        if (statusEffectInstance.getEffect() == SarcousMobEffects.HYPOVOLEMIA) {
            return sarcous$drawStatusEffectBackgrounds(context, textRenderer, description, duration, x, y, width);
        }
        return original.call(instance, context, textRenderer, description, duration, x, y, ambient, width);
    }

    @Unique
    private int sarcous$drawStatusEffectBackgrounds(GuiGraphics context, Font textRenderer, Component description, Component duration, int x, int y, int width) {
        int i = 32 + textRenderer.width(description) + 7;
        int j = 32 + textRenderer.width(duration) + 7;
        int k = Math.min(width, Math.max(i, j));
        context.blitSprite(RenderPipelines.GUI_TEXTURED, Sarcous.id("container/inventory/blood_background"), x, y, k, 32);
        return k;
    }
}
