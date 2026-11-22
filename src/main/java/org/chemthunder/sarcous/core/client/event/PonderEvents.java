package org.chemthunder.sarcous.core.client.event;

import net.acoyt.acornlib.api.util.MiscUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
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
    public static @Nullable List<Component> tooltip = null;
    public static @Nullable Item renderedBlock = null;

    public static float opacity = 0.0F;

    public static boolean pondering = false;

    public static void init() {
        ClientTickEvents.START_CLIENT_TICK.register(new Tick());
        HudElementRegistry.addFirst(Sarcous.id("ponder"), new Element());
    }

    public static class Tick implements ClientTickEvents.StartTick {
        public void onStartTick(Minecraft client) {
            if (client.isPaused()) return;

            Player player = client.player;
            if (player == null) return;

            Level world = client.level;
            if (world == null) return;

            HitResult ray = client.hitResult;

            if (ray instanceof BlockHitResult blockHitResult) {
                BlockPos target = blockHitResult.getBlockPos();
                BlockState state = world.getBlockState(target);

                if (state.getBlock() instanceof PonderedBlock ponder) {
                    List<Component> output = new ArrayList<>();

                    output.add(Component.literal(MiscUtils.formatString(BuiltInRegistries.BLOCK.getKey(state.getBlock()).getPath()))
                            .withStyle(ChatFormatting.RED, ChatFormatting.BOLD, ChatFormatting.ITALIC)
                    );

                    if (world.getBlockEntity(target) instanceof PoweredBlockEntity power) {
                        output.add(Component.empty()
                                .append(Component.literal("Fuel Value: ").withStyle(ChatFormatting.DARK_GRAY))
                                .append(Component.literal(power.getFuel() + "").withStyle(ChatFormatting.RED, ChatFormatting.BOLD)));
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
        public void render(GuiGraphics context, DeltaTracker tickCounter) {
            Font tr = Minecraft.getInstance().font;
            Matrix3x2fStack stack = context.pose();

            if (tooltip != null) {
                int x = context.guiWidth() / 2 + 10;
                int y = context.guiHeight() / 2 - 20;

                int width = 0;
                int height = 12;

                for (Component line : tooltip) {
                    int tested = tr.width(line);

                    if (tested > width) {
                        width = tested;
                    }

                    height += 12;
                }

                if (renderedBlock != null) {
                    stack.pushMatrix();

                    stack.translate(x + (width / 2F), y + 3);
                    stack.scale(4);

                    context.renderItem(
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
                        ARGB.color(opacity / 2, 0xFF000000)
                );

                for (Component line : tooltip) {
                    context.drawString(
                            tr,
                            line,
                            x,
                            y + (tooltip.indexOf(line) * 14),
                            ARGB.color(opacity, 0xFFffffff),
                            true
                    );
                }
            }
        }
    }
}
