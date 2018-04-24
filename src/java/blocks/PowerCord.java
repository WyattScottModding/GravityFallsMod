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
import net.minecraft.block.BlockObserver;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PowerCord extends BlockRedstoneWire implements IHasModel{

	public static final AxisAlignedBB POWERCORD = new AxisAlignedBB(0.375D, 0D, 0D, 0.25D, 0.25D, 1.0D);
	public static final PropertyEnum<PowerCord.EnumAttachPosition> NORTH = PropertyEnum.<PowerCord.EnumAttachPosition>create("north", PowerCord.EnumAttachPosition.class);
	public static final PropertyEnum<PowerCord.EnumAttachPosition> EAST = PropertyEnum.<PowerCord.EnumAttachPosition>create("east", PowerCord.EnumAttachPosition.class);
	public static final PropertyEnum<PowerCord.EnumAttachPosition> SOUTH = PropertyEnum.<PowerCord.EnumAttachPosition>create("south", PowerCord.EnumAttachPosition.class);
	public static final PropertyEnum<PowerCord.EnumAttachPosition> WEST = PropertyEnum.<PowerCord.EnumAttachPosition>create("west", PowerCord.EnumAttachPosition.class);
	protected static final AxisAlignedBB[] REDSTONE_WIRE_AABB = new AxisAlignedBB[] {new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 0.8125D), new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 0.8125D), new AxisAlignedBB(0.0D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 1.0D), new AxisAlignedBB(0.1875D, 0.0D, 0.0D, 0.8125D, 0.0625D, 0.8125D), new AxisAlignedBB(0.1875D, 0.0D, 0.0D, 0.8125D, 0.0625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.8125D, 0.0625D, 0.8125D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.8125D, 0.0625D, 1.0D), new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 1.0D, 0.0625D, 0.8125D), new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 1.0D, 0.0625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.1875D, 1.0D, 0.0625D, 0.8125D), new AxisAlignedBB(0.0D, 0.0D, 0.1875D, 1.0D, 0.0625D, 1.0D), new AxisAlignedBB(0.1875D, 0.0D, 0.0D, 1.0D, 0.0625D, 0.8125D), new AxisAlignedBB(0.1875D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 0.8125D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D)};
	private final Set<BlockPos> blocksNeedingUpdate = Sets.<BlockPos>newHashSet();
	private boolean canProvidePower = true;

	public PowerCord(String name)
	{
		super();
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsblocks);


		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
		//Variants
	}

	private static int getAABBIndex(IBlockState state)
	{
		int i = 0;
		boolean flag = state.getValue(NORTH) != PowerCord.EnumAttachPosition.NONE;
		boolean flag1 = state.getValue(EAST) != PowerCord.EnumAttachPosition.NONE;
		boolean flag2 = state.getValue(SOUTH) != PowerCord.EnumAttachPosition.NONE;
		boolean flag3 = state.getValue(WEST) != PowerCord.EnumAttachPosition.NONE;

		if (flag || flag2 && !flag && !flag1 && !flag3)
		{
			i |= 1 << EnumFacing.NORTH.getHorizontalIndex();
		}

		if (flag1 || flag3 && !flag && !flag1 && !flag2)
		{
			i |= 1 << EnumFacing.EAST.getHorizontalIndex();
		}

		if (flag2 || flag && !flag1 && !flag2 && !flag3)
		{
			i |= 1 << EnumFacing.SOUTH.getHorizontalIndex();
		}

		if (flag3 || flag1 && !flag && !flag2 && !flag3)
		{
			i |= 1 << EnumFacing.WEST.getHorizontalIndex();
		}

		return i;
	}

	private PowerCord.EnumAttachPosition getAttachPosition(IBlockAccess worldIn, BlockPos pos, EnumFacing direction)
	{
		BlockPos blockpos = pos.offset(direction);
		IBlockState iblockstate = worldIn.getBlockState(pos.offset(direction));

		if (!canConnectTo(worldIn.getBlockState(blockpos), direction, worldIn, blockpos) && (iblockstate.isNormalCube() || !canConnectUpwardsTo(worldIn, blockpos.down())))
		{
			IBlockState iblockstate1 = worldIn.getBlockState(pos.up());

			if (!iblockstate1.isNormalCube())
			{
				boolean flag = worldIn.getBlockState(blockpos).isSideSolid(worldIn, blockpos, EnumFacing.UP) || worldIn.getBlockState(blockpos).getBlock() == Blocks.GLOWSTONE;

				if (flag && canConnectUpwardsTo(worldIn, blockpos.up()))
				{
					if (iblockstate.isBlockNormalCube())
					{
						return PowerCord.EnumAttachPosition.UP;
					}

					return PowerCord.EnumAttachPosition.SIDE;
				}
			}

			return PowerCord.EnumAttachPosition.NONE;
		}
		else
		{
			return PowerCord.EnumAttachPosition.SIDE;
		}
	}

	private IBlockState updateSurroundingPowerCords(World worldIn, BlockPos pos, IBlockState state)
	{
		state = this.calculateCurrentChanges(worldIn, pos, pos, state);
		List<BlockPos> list = Lists.newArrayList(this.blocksNeedingUpdate);
		this.blocksNeedingUpdate.clear();

		for (BlockPos blockpos : list)
		{
			worldIn.notifyNeighborsOfStateChange(blockpos, this, false);
		}

		return state;
	}

	private IBlockState calculateCurrentChanges(World worldIn, BlockPos pos1, BlockPos pos2, IBlockState state)
	{
		IBlockState iblockstate = state;
		int i = ((Integer)state.getValue(POWER)).intValue();
		int j = 0;
		j = this.getMaxCurrentStrength(worldIn, pos2, j);
		this.canProvidePower = false;
		int k = worldIn.isBlockIndirectlyGettingPowered(pos1);
		this.canProvidePower = true;

		if (k > 0 && k > j - 1)
		{
			j = k;
		}

		int l = 0;

		for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
		{
			BlockPos blockpos = pos1.offset(enumfacing);
			boolean flag = blockpos.getX() != pos2.getX() || blockpos.getZ() != pos2.getZ();

			if (flag)
			{
				l = this.getMaxCurrentStrength(worldIn, blockpos, l);
			}

			if (worldIn.getBlockState(blockpos).isNormalCube() && !worldIn.getBlockState(pos1.up()).isNormalCube())
			{
				if (flag && pos1.getY() >= pos2.getY())
				{
					l = this.getMaxCurrentStrength(worldIn, blockpos.up(), l);
				}
			}
			else if (!worldIn.getBlockState(blockpos).isNormalCube() && flag && pos1.getY() <= pos2.getY())
			{
				l = this.getMaxCurrentStrength(worldIn, blockpos.down(), l);
			}
		}

		if (l > j)
		{
			j = l - 1;
		}
		else if (j > 0)
		{
			--j;
		}
		else
		{
			j = 0;
		}

		if (k > j - 1)
		{
			j = k;
		}

		if (i != j)
		{
			state = state.withProperty(POWER, Integer.valueOf(j));

			if (worldIn.getBlockState(pos1) == iblockstate)
			{
				worldIn.setBlockState(pos1, state, 2);
			}

			this.blocksNeedingUpdate.add(pos1);

			for (EnumFacing enumfacing1 : EnumFacing.values())
			{
				this.blocksNeedingUpdate.add(pos1.offset(enumfacing1));
			}
		}

		return state;
	}

	private void notifyWireNeighborsOfStateChange(World worldIn, BlockPos pos)
	{
		if (worldIn.getBlockState(pos).getBlock() == this)
		{
			worldIn.notifyNeighborsOfStateChange(pos, this, false);

			for (EnumFacing enumfacing : EnumFacing.values())
			{
				worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this, false);
			}
		}
	}

	private int getMaxCurrentStrength(World worldIn, BlockPos pos, int strength)
	{
		if (worldIn.getBlockState(pos).getBlock() != this)
		{
			return strength;
		}
		else
		{
			int i = ((Integer)worldIn.getBlockState(pos).getValue(POWER)).intValue();
			return i > strength ? i : strength;
		}
	}

	private boolean isPowerSourceAt(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
	{
		BlockPos blockpos = pos.offset(side);
		IBlockState iblockstate = worldIn.getBlockState(blockpos);
		boolean flag = iblockstate.isNormalCube();
		boolean flag1 = worldIn.getBlockState(pos.up()).isNormalCube();

		if (!flag1 && flag && canConnectUpwardsTo(worldIn, blockpos.up()))
		{
			return true;
		}
		else if (canConnectTo(iblockstate, side, worldIn, pos))
		{
			return true;
		}
		else if (iblockstate.getBlock() == Blocks.POWERED_REPEATER && iblockstate.getValue(BlockRedstoneDiode.FACING) == side)
		{
			return true;
		}
		else
		{
			return !flag && canConnectUpwardsTo(worldIn, blockpos.down());
		}
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
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return POWERCORD;
	}

	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state)
	{
		//return new ItemStack(BlockInit.POWER_CORD);
		return null;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		state = state.withProperty(WEST, this.getAttachPosition(worldIn, pos, EnumFacing.WEST));
		state = state.withProperty(EAST, this.getAttachPosition(worldIn, pos, EnumFacing.EAST));
		state = state.withProperty(NORTH, this.getAttachPosition(worldIn, pos, EnumFacing.NORTH));
		state = state.withProperty(SOUTH, this.getAttachPosition(worldIn, pos, EnumFacing.SOUTH));
		return state;
	}

	@Override
	public void registerModels()
	{
		for(int i = 0; i < EnumAttachPosition.values().length; i++)
		{
			GravityFalls.proxy.registerVariantRenderer(Item.getItemFromBlock(this), i, "powercord_" + EnumAttachPosition.values()[i].getName(), "inventory");
		}	
	}

	@Override
	public boolean canProvidePower(IBlockState state)
	{
		return this.canProvidePower;
	}

	static enum EnumAttachPosition implements IStringSerializable
	{
		UP("up"),
		SIDE("side"),
		NONE("none");

		private final String name;

		private EnumAttachPosition(String name)
		{
			this.name = name;
		}

		public String toString()
		{
			return this.getName();
		}

		public String getName()
		{
			return this.name;
		}
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	{
		if (!worldIn.isRemote)
		{
			this.updateSurroundingRedstone(worldIn, pos, state);

			for (EnumFacing enumfacing : EnumFacing.Plane.VERTICAL)
			{
				worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this, false);
			}

			for (EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL)
			{
				this.notifyWireNeighborsOfStateChange(worldIn, pos.offset(enumfacing1));
			}

			for (EnumFacing enumfacing2 : EnumFacing.Plane.HORIZONTAL)
			{
				BlockPos blockpos = pos.offset(enumfacing2);

				if (worldIn.getBlockState(blockpos).isNormalCube())
				{
					this.notifyWireNeighborsOfStateChange(worldIn, blockpos.up());
				}
				else
				{
					this.notifyWireNeighborsOfStateChange(worldIn, blockpos.down());
				}
			}
		}
	}


	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		super.breakBlock(worldIn, pos, state);

		if (!worldIn.isRemote)
		{
			for (EnumFacing enumfacing : EnumFacing.values())
			{
				worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this, false);
			}

			this.updateSurroundingRedstone(worldIn, pos, state);

			for (EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL)
			{
				this.notifyWireNeighborsOfStateChange(worldIn, pos.offset(enumfacing1));
			}

			for (EnumFacing enumfacing2 : EnumFacing.Plane.HORIZONTAL)
			{
				BlockPos blockpos = pos.offset(enumfacing2);

				if (worldIn.getBlockState(blockpos).isNormalCube())
				{
					this.notifyWireNeighborsOfStateChange(worldIn, blockpos.up());
				}
				else
				{
					this.notifyWireNeighborsOfStateChange(worldIn, blockpos.down());
				}
			}
		}
	}

	private IBlockState updateSurroundingRedstone(World worldIn, BlockPos pos, IBlockState state)
	{
		state = this.calculateCurrentChanges(worldIn, pos, pos, state);
		List<BlockPos> list = Lists.newArrayList(this.blocksNeedingUpdate);
		this.blocksNeedingUpdate.clear();

		for (BlockPos blockpos : list)
		{
			worldIn.notifyNeighborsOfStateChange(blockpos, this, false);
		}

		return state;
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
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
	{
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((Integer)state.getValue(POWER)).intValue();
	}
	
    
    @SideOnly(Side.CLIENT)
    public static int colorMultiplier(int p_176337_0_)
    {
        float f = (float)p_176337_0_ / 15.0F;
        float f1 = f * 0.6F + 0.4F;

        if (p_176337_0_ == 0)
        {
            f1 = 0.3F;
        }

        float f2 = f * f * 0.7F - 0.5F;
        float f3 = f * f * 0.6F - 0.7F;

        if (f2 < 0.0F)
        {
            f2 = 0.0F;
        }

        if (f3 < 0.0F)
        {
            f3 = 0.0F;
        }

        int i = MathHelper.clamp((int)(f1 * 255.0F), 0, 255);
        int j = MathHelper.clamp((int)(f2 * 255.0F), 0, 255);
        int k = MathHelper.clamp((int)(f3 * 255.0F), 0, 255);
        return -16777216 | i << 16 | j << 8 | k;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
    {
        int i = ((Integer)stateIn.getValue(POWER)).intValue();

        if (i != 0)
        {
            double d0 = (double)pos.getX() + 0.5D + ((double)rand.nextFloat() - 0.5D) * 0.2D;
            double d1 = (double)((float)pos.getY() + 0.0625F);
            double d2 = (double)pos.getZ() + 0.5D + ((double)rand.nextFloat() - 0.5D) * 0.2D;
            float f = (float)i / 15.0F;
            float f1 = f * 0.6F + 0.4F;
            float f2 = Math.max(0.0F, f * f * 0.7F - 0.5F);
            float f3 = Math.max(0.0F, f * f * 0.6F - 0.7F);
            worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d0, d1, d2, (double)f1, (double)f2, (double)f3);
        }
    
    }
    
    @Override
    public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        return !this.canProvidePower ? 0 : blockState.getWeakPower(blockAccess, pos, side);
    }

    @Override
    public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
    {
        if (!this.canProvidePower)
        {
            return 0;
        }
        else
        {
            int i = ((Integer)blockState.getValue(POWER)).intValue();

            if (i == 0)
            {
                return 0;
            }
            else if (side == EnumFacing.UP)
            {
                return i;
            }
            else
            {
                EnumSet<EnumFacing> enumset = EnumSet.<EnumFacing>noneOf(EnumFacing.class);

                for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
                {
                    if (this.isPowerSourceAt(blockAccess, pos, enumfacing))
                    {
                        enumset.add(enumfacing);
                    }
                }

                if (side.getAxis().isHorizontal() && enumset.isEmpty())
                {
                    return i;
                }
                else if (enumset.contains(side) && !enumset.contains(side.rotateYCCW()) && !enumset.contains(side.rotateY()))
                {
                    return i;
                }
                else
                {
                    return 0;
                }
            }
        }
    }
}
