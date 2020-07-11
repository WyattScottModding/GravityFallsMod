package blocks;

import java.util.Random;

import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class Book_3 extends Journal implements IHasModel
{
	public Book_3(String name)
	{
		super(name);
	}
	
	@Override
	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) 
	{
		return ItemInit.BOOK3;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
			EntityPlayer player) 
	{
		return new ItemStack(ItemInit.BOOK3);
	}	
}