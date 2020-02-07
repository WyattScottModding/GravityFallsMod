package tileEntities;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityBlockTeleporter extends TileEntity implements ITickable {

	public TileEntityBlockTeleporter() {
	}
	
	@Override
	public void update() {
		AxisAlignedBB bb = new AxisAlignedBB(this.pos.getX() - 5, this.pos.getY() - 6, this.pos.getZ() - 5, this.pos.getX() + 5, this.pos.getY() + 6, this.pos.getZ() + 5);

		List<EntityPlayer> list = world.getEntitiesWithinAABB(EntityPlayer.class, bb);

		for(int j = 0; j < list.size(); ++j)
		{	
			EntityPlayer entity = list.get(j);
			
			double xDif = entity.posX - pos.getX();
			double yDif = entity.posY - pos.getY();
			double zDif = entity.posZ - pos.getZ();


			if(xDif != 0)
				entity.motionX -= (.008 * xDif);


			if(yDif != 0)
				entity.motionY -= (.035 * yDif);


			if(zDif != 0)
				entity.motionZ -= (.008 * zDif);
		}
	}
}