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
import net.minecraft.block.properties.PropertyBool;
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
import scala.reflect.internal.Trees.This;

public class PowerCord extends Block implements IHasModel
{

	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");
	public static AxisAlignedBB POWERCORD = new AxisAlignedBB(0.0D, 0D, 0.0D, 1.0D, 0.2D, 1.0D);
	private boolean isPowered = false;

	public PowerCord(String name)
	{
		super(Material.CIRCUITS);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsblocks);
		this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, Boolean.valueOf(false)).withProperty(EAST, Boolean.valueOf(false)).withProperty(SOUTH, Boolean.valueOf(false)).withProperty(WEST, Boolean.valueOf(false)));
		this.setTickRandomly(true);

		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
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

	public static boolean isConnectedTo(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing direction)
	{
		BlockPos blockpos = pos.offset(direction);
		IBlockState iblockstate = worldIn.getBlockState(blockpos);
		Block block = iblockstate.getBlock();

		return (block == BlockInit.POWER_CORD) || (block == BlockInit.HYPER_DRIVE) || (block == BlockInit.PORTAL_CONTROLALLBOOKS);
	}

	public boolean shouldBePowered(IBlockAccess world, BlockPos pos)
	{
		IBlockState iblockstate = world.getBlockState(pos);
		Block block = iblockstate.getBlock();


		if(block == BlockInit.HYPER_DRIVE)
		{
			HyperDrive hyperdrive = (HyperDrive) block;

			if(hyperdrive.isOn())
			{
				return true;
			}
		}
		else if(block == BlockInit.POWER_CORD)
		{
			PowerCord powercord = (PowerCord) block;

			if(powercord.isPowered)
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Get the actual Block state of this Block at the given position. This applies properties not visible in the
	 * metadata, such as fence connections.
	 */
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		return state.withProperty(NORTH, Boolean.valueOf(isConnectedTo(worldIn, pos, state, EnumFacing.NORTH))).withProperty(EAST, Boolean.valueOf(isConnectedTo(worldIn, pos, state, EnumFacing.EAST))).withProperty(SOUTH, Boolean.valueOf(isConnectedTo(worldIn, pos, state, EnumFacing.SOUTH))).withProperty(WEST, Boolean.valueOf(isConnectedTo(worldIn, pos, state, EnumFacing.WEST)));
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot)
	{
		switch (rot)
		{
		case CLOCKWISE_180:
			return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(EAST, state.getValue(WEST)).withProperty(SOUTH, state.getValue(NORTH)).withProperty(WEST, state.getValue(EAST));
		case COUNTERCLOCKWISE_90:
			return state.withProperty(NORTH, state.getValue(EAST)).withProperty(EAST, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(WEST)).withProperty(WEST, state.getValue(NORTH));
		case CLOCKWISE_90:
			return state.withProperty(NORTH, state.getValue(WEST)).withProperty(EAST, state.getValue(NORTH)).withProperty(SOUTH, state.getValue(EAST)).withProperty(WEST, state.getValue(SOUTH));
		default:
			return state;
		}
	}


	/**
	 * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
	 * blockstate.
	 */
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
	{
		switch (mirrorIn)
		{
		case LEFT_RIGHT:
			return state.withProperty(NORTH, state.getValue(SOUTH)).withProperty(SOUTH, state.getValue(NORTH));
		case FRONT_BACK:
			return state.withProperty(EAST, state.getValue(WEST)).withProperty(WEST, state.getValue(EAST));
		default:
			return super.withMirror(state, mirrorIn);
		}
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {NORTH, EAST, WEST, SOUTH});
	}

	@Override
	public int getMetaFromState(IBlockState state) 
	{
		return 0;
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY,
			float hitZ, int meta, EntityLivingBase placer) 
	{
		return this.getDefaultState().withProperty(NORTH, false).withProperty(SOUTH, false).withProperty(EAST, false).withProperty(WEST, false);
	}


	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) 
	{
		return POWERCORD;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) 
	{
		return POWERCORD;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		return POWERCORD;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) 
	{
		return new ItemStack(Item.getItemFromBlock(this));
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) 
	{

		super.updateTick(world, pos, state, rand);
	}

	@Override
	public boolean requiresUpdates()
	{
		return true;
	}

	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) 
	{
		BlockPos posNorth = pos.north();
		BlockPos posSouth = pos.south();
		BlockPos posWest = pos.west();
		BlockPos posEast = pos.east();


		if(shouldBePowered(world, posNorth) || shouldBePowered(world, posSouth) || shouldBePowered(world, posWest) || shouldBePowered(world, posEast))
			this.isPowered = true;
		else
			this.isPowered = false;

		System.out.println("Update Cord");


		super.onNeighborChange(world, pos, neighbor);
	}


}