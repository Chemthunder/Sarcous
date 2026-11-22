package org.chemthunder.sarcous.datagen.providers.server;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.criterion.PlayerTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.chemthunder.sarcous.core.Sarcous;
import org.chemthunder.sarcous.core.index.SarcousBlocks;
import org.chemthunder.sarcous.core.index.SarcousDataComponentTypes;
import org.chemthunder.sarcous.core.index.SarcousItems;
import org.chemthunder.sarcous.core.index.SarcousPlayerTriggers;
import org.chemthunder.sarcous.core.infrastructure.EnergyTier;
import org.chemthunder.sarcous.core.item.RollieItem;

import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * @author Chemthunder
 */
public class SarcousAdvancementProvider extends FabricAdvancementProvider {
    public SarcousAdvancementProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(output, registryLookup);
    }

    public void generateAdvancement(HolderLookup.Provider registryLookup, Consumer<AdvancementHolder> consumer) {
        AdvancementHolder root = Advancement.Builder.advancement()
                .display(
                        SarcousBlocks.DRILLS.get(EnergyTier.COALBURNER).asItem(),
                        Component.literal("Sarcous"),
                        Component.literal("Tech Must Progress"),
                        Sarcous.id("gui/advancements/backgrounds/sarcous"),
                        AdvancementType.TASK,
                        false,
                        false,
                        false
                )
                .addCriterion("e", PlayerTrigger.TriggerInstance.tick().trigger().createCriterion(PlayerTrigger.TriggerInstance.tick().triggerInstance()))
                .requirements(AdvancementRequirements.allOf(Collections.singleton("e")))
                .build(Sarcous.id("root"));

        consumer.accept(root);

        ItemStack burntRollie = new ItemStack(SarcousItems.ROLLIE);

        burntRollie.set(SarcousDataComponentTypes.BLACKNESS, RollieItem.MAX_TIME);

        AdvancementHolder fullSmoke = Advancement.Builder.advancement()
                .parent(root)
                .display(
                        burntRollie,
                        Component.literal("Just to Take the Edge Off"),
                        Component.literal("Inhale enough smoke from a Rollie for it to fully blacken."),
                        Sarcous.id("gui/advancements/backgrounds/sarcous"),
                        AdvancementType.GOAL,
                        true,
                        true,
                        true
                )
                .addCriterion("e", SarcousPlayerTriggers.FULL_SMOKE.createCriterion(PlayerTrigger.TriggerInstance.tick().triggerInstance()))
                .requirements(AdvancementRequirements.allOf(Collections.singleton("e")))
                .build(Sarcous.id("full_smoke"));

        consumer.accept(fullSmoke);
    }
}
