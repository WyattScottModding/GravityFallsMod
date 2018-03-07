package init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GravityFallsTab extends CreativeTabs{

	public GravityFallsTab()
	{
		super("tabGravityFalls");
	}


	public ItemStack getTabIconItem() 
	{
        return new ItemStack(ItemInit.MAGICFLASHLIGHT);
	}

	
	
}
