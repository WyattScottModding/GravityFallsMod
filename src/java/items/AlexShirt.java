package items;

import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.item.Item;

public class AlexShirt extends Item implements IHasModel{

	public AlexShirt(String name)
	{
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		
		ItemInit.ITEMS.add(this);
	}
	
	@Override
	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}	
}