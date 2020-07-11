package blocks.slabs;

import init.BlockInit;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class HalfSlab extends BlockSlabBase implements IHasModel
{
	public HalfSlab(String name, Material materialIn, BlockSlab doubleSlab)
	{
		super(name, materialIn);
		
		ItemInit.ITEMS.add(new ItemSlab(this, this, doubleSlab).setRegistryName(name));
	}
	
	@Override
	public boolean isDouble() 
	{
		return false;
	}

	@Override
	public void registerModels() 
	{
		GravityFalls.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
}
