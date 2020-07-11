package blocks;

import java.util.Random;

import org.lwjgl.input.Keyboard;

import entity.EntityBill;
import handlers.RegistryHandler;
import handlers.SoundsHandler;
import init.BlockInit;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
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
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
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
import tileEntities.TileEntityBook2;

public class Book_2 extends Journal implements IHasModel, ITileEntityProvider
{
	public static AxisAlignedBB Book = new AxisAlignedBB(0.3D, 0D, 0.2D, .7D, 0.30D, .8D);
	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	public Book_2(String name)
	{
		super(name);
	}

	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) 
	{
		return ItemInit.BOOK2;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
			EntityPlayer player) 
	{
		return new ItemStack(ItemInit.BOOK2);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityBook2();
	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}
}