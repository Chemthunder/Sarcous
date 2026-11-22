package org.chemthunder.sarcous.core.infrastructure;

import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

/**
 * @author Chemthunder
 */
@ApiStatus.NonExtendable
public abstract class MachineryUtil {
    public static String removeTieredPrefix(String input) {
        EnergyTier marked = null;
        String output;

        for (EnergyTier tier : EnergyTier.values()) {
            if (input.contains(tier.getSerializedName())) {
                marked = tier;
                break;
            }
        }

        if (marked != null) {
            output = input.replaceAll(marked.getSerializedName(), "");

            return output;
        }
        return input;
    }

    public static List<BlockPos> getDirectionalized(BlockPos center) {
        List<BlockPos> list = new ArrayList<>();

        for (Direction dir : Direction.values()) {
            list.add(center.relative(dir));
        }

        return list;
    }
}
