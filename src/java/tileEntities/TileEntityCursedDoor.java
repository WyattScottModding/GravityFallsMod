package tileEntities;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityCursedDoor extends TileEntity implements ITickable {

	public TileEntityCursedDoor() {
	}

	@Override
	public void update() {		
		if(!world.isRemote) {
			AxisAlignedBB Box = new AxisAlignedBB(pos.getX() - 1, pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() - 1);

			List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, Box);

			for (EntityLivingBase element : list) {
				int x = (int) (Math.random() * 20000) - 10000 + pos.getX();
				int z = (int) (Math.random() * 20000) - 10000 + pos.getZ();
				int y = calculateHeight(x, z);

				element.setPositionAndUpdate(x, y, z);
			}
		}
	}
	
	private int calculateHeight(int x, int z)
	{
		int y = world.getHeight();
		boolean foundGround = false;

		while(!foundGround && y-- >= 0)
		{
			Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
			
			if(block != Blocks.AIR) {
				foundGround = true;
			}
		}

		return y + 1;
	}
}