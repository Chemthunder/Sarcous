package org.chemthunder.sarcous.core.index;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.item.ItemStack;
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
                    .syncWith(ItemStack.PACKET_CODEC, AttachmentSyncPredicate.all())
    );

    static void init() {}

    static <T> AttachmentType<T> register(String name, Consumer<AttachmentRegistry.Builder<T>> consumer) {
        return AttachmentRegistry.create(Sarcous.id(name), consumer);
    }
}
