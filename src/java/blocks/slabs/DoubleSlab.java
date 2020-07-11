package blocks.slabs;

import init.BlockInit;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class DoubleSlab extends BlockSlabBase
{

	public DoubleSlab(String name, Material materialIn) 
	{
		super(name, materialIn);
	}

	@Override
	public boolean isDouble() 
	{	
		return true;
	}
}