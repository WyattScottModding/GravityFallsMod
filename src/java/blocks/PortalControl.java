package blocks;

import javax.annotation.Nullable;

import init.BlockInit;
import init.ItemInit;
import items.BlackLight;
import items.Book1;
import items.Book2;
import items.Book3;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import updates.PortalUpdate;

public class PortalControl extends Block implements IHasModel
{
	public static AxisAlignedBB PORTALCONTROL;
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool LIGHT = PropertyBool.create("light");
	public static final PropertyBool BOOK1 = PropertyBool.create("book1");
	public static final PropertyBool BOOK2 = PropertyBool.create("book2");
	public static final PropertyBool BOOK3 = PropertyBool.create("book3");


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
			boolean book1 = false;
			boolean book2 = false;
			boolean book3 = false;
			boolean light = false;

			ItemStack itemstack = player.getHeldItem(hand);

			//Put Book1 on the PortalControl
			if(itemstack.getItem() instanceof Book1)
			{
				EnumFacing face = (EnumFacing)state.getValue(FACING);

				if(!(Boolean)state.getProperties().get(BOOK1))
					itemstack.shrink(1);

				book1 = true;
				book2 = (Boolean)state.getProperties().get(BOOK2);
				book3 = (Boolean)state.getProperties().get(BOOK3);
				light = (Boolean)state.getProperties().get(LIGHT);
				
				if(book1 && book2 && book3)
					PortalUpdate.nbt.setBoolean("portalControl", true);
				else
				{
					PortalUpdate.nbt.setBoolean("portalControl", false);
				}

				IBlockState state2 = BlockInit.PORTAL_CONTROL.getDefaultState().withProperty(FACING, face).withProperty(BOOK1, true).withProperty(BOOK2, book2).withProperty(BOOK3, book3).withProperty(LIGHT, light);
				worldIn.setBlockState(pos, state2);
			}
			//Put Book2 on the PortalControl
			else if(itemstack.getItem() instanceof Book2)
			{
				EnumFacing face = (EnumFacing)state.getValue(FACING);

				if(!(Boolean)state.getProperties().get(BOOK2))
					itemstack.shrink(1);

				book1 = (Boolean)state.getProperties().get(BOOK1);
				book2 = true;
				book3 = (Boolean)state.getProperties().get(BOOK3);
				light = (Boolean)state.getProperties().get(LIGHT);
				
				if(book1 && book2 && book3)
					PortalUpdate.nbt.setBoolean("portalControl", true);
				else
				{
					PortalUpdate.nbt.setBoolean("portalControl", false);
				}


				IBlockState state2 = BlockInit.PORTAL_CONTROL.getDefaultState().withProperty(FACING, face).withProperty(BOOK1, book1).withProperty(BOOK2, true).withProperty(BOOK3, book3).withProperty(LIGHT, light);
				worldIn.setBlockState(pos, state2);
			}
			//Put Book3 on the PortalControl
			else if(itemstack.getItem() instanceof Book3)
			{
				EnumFacing face = (EnumFacing)state.getValue(FACING);

				if(!(Boolean)state.getProperties().get(BOOK3))
					itemstack.shrink(1);

				book1 = (Boolean)state.getProperties().get(BOOK1);
				book2 = (Boolean)state.getProperties().get(BOOK2);
				book3 = true;
				light = (Boolean)state.getProperties().get(LIGHT);
				
				if(book1 && book2 && book3)
					PortalUpdate.nbt.setBoolean("portalControl", true);
				else
				{
					PortalUpdate.nbt.setBoolean("portalControl", false);
				}


				IBlockState state2 = BlockInit.PORTAL_CONTROL.getDefaultState().withProperty(FACING, face).withProperty(BOOK1, book1).withProperty(BOOK2, book2).withProperty(BOOK3, true).withProperty(LIGHT, light);
				worldIn.setBlockState(pos, state2);				
			}
			//Turn BlackLight ON
			else if(player.getHeldItemMainhand().getItem() instanceof BlackLight)
			{
				EnumFacing face = (EnumFacing)state.getValue(FACING);

				book1 = (Boolean)state.getProperties().get(BOOK1);
				book2 = (Boolean)state.getProperties().get(BOOK2);
				book3 = (Boolean)state.getProperties().get(BOOK3);
				
				if(book1 && book2 && book3)
					PortalUpdate.nbt.setBoolean("portalControl", true);
				else
				{
					PortalUpdate.nbt.setBoolean("portalControl", false);
				}

				IBlockState state2 = BlockInit.PORTAL_CONTROL.getDefaultState().withProperty(FACING, face).withProperty(BOOK1, book1).withProperty(BOOK2, book2).withProperty(BOOK3, book3).withProperty(LIGHT, true);
				worldIn.setBlockState(pos, state2);
			}
			//Turn BlackLight OFF
			else if(!(player.getHeldItemMainhand().getItem() instanceof BlackLight))
			{				
				EnumFacing face = (EnumFacing)state.getValue(FACING);

				book1 = (Boolean)state.getProperties().get(BOOK1);
				book2 = (Boolean)state.getProperties().get(BOOK2);
				book3 = (Boolean)state.getProperties().get(BOOK3);
				
				if(book1 && book2 && book3)
					PortalUpdate.nbt.setBoolean("portalControl", true);
				else
				{
					PortalUpdate.nbt.setBoolean("portalControl", false);
				}

				IBlockState state2 = BlockInit.PORTAL_CONTROL.getDefaultState().withProperty(FACING, face).withProperty(BOOK1, book1).withProperty(BOOK2, book2).withProperty(BOOK3, book3).withProperty(LIGHT, false);
				worldIn.setBlockState(pos, state2);
			}

			book1 = (Boolean)state.getProperties().get(BOOK1);
			book2 = (Boolean)state.getProperties().get(BOOK2);
			book3 = (Boolean)state.getProperties().get(BOOK3);
			light = (Boolean)state.getProperties().get(LIGHT);

			
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

		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
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
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta)).withProperty(BOOK1, Boolean.valueOf((meta & 9) > 0)).withProperty(BOOK2, Boolean.valueOf((meta & 8) > 0)).withProperty(BOOK3, Boolean.valueOf((meta & 7) > 0)).withProperty(LIGHT, Boolean.valueOf((meta & 6) > 0));
	}

	@Nullable
	public static EnumFacing getFacing(int meta)
	{
		int i = meta & 7;
		return i > 5 ? null : EnumFacing.getFront(i);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		int i = 0;
		i = i | ((EnumFacing)state.getValue(FACING)).getHorizontalIndex();

		if (((Boolean)state.getValue(BOOK1)).booleanValue())
		{
			i |= 9;
		}

		if (((Boolean)state.getValue(BOOK2)).booleanValue())
		{
			i |= 8;
		}
		if (((Boolean)state.getValue(BOOK3)).booleanValue())
		{
			i |= 7;
		}
		if (((Boolean)state.getValue(LIGHT)).booleanValue())
		{
			i |= 6;
		}
		return i;
	}

	@Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) 
	{
		PortalUpdate.nbt.setBoolean("portalControl", false);

		super.onBlockDestroyedByExplosion(worldIn, pos, explosionIn);
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState state) 
	{
		if(!world.isRemote)
		{
			boolean book1 = (Boolean)state.getProperties().get(BOOK1);
			boolean book2 = (Boolean)state.getProperties().get(BOOK2);
			boolean book3 = (Boolean)state.getProperties().get(BOOK3);
			boolean light = (Boolean)state.getProperties().get(LIGHT);

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

		PortalUpdate.nbt.setBoolean("portalControl", false);

		super.onBlockDestroyedByPlayer(world, pos, state);
	}
}