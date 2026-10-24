package org.chemthunder.sarcous.api.data.helper;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * @author Chemthunder
 */
public record PlayableSound(SoundEvent event, float volume, float pitch) {
    public static final Codec<PlayableSound> CODEC = RecordCodecBuilder.create(codec -> codec.group(
            SoundEvent.CODEC.fieldOf("sound").forGetter(PlayableSound::event),
            Codec.FLOAT.optionalFieldOf("volume", 1.0F).forGetter(PlayableSound::volume),
            Codec.FLOAT.optionalFieldOf("pitch", 1.0F).forGetter(PlayableSound::pitch)
    ).apply(codec, PlayableSound::new));

    public static final PacketCodec<ByteBuf, PlayableSound> PACKET_CODEC = PacketCodecs.codec(CODEC);

    public void playSound(World world, Vec3d pos) {
        world.playSound(
                null,
                pos.x,
                pos.y,
                pos.z,
                this.event,
                SoundCategory.BLOCKS,
                this.volume,
                this.pitch
        );
    }
}
