package org.chemthunder.sarcous.core.item;

import net.acoyt.acornlib.api.util.NetworkingUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.chemthunder.sarcous.core.index.SarcousAttachmentTypes;
import org.chemthunder.sarcous.core.networking.clientbound.SyncOverclockedPayload;

/**
 * @author Chemthunder
 */
public class AmeliorateItem extends Item {
    public AmeliorateItem(Properties settings) {
        super(settings);
    }

    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();

        if (level.getBlockEntity(pos) != null) {
            BlockEntity entity = level.getBlockEntity(pos);

            if (entity != null) {
                if (!entity.hasAttached(SarcousAttachmentTypes.OVERCLOCKED)) {
                    entity.setAttached(SarcousAttachmentTypes.OVERCLOCKED, true);
                    NetworkingUtils.sendForAllPlayers(level, new SyncOverclockedPayload(pos));
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return super.useOn(context);
    }
}
