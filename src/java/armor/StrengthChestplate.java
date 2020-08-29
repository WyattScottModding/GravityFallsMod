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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class StrengthChestplate extends ItemArmor implements IHasModel
{	
	public StrengthChestplate(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) 
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
		//Set the NBT to a new NBT if it is null
		NBTTagCompound nbt = new NBTTagCompound();

		if(stack.getTagCompound() != null)
			nbt = stack.getTagCompound();
		
		int counter = 0;
		
		if(nbt.hasKey("counter"))
			counter = nbt.getInteger("counter");
		
		if(counter < 18)
			counter++;
		else if(counter == 18)
			counter = 0;
		
		if(entityIn instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entityIn;

			if(player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == ItemInit.STRENGTH_CHESTPLATE)
			{
				player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 5, 2));
				player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 5, 2));
				player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 5, 2));

				player.fallDistance = 0;	
			}
		}
		nbt.setInteger("counter", counter);
		stack.setTagCompound(nbt);

		super.onUpdate(stack, world, entityIn, itemSlot, isSelected);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) 
	{
		//Set the NBT to a new NBT if it is null
		NBTTagCompound nbt = new NBTTagCompound();

		if(stack.getTagCompound() != null)
			nbt = stack.getTagCompound();
		
		int counter = 0;
		
		if(nbt.hasKey("counter"))
			counter = nbt.getInteger("counter");
		
		if(counter == 0 || counter == 1 || counter == 2)
			return Reference.MODID + ":textures/models/armor/magic_layer_1-1.png";
		else if(counter == 3 || counter == 4 || counter == 5)
			return Reference.MODID + ":textures/models/armor/magic_layer_1-2.png";
		else if(counter == 6 || counter == 7 || counter == 8)
			return Reference.MODID + ":textures/models/armor/magic_layer_1-3.png";
		else if(counter == 9 || counter == 10 || counter == 11)
			return Reference.MODID + ":textures/models/armor/magic_layer_1-4.png";
		else if(counter == 12 || counter == 13 || counter == 14)
			return Reference.MODID + ":textures/models/armor/magic_layer_1-3.png";
		else //if(counter == 15 || counter == 16 || counter == 17)
			return Reference.MODID + ":textures/models/armor/magic_layer_1-2.png";
	}

	@Override
	public void registerModels() 
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}
}