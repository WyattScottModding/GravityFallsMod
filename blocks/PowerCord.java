package blocks;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import init.BlockInit;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockObserver;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PowerCord extends Block implements IHasModel
{

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static AxisAlignedBB POWERCORD = new AxisAlignedBB(0.375D, 0D, 0D, 0.675D, 0.25D, 1.0D);
	private boolean isPowered = false;

	public PowerCord(String name)
	{
		super(Material.CIRCUITS);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsblocks);

		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) 
	{

		Block block = world.getBlockState(neighbor).getBlock();

		System.out.println("Neighbor Change");
		
		if(block == BlockInit.HYPER_DRIVE)
		{
			System.out.println("Found Hyper Drive");
			HyperDrive hyperDrive = (HyperDrive) block;

			if(hyperDrive.isOn())
			{
				isPowered = true;
				System.out.println("Is Powered");
			}
		}
		if(block == BlockInit.POWER_CORD)
		{
			PowerCord powercord = (PowerCord) block;
			
			if(powercord.isPowered())
			{
				isPowered = true;
				System.out.println("Is Powered");
			}
		}


		super.onNeighborChange(world, pos, neighbor);
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
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		//return new ItemStack(BlockInit.POWER_CORD);
		return null;
	}


	@Override
	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}


	public boolean isPowered()
	{
		return isPowered;
	}

	public void setPower(boolean power)
	{
		isPowered = power;
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
			POWERCORD = new AxisAlignedBB(0.375D, 0D, 0D, 0.675D, 0.25D, 1.0D);
		else if (face == EnumFacing.SOUTH) 
			POWERCORD = new AxisAlignedBB(0.375D, 0D, 0D, 0.675D, 0.25D, 1.0D);
		else if (face == EnumFacing.WEST) 
			POWERCORD = new AxisAlignedBB(0.0D, 0D, 0.375D, 1.0D, 0.25D, 0.675D);
		else if (face == EnumFacing.EAST) 
			POWERCORD = new AxisAlignedBB(0.0D, 0D, 0.375D, 1.0D, 0.25D, 0.675D);

		return POWERCORD;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) 
	{
		EnumFacing face = (EnumFacing)state.getValue(FACING);
		if (face == EnumFacing.NORTH) 
			POWERCORD = new AxisAlignedBB(0.375D, 0D, 0D, 0.675D, 0.25D, 1.0D);
		else if (face == EnumFacing.SOUTH) 
			POWERCORD = new AxisAlignedBB(0.375D, 0D, 0D, 0.675D, 0.25D, 1.0D);
		else if (face == EnumFacing.WEST) 
			POWERCORD = new AxisAlignedBB(0.0D, 0D, 0.375D, 1.0D, 0.25D, 0.675D);
		else if (face == EnumFacing.EAST) 
			POWERCORD = new AxisAlignedBB(0.0D, 0D, 0.375D, 1.0D, 0.25D, 0.675D);

		return POWERCORD;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		EnumFacing face = (EnumFacing)blockState.getValue(FACING);
		if (face == EnumFacing.NORTH) 
			POWERCORD = new AxisAlignedBB(0.375D, 0D, 0D, 0.675D, 0.25D, 1.0D);
		else if (face == EnumFacing.SOUTH) 
			POWERCORD = new AxisAlignedBB(0.375D, 0D, 0D, 0.675D, 0.25D, 1.0D);
		else if (face == EnumFacing.WEST) 
			POWERCORD = new AxisAlignedBB(0.0D, 0D, 0.375D, 1.0D, 0.25D, 0.675D);
		else if (face == EnumFacing.EAST) 
			POWERCORD = new AxisAlignedBB(0.0D, 0D, 0.375D, 1.0D, 0.25D, 0.675D);

		return POWERCORD;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) 
	{
		return new ItemStack(Item.getItemFromBlock(this));
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
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) 
	{
		if(world.getBlockState(fromPos).getBlock() != null)
		{
			if(world.getBlockState(fromPos).getBlock() instanceof PowerCord)
			{
				PowerCord cord = (PowerCord) world.getBlockState(fromPos).getBlock();

				if(cord.isPowered)
				{
					this.setPower(true);
					this.setLightLevel(15.0F);
				}
			}
		}

		super.neighborChanged(state, world, pos, block, fromPos);
	}
}