package blocks;

import java.util.ArrayList;
import java.util.Random;

import org.fusesource.jansi.Ansi.Color;

import handlers.PortalBlocks;
import handlers.RegistryHandler;
import handlers.SoundsHandler;
import init.BlockInit;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class PortalLever extends Block implements IHasModel
{
	public static final AxisAlignedBB PORTALLEVER1 = new AxisAlignedBB(0.0D, 0D, 0.25D, 1.0D, 1.2875D, .75D);
	public static final AxisAlignedBB PORTALLEVER2 = new AxisAlignedBB(0.25D, 0D, 0.0D, 0.75D, 1.2875D, 1.0D);
	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	public boolean powered = false;
	public boolean clicked = false;

	public PortalLever(String name, Material material)
	{
		super(material);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setSoundType(SoundType.STONE);
		this.setHardness(6.0F);
		this.setResistance(20.0F);
		this.setTickRandomly(true);

		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) 
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) 
	{
		return false;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) 
	{	
		return worldIn.getBlockState(pos.down()).isFullCube();	
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) 
	{
		if (!worldIn.isRemote) 
		{
			IBlockState north = worldIn.getBlockState(pos.north());
			IBlockState south = worldIn.getBlockState(pos.south());
			IBlockState west = worldIn.getBlockState(pos.west());
			IBlockState east = worldIn.getBlockState(pos.east());
			EnumFacing face = (EnumFacing)state.getValue(FACING);

			if (face == EnumFacing.NORTH && north.isFullBlock() && !south.isFullBlock()) face = EnumFacing.NORTH;
			else if (face == EnumFacing.SOUTH && south.isFullBlock() && !north.isFullBlock()) face = EnumFacing.SOUTH;
			else if (face == EnumFacing.WEST && west.isFullBlock() && !east.isFullBlock()) face = EnumFacing.WEST;
			else if (face == EnumFacing.EAST && east.isFullBlock() && !west.isFullBlock()) face = EnumFacing.EAST;
			worldIn.setBlockState(pos, state.withProperty(FACING, face), 2);
		}
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) 
	{
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) 
	{
		worldIn.setBlockState(pos, this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing()), 2);
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot)
	{
		return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) 
	{
		return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
	}

	@Override
	protected BlockStateContainer createBlockState() 
	{
		return new BlockStateContainer(this, new IProperty[] {FACING});
	}

	@Override
	public IBlockState getStateFromMeta(int meta) 
	{
		EnumFacing facing = EnumFacing.getFront(meta);
		if(facing.getAxis() == EnumFacing.Axis.Y) facing = EnumFacing.NORTH;
		return this.getDefaultState().withProperty(FACING, facing);
	}

	@Override
	public int getMetaFromState(IBlockState state) 
	{
		return ((EnumFacing)state.getValue(FACING)).getIndex();
	}	


	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) 
	{
		EnumFacing face = (EnumFacing)state.getValue(FACING);
		if (face == EnumFacing.NORTH) 
			return PORTALLEVER1;
		else if (face == EnumFacing.SOUTH) 
			return PORTALLEVER1;
		else if (face == EnumFacing.WEST) 
			return PORTALLEVER2;
		else if (face == EnumFacing.EAST) 
			return PORTALLEVER2;

		return PORTALLEVER1;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) 
	{
		EnumFacing face = (EnumFacing)state.getValue(FACING);
		if (face == EnumFacing.NORTH) 
			return PORTALLEVER1;
		else if (face == EnumFacing.SOUTH) 
			return PORTALLEVER1;
		else if (face == EnumFacing.WEST) 
			return PORTALLEVER2;
		else if (face == EnumFacing.EAST) 
			return PORTALLEVER2;

		return PORTALLEVER1;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		EnumFacing face = (EnumFacing)state.getValue(FACING);
		if (face == EnumFacing.NORTH) 
			return PORTALLEVER1;
		else if (face == EnumFacing.SOUTH) 
			return PORTALLEVER1;
		else if (face == EnumFacing.WEST) 
			return PORTALLEVER2;
		else if (face == EnumFacing.EAST) 
			return PORTALLEVER2;

		return PORTALLEVER1;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) 
	{
		if(!clicked)
		{				
			this.powered = checkPower(worldIn, pos);
			//this.powered = true;

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
			portalBlocks.add(pos.add(-6, 5, -1));
			portalBlocks.add(pos.add(-6, 5, -2));
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


			PortalBlocks blocks = new PortalBlocks(pos, portalBlocks, ringBlocks, triangleBlocks, circleBlocks);
			RegistryHandler.portalBlocks = blocks;


			//Change portal blocks if there is a power source and switched to on
			if(powered)
			{
				RegistryHandler.portalActive = true;
				RegistryHandler.countdown = 36000;
				RegistryHandler.countdown = 1000;
				RegistryHandler.setPortal();
			}
			else
			{
				RegistryHandler.portalActive = false;
				RegistryHandler.removePortal();
			}
			if(powered && RegistryHandler.countdown == 0)
			{
				worldIn.setBlockState(pos.up(), Blocks.DIRT.getDefaultState());
			}

			clicked = true;

			return true;
		}

		clicked = false;

		return false;
	}


	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) 
	{
		this.powered = checkPower(world, pos);
		
		if(!powered)
		{
			RegistryHandler.portalActive = false;
			RegistryHandler.removePortal();
		}

		super.neighborChanged(state, world, pos, blockIn, fromPos);
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


	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) 
	{
		return new ItemStack(Item.getItemFromBlock(this));
	}

}