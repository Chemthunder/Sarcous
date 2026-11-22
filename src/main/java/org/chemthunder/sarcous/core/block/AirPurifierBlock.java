package org.chemthunder.sarcous.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

/**
 * @author Chemthunder
 */
public class AirPurifierBlock extends Block {
    public AirPurifierBlock(Properties properties) {
        super(properties);
    }

    protected RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    protected void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        Random rand = new Random();

        serverLevel.addParticle(
                new DustParticleOptions(0xFFbfbfbf, 0.9F),
                blockPos.getCenter().x + rand.nextFloat(-0.7F, 0.7F),
                blockPos.getCenter().y,
                blockPos.getCenter().z + rand.nextFloat(-0.7F, 0.7F),
                0,
                rand.nextFloat(0.2F, 0.8F),
                0
        );

        super.randomTick(blockState, serverLevel, blockPos, randomSource);
    }
}
