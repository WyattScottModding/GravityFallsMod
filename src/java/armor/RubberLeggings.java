package armor;

import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

public class RubberLeggings extends ItemArmor implements IHasModel{

	public RubberLeggings(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) 
	{
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallstab);
		
		ItemInit.ITEMS.add(this);
		
	}

	@Override
	public void registerModels() 
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
		
	}

}
