package org.chemthunder.sarcous.core.index;

import net.acoyt.acornlib.api.registrants.ItemRegistrant;
import net.minecraft.item.Item;
import org.chemthunder.sarcous.core.Sarcous;
import org.chemthunder.sarcous.core.item.CollectorsCarverItem;

/**
 * @author Chemthunder
 */
public interface SarcousItems {
    ItemRegistrant plugin = new ItemRegistrant(Sarcous.MOD_ID);

    Item COLLECTORS_CARVER = plugin.register("collectors_carver", CollectorsCarverItem::new, new Item.Settings()
            .maxCount(1)
            .attributeModifiers(CollectorsCarverItem.attributes())
    );

    static void init() {}
}
