package items;

import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.item.Item;

public class CrystalShard extends Item implements IHasModel{

	public CrystalShard(String name)
	{
		this.setMaxStackSize(64);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsitems);
		
		ItemInit.ITEMS.add(this);
	}
	
	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}	
}