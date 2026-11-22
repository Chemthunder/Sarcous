package org.chemthunder.sarcous.core.index;

import com.mojang.serialization.Codec;
import net.acoyt.acornlib.api.registrants.DataComponentTypeRegistrant;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.ByteBufCodecs;
import org.chemthunder.sarcous.core.Sarcous;

/**
 * @author Chemthunder
 */
public interface SarcousDataComponentTypes {
    DataComponentTypeRegistrant plugin = new DataComponentTypeRegistrant(Sarcous.MOD_ID);

    DataComponentType<Integer> BLACKNESS = plugin.register("blackness", Codec.INT, ByteBufCodecs.INT);

    static void init() {}
}
