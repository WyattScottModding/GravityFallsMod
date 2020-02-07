package items;

import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlackLight extends Item implements IHasModel{

	public BlackLight(String name)
	{
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsitems);
		
		ItemInit.ITEMS.add(this);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) 
	{
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}
	
	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}	
}