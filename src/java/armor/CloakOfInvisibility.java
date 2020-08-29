package armor;

import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import main.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class CloakOfInvisibility extends ItemArmor implements IHasModel
{
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
			//Set the NBT to a new NBT if it is null
			NBTTagCompound nbt = new NBTTagCompound();

			if(stack.getTagCompound() != null)
				nbt = stack.getTagCompound();
			
			int counter = 100;
			boolean invisible = false;
			
			if(nbt.hasKey("counter"))
				counter = nbt.getInteger("counter");
			if(nbt.hasKey("invisible"))
				invisible = nbt.getBoolean("invisible");
			
			EntityPlayer player = (EntityPlayer)entityIn;

			if(player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == ItemInit.CLOAK_OF_INVISIBILITY)
			{
				if(invisible)
					player.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 5, 5, true, false));

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
			nbt.setInteger("counter", counter);
			nbt.setBoolean("invisible", invisible);
			stack.setTagCompound(nbt);
		}

		super.onUpdate(stack, world, entityIn, itemSlot, isSelected);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) 
	{
		//Set the NBT to a new NBT if it is null
		NBTTagCompound nbt = new NBTTagCompound();

		if(stack.getTagCompound() != null)
			nbt = stack.getTagCompound();
		
		boolean invisible = false;
	
		if(nbt.hasKey("invisible"))
			invisible = nbt.getBoolean("invisible");
		
		if(invisible)
			return Reference.MODID + ":textures/models/armor/cloak_layer_1.png";
		else
			return Reference.MODID + ":textures/models/armor/cloak_layer_1-2.png";
	}
	
	/**
     * Return whether this item is repairable in an anvil.
     *  
     * @param toRepair the {@code ItemStack} being repaired
     * @param repair the {@code ItemStack} being used to perform the repair
     */
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return repair.getItem() == Items.LEATHER;
    }

	@Override
	public void registerModels() 
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}
}