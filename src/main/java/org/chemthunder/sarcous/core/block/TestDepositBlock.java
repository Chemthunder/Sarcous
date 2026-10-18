package org.chemthunder.sarcous.core.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.chemthunder.sarcous.core.block.api.DrillableBlock;

/**
 * @author Chemthunder
 */
public class TestDepositBlock extends Block implements DrillableBlock {
    public TestDepositBlock(Settings settings) {
        super(settings);
    }

    public SoundEvent getDrillSound(BlockState state, BlockPos pos, World world) {
        return SoundEvents.BLOCK_BASALT_BREAK;
    }

    public void onDrilled(BlockState state, BlockPos pos, World world) {
        ItemScatterer.spawn(world, pos.toCenterPos().x, pos.toCenterPos().y, pos.toCenterPos().z, new ItemStack(Items.IRON_AXE));
    }
}
