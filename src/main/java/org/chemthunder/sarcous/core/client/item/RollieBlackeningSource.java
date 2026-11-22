package org.chemthunder.sarcous.core.client.item;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.chemthunder.sarcous.core.Sarcous;
import org.chemthunder.sarcous.core.index.SarcousDataComponentTypes;
import org.chemthunder.sarcous.core.item.RollieItem;
import org.jspecify.annotations.Nullable;

/**
 * @author Chemthunder
 */
public record RollieBlackeningSource() implements ItemTintSource {
    public static final MapCodec<RollieBlackeningSource> CODEC = MapCodec.unit(new RollieBlackeningSource());
    public static final Identifier ID = Sarcous.id("rollie_blackening");

    public int calculate(ItemStack itemStack, @Nullable ClientLevel clientLevel, @Nullable LivingEntity livingEntity) {
        int time = itemStack.getOrDefault(SarcousDataComponentTypes.BLACKNESS, 0);

        if (time > 0) {
            return ARGB.linearLerp(((float) time / RollieItem.MAX_TIME), 0xFFffffff, 0xFF363636);
        }
        return 0xFFffffff;
    }

    public MapCodec<? extends ItemTintSource> type() {
        return CODEC;
    }
}
