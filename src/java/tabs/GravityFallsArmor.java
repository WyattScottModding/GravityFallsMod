package tabs;

import init.ItemInit;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GravityFallsArmor extends CreativeTabs{

	public GravityFallsArmor()
	{
		super("tabGravityFallsArmor");
	}


	public ItemStack getTabIconItem() 
	{
        return new ItemStack(ItemInit.PINE_HAT);
	}

	
	
}
