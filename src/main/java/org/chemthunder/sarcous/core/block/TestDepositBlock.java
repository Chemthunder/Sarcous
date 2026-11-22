package org.chemthunder.sarcous.core.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.chemthunder.sarcous.api.block.DrillableBlock;

/**
 * @author Chemthunder
 */
public class TestDepositBlock extends Block implements DrillableBlock {
    public TestDepositBlock(Properties settings) {
        super(settings);
    }

    public SoundEvent getDrillSound(BlockState state, BlockPos pos, Level world) {
        return SoundEvents.BASALT_BREAK;
    }

    public void onDrilled(BlockState state, BlockPos pos, Level world) {
        Containers.dropItemStack(world, pos.getCenter().x, pos.getCenter().y, pos.getCenter().z, new ItemStack(Items.IRON_AXE));
    }
}
