package items;

import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import main.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class GideonBotPart extends Item implements IHasModel
{

	public GideonBotPart(String name)
	{
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		
		ItemInit.ITEMS.add(this);
	}
	
	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}	
}