package org.chemthunder.sarcous.core.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.chemthunder.sarcous.core.index.SarcousAttachmentTypes;
import org.chemthunder.sarcous.core.index.SarcousBlockEntityTypes;

import java.util.Random;

/**
 * @author Chemthunder
 */
public class AirPurifierBlockEntity extends BlockEntity {
    public AirPurifierBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(SarcousBlockEntityTypes.AIR_PURIFIER, blockPos, blockState);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        ChunkAccess chunk = level.getChunk(pos);

        double pollution = chunk.getAttachedOrCreate(SarcousAttachmentTypes.POLLUTION_LEVEL);

        if (pollution < 0) {
            chunk.setAttached(SarcousAttachmentTypes.POLLUTION_LEVEL, 0.0);
        }

        if (pollution > 0) {
            if (level.random.nextInt(1, 12) == 2) {
                chunk.setAttached(SarcousAttachmentTypes.POLLUTION_LEVEL, pollution - 0.5);
            }

            for (int i = 0; i < 3; i++) {
                Random rand = new Random();

                level.addParticle(
                        new DustParticleOptions(0xFFffffff, 1.3F),
                        pos.getCenter().x + rand.nextFloat(-0.5F, 0.5F),
                        pos.getCenter().y + 0.9F,
                        pos.getCenter().z + rand.nextFloat(-0.5F, 0.5F),
                        0,
                        0.8F,
                        0
                );
            }
        }
    }
}
