package org.chemthunder.sarcous.api.energy;

import net.minecraft.util.StringIdentifiable;

/**
 * @author Chemthunder
 */
public enum EnergyTier implements StringIdentifiable {
    COALBURNER("coalburner"), // powered by inputting coal
    GEOTHERMIC("geothermic"), // powered by generators
    BILEFUEL("bilefuel"); // powered by bile

    private final String id;

    EnergyTier(String id) {
        this.id = id;
    }

    public String asString() {
        return this.id;
    }
}
