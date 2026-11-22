package org.chemthunder.sarcous.core.index;

import net.acoyt.acornlib.api.registrants.GameRuleRegistrant;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import org.chemthunder.sarcous.core.Sarcous;

/**
 * @author Chemthunder
 */
public interface SarcousGameRules {
    GameRuleRegistrant plugin = new GameRuleRegistrant(Sarcous.MOD_ID);

    GameRule<Boolean> OVERCLOCK_HOPPERS = plugin.registerBooleanRule("overclock_hoppers", GameRuleCategory.MISC, false);

    static void init() {}
}
