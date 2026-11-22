package org.chemthunder.sarcous.core.cca.entity;

import net.minecraft.advancements.criterion.*;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.chemthunder.sarcous.core.Sarcous;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

/**
 * @author Chemthunder
 */
public class ExhaleComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<ExhaleComponent> KEY = ComponentRegistry.getOrCreate(
            Sarcous.id("exhale"),
            ExhaleComponent.class
    );
    private final LivingEntity living;

    private int exhaleTime = 0;

    public ExhaleComponent(LivingEntity living) {
        this.living = living;
    }

    public void tick() {
        if (this.exhaleTime > 0) {
            this.exhaleTime--;

            Vec3 ray = living.pick(0.44, 0, true).getLocation();

            living.level().addParticle(
                    ParticleTypes.SMOKE,
                    ray.x,
                    ray.y,
                    ray.z,
                    0,
                    0,
                    0
            );

            HitResult vector = ProjectileUtil.getHitResultOnViewVector(living, Entity::isPushable, 2.5);

            if (vector instanceof EntityHitResult entityHitResult) {
                Entity entity = entityHitResult.getEntity();

                if (entity instanceof LivingEntity larper) {
                    larper.addEffect(new MobEffectInstance(MobEffects.NAUSEA, (6 * 20)));
                }
            }

            if (this.exhaleTime <= 0) {
                this.sync();
            }
        }
    }

    public void sync() {
        KEY.sync(living);
    }

    public void readData(ValueInput readView) {
        exhaleTime = readView.getIntOr("ExhaleTime", 0);
    }

    public void writeData(ValueOutput writeView) {
        writeView.putInt("ExhaleTime", exhaleTime);
    }

    public void setExhaleTime(int exhaleTime) {
        this.exhaleTime = exhaleTime;
        sync();
    }
}

