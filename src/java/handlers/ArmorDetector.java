package handlers;

import init.ItemInit;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;

public class ArmorDetector {

	public static boolean isMabel(EntityLivingBase entity)
	{
		Item head = entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem();
		Item chest = entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem();
		Item legs = entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem();
		Item feet = entity.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem();

		if(feet == ItemInit.MABEL_SHOES)
		{
			if(legs == ItemInit.MABEL_PANTS)
			{
				if(chest == ItemInit.MABEL_SWEATER || chest == ItemInit.LIGHT_SWEATER)
				{
					if(head == ItemInit.MABEL_BANDANA)
					{
						return true;
					}
				}
			}
		}	

		return false;
	}

	public static boolean isDipper(EntityLivingBase entity)
	{
		Item head = entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem();
		Item chest = entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem();
		Item legs = entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem();
		Item feet = entity.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem();

		if(feet == ItemInit.PINE_SHOES)
		{
			if(legs == ItemInit.PINE_PANTS)
			{
				if(chest == ItemInit.PINE_SHIRT)
				{
					if(head == ItemInit.PINE_HAT)
					{
						return true;
					}
				}
			}
		}

		return false;
	}

	public static boolean isRubberSuit(EntityLivingBase entity)
	{
		Item head = entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem();
		Item chest = entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem();
		Item legs = entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem();
		Item feet = entity.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem();

		if(head == ItemInit.RUBBER_HAT)
		{
			if(chest == ItemInit.RUBBER_CHESTPLATE)
			{
				if(legs == ItemInit.RUBBER_LEGGINGS)
				{
					if(feet == ItemInit.RUBBER_BOOTS)
					{
						return true;
					}
				}
			}
		}

		return false;
	}

	public static boolean isMysticAmulet(EntityLivingBase entity)
	{
		Item chest = entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem();

		if(chest == ItemInit.MYSTIC_AMULET)
		{
			return true;
		}
		
		return false;
	}
}
