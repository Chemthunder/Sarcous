package org.chemthunder.sarcous.api.data.helper;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * @author Chemthunder
 */
public record PlayableSound(SoundEvent event, float volume, float pitch) {
    public static final Codec<PlayableSound> CODEC = RecordCodecBuilder.create(codec -> codec.group(
            SoundEvent.DIRECT_CODEC.fieldOf("sound").forGetter(PlayableSound::event),
            Codec.FLOAT.optionalFieldOf("volume", 1.0F).forGetter(PlayableSound::volume),
            Codec.FLOAT.optionalFieldOf("pitch", 1.0F).forGetter(PlayableSound::pitch)
    ).apply(codec, PlayableSound::new));

    public static final StreamCodec<ByteBuf, PlayableSound> PACKET_CODEC = ByteBufCodecs.fromCodec(CODEC);

    public void playSound(Level world, Vec3 pos) {
        world.playSound(
                null,
                pos.x,
                pos.y,
                pos.z,
                this.event,
                SoundSource.BLOCKS,
                this.volume,
                this.pitch
        );
    }
}
