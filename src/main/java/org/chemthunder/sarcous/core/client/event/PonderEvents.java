package org.chemthunder.sarcous.core.client.event;

import net.acoyt.acornlib.api.util.MiscUtils;
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
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.world.World;
import org.chemthunder.sarcous.core.infrastructure.EnergyTier;
import org.chemthunder.sarcous.core.Sarcous;
import org.chemthunder.sarcous.api.block.entity.PoweredBlockEntity;
import org.chemthunder.sarcous.api.block.PonderedBlock;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix3x2fStack;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chemthunder
 */
@Environment(EnvType.CLIENT)
public class PonderEvents {
    public static @Nullable List<Text> tooltip = null;
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

                    output.add(Text.literal(MiscUtils.formatString(Registries.BLOCK.getId(state.getBlock()).getPath()))
                            .formatted(Formatting.RED, Formatting.BOLD, Formatting.ITALIC)
                    );

                    if (world.getBlockEntity(target) instanceof PoweredBlockEntity power) {
                        output.add(Text.empty()
                                .append(Text.literal("Fuel Value: ").formatted(Formatting.DARK_GRAY))
                                .append(Text.literal(power.getFuel() + "").formatted(Formatting.RED, Formatting.BOLD)));
                    }

                    output.addAll(ponder.getPonderedText(state, target, world, player));

                    tooltip = output;
                    renderedBlock = state.getBlock().asItem();
                    pondering = true;

                    if (opacity < 1.0) {
                        opacity += 0.1F;
                    }
                    return;
                }
            }
            tooltip = null;
            pondering = false;
            opacity = 0.0F;
        }
    }

    public static class Element implements HudElement {
        public void render(DrawContext context, RenderTickCounter tickCounter) {
            TextRenderer tr = MinecraftClient.getInstance().textRenderer;
            Matrix3x2fStack stack = context.getMatrices();

            if (tooltip != null) {
                int x = context.getScaledWindowWidth() / 2 + 10;
                int y = context.getScaledWindowHeight() / 2 - 20;

                int width = 0;
                int height = 12;

                for (Text line : tooltip) {
                    int tested = tr.getWidth(line);

                    if (tested > width) {
                        width = tested;
                    }

                    height += 12;
                }

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
                        y + height,
                        ColorHelper.withAlpha(opacity / 2, 0xFF000000)
                );

                for (Text line : tooltip) {
                    context.drawText(
                            tr,
                            line,
                            x,
                            y + (tooltip.indexOf(line) * 14),
                            ColorHelper.withAlpha(opacity, 0xFFffffff),
                            true
                    );
                }
            }
        }
    }
}
