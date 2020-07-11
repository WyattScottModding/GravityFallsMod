package items;

import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.item.Item;

public class LightBulb extends Item implements IHasModel
{
	public LightBulb(String name)
	{
		this.setMaxStackSize(64);
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
		this.setCreativeTab(GravityFalls.gravityfallsitems);
		
		ItemInit.ITEMS.add(this);
	}

	public void registerModels() 
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
