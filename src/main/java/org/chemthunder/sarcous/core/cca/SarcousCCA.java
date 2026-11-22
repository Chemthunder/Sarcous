package org.chemthunder.sarcous.core.cca;

import net.minecraft.world.entity.LivingEntity;
import org.chemthunder.sarcous.core.cca.entity.ExhaleComponent;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;

/**
 * @author Chemthunder
 */
public class SarcousCCA implements EntityComponentInitializer {
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerFor(LivingEntity.class, ExhaleComponent.KEY, ExhaleComponent::new);
    }
}
