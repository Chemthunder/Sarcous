package org.chemthunder.sarcous.core.infrastructure;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chemthunder
 */
@ApiStatus.NonExtendable
public abstract class MachineryUtil {
    public static String removeTieredPrefix(String input) {
        EnergyTier marked = null;
        String output;

        for (EnergyTier tier : EnergyTier.values()) {
            if (input.contains(tier.asString())) {
                marked = tier;
                break;
            }
        }

        if (marked != null) {
            output = input.replaceAll(marked.asString(), "");

            return output;
        }
        return input;
    }

    public static List<BlockPos> getDirectionalized(BlockPos center) {
        List<BlockPos> list = new ArrayList<>();

        for (Direction dir : Direction.values()) {
            list.add(center.offset(dir));
        }

        return list;
    }
}
