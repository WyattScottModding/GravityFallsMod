package blocks.portalcontrol;

import java.util.List;

import init.BlockInit;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class PortalControl extends Block implements IHasModel{

	public static AxisAlignedBB PORTALCONTROL;
	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	public PortalControl(String name, Material material)
	{
		super(material);
		this.setHardness(10.0F);
		this.setResistance(50.0F);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
	//	this.setCreativeTab(GravityFalls.gravityfallsblocks);
		this.setSoundType(SoundType.STONE);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));


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
		return true;
	}

	@Override
	public boolean isFullCube(IBlockState state) 
	{
		return true;
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
		return new ItemStack(Item.getItemFromBlock(this), 1, getMetaFromState(world.getBlockState(pos)));
	}


	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) 
	{
		ItemStack itemstack = playerIn.getHeldItem(hand);

		if (itemstack.isEmpty())
		{
			return true;
		}
		else
		{
			if(!worldIn.isRemote)
			{
				Item item = itemstack.getItem();
				EnumFacing face = (EnumFacing)state.getValue(FACING);
				
				if (item == ItemInit.BOOK1)
				{		
					IBlockState state2 = BlockInit.PORTAL_CONTROLBOOK1.getDefaultState().withProperty(FACING, face);
					worldIn.setBlockState(pos, state2);

					playerIn.getHeldItemMainhand().shrink(1);

					return true;
				}
				else if(item == ItemInit.BOOK2)
				{
					IBlockState state2 = BlockInit.PORTAL_CONTROLBOOK2.getDefaultState().withProperty(FACING, face);

					worldIn.setBlockState(pos, state2);


					playerIn.getHeldItemMainhand().shrink(1);

					return true;
				}
				else if(item == ItemInit.BOOK3)
				{
					IBlockState state2 = BlockInit.PORTAL_CONTROLBOOK3.getDefaultState().withProperty(FACING, face);
					worldIn.setBlockState(pos, state2);

					playerIn.getHeldItemMainhand().shrink(1);

					return true;
				}
			}

			return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
		}
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
	public EnumBlockRenderType getRenderType(IBlockState state) 
	{
		return EnumBlockRenderType.MODEL;
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

}
