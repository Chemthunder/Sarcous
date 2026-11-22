package org.chemthunder.sarcous.core.networking.clientbound;

import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.chemthunder.sarcous.core.Sarcous;
import org.chemthunder.sarcous.core.index.SarcousAttachmentTypes;

/**
 * @author Chemthunder
 */
public record SyncOverclockedPayload(BlockPos pos) implements CustomPacketPayload {
    public static final Type<SyncOverclockedPayload> TYPE = new Type<>(Sarcous.id("sync_overclocked"));

    public static final StreamCodec<ByteBuf, SyncOverclockedPayload> CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, SyncOverclockedPayload::pos,
            SyncOverclockedPayload::new
    );

    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static class Reciever implements ClientPlayNetworking.PlayPayloadHandler<SyncOverclockedPayload> {
        public void receive(SyncOverclockedPayload payload, ClientPlayNetworking.Context context) {
            Level level = context.client().level;
            BlockPos pos = payload.pos;

            if (level == null) return;

            if (level.getBlockEntity(pos) != null) {
                BlockEntity be = level.getBlockEntity(pos);

                if (be != null) {
                    be.setAttached(SarcousAttachmentTypes.OVERCLOCKED, true);
                }
            }
        }
    }
}
