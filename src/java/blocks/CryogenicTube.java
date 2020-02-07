package blocks;

import java.util.Random;

import init.BlockInit;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tileEntities.TileEntityCryogenicTube;

public class CryogenicTube extends Block implements IHasModel, ITileEntityProvider
{
	public static AxisAlignedBB CRYOGENICTUBE = new AxisAlignedBB(0D, 0D, 0D, 2.0D, 0.10D, 2.0D);

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyEnum<CryogenicTube.EnumHalf> HALF = PropertyEnum.<CryogenicTube.EnumHalf>create("half", CryogenicTube.EnumHalf.class);


	public CryogenicTube(String name, Material material)
	{
		super(material);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setSoundType(SoundType.METAL);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));

		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}


	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) 
	{
		
		EnumFacing face = (EnumFacing)state.getValue(FACING);
		if (face == EnumFacing.NORTH) 
			CRYOGENICTUBE = new AxisAlignedBB(1D, 0D, 0D, 3.0D, 0.10D, 2.0D);
		else if (face == EnumFacing.SOUTH) 
			CRYOGENICTUBE = new AxisAlignedBB(0.0D, 0D, 0D, 2.0D, 0.10D, 2.0D);
		else if (face == EnumFacing.WEST) 
			CRYOGENICTUBE = new AxisAlignedBB(1D, 0D, -1.0D, 2.0D, 0.10D, 2.0D);
		else if (face == EnumFacing.EAST) 
			CRYOGENICTUBE = new AxisAlignedBB(1D, 0D, 0D, 3.0D, 0.10D, 2.0D);

		
		return CRYOGENICTUBE;
	}


	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) 
	{
		EnumFacing face = (EnumFacing)state.getValue(FACING);
		if (face == EnumFacing.NORTH) 
			CRYOGENICTUBE = new AxisAlignedBB(1D, 0D, 0D, 3.0D, 0.10D, 2.0D);
		else if (face == EnumFacing.SOUTH) 
			CRYOGENICTUBE = new AxisAlignedBB(0.0D, 0D, 0D, 2.0D, 0.10D, 2.0D);
		else if (face == EnumFacing.WEST) 
			CRYOGENICTUBE = new AxisAlignedBB(1D, 0D, -1.0D, 2.0D, 0.10D, 2.0D);
		else if (face == EnumFacing.EAST) 
			CRYOGENICTUBE = new AxisAlignedBB(1D, 0D, 0D, 3.0D, 0.10D, 2.0D);
		
//		CRYOGENICTUBE = new AxisAlignedBB(0D, 0D, 0D, 0.0D, 0.0D, 0.0D);


		return CRYOGENICTUBE;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		EnumFacing face = (EnumFacing)blockState.getValue(FACING);
		if (face == EnumFacing.NORTH) 
			CRYOGENICTUBE = new AxisAlignedBB(1D, 0D, 0D, 3.0D, 0.10D, 2.0D);
		else if (face == EnumFacing.SOUTH) 
			CRYOGENICTUBE = new AxisAlignedBB(0.0D, 0D, 0D, 2.0D, 0.10D, 2.0D);
		else if (face == EnumFacing.WEST) 
			CRYOGENICTUBE = new AxisAlignedBB(1D, 0D, -1.0D, 2.0D, 0.10D, 2.0D);
		else if (face == EnumFacing.EAST) 
			CRYOGENICTUBE = new AxisAlignedBB(1D, 0D, 0D, 3.0D, 0.10D, 2.0D);
	 

		return CRYOGENICTUBE;
	}

	@Override
	public boolean isCollidable() {
		return false;
	}

	@Override
	public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid)
	{
		return false;
	}


	/**
	 * Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor
	 * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
	 * block, etc.
	 */
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos)
	{
		if (state.getValue(HALF) == CryogenicTube.EnumHalf.UPPER)
		{
			BlockPos blockpos = pos.down();
			IBlockState iblockstate = worldIn.getBlockState(blockpos);

			if (iblockstate.getBlock() != this)
			{
				worldIn.setBlockToAir(pos);
			}
			else if (blockIn != this)
			{
				iblockstate.neighborChanged(worldIn, blockpos, blockIn, fromPos);
			}
		}
		/*
        else
        {
            boolean flag1 = false;
            BlockPos blockpos1 = pos.up();
            IBlockState iblockstate1 = worldIn.getBlockState(blockpos1);

            if (iblockstate1.getBlock() != this)
            {
                worldIn.setBlockToAir(pos);
                flag1 = true;
            }

            if (!worldIn.getBlockState(pos.down()).isSideSolid(worldIn,  pos.down(), EnumFacing.UP))
            {
                worldIn.setBlockToAir(pos);
                flag1 = true;

                if (iblockstate1.getBlock() == this)
                {
                    worldIn.setBlockToAir(blockpos1);
                }
            }

            if (flag1)
            {
                if (!worldIn.isRemote)
                {
                    this.dropBlockAsItem(worldIn, pos, state, 0);
                }
            }
        }
		 */
	}

	/**
	 * Get the Item that this Block should drop when harvested.
	 */
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return state.getValue(HALF) == CryogenicTube.EnumHalf.UPPER ? Items.AIR : getItem();
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		return new ItemStack(ItemInit.CRYOGENICTUBE_ITEM);
	}

	public Item getItem()
	{
		return ItemInit.CRYOGENICTUBE_ITEM;
	}

	/**
	 * Checks if this block can be placed exactly at the given position.
	 */
	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		if (pos.getY() >= worldIn.getHeight() - 2)
		{
			return false;
		}
		else
		{
			IBlockState state = worldIn.getBlockState(pos.down());
			return (state.isTopSolid() || state.getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP) == BlockFaceShape.SOLID) && super.canPlaceBlockAt(worldIn, pos) && super.canPlaceBlockAt(worldIn, pos.up());
		}
	}

	/**
	 * Called before the Block is set to air in the world. Called regardless of if the player's tool can actually
	 * collect this block
	 */
	@Override
	public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		BlockPos blockpos = pos.down();
		BlockPos blockpos1 = pos.up();

		if (player.capabilities.isCreativeMode && state.getValue(HALF) == CryogenicTube.EnumHalf.UPPER && worldIn.getBlockState(blockpos).getBlock() == this)
		{
			worldIn.setBlockToAir(blockpos);
		}

		if (state.getValue(HALF) == CryogenicTube.EnumHalf.LOWER && worldIn.getBlockState(blockpos1).getBlock() == this)
		{
			if (player.capabilities.isCreativeMode)
			{
				worldIn.setBlockToAir(pos);
			}

			worldIn.setBlockToAir(blockpos1);
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

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	/**
	 * Get the actual Block state of this Block at the given position. This applies properties not visible in the
	 * metadata, such as fence connections.
	 */
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		if (state.getValue(HALF) == CryogenicTube.EnumHalf.UPPER) {
			IBlockState iblockstate1 = worldIn.getBlockState(pos.down());

			if (iblockstate1.getBlock() == this)
			{
				state = state.withProperty(FACING, iblockstate1.getValue(FACING));
			}
		}

		return state;
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
	public boolean isOpaqueCube(IBlockState state) 
	{
		return false;
	}

	@Override
	public boolean isFullBlock(IBlockState state) 
	{
		return false;
	}

	@Override
	public boolean causesSuffocation(IBlockState state) 
	{
		return false;
	}
	/*
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot)
	{
		return state.withRotation(rot);
	}
	 */
	/**
	 * Returns the blockstate with the given mirror of the passed blockstate. If inapplicable, returns the passed
	 * blockstate.
	 */
	/*
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
	{
		return state.withMirror(mirrorIn);
	}
	 */

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
			EntityPlayer player) 
	{
		return new ItemStack(Item.getItemFromBlock(this), 1, getMetaFromState(world.getBlockState(pos)));
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCryogenicTube();
	}

	@Override
	public boolean hasTileEntity() {
		return true;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) 
	{
		return (meta & 8) > 0 ? this.getDefaultState().withProperty(HALF, CryogenicTube.EnumHalf.UPPER) : this.getDefaultState().withProperty(HALF, CryogenicTube.EnumHalf.LOWER).withProperty(FACING, EnumFacing.getHorizontal(meta & 3).rotateYCCW());
	}


	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state)
	{
		int i = 0;

		if (state.getValue(HALF) == CryogenicTube.EnumHalf.UPPER)
		{
			i = i | 8;
		}
		else
		{
			i = i | ((EnumFacing)state.getValue(FACING)).rotateY().getHorizontalIndex();
		}

		return i;
	}

	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
	{
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	protected BlockStateContainer createBlockState() 
	{
		return new BlockStateContainer(this, new IProperty[] {HALF, FACING});
	}

	public static enum EnumHalf implements IStringSerializable
	{
		UPPER,
		LOWER;

		public String toString()
		{
			return this.getName();
		}

		public String getName()
		{
			return this == UPPER ? "upper" : "lower";
		}
	}
}