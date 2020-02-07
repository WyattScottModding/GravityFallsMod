package tileEntities;

import java.util.List;

import init.PotionInit;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityCryogenicTube extends TileEntity implements ITickable {

	public TileEntityCryogenicTube() {
	}
	
	@Override
	public void update() {

		if(hasPower())
		{
			AxisAlignedBB Box = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() - 1, pos.getY() + 1, pos.getZ() + 1);

			if(world.getBlockState(pos).getValue(BlockHorizontal.FACING) == EnumFacing.NORTH) 
				Box = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() - 1, pos.getY() + 1, pos.getZ() + 2);

			else if(world.getBlockState(pos).getValue(BlockHorizontal.FACING) == EnumFacing.SOUTH) 
				Box = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 2, pos.getY() + 1, pos.getZ() - 2);

			else if(world.getBlockState(pos).getValue(BlockHorizontal.FACING) == EnumFacing.EAST) 
				Box = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() - 2, pos.getY() + 1, pos.getZ() - 2);

			else if(world.getBlockState(pos).getValue(BlockHorizontal.FACING) == EnumFacing.WEST) 
				Box = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 2, pos.getY() + 1, pos.getZ() + 2);


			List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, Box);

			for (EntityLivingBase entity : list) {
				entity.addPotionEffect(new PotionEffect(PotionInit.FREEZE_EFFECT, 10, 0));
				entity.setJumping(false);
				entity.motionX = 0;
				entity.motionY = 0;
				entity.motionZ = 0;

				
				if(entity instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) entity;
					
					player.velocityChanged = true;
					
				}
			}
		}
	}

	public boolean hasPower() {

		if(world.isBlockPowered(pos))
			return true;
		else if(world.isBlockPowered(pos.up()))
			return true;

		if(world.getBlockState(pos).getValue(BlockHorizontal.FACING) == EnumFacing.NORTH) {
			if(world.isBlockPowered(pos.add(-1, 0, 0)))
				return true;
			else if(world.isBlockPowered(pos.add(0, 0, 1)))
				return true;
			else if(world.isBlockPowered(pos.add(-1, 0, 1)))
				return true;
			else if(world.isBlockPowered(pos.add(-1, 0, 0).up()))
				return true;
			else if(world.isBlockPowered(pos.add(0, 0, 1).up()))
				return true;
			else if(world.isBlockPowered(pos.add(-1, 0, 1).up()))
				return true;
		}
		else if(world.getBlockState(pos).getValue(BlockHorizontal.FACING) == EnumFacing.SOUTH) {
			if(world.isBlockPowered(pos.add(1, 0, 0)))
				return true;
			else if(world.isBlockPowered(pos.add(0, 0, -1)))
				return true;
			else if(world.isBlockPowered(pos.add(1, 0, -1)))
				return true;
			else if(world.isBlockPowered(pos.add(1, 0, 0).up()))
				return true;
			else if(world.isBlockPowered(pos.add(0, 0, -1).up()))
				return true;
			else if(world.isBlockPowered(pos.add(1, 0, -1).up()))
				return true;
		}
		else if(world.getBlockState(pos).getValue(BlockHorizontal.FACING) == EnumFacing.EAST) {
			if(world.isBlockPowered(pos.add(-1, 0, 0)))
				return true;
			else if(world.isBlockPowered(pos.add(0, 0, -1)))
				return true;
			else if(world.isBlockPowered(pos.add(-1, 0, -1)))
				return true;
			else if(world.isBlockPowered(pos.add(-1, 0, 0).up()))
				return true;
			else if(world.isBlockPowered(pos.add(0, 0, -1).up()))
				return true;
			else if(world.isBlockPowered(pos.add(-1, 0, -1).up()))
				return true;
		}
		else if(world.getBlockState(pos).getValue(BlockHorizontal.FACING) == EnumFacing.WEST) {
			if(world.isBlockPowered(pos.add(1, 0, 0)))
				return true;
			else if(world.isBlockPowered(pos.add(0, 0, 1)))
				return true;
			else if(world.isBlockPowered(pos.add(1, 0, 1)))
				return true;
			else if(world.isBlockPowered(pos.add(1, 0, 0).up()))
				return true;
			else if(world.isBlockPowered(pos.add(0, 0, 1).up()))
				return true;
			else if(world.isBlockPowered(pos.add(1, 0, 1).up()))
				return true;
		}

		return false;
	}
}