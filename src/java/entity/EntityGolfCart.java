package entity;

import net.minecraft.entity.item.EntityBoat;
import net.minecraft.world.World;

public class EntityGolfCart extends EntityBoat
{


	public EntityGolfCart(World worldIn, double x, double y, double z)
    {
        super(worldIn);
        this.setPosition(x, y, z);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
		this.setSize(1.9F, 1.9F);

    }
	
}
