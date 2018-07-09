package tabs;

import init.BlockInit;
import init.ItemInit;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GravityFallsBlocks extends CreativeTabs{

	public GravityFallsBlocks()
	{
		super("tabGravityFallsBlocks");
	}


	public ItemStack getTabIconItem() 
	{
        return new ItemStack(BlockInit.URANIUM);
	}

	
	
}
