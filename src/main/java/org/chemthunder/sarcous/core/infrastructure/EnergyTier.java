package org.chemthunder.sarcous.core.infrastructure;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.state.properties.EnumProperty;

/**
 * @author Chemthunder
 */
public enum EnergyTier implements StringRepresentable {
    COALBURNER("coalburning"), // powered by inputting coal
    GEOTHERMIC("geothermic"), // powered by generators
    BILEFUEL("bilefueled"); // powered by bile

    private final String id;

    public static final Codec<EnergyTier> CODEC = StringRepresentable.fromEnum(EnergyTier::values);

    public static final EnumProperty<EnergyTier> PROPERTY = EnumProperty.create(
            "energy_tier",
            EnergyTier.class
    );

    EnergyTier(String id) {
        this.id = id;
    }

    public String getSerializedName() {
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
