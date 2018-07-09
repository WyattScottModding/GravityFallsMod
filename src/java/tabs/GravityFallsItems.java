package tabs;

import init.ItemInit;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GravityFallsItems extends CreativeTabs{

	public GravityFallsItems()
	{
		super("tabGravityFallsItems");
	}


	public ItemStack getTabIconItem() 
	{
        return new ItemStack(ItemInit.GRAPPLING_HOOK);
	}

	
	
}
