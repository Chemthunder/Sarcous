package org.chemthunder.sarcous.core.index;

import net.acoyt.acornlib.api.registrants.CreativeModeTabRegistrant;
import net.acoyt.acornlib.api.util.MiscUtils;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import org.chemthunder.sarcous.core.Sarcous;

/**
 * @author Chemthunder
 */
public interface SarcousItemGroups {
    CreativeModeTabRegistrant plugin = new CreativeModeTabRegistrant(Sarcous.MOD_ID);

    RegistryKey<ItemGroup> GROUP_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, Sarcous.id(Sarcous.MOD_ID));

    static void init() {
        plugin.register(GROUP_KEY.getValue().getPath(), FabricItemGroup.builder()
                .icon(() -> new ItemStack(SarcousBlocks.DRILL.asItem()))
                .displayName(Text.literal(MiscUtils.formatString(Sarcous.MOD_ID)).withColor(0xFFff0000))
                .build());

        ItemGroupEvents.modifyEntriesEvent(GROUP_KEY).register(SarcousItemGroups::addEntries);
    }

    private static void addEntries(FabricItemGroupEntries entries) {
        for (Block block : SarcousBlocks.plugin.toRegister) {
            entries.add(block);
        }
    }
}
