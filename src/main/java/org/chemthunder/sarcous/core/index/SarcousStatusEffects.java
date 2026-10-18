package org.chemthunder.sarcous.core.index;

import net.acoyt.acornlib.api.registrants.MobEffectRegistrant;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.entry.RegistryEntry;
import org.chemthunder.sarcous.core.Sarcous;
import org.chemthunder.sarcous.core.status_effect.HypovolemiaStatusEffect;

/**
 * @author Chemthunder
 */
public interface SarcousStatusEffects {
    MobEffectRegistrant plugin = new MobEffectRegistrant(Sarcous.MOD_ID);

    RegistryEntry<StatusEffect> HYPOVOLEMIA = plugin.registerRef("hypovolemia",
            new HypovolemiaStatusEffect(
                    StatusEffectCategory.HARMFUL,
                    0xFFff0000
            )
    );

    static void init() {}
}
