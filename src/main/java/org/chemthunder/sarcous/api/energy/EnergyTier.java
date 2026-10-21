package org.chemthunder.sarcous.api.energy;

import com.mojang.serialization.Codec;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;

/**
 * @author Chemthunder
 */
public enum EnergyTier implements StringIdentifiable {
    COALBURNER("coalburner"), // powered by inputting coal
    GEOTHERMIC("geothermic"), // powered by generators
    BILEFUEL("bilefuel"); // powered by bile

    private final String id;

    public static final Codec<EnergyTier> CODEC = StringIdentifiable.createCodec(EnergyTier::values);

    public static final EnumProperty<EnergyTier> PROPERTY = EnumProperty.of(
            "energy_tier",
            EnergyTier.class
    );

    EnergyTier(String id) {
        this.id = id;
    }

    public String asString() {
        return this.id;
    }
}
