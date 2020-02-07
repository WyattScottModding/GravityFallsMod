package tileEntities;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityComputer extends TileEntity implements ITickable
{
	public int countdown = -5;

	public TileEntityComputer() {
	}

	@Override
	public void update()
	{
	//	if(!world.isRemote) {
	//		TileEntityPortalLever tileEntityPortalLever = searchForLever();
	//		countdown = tileEntityPortalLever.nbt.getInteger("countdown");
	//	}
	}



	public TileEntityPortalLever searchForLever() {
		for(int x = pos.getX() - 20; x <= pos.getX() + 20; x++) {
			for(int y = pos.getY() - 20; y <= pos.getY() + 20; y++) {
				for(int z = pos.getZ() - 20; z <= pos.getZ() + 20; z++) {
					if(world.getTileEntity(new BlockPos(x, y, z)) instanceof TileEntityPortalLever) {
						return (TileEntityPortalLever) world.getTileEntity(new BlockPos(x, y, z));
					}
				}
			}
		}
		return null;
	}

}