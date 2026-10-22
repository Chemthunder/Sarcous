package org.chemthunder.sarcous.core.infrastructure;

import com.mojang.serialization.Codec;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.StringIdentifiable;

/**
 * @author Chemthunder
 */
public enum EnergyTier implements StringIdentifiable {
    COALBURNER("coalburning"), // powered by inputting coal
    GEOTHERMIC("geothermic"), // powered by generators
    BILEFUEL("bilefueled"); // powered by bile

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

    public double multiplyDouble(double input) {
        switch (this) {
            case COALBURNER -> {
                return input * 1;
            }
            case GEOTHERMIC -> {
                return input * 1.33;
            }
            case BILEFUEL -> {
                return input * 2;
            }
        }
        return input;
    }

    public static double multiplyDouble(EnergyTier tier, double input) {
        switch (tier) {
            case COALBURNER -> {
                return input * 1;
            }
            case GEOTHERMIC -> {
                return input * 1.33;
            }
            case BILEFUEL -> {
                return input * 2;
            }
        }
        return input;
    }

    public int multiplyInt(int input) {
        switch (this) {
            case COALBURNER -> {
                return input;
            }
            case GEOTHERMIC -> {
                return input * 2;
            }
            case BILEFUEL -> {
                return input * 3;
            }
        }
        return input;
    }

    public int inSeconds() {
        return switch (this) {
            case COALBURNER -> 50;
            case GEOTHERMIC -> 25;
            case BILEFUEL -> 10;
        };
    }
}
