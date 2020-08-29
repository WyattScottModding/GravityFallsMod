package blocks;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import handlers.KeyBindings;
import init.BlockInit;
import init.ItemInit;
import main.ConfigHandler;
import main.GravityFalls;
import main.IHasModel;
import main.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import network.MessageOpenGUI;
import network.MessagePlaySound;
import network.Messages;
import tileEntities.TileEntityComputer;
import tileEntities.TileEntityPortalLever;
import tileEntities.TileEntityUraniumFurnace;

public class ComputerOpen extends Block
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	public ComputerOpen(String name, Material material)
	{
		super(material);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setSoundType(SoundType.METAL);
		this.setHardness(1.0F);
		this.setResistance(10.0F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));


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
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		EnumFacing face = (EnumFacing)this.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, this.getMetaFromState(state), player).getValue(FACING);
		GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
		
		if(player != null)
		{
			if(!gameSettings.keyBindSprint.isKeyDown())
			{
				IBlockState state2 = BlockInit.COMPUTER_CLOSED.getDefaultState().withProperty(FACING, face);
				worldIn.setBlockState(pos, state2);		
			}
			else
			{
				player.openGui(GravityFalls.instance, ConfigHandler.COMPUTER, player.world, (int) player.posX, (int) player.posY, (int) player.posZ);
			}
			return true;
		}

		return false;
	}

	public static TileEntityPortalLever searchForLever(World world, BlockPos pos) {
		for(int x = pos.getX() - 20; x <= pos.getX() + 20; x++) {
			for(int y = pos.getY() - 20; y <= pos.getY() + 20; y++) {
				for(int z = pos.getZ() - 20; z <= pos.getZ() + 20; z++) {
					if(world.getTileEntity(new BlockPos(x, y, z)) instanceof TileEntityPortalLever) {
						return (TileEntityPortalLever) world.getTileEntity(new BlockPos(x, y, z));
					}
				}
			}
		}
		return null;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
			EntityPlayer player) 
	{
		return new ItemStack(Item.getItemFromBlock(BlockInit.COMPUTER_CLOSED), 1);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) 
	{
		return Item.getItemFromBlock(BlockInit.COMPUTER_CLOSED);
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
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityComputer();
	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}

}