package blocks;

import java.util.ArrayList;
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
import net.minecraft.world.Explosion;
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
	public boolean powered = false;

	public PowerCord(String name, boolean powered)
	{
		super(Material.CIRCUITS);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.powered = powered;

		if(!powered)
			this.setCreativeTab(GravityFalls.gravityfallsblocks);


		this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, Boolean.valueOf(false)).withProperty(EAST, Boolean.valueOf(false)).withProperty(SOUTH, Boolean.valueOf(false)).withProperty(WEST, Boolean.valueOf(false)));

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
		return new ItemStack(BlockInit.POWER_CORD);
	}


	@Override
	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}

	public static boolean isConnectedTo(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing direction)
	{
		BlockPos blockpos = pos.offset(direction);
		IBlockState iblockstate = worldIn.getBlockState(blockpos);
		Block block = iblockstate.getBlock();

		return (block == BlockInit.POWER_CORD) || (block == BlockInit.HYPER_DRIVE) || (block == BlockInit.HYPER_DRIVE_ON) || (block == BlockInit.PORTAL_LEVER) || (block == BlockInit.POWER_CORD_ON);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) 
	{
		world.scheduleBlockUpdate(pos, this, 0, 1);

		super.onBlockAdded(world, pos, state);
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) 
	{
		world.scheduleBlockUpdate(pos, this, 0, 1);
		
		if(powered && (world.getBlockState(fromPos).getBlock() != BlockInit.POWER_CORD && world.getBlockState(fromPos).getBlock() != BlockInit.POWER_CORD_ON))
		{
			List<BlockPos> listBlocks = new ArrayList<BlockPos>();
			listBlocks.add(pos);

			if(!shouldBePowered(world, pos, listBlocks))
				world.setBlockState(pos, BlockInit.POWER_CORD.getDefaultState());
		}

		super.neighborChanged(state, world, pos, blockIn, fromPos);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) 
	{
		//if(world.isBlockPowered(pos) || powerCordPower(world, pos))
		if(powerCordPower(world, pos))
		{
			//When Powered
			world.setBlockState(pos, BlockInit.POWER_CORD_ON.getDefaultState());
			world.notifyNeighborsOfStateChange(pos, this, false);
		}
		else
		{
			//When Not Powered
			world.setBlockState(pos, BlockInit.POWER_CORD.getDefaultState());
			world.notifyNeighborsOfStateChange(pos, this, false);
		}

		super.updateTick(world, pos, state, rand);
	}

	public boolean shouldBePowered(World world, BlockPos pos, List listBlocks)
	{
		List<BlockPos> list = new ArrayList<BlockPos>();

		list.add(pos.up());
		list.add(pos.down());
		list.add(pos.east());
		list.add(pos.west());
		list.add(pos.north());
		list.add(pos.south());

		for(BlockPos element : list)
		{
			if(!listBlocks.contains(element))
			{
				listBlocks.add(element);
				
				if(world.getBlockState(element).getBlock() == BlockInit.HYPER_DRIVE_ON)
				{
					return true;
				}
				else if(world.getBlockState(element).getBlock() == BlockInit.POWER_CORD_ON)
				{
					if(!shouldBePowered(world, element, listBlocks))
						world.setBlockState(element, BlockInit.POWER_CORD.getDefaultState());

				}
			}
		}


		return false;
	}

	public boolean powerCordPower(World world, BlockPos pos)
	{
		Block up = world.getBlockState(pos.up()).getBlock();
		Block down = world.getBlockState(pos.down()).getBlock();
		Block west = world.getBlockState(pos.west()).getBlock();
		Block east = world.getBlockState(pos.east()).getBlock();
		Block south = world.getBlockState(pos.south()).getBlock();
		Block north = world.getBlockState(pos.north()).getBlock();
				
		
		if (up == BlockInit.POWER_CORD_ON || up  == BlockInit.HYPER_DRIVE_ON)
		{
			return true;
		}
		else if (down == BlockInit.POWER_CORD_ON || down  == BlockInit.HYPER_DRIVE_ON)
		{
			return true;
		}
		else if (east == BlockInit.POWER_CORD_ON || east  == BlockInit.HYPER_DRIVE_ON)
		{
			return true;
		}
		else if (west == BlockInit.POWER_CORD_ON || west  == BlockInit.HYPER_DRIVE_ON)
		{
			return true;
		}
		else if (north == BlockInit.POWER_CORD_ON || north  == BlockInit.HYPER_DRIVE_ON)
		{
			return true;
		}
		else if (south == BlockInit.POWER_CORD_ON || south  == BlockInit.HYPER_DRIVE_ON)
		{
			return true;
		}
		else 
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
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) 
	{
		List<BlockPos> list = new ArrayList<BlockPos>();

		list.add(pos.up());
		list.add(pos.down());
		list.add(pos.east());
		list.add(pos.west());
		list.add(pos.north());
		list.add(pos.south());

		List<BlockPos> listBlocks = new ArrayList<BlockPos>();
		listBlocks.add(pos);
		
		for(BlockPos element : list)
		{
			if(world.getBlockState(element).getBlock() == BlockInit.POWER_CORD_ON)
			{
				if(!shouldBePowered(world, element, listBlocks))
					world.setBlockState(element, BlockInit.POWER_CORD.getDefaultState());
			}
		}

		
		super.onBlockHarvested(world, pos, state, player);
	}
	
	@Override
	public void onBlockDestroyedByExplosion(World world, BlockPos pos, Explosion explosionIn) 
	{
		List<BlockPos> list = new ArrayList<BlockPos>();

		list.add(pos.up());
		list.add(pos.down());
		list.add(pos.east());
		list.add(pos.west());
		list.add(pos.north());
		list.add(pos.south());

		List<BlockPos> listBlocks = new ArrayList<BlockPos>();
		listBlocks.add(pos);
		
		for(BlockPos element : list)
		{
			if(world.getBlockState(element).getBlock() == BlockInit.POWER_CORD_ON)
			{
				if(!shouldBePowered(world, element, listBlocks))
					world.setBlockState(element, BlockInit.POWER_CORD.getDefaultState());
			}
		}

		
		super.onBlockDestroyedByExplosion(world, pos, explosionIn);
	}
	
	@Override
	public int tickRate(World worldIn) 
	{
		return 1;
	}
	
}