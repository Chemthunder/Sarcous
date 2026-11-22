package org.chemthunder.sarcous.core.index;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.item.ItemStack;
import org.chemthunder.sarcous.core.Sarcous;

import java.util.function.Consumer;

/**
 * @author Chemthunder
 */
public interface SarcousAttachmentTypes {
    // Block Entity
    AttachmentType<ItemStack> STOCKPILE_FILTER = register("stockpile_filter",
            builder -> builder
                    .persistent(ItemStack.CODEC)
                    .syncWith(ItemStack.STREAM_CODEC, AttachmentSyncPredicate.all())
                    .copyOnDeath()
    );

    AttachmentType<Boolean> OVERCLOCKED = register("overclocked",
            builder -> builder
                    .persistent(Codec.BOOL)
                    .syncWith(ByteBufCodecs.BOOL, AttachmentSyncPredicate.all())
    );

    // Chunk
    AttachmentType<Double> POLLUTION_LEVEL = register("pollution_leve",
            builder -> builder
                    .persistent(Codec.DOUBLE)
                    .syncWith(ByteBufCodecs.DOUBLE, AttachmentSyncPredicate.all())
                    .copyOnDeath()
                    .initializer(() -> 0.0)
    );

    // Entity
    AttachmentType<Integer> EXHALE_TIME = register("exhale_time",
            builder -> builder
                    .persistent(Codec.INT)
                    .syncWith(ByteBufCodecs.INT, AttachmentSyncPredicate.all())
                    .initializer(() -> 0)
    );

    static void init() {}

    static <T> AttachmentType<T> register(String name, Consumer<AttachmentRegistry.Builder<T>> consumer) {
        return AttachmentRegistry.create(Sarcous.id(name), consumer);
    }
}
