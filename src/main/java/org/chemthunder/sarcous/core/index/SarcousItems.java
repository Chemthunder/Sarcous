package org.chemthunder.sarcous.core.index;

import net.acoyt.acornlib.api.registrants.ItemRegistrant;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import org.chemthunder.sarcous.core.Sarcous;
import org.chemthunder.sarcous.core.item.AmeliorateItem;
import org.chemthunder.sarcous.core.item.CollectorsCarverItem;
import org.chemthunder.sarcous.core.item.RollieItem;

/**
 * @author Chemthunder
 */
public interface SarcousItems {
    ItemRegistrant plugin = new ItemRegistrant(Sarcous.MOD_ID);

    Item COLLECTORS_CARVER = plugin.register("collectors_carver", CollectorsCarverItem::new, new Item.Properties()
            .stacksTo(1)
            .attributes(CollectorsCarverItem.attributes())
    );

    Item AMELIORATE = plugin.register("ameliorate", AmeliorateItem::new, new Item.Properties()
            .stacksTo(16)
    );

    Item ROLLIE = plugin.register("rollie", RollieItem::new, new Item.Properties()
            .stacksTo(16)
    );

    Item ASHTRAY = plugin.register("ashtray", (properties -> new BlockItem(SarcousBlocks.ASHTRAY, properties)), new Item.Properties()
            .stacksTo(16)
    );

    static void init() {}
}
