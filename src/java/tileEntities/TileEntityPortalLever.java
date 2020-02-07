package tileEntities;

import java.util.ArrayList;
import java.util.List;

import handlers.PortalBlocks;
import handlers.SoundsHandler;
import init.BlockInit;
import main.ConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ITickable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import network.MessagePlayCountdown;
import network.MessagePlayPortalSound;
import network.Messages;

public class TileEntityPortalLever extends TileEntity implements ITickable {

	public boolean powered = false;
	public boolean clicked = false;


	public NBTTagCompound nbt = new NBTTagCompound();

	public boolean returnDevice = false;

	public PortalBlocks allPortalBlocks = null;

	private boolean setBlocks = false;


	public TileEntityPortalLever() {
	}

	@Override
	public void update() {
		if(!world.isRemote) {
			init();
			initEntityLiving();
			PortalBlocks.portalMap.put(pos, nbt.getInteger("countdown"));
		}
	}


	public boolean activate() {
		this.powered = checkPower(world, pos);
		//this.hasPortalController = nbt.getBoolean("portalControl");

		if(!clicked) {
			clicked = true;

			if(powered)
			{
				if(nbt.getBoolean("portalControl"))
				{
					ArrayList<BlockPos> triangleBlocks = new ArrayList<BlockPos>();
					ArrayList<BlockPos> circleBlocks = new ArrayList<BlockPos>();
					ArrayList<BlockPos> ringBlocks = new ArrayList<BlockPos>();
					ArrayList<BlockPos> portalBlocks = new ArrayList<BlockPos>();


					//Triangle
					triangleBlocks.add(pos.add(-6, 0, 0));
					triangleBlocks.add(pos.add(-6, 1, 1));
					triangleBlocks.add(pos.add(-6, 1, -1));
					triangleBlocks.add(pos.add(-6, 2, 2));
					triangleBlocks.add(pos.add(-6, 2, -2));
					triangleBlocks.add(pos.add(-6, 3, 3));
					triangleBlocks.add(pos.add(-6, 3, -3));
					triangleBlocks.add(pos.add(-6, 4, 4));
					triangleBlocks.add(pos.add(-6, 4, -4));
					triangleBlocks.add(pos.add(-6, 5, 5));
					triangleBlocks.add(pos.add(-6, 5, -5));
					triangleBlocks.add(pos.add(-6, 6, 6));
					triangleBlocks.add(pos.add(-6, 6, -6));
					triangleBlocks.add(pos.add(-6, 7, 6));
					triangleBlocks.add(pos.add(-6, 7, -6));
					triangleBlocks.add(pos.add(-6, 8, 7));
					triangleBlocks.add(pos.add(-6, 8, -7));
					triangleBlocks.add(pos.add(-6, 9, 7));
					triangleBlocks.add(pos.add(-6, 9, -7));
					triangleBlocks.add(pos.add(-6, 10, 7));
					triangleBlocks.add(pos.add(-6, 10, -7));
					triangleBlocks.add(pos.add(-6, 10, 6));
					triangleBlocks.add(pos.add(-6, 10, -6));
					triangleBlocks.add(pos.add(-6, 10, 5));
					triangleBlocks.add(pos.add(-6, 10, -5));
					triangleBlocks.add(pos.add(-6, 10, 4));
					triangleBlocks.add(pos.add(-6, 10, -4));
					triangleBlocks.add(pos.add(-6, 10, 3));
					triangleBlocks.add(pos.add(-6, 10, -3));
					triangleBlocks.add(pos.add(-6, 10, 2));
					triangleBlocks.add(pos.add(-6, 10, -2));
					triangleBlocks.add(pos.add(-6, 10, 1));
					triangleBlocks.add(pos.add(-6, 10, -1));
					triangleBlocks.add(pos.add(-6, 10, 0));

					//Ring
					ringBlocks.add(pos.add(-6, 3, 0));
					ringBlocks.add(pos.add(-6, 3, 1));
					ringBlocks.add(pos.add(-6, 3, -1));
					ringBlocks.add(pos.add(-6, 4, -2));
					ringBlocks.add(pos.add(-6, 4, 2));
					ringBlocks.add(pos.add(-6, 5, -3));
					ringBlocks.add(pos.add(-6, 5, 3));
					ringBlocks.add(pos.add(-6, 6, -3));
					ringBlocks.add(pos.add(-6, 6, 3));
					ringBlocks.add(pos.add(-6, 7, -3));
					ringBlocks.add(pos.add(-6, 7, 3));
					ringBlocks.add(pos.add(-6, 8, -2));
					ringBlocks.add(pos.add(-6, 8, 2));
					ringBlocks.add(pos.add(-6, 9, -1));
					ringBlocks.add(pos.add(-6, 9, 1));
					ringBlocks.add(pos.add(-6, 9, 0));


					//Right Bottom
					circleBlocks.add(pos.add(1,-1,-6));
					circleBlocks.add(pos.add(2,-1,-6));
					circleBlocks.add(pos.add(0,-1,-7));
					circleBlocks.add(pos.add(1,-1,-7));
					circleBlocks.add(pos.add(2,-1,-7));
					circleBlocks.add(pos.add(3,-1,-7));
					circleBlocks.add(pos.add(0,-1,-8));
					circleBlocks.add(pos.add(1,-1,-8));
					circleBlocks.add(pos.add(2,-1,-8));
					circleBlocks.add(pos.add(3,-1,-8));
					circleBlocks.add(pos.add(1,-1,-9));
					circleBlocks.add(pos.add(2,-1,-9));

					//Right Top
					circleBlocks.add(pos.add(1, 11,-6));
					circleBlocks.add(pos.add(2, 11,-6));
					circleBlocks.add(pos.add(0,11,-7));
					circleBlocks.add(pos.add(1,11,-7));
					circleBlocks.add(pos.add(2,11,-7));
					circleBlocks.add(pos.add(3,11,-7));
					circleBlocks.add(pos.add(0,11,-8));
					circleBlocks.add(pos.add(1,11,-8));
					circleBlocks.add(pos.add(2,11,-8));
					circleBlocks.add(pos.add(3,11,-8));
					circleBlocks.add(pos.add(1,11,-9));
					circleBlocks.add(pos.add(2,11,-9));

					//Left Bottom
					circleBlocks.add(pos.add(1,-1,6));
					circleBlocks.add(pos.add(2,-1,6));
					circleBlocks.add(pos.add(0,-1,7));
					circleBlocks.add(pos.add(1,-1,7));
					circleBlocks.add(pos.add(2,-1,7));
					circleBlocks.add(pos.add(3,-1,7));
					circleBlocks.add(pos.add(0,-1,8));
					circleBlocks.add(pos.add(1,-1,8));
					circleBlocks.add(pos.add(2,-1,8));
					circleBlocks.add(pos.add(3,-1,8));
					circleBlocks.add(pos.add(1,-1,9));
					circleBlocks.add(pos.add(2,-1,9));

					//Left Top
					circleBlocks.add(pos.add(1, 11,6));
					circleBlocks.add(pos.add(2, 11,6));
					circleBlocks.add(pos.add(0,11,7));
					circleBlocks.add(pos.add(1,11,7));
					circleBlocks.add(pos.add(2,11,7));
					circleBlocks.add(pos.add(3,11,7));
					circleBlocks.add(pos.add(0,11,8));
					circleBlocks.add(pos.add(1,11,8));
					circleBlocks.add(pos.add(2,11,8));
					circleBlocks.add(pos.add(3,11,8));
					circleBlocks.add(pos.add(1,11,9));
					circleBlocks.add(pos.add(2,11,9));

					//Portal
					portalBlocks.add(pos.add(-6, 4, -1));
					portalBlocks.add(pos.add(-6, 4, 0));
					portalBlocks.add(pos.add(-6, 4, 1));
					portalBlocks.add(pos.add(-6, 5, -2));
					portalBlocks.add(pos.add(-6, 5, -1));
					portalBlocks.add(pos.add(-6, 5, 0));
					portalBlocks.add(pos.add(-6, 5, 1));
					portalBlocks.add(pos.add(-6, 5, 2));
					portalBlocks.add(pos.add(-6, 6, -2));
					portalBlocks.add(pos.add(-6, 6, -1));
					portalBlocks.add(pos.add(-6, 6, 0));
					portalBlocks.add(pos.add(-6, 6, 1));
					portalBlocks.add(pos.add(-6, 6, 2));
					portalBlocks.add(pos.add(-6, 7, -2));
					portalBlocks.add(pos.add(-6, 7, -1));
					portalBlocks.add(pos.add(-6, 7, 0));
					portalBlocks.add(pos.add(-6, 7, 1));
					portalBlocks.add(pos.add(-6, 7, 2));
					portalBlocks.add(pos.add(-6, 8, -1));
					portalBlocks.add(pos.add(-6, 8, 0));
					portalBlocks.add(pos.add(-6, 8, 1));


					allPortalBlocks = new PortalBlocks(pos, portalBlocks, ringBlocks, triangleBlocks, circleBlocks);


					//Change portal blocks if there is a power source and switched to on
					if(!nbt.getBoolean("portalActive"))
					{
						nbt.setBoolean("portalActive", true);
						nbt.setInteger("countdown", ConfigHandler.PORTAL_COUNTDOWN);

						setPortal(world);
					}
					else
					{
						nbt.setBoolean("portalActive", false);
						nbt.setInteger("countdown", -1);
						removePortal(world);
					}

					return true;
				}
				else {
					//If it is powered and switched off, turn the portal off
					removePortal(world);
				}
			}
		}
		clicked = false;

		return false;
	}

	public boolean checkPower(World world, BlockPos pos)
	{
		ArrayList<IBlockState> list = new ArrayList<IBlockState>();
		list.add(world.getBlockState(pos.up()));
		list.add(world.getBlockState(pos.down()));
		list.add(world.getBlockState(pos.north()));
		list.add(world.getBlockState(pos.south()));
		list.add(world.getBlockState(pos.west()));
		list.add(world.getBlockState(pos.east()));

		for(IBlockState element: list)
		{
			if(element.getBlock() == BlockInit.POWER_CORD_ON)
			{
				return true;
			}
		}
		return false;
	}


	public void init()
	{
		portalRender(world);

		if(!nbt.hasKey("portalActive"))
			nbt.setBoolean("portalActive", false);

		if(!nbt.hasKey("portalControl"))
			nbt.setBoolean("portalControl", false);

		if(!nbt.hasKey("countdown"))
			nbt.setInteger("countdown", -1);
	}

	public void initEntityLiving()
	{
		AxisAlignedBB entityPos = new AxisAlignedBB(pos.getX() - 200, pos.getY() -80, pos.getZ() - 200, pos.getX() + 120, pos.getY() + 150, pos.getZ() + 200);

		List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, entityPos);

		for(int j = 0; j < list.size(); ++j)
		{
			levitationRender(list.get(j));

			if(list.get(j) instanceof EntityPlayerMP && nbt.getInteger("countdown") == 381) {
				EntityPlayerMP serverPlayer = (EntityPlayerMP) list.get(j);
				Messages.INSTANCE.sendTo(new MessagePlayCountdown(),  serverPlayer);
			}
			if(list.get(j) instanceof EntityPlayerMP && nbt.getInteger("countdown") == 1) {
				EntityPlayerMP serverPlayer = (EntityPlayerMP) list.get(j);
				Messages.INSTANCE.sendTo(new MessagePlayPortalSound(),  serverPlayer);
			}
		}

	}


	public void portalRender(World world)
	{
		if(nbt.getBoolean("portalActive"))
		{
			if(nbt.getInteger("countdown") > 0)
				nbt.setInteger("countdown", nbt.getInteger("countdown") - 1);
			else if(nbt.getInteger("countdown") == 0)
				portalOn(world);

			if(allPortalBlocks != null)
			{
				Block block = world.getBlockState(allPortalBlocks.getLeverPos()).getBlock();

				if(block != BlockInit.PORTAL_LEVER)
					removePortal(world);
			}
		}
		else
		{
			removePortal(world);
		}
	}	

	public void levitationRender(EntityLivingBase entity)
	{
		if(nbt.getBoolean("portalActive"))
		{
			int countdown = nbt.getInteger("countdown");

			if(countdown == 35500)
				entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 20, 0));
			else if(countdown == 27000)
				entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 40, 0));
			else if(countdown == 20000)
				entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 60, 0));
			else if(countdown == 14000)
				entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 80, 0));
			else if(countdown == 8000)
				entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 100, 0));
			else if(countdown == 6000)
				entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 120, 0));
			else if(countdown == 4000)
				entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 140, 0));
			else if(countdown == 3000)
				entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 180, 0));
			else if(countdown == 2000)
				entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 250, 0));
			else if(countdown == 1500)
				entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 400, 0));
			else if(countdown == 1000)
				entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 1000, 0));
		}
	}

	public void portalOn(World world)
	{
		if(allPortalBlocks != null)
		{			
			nbt.setInteger("PortalX", allPortalBlocks.getPortal().get(0).getX() + 5);
			nbt.setInteger("PortalY", allPortalBlocks.getPortal().get(0).getY() - 4);
			nbt.setInteger("PortalZ", allPortalBlocks.getPortal().get(0).getZ());

			IBlockState state =  BlockInit.BLOCKTELEPORTER.getDefaultState();

			for(BlockPos element : allPortalBlocks.getPortal())
			{
				world.setBlockState(element, state);
			}
		}
	}

	public void setPortal(World world)
	{
		if(allPortalBlocks != null && !setBlocks)
		{
			IBlockState state =  BlockInit.LIGHT_WHITE.getDefaultState();

			for(BlockPos element : allPortalBlocks.getTriangle())
			{
				world.setBlockState(element, state);
			}

			state =  BlockInit.LIGHT_BLACK.getDefaultState();

			for(BlockPos element : allPortalBlocks.getRing())
			{
				world.setBlockState(element, state);
			}

			state =  BlockInit.LIGHT_CYAN.getDefaultState();

			for(BlockPos element : allPortalBlocks.getCircle())
			{
				world.setBlockState(element, state);
			}

			setBlocks = true;
		}
	}

	public void removePortal(World world)
	{
		if(allPortalBlocks != null && setBlocks)
		{
			IBlockState state = Blocks.CONCRETE.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.WHITE);

			for(BlockPos element : allPortalBlocks.getTriangle())
			{
				world.setBlockState(element, state);
			}

			state = Blocks.CONCRETE.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.BLACK);

			for(BlockPos element : allPortalBlocks.getRing())
			{
				world.setBlockState(element, state);
			}

			state = Blocks.CONCRETE.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.CYAN);

			for(BlockPos element : allPortalBlocks.getCircle())
			{
				world.setBlockState(element, state);
			}

			state =  Blocks.AIR.getDefaultState();

			for(BlockPos element : allPortalBlocks.getPortal())
			{
				world.setBlockState(element, state);
			}
			setBlocks = false;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		nbt.setBoolean("portalControl", compound.getBoolean("portalControl"));
		nbt.setBoolean("portalActive", compound.getBoolean("portalActive"));
		nbt.setInteger("countdown", compound.getInteger("countdown"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		compound.setBoolean("portalControl", nbt.getBoolean("portalControl"));
		compound.setBoolean("portalActive", nbt.getBoolean("portalActive"));
		compound.setInteger("countdown", nbt.getInteger("countdown"));

		return compound;
	}

}