package org.chemthunder.sarcous.core.index;

import net.acoyt.acornlib.api.registrants.MobEffectRegistrant;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import org.chemthunder.sarcous.core.Sarcous;
import org.chemthunder.sarcous.core.mob_effect.HypovolemiaMobEffect;

/**
 * @author Chemthunder
 */
public interface SarcousMobEffects {
    MobEffectRegistrant plugin = new MobEffectRegistrant(Sarcous.MOD_ID);

    Holder<MobEffect> HYPOVOLEMIA = plugin.registerRef("hypovolemia",
            new HypovolemiaMobEffect(
                    MobEffectCategory.HARMFUL,
                    0xFFff0000
            )
    );

    static void init() {}
}
