package org.chemthunder.sarcous.core.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.chemthunder.sarcous.core.Sarcous;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class OverclockedBlockEntityRenderer {
    public static void render(BlockEntityRenderState s, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        Minecraft client = Minecraft.getInstance();

        Sarcous.LOGGER.info("im rendering YAY");
    }
}
