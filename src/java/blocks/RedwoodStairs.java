package blocks;

import init.BlockInit;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class RedwoodStairs extends BlockStairs implements IHasModel
{
	public RedwoodStairs(String name, IBlockState state)
	{
		super(state);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setHardness(2.0F);
		this.setResistance(15.0F);
		this.setCreativeTab(GravityFalls.gravityfallsblocks);
		this.setSoundType(SoundType.WOOD);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		this.useNeighborBrightness = true;
		
		
		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
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
		return new ItemStack(Item.getItemFromBlock(this));
	}
}
