package org.chemthunder.sarcous.core.index;

import net.acoyt.acornlib.api.registrants.CriterionTriggerRegistrant;
import net.minecraft.advancements.criterion.PlayerTrigger;
import org.chemthunder.sarcous.core.Sarcous;

/**
 * @author Chemthunder
 */
public interface SarcousPlayerTriggers {
    CriterionTriggerRegistrant plugin = new CriterionTriggerRegistrant(Sarcous.MOD_ID);

    PlayerTrigger FULL_SMOKE = plugin.register("full_smoke", new PlayerTrigger());

    static void init() {}
}
