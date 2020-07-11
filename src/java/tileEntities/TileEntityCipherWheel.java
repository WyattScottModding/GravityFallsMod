package tileEntities;

import java.util.List;

import entity.EntityCipherWheel;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityCipherWheel extends TileEntity implements ITickable {

	public EntityCipherWheel cipher_wheel;
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	public TileEntityCipherWheel() {	
	}
	
	@Override
	public void update() {
	}
	
	@Override
	public void onLoad() {
		super.onLoad();
		
		float yaw = 0;
		EnumFacing facing = world.getBlockState(this.getPos()).getValue(FACING);
		
		if(facing == EnumFacing.EAST)
			yaw = 90;
		else if(facing == EnumFacing.SOUTH)
			yaw = 180;
		else if(facing == EnumFacing.WEST)
			yaw = 270;
		
		cipher_wheel = new EntityCipherWheel(world);
		cipher_wheel.setNoGravity(false);
		cipher_wheel.setLocationAndAngles(this.pos.getX(), this.pos.getY() + .15, this.pos.getZ(), yaw, 0);
		world.spawnEntity(cipher_wheel);
	}
	
	public void onUnload()
	{
		cipher_wheel.setDead();
	}
}