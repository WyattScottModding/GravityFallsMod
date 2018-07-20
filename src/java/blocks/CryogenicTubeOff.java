package blocks;

import java.util.Random;

import init.BlockInit;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class CryogenicTubeOff extends Block implements IHasModel
{
	public static final AxisAlignedBB CRYOGENICTUBE = new AxisAlignedBB(0D, 0D, 0D, 1.0D, 2.00D, 1.0D);


	public CryogenicTubeOff(String name, Material material)
	{
		super(material);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
	//	this.setCreativeTab(GravityFalls.gravityfallsblocks);
		this.setSoundType(SoundType.METAL);


		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) 
	{
		return CRYOGENICTUBE;
	}
	
	@Override
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) 
	{
		return CRYOGENICTUBE;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos)
	{
		return CRYOGENICTUBE;
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

	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) 
	{
		if(worldIn.isBlockPowered(pos))
		{
			IBlockState state2 = BlockInit.CRYOGENIC_TUBE_ON.getDefaultState();
			worldIn.setBlockState(pos, state2);
			
		}
	}


	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
			EntityPlayer player) 
	{
		return new ItemStack(Item.getItemFromBlock(this), 1, getMetaFromState(world.getBlockState(pos)));
	}


}