package org.chemthunder.sarcous.core.client.event;

import avogodo.barometric.Barometric;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.chemthunder.sarcous.core.index.SarcousAttachmentTypes;

/**
 * @author Chemthunder
 */
public class BarometricPollutionEvent implements HudElement {
    public void render(GuiGraphics context, DeltaTracker tickCounter) {
        Minecraft minecraft = Minecraft.getInstance();

        LocalPlayer player = minecraft.player;
        if (player == null) return;

        ChunkAccess chunk = player.level().getChunk(player.blockPosition());

        if (Barometric.getRadio(player) != null) {
            String out = "Pollution Level: " + chunk.getAttachedOrCreate(SarcousAttachmentTypes.POLLUTION_LEVEL);

            context.hLine(
                    5,
                    minecraft.font.width(out),
                    context.guiHeight() - 40,
                    0xFFffffff
            );

            context.drawString(
                    minecraft.font,
                    out,
                    5,
                    context.guiHeight() - 50,
                    0xFFffffff,
                    false
            );
        }
    }
}

