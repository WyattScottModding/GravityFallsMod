package blocks.slabs;

import init.BlockInit;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RedWoodHalfSlab extends RedWoodBlockSlab implements IHasModel
{

	public RedWoodHalfSlab(String name, Material materialIn) 
	{
		super(name, materialIn);
		this.setCreativeTab(GravityFalls.gravityfallsblocks);
	}

	@Override
	public boolean isDouble() 
	{
		return false;
	}
	
	/*
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
				
				if (item == Item.getItemFromBlock(BlockInit.REDWOOD_SLAB_HALF))
				{		
					IBlockState state2 = BlockInit.REDWOOD_SLAB_DOUBLE.getDefaultState();
					worldIn.setBlockState(pos, state2);

					playerIn.getHeldItemMainhand().shrink(1);

					return true;
				}
				
			}

			return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
		}
	}
	*/
	
	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
	
}
