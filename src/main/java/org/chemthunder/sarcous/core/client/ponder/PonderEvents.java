package org.chemthunder.sarcous.core.client.ponder;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.world.World;
import org.chemthunder.sarcous.core.Sarcous;
import org.chemthunder.sarcous.core.client.api.PonderedBlock;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3x2fStack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class PonderEvents {
    public static @Nullable List<Text> lastDisplayed = null;
    public static @Nullable Item renderedBlock = null;
    public static float opacity = 0.0F;

    public static boolean pondering = false;

    public static void init() {
        ClientTickEvents.START_CLIENT_TICK.register(new Tick());
        HudElementRegistry.addFirst(Sarcous.id("ponder"), new Element());
    }

    public static class Tick implements ClientTickEvents.StartTick {
        public void onStartTick(MinecraftClient client) {
            if (client.isPaused()) return;

            PlayerEntity player = client.player;
            if (player == null) return;

            World world = client.world;
            if (world == null) return;

            HitResult ray = client.crosshairTarget;

            if (ray instanceof BlockHitResult blockHitResult) {
                BlockPos target = blockHitResult.getBlockPos();
                BlockState state = world.getBlockState(target);

                if (state.getBlock() instanceof PonderedBlock ponder) {
                    List<Text> output = new ArrayList<>();

                    output.add(Text.literal(Registries.BLOCK.getId(state.getBlock()).getPath())
                            .formatted(Formatting.RED)
                            .formatted(Formatting.UNDERLINE)
                    );
                    output.addAll(ponder.getPonderedText(state, target, world, player));

                    lastDisplayed = output;
                    renderedBlock = state.getBlock().asItem();
                    pondering = true;

                    if (opacity < 1.0) {
                        opacity += 0.1F;
                    }
                    return;
                }
            }
            lastDisplayed = null;
            pondering = false;
            opacity = 0.0F;
        }
    }

    public static class Element implements HudElement {
        public void render(DrawContext context, RenderTickCounter tickCounter) {
            if (lastDisplayed != null) {
                int x = context.getScaledWindowWidth() / 2 + 10;
                int y = context.getScaledWindowHeight() / 2 - 20;

                TextRenderer font = MinecraftClient.getInstance().textRenderer;

                int width = font.getWidth(lastDisplayed.getFirst());
                int lastWidth = width;

                for (Text line : lastDisplayed) {
                    int tested = font.getWidth(line);

                    if (tested > width) {
                        width = tested;
                    }
                }

                Matrix3x2fStack stack = context.getMatrices();

                if (renderedBlock != null) {
                    stack.pushMatrix();

                    stack.translate(x + (width / 2F), y + 3);
                    stack.scale(4);

                    context.drawItem(
                            new ItemStack(renderedBlock),
                            0, 0
                    );

                    stack.popMatrix();
                }

                context.fill(
                        x - 3,
                        y - 3,
                        x + (width + 5),
                        y + 64,
                        ColorHelper.withAlpha(opacity / 2, 0xFF000000)
                );

                for (Text line : lastDisplayed) {
                    context.drawText(
                            MinecraftClient.getInstance().textRenderer,
                            line,
                            x,
                            y + (lastDisplayed.indexOf(line) * 15),
                            ColorHelper.withAlpha(opacity, 0xFFffffff),
                            true
                    );
                }
            }
        }
    }
}
