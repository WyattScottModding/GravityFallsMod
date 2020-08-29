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

public class RegenerationLegs extends ItemArmor implements IHasModel
{	
	public RegenerationLegs(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) 
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

		if(counter < 22)
			counter++;
		else if(counter == 22)
			counter = 0;

		if(entityIn instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entityIn;

			if(player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() == ItemInit.REGENERATION_LEGS)
			{
				player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 5, 1));
				player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 5, 3));

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

		if(counter == 0 || counter == 1)
			return Reference.MODID + ":textures/models/armor/magic_layer_2-1.png";
		else if(counter == 2 || counter == 3)
			return Reference.MODID + ":textures/models/armor/magic_layer_2-2.png";
		else if(counter == 4 || counter == 5)
			return Reference.MODID + ":textures/models/armor/magic_layer_2-3.png";
		else if(counter == 6 || counter == 7)
			return Reference.MODID + ":textures/models/armor/magic_layer_2-4.png";
		else if(counter == 8 || counter == 9)
			return Reference.MODID + ":textures/models/armor/magic_layer_2-5.png";
		else if(counter == 10 || counter == 11)
			return Reference.MODID + ":textures/models/armor/magic_layer_2-6.png";
		else if(counter == 12 || counter == 13)
			return Reference.MODID + ":textures/models/armor/magic_layer_2-7.png";
		else if(counter == 14 || counter == 15)
			return Reference.MODID + ":textures/models/armor/magic_layer_2-8.png";
		else if(counter == 16 || counter == 17)
			return Reference.MODID + ":textures/models/armor/magic_layer_2-9.png";
		else if(counter == 18 || counter == 19)
			return Reference.MODID + ":textures/models/armor/magic_layer_2-10.png";
		else// if(counter == 20 || counter == 21)
			return Reference.MODID + ":textures/models/armor/magic_layer_2-11.png";
	}

	@Override
	public void registerModels() 
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}
}