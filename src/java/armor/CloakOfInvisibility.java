package armor;

import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import main.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class CloakOfInvisibility extends ItemArmor implements IHasModel
{
	public boolean invisible = true;

	public int counter = 100;

	public CloakOfInvisibility(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) 
	{
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsarmor);
		
		ItemInit.ITEMS.add(this);
	}


	@Override
	public void onUpdate(ItemStack stack, World world, Entity entityIn, int itemSlot, boolean isSelected) 
	{
		if(entityIn instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entityIn;

			if(player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == ItemInit.CLOAK_OF_INVISIBILITY)
			{
				if(invisible)
					player.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 5, 5));

				if(counter > 0)
					counter--;

				if(counter == 0)
				{
					int random = (int)(Math.random() * 3) + 1;

					if(random == 1 || random == 2)
						invisible = true;
					else 
						invisible = false;
					counter = 100;
				}
			}
		}

		super.onUpdate(stack, world, entityIn, itemSlot, isSelected);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) 
	{		
		if(invisible)
			return Reference.MODID + ":textures/models/armor/cloak_layer_1.png";
		else
			return Reference.MODID + ":textures/models/armor/cloak_layer_1-2.png";
	}

	@Override
	public void registerModels() 
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}
}