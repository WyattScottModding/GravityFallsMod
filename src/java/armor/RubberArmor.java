package armor;

import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class RubberArmor extends ItemArmor implements IHasModel {

	public RubberArmor(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) 
	{
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsarmor);
		
		ItemInit.ITEMS.add(this);
	}

	 /**
     * Return whether this item is repairable in an anvil.
     *  
     * @param toRepair the {@code ItemStack} being repaired
     * @param repair the {@code ItemStack} being used to perform the repair
     */
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return repair.getItem() == ItemInit.RUBBER_ITEM;
    }
	
	@Override
	public void registerModels() 
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
