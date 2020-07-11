package blocks;

import java.util.Random;

import javax.annotation.Nullable;

import commands.Teleport;
import init.BlockInit;
import init.ItemInit;
import main.ConfigHandler;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tileEntities.TileEntityBlockTeleporter;

public class BlockTeleporter extends Block implements IHasModel, ITileEntityProvider
{
	public static final PropertyEnum<EnumFacing.Axis> AXIS = PropertyEnum.<EnumFacing.Axis>create("axis", EnumFacing.Axis.class, EnumFacing.Axis.X, EnumFacing.Axis.Z);
	protected static final AxisAlignedBB X_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D);
	protected static final AxisAlignedBB Z_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D);
	protected static final AxisAlignedBB Y_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D);

	public BlockTeleporter(String name)
	{
		super(Material.PORTAL);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setSoundType(SoundType.STONE);
		this.setBlockUnbreakable();
		this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumFacing.Axis.Z));
		this.setLightLevel(0.5F);


		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public Block setTickRandomly(boolean shouldTick) 
	{
		return super.setTickRandomly(true);
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity) 
	{				
		if(entity != null && !world.isRemote)
		{
			if(entity instanceof EntityPlayerMP){
				EntityPlayerMP player = (EntityPlayerMP) entity;
				attemptTeleport(world, player);
			}
			/*
			else if(entity instanceof EntityLivingBase) {
				EntityLivingBase entityLiving = (EntityLivingBase) entity;
				attemptTeleportEntity(world, entityLiving);
			}
			*/
		}

	}

	@Override
	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
			EntityPlayer player) 
	{
		return new ItemStack(Item.getItemFromBlock(this), 1, getMetaFromState(world.getBlockState(pos)));
	}

	public void attemptTeleport(World world, EntityPlayerMP player)
	{
		WorldServer worldServer = world.getMinecraftServer().getWorld(ConfigHandler.NIGHTMARE_REALM);
		int dimension = worldServer.provider.getDimension();

		if(dimension == ConfigHandler.NIGHTMARE_REALM) {
			BlockPos pos = generateSpawn(worldServer);
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			/*
			try
			{
				worldServer.getMinecraftServer().getPlayerList().transferPlayerToDimension(player, ConfigHandler.NIGHTMARE_REALM, new Teleport(worldServer, x, y, z));
				player.setPositionAndUpdate(x, y, z);
			}
			catch(NullPointerException exception)
			{
				return;
			}
			 */
			Teleport.teleportToDimension(player, ConfigHandler.NIGHTMARE_REALM, x, y, z);
		}
	}

	public void attemptTeleportEntity(World world, EntityLivingBase entity)
	{
		WorldServer worldServer = world.getMinecraftServer().getWorld(ConfigHandler.NIGHTMARE_REALM);
		int dimension = worldServer.provider.getDimension();

		if(dimension == ConfigHandler.NIGHTMARE_REALM) {
			BlockPos pos = generateSpawn(worldServer);
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();


			entity.changeDimension(ConfigHandler.NIGHTMARE_REALM);
			entity.setPositionAndUpdate(x, y, z);

		}
	}

	private BlockPos generateSpawn(World world)
	{
		int x = (int) ((Math.random() * 200) - 100);
		int z = (int) ((Math.random() * 200) - 100);

		int y = 100;
		boolean foundGround = false;

		while(!foundGround && y-- >= 0)
		{
			Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
			Block blockUp = world.getBlockState(new BlockPos(x, y + 1, z)).getBlock();
			Block blockDown = world.getBlockState(new BlockPos(x, y - 1, z)).getBlock();

			if(block == Blocks.AIR && blockUp == Blocks.AIR && blockDown != Blocks.AIR) {
				foundGround = true;
			}
		}

		if(foundGround)
			return new BlockPos(x, y, z);
		else
			return generateSpawn(world);
	}

	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		switch ((EnumFacing.Axis)state.getValue(AXIS))
		{
		case X:
			return X_AABB;
		case Y:
		default:
			return Y_AABB;
		case Z:
			return Z_AABB;
		}
	}

	@Nullable
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		return NULL_AABB;
	}

	public static int getMetaForAxis(EnumFacing.Axis axis)
	{
		if (axis == EnumFacing.Axis.X)
		{
			return 1;
		}
		else
		{
			return axis == EnumFacing.Axis.Z ? 2 : 0;
		}
	}

	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
	{
		return BlockFaceShape.UNDEFINED;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}


	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		pos = pos.offset(side);
		EnumFacing.Axis enumfacing$axis = null;

		if (blockState.getBlock() == this)
		{
			enumfacing$axis = (EnumFacing.Axis)blockState.getValue(AXIS);

			if (enumfacing$axis == null)
			{
				return false;
			}

			if (enumfacing$axis == EnumFacing.Axis.Z && side != EnumFacing.EAST && side != EnumFacing.WEST)
			{
				return false;
			}

			if (enumfacing$axis == EnumFacing.Axis.X && side != EnumFacing.SOUTH && side != EnumFacing.NORTH)
			{
				return false;
			}
		}

		boolean flag = blockAccess.getBlockState(pos.west()).getBlock() == this && blockAccess.getBlockState(pos.west(2)).getBlock() != this;
		boolean flag1 = blockAccess.getBlockState(pos.east()).getBlock() == this && blockAccess.getBlockState(pos.east(2)).getBlock() != this;
		boolean flag2 = blockAccess.getBlockState(pos.north()).getBlock() == this && blockAccess.getBlockState(pos.north(2)).getBlock() != this;
		boolean flag3 = blockAccess.getBlockState(pos.south()).getBlock() == this && blockAccess.getBlockState(pos.south(2)).getBlock() != this;
		boolean flag4 = flag || flag1 || enumfacing$axis == EnumFacing.Axis.X;
		boolean flag5 = flag2 || flag3 || enumfacing$axis == EnumFacing.Axis.Z;

		if (flag4 && side == EnumFacing.WEST)
		{
			return true;
		}
		else if (flag4 && side == EnumFacing.EAST)
		{
			return true;
		}
		else if (flag5 && side == EnumFacing.NORTH)
		{
			return true;
		}
		else
		{
			return flag5 && side == EnumFacing.SOUTH;
		}
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		if(isConnectedTo(world, pos, state, EnumFacing.NORTH))
			return state.withProperty(AXIS, Axis.Z);
		else if(isConnectedTo(world, pos, state, EnumFacing.SOUTH))
			return state.withProperty(AXIS, Axis.Z);
		else if(isConnectedTo(world, pos, state, EnumFacing.WEST))
			return state.withProperty(AXIS, Axis.X);
		else
			return state.withProperty(AXIS, Axis.X);
	}

	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos)
	{
		if(isConnectedTo(world, pos, state, EnumFacing.WEST))
			world.setBlockState(pos, BlockInit.BLOCKTELEPORTER.getDefaultState().withProperty(AXIS, Axis.X));
		else if(isConnectedTo(world, pos, state, EnumFacing.EAST))
			world.setBlockState(pos, BlockInit.BLOCKTELEPORTER.getDefaultState().withProperty(AXIS, Axis.X));
		else if(isConnectedTo(world, pos, state, EnumFacing.NORTH))
			world.setBlockState(pos, BlockInit.BLOCKTELEPORTER.getDefaultState().withProperty(AXIS, Axis.Z));
		else if(isConnectedTo(world, pos, state, EnumFacing.SOUTH))
			world.setBlockState(pos, BlockInit.BLOCKTELEPORTER.getDefaultState().withProperty(AXIS, Axis.Z));
	}

	public static boolean isConnectedTo(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing direction)
	{
		BlockPos blockpos = pos.offset(direction);
		IBlockState iblockstate = worldIn.getBlockState(blockpos);
		Block block = iblockstate.getBlock();

		return (block == BlockInit.BLOCKTELEPORTER) ;
	}


	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(AXIS, (meta & 3) == 2 ? EnumFacing.Axis.Z : EnumFacing.Axis.X);
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		if (rand.nextInt(100) == 0)
		{
			worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
		}

		for (int i = 0; i < 4; ++i)
		{
			double d0 = (double)((float)pos.getX() + rand.nextFloat());
			double d1 = (double)((float)pos.getY() + rand.nextFloat());
			double d2 = (double)((float)pos.getZ() + rand.nextFloat());
			double d3 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
			double d4 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
			double d5 = ((double)rand.nextFloat() - 0.5D) * 0.5D;
			int j = rand.nextInt(2) * 2 - 1;

			if (worldIn.getBlockState(pos.west()).getBlock() != this && worldIn.getBlockState(pos.east()).getBlock() != this)
			{
				d0 = (double)pos.getX() + 0.5D + 0.25D * (double)j;
				d3 = (double)(rand.nextFloat() * 2.0F * (float)j);
			}
			else
			{
				d2 = (double)pos.getZ() + 0.5D + 0.25D * (double)j;
				d5 = (double)(rand.nextFloat() * 2.0F * (float)j);
			}

			worldIn.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
		}
	}

	public int getMetaFromState(IBlockState state)
	{
		return getMetaForAxis((EnumFacing.Axis)state.getValue(AXIS));
	}

	/**
	 * Returns the blockstate with the given rotation from the passed blockstate. If inapplicable, returns the passed
	 * blockstate.
	 */
	public IBlockState withRotation(IBlockState state, Rotation rot)
	{
		switch (rot)
		{
		case COUNTERCLOCKWISE_90:
		case CLOCKWISE_90:

			switch ((EnumFacing.Axis)state.getValue(AXIS))
			{
			case X:
				return state.withProperty(AXIS, EnumFacing.Axis.X);
			case Z:
				return state.withProperty(AXIS, EnumFacing.Axis.Z);
			default:
				return state;
			}

		default:
			return state;
		}
	}

	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {AXIS});
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityBlockTeleporter();
	}

	@Override
	public boolean hasTileEntity() {
		return true;
	}
}