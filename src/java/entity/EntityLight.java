package entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

public class EntityLight extends Entity implements ITickable
{
	public World world;
	
	public EntityLight(World worldIn)
	{
		super(worldIn);
		this.world = worldIn;
	}

	public int ticksExisted;

	public void update()
	{
		this.ticksExisted += 1;
		if (this.ticksExisted > 2) 
		{
			this.world.destroyBlock(this.getPosition(), false);
		}

	}

	@Override
	protected void entityInit() 
	{
		
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) 
	{
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) 
	{
		
	}
}
