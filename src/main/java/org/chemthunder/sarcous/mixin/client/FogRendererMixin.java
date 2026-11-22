package org.chemthunder.sarcous.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.util.ARGB;
import net.minecraft.util.EasingType;
import org.chemthunder.sarcous.core.client.event.PollutionFadeInEvent;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/**
 * @author Chemthunder
 */
@Mixin(value = FogRenderer.class)
public abstract class FogRendererMixin {

    @ModifyReturnValue(method = "computeFogColor", at = @At(value = "RETURN"))
    private Vector4f sarcous$adjustFogColor(Vector4f original) {
        if (PollutionFadeInEvent.value > 0) {
            return original.lerp(ARGB.vector4fFromARGB32(0xFF3a5242), EasingType.OUT_EXPO.apply(PollutionFadeInEvent.value));
        }
        return original;
    }
}
