package blocks;

import java.util.Random;

import javax.annotation.Nullable;

import blocks.HyperDrive;
import blocks.PowerCord;
import handlers.RegistryHandler;
import init.BlockInit;
import init.ItemInit;
import items.BlackLight;
import items.Book1;
import items.Book2;
import items.Book3;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockProperties;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PortalControl extends Block implements IHasModel
{
	public static AxisAlignedBB PORTALCONTROL;
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool LIGHT = PropertyBool.create("light");
	public static final PropertyBool BOOK1 = PropertyBool.create("book1");
	public static final PropertyBool BOOK2 = PropertyBool.create("book2");
	public static final PropertyBool BOOK3 = PropertyBool.create("book3");

	public boolean light = false;
	public boolean book1 = false;
	public boolean book2 = false;
	public boolean book3 = false;

	public boolean portalActive = false;
	private boolean isPowered = false;

	public PortalControl(String name, Material material)
	{
		super(material);
		this.setHardness(2.0F);
		this.setResistance(50.0F);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsblocks);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(LIGHT, false).withProperty(BOOK1, false).withProperty(BOOK2, false).withProperty(BOOK3, false));

		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

	@Override
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
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) 
	{
		EnumFacing face = (EnumFacing)state.getValue(FACING);
		if (face == EnumFacing.NORTH) 
			PORTALCONTROL = new AxisAlignedBB(0D, 0D, 0D, 2.0D, 2.00D, 1.0D);
		else if (face == EnumFacing.SOUTH) 
			PORTALCONTROL = new AxisAlignedBB(-1.0D, 0D, 0D, 1.0D, 2.00D, 1.0D);
		else if (face == EnumFacing.WEST) 
			PORTALCONTROL = new AxisAlignedBB(0D, 0D, -1.0D, 1.0D, 2.00D, 1.0D);
		else if (face == EnumFacing.EAST) 
			PORTALCONTROL = new AxisAlignedBB(0D, 0D, 0D, 1.0D, 2.00D, 2.0D);

		return PORTALCONTROL;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) 
	{
		EnumFacing face = (EnumFacing)state.getValue(FACING);
		if (face == EnumFacing.NORTH) 
			PORTALCONTROL = new AxisAlignedBB(0D, 0D, 0D, 2.0D, 2.00D, 1.0D);
		else if (face == EnumFacing.SOUTH) 
			PORTALCONTROL = new AxisAlignedBB(-1.0D, 0D, 0D, 1.0D, 2.00D, 1.0D);
		else if (face == EnumFacing.WEST) 
			PORTALCONTROL = new AxisAlignedBB(0D, 0D, -1.0D, 1.0D, 2.00D, 1.0D);
		else if (face == EnumFacing.EAST) 
			PORTALCONTROL = new AxisAlignedBB(0D, 0D, 0D, 1.0D, 2.00D, 2.0D);

		return PORTALCONTROL;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		EnumFacing face = (EnumFacing)blockState.getValue(FACING);
		if (face == EnumFacing.NORTH) 
			PORTALCONTROL = new AxisAlignedBB(0D, 0D, 0D, 2.0D, 2.00D, 1.0D);
		else if (face == EnumFacing.SOUTH) 
			PORTALCONTROL = new AxisAlignedBB(-1.0D, 0D, 0D, 1.0D, 2.00D, 1.0D);
		else if (face == EnumFacing.WEST) 
			PORTALCONTROL = new AxisAlignedBB(0D, 0D, -1.0D, 1.0D, 2.00D, 1.0D);
		else if (face == EnumFacing.EAST) 
			PORTALCONTROL = new AxisAlignedBB(0D, 0D, 0D, 1.0D, 2.00D, 2.0D);

		return PORTALCONTROL;
	}


	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
			EntityPlayer player) 
	{
		return new ItemStack(Item.getItemFromBlock(this));
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) 
	{
		if(!worldIn.isRemote)
		{
			ItemStack itemstack = player.getHeldItem(hand);

			//Put Book1 on the PortalControl
			if(itemstack.getItem() instanceof Book1)
			{
				EnumFacing face = (EnumFacing)state.getValue(FACING);

				IBlockState state2 = BlockInit.PORTAL_CONTROL.getDefaultState().withProperty(FACING, face).withProperty(BOOK1, true).withProperty(BOOK2, book2).withProperty(BOOK3, book3).withProperty(LIGHT, light);
				worldIn.setBlockState(pos, state2);
				book1 = true;
				itemstack.shrink(1);
			}
			//Put Book2 on the PortalControl
			else if(itemstack.getItem() instanceof Book2)
			{
				EnumFacing face = (EnumFacing)state.getValue(FACING);

				IBlockState state2 = BlockInit.PORTAL_CONTROL.getDefaultState().withProperty(FACING, face).withProperty(BOOK1, book1).withProperty(BOOK2, true).withProperty(BOOK3, book3).withProperty(LIGHT, light);
				worldIn.setBlockState(pos, state2);
				book2 = true;
				itemstack.shrink(1);
			}
			//Put Book3 on the PortalControl
			else if(itemstack.getItem() instanceof Book3)
			{
				EnumFacing face = (EnumFacing)state.getValue(FACING);

				IBlockState state2 = BlockInit.PORTAL_CONTROL.getDefaultState().withProperty(FACING, face).withProperty(BOOK1, book1).withProperty(BOOK2, book2).withProperty(BOOK3, true).withProperty(LIGHT, light);
				worldIn.setBlockState(pos, state2);
				book3 = true;
				itemstack.shrink(1);
			}
			//Turn BlackLight ON
			else if(player.getHeldItemMainhand().getItem() instanceof BlackLight && !light)
			{
				EnumFacing face = (EnumFacing)state.getValue(FACING);

				IBlockState state2 = BlockInit.PORTAL_CONTROL.getDefaultState().withProperty(FACING, face).withProperty(BOOK1, book1).withProperty(BOOK2, book2).withProperty(BOOK3, book3).withProperty(LIGHT, true);
				worldIn.setBlockState(pos, state2);
				light = true;
			}
			//Turn BlackLight OFF
			else if(!(player.getHeldItemMainhand().getItem() instanceof BlackLight) && light)
			{				
				EnumFacing face = (EnumFacing)state.getValue(FACING);

				IBlockState state2 = BlockInit.PORTAL_CONTROL.getDefaultState().withProperty(FACING, face).withProperty(BOOK1, book1).withProperty(BOOK2, book2).withProperty(BOOK3, book3).withProperty(LIGHT, false);
				worldIn.setBlockState(pos, state2);
				light = false;
			}

			EnumFacing face = (EnumFacing)state.getValue(FACING);

			IBlockState state2 = BlockInit.PORTAL_CONTROL.getDefaultState().withProperty(FACING, face).withProperty(BOOK1, book1).withProperty(BOOK2, book2).withProperty(BOOK3, book3).withProperty(LIGHT, light);
			worldIn.setBlockState(pos, state2);
		}

		if(book1 && book2 && book3)
			RegistryHandler.portalControl = true;
		else
		{
			RegistryHandler.portalControl = false;
			RegistryHandler.removePortal();
		}


		return super.onBlockActivated(worldIn, pos, state, player, hand, facing, hitX, hitY, hitZ);
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
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing()).withProperty(BOOK1, book1).withProperty(BOOK2, book2).withProperty(BOOK3, book3).withProperty(LIGHT, light);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) 
	{
		worldIn.setBlockState(pos, this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing()).withProperty(BOOK1, false).withProperty(BOOK2, false).withProperty(BOOK3, false).withProperty(LIGHT, false), 2);
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) 
	{
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot)
	{
		return state.withRotation(rot);
	}


	/**
	 * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
	 * blockstate.
	 */
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
	{
		return state.withMirror(mirrorIn);
	}

	@Override
	protected BlockStateContainer createBlockState() 
	{
		return new BlockStateContainer(this, new IProperty[] {LIGHT, FACING, BOOK1, BOOK2, BOOK3});
	}

	@Override
	public IBlockState getStateFromMeta(int meta) 
	{
		EnumFacing facing = EnumFacing.getFront(meta);
		if(facing.getAxis() == EnumFacing.Axis.Y) facing = EnumFacing.NORTH;
		return this.getDefaultState().withProperty(FACING, facing).withProperty(BOOK1, book1).withProperty(BOOK2, book2).withProperty(BOOK3, book3).withProperty(LIGHT, light);
	}

	@Override
	public int getMetaFromState(IBlockState state) 
	{
		//return ((EnumFacing)state.getValue(FACING)).getIndex();
		return 0;
	}	

	@Override
	public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) 
	{
		BlockPos posNorth = pos.north();
		BlockPos posSouth = pos.south();
		BlockPos posWest = pos.west();
		BlockPos posEast = pos.east();

		if(shouldBePowered(world, posNorth) || shouldBePowered(world, posSouth) || shouldBePowered(world, posWest) || shouldBePowered(world, posEast))
		{
			//	this.isPowered = true;
			//	RegistryHandler.portalActive = true;
		}
		else
		{
			//	this.isPowered = false;
			//	RegistryHandler.portalActive = false;
		}


		super.onNeighborChange(world, pos, neighbor);
	}

	public static boolean isConnectedTo(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing direction)
	{
		BlockPos blockpos = pos.offset(direction);
		IBlockState iblockstate = worldIn.getBlockState(blockpos);
		Block block = iblockstate.getBlock();

		return block == BlockInit.POWER_CORD;
	}

	public boolean shouldBePowered(IBlockAccess world, BlockPos pos)
	{
		IBlockState iblockstate = world.getBlockState(pos);
		Block block = iblockstate.getBlock();

		this.isPowered = false;

		if(block == BlockInit.POWER_CORD)
		{
			PowerCord powercord = (PowerCord) block;
		}

		return false;
	}

	public boolean isPowered()
	{
		return isPowered;
	}

	@Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) 
	{
		RegistryHandler.portalControl = false;
		RegistryHandler.removePortal();
		super.onBlockDestroyedByExplosion(worldIn, pos, explosionIn);
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState state) 
	{
		if(!world.isRemote)
		{
			if(book1)
			{
				EntityItem item = new EntityItem(world);
				item.setItem(new ItemStack(ItemInit.BOOK1));
				item.setPosition(pos.getX(), pos.getY(), pos.getZ());
				world.spawnEntity(item);
				book1 = false;
			}
			if(book2)
			{
				EntityItem item = new EntityItem(world);
				item.setItem(new ItemStack(ItemInit.BOOK2));
				item.setPosition(pos.getX(), pos.getY(), pos.getZ());
				world.spawnEntity(item);
				book2 = false;
			}
			if(book3)
			{
				EntityItem item = new EntityItem(world);
				item.setItem(new ItemStack(ItemInit.BOOK3));
				item.setPosition(pos.getX(), pos.getY(), pos.getZ());
				world.spawnEntity(item);
				book3 = false;
			}
		}

		RegistryHandler.portalControl = false;
		RegistryHandler.removePortal();

		super.onBlockDestroyedByPlayer(world, pos, state);
	}

	@Override
	public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player) 
	{
		if(!world.isRemote)
		{
			if(book1)
			{
				EntityItem item = new EntityItem(world);
				item.setItem(new ItemStack(ItemInit.BOOK1));
				item.setPosition(pos.getX(), pos.getY(), pos.getZ());
				world.spawnEntity(item);
				book1 = false;
			}
			if(book2)
			{
				EntityItem item = new EntityItem(world);
				item.setItem(new ItemStack(ItemInit.BOOK2));
				item.setPosition(pos.getX(), pos.getY(), pos.getZ());
				world.spawnEntity(item);
				book2 = false;
			}
			if(book3)
			{
				EntityItem item = new EntityItem(world);
				item.setItem(new ItemStack(ItemInit.BOOK3));
				item.setPosition(pos.getX(), pos.getY(), pos.getZ());
				world.spawnEntity(item);
				book3 = false;
			}		
		}

		RegistryHandler.portalControl = false;
		RegistryHandler.removePortal();

		super.onBlockHarvested(world, pos, state, player);
	}
}