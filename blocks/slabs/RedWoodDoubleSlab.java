package blocks.slabs;

import init.BlockInit;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

public class RedWoodDoubleSlab extends RedWoodBlockSlab implements IHasModel
{

	public RedWoodDoubleSlab(String name, Material materialIn) 
	{
		super(name, materialIn);

	}

	@Override
	public boolean isDouble()
	{
		return true;
	}

	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}

}
