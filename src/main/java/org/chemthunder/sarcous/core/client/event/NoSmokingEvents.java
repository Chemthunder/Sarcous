package org.chemthunder.sarcous.core.client.event;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.util.ARGB;
import org.chemthunder.sarcous.core.Sarcous;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class NoSmokingEvents {
    public static float opacity = 0.0F;

    public static void init() {
        ClientTickEvents.START_CLIENT_TICK.register(new Tick());
        HudElementRegistry.addFirst(Sarcous.id("no_smoking_flash"), new Hud());
    }

    public static class Tick implements ClientTickEvents.StartTick {
        public void onStartTick(Minecraft client) {
            opacity = Math.clamp(opacity, 0, 1);

            if (opacity > 0) {
                opacity -= 0.05F;
            }
        }
    }

    public static class Hud implements HudElement {
        public void render(GuiGraphics context, DeltaTracker tickCounter) {
            if (opacity > 0) {
                context.blitSprite(
                        RenderPipelines.GUI_TEXTURED,
                        Sarcous.id("hud/no_fucking_smoking"),
                        0,
                        0,
                        context.guiWidth(),
                        context.guiHeight(),
                        ARGB.color(opacity, 0xFFffffff)
                );
            }
        }
    }
}
