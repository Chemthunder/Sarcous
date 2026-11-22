package org.chemthunder.sarcous.core.index;

import net.acoyt.acornlib.api.registrants.CreativeModeTabRegistrant;
import net.acoyt.acornlib.api.util.MiscUtils;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import org.chemthunder.sarcous.core.Sarcous;

/**
 * @author Chemthunder
 */
public interface SarcousCreativeModeTabs {
    CreativeModeTabRegistrant plugin = new CreativeModeTabRegistrant(Sarcous.MOD_ID);

    ResourceKey<CreativeModeTab> GROUP_KEY = ResourceKey.create(Registries.CREATIVE_MODE_TAB, Sarcous.id(Sarcous.MOD_ID));

    static void init() {
        plugin.register(GROUP_KEY.identifier().getPath(), FabricItemGroup.builder()
                .icon(() -> new ItemStack(Items.ROTTEN_FLESH))
                .title(Component.literal(MiscUtils.formatString(Sarcous.MOD_ID)).withColor(0xFFff0000))
                .build());

        ItemGroupEvents.modifyEntriesEvent(GROUP_KEY).register(SarcousCreativeModeTabs::addEntries);
    }

    private static void addEntries(FabricItemGroupEntries entries) {
        for (Block block : SarcousBlocks.plugin.toRegister) {
            entries.accept(block);
        }

        for (Item item : SarcousItems.plugin.toRegister) {
            entries.accept(item);
        }
    }
}
