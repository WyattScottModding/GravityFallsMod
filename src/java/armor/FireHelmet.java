package armor;

import org.lwjgl.input.Keyboard;

import handlers.RegistryHandler;
import init.BlockInit;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import main.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class FireHelmet extends ItemArmor implements IHasModel
{
	private int counter = 0;

	public FireHelmet(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) 
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
		if(counter < 36)
			counter++;
		else if(counter == 36)
			counter = 0;

		if(entityIn instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entityIn;

			if(player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == ItemInit.FIRE_HELMET)
			{
				player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 5, 1));
				player.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 5, 1));

				player.fallDistance = 0;	
			}
		}
		super.onUpdate(stack, world, entityIn, itemSlot, isSelected);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) 
	{		
		if(counter >= 0 && counter < 4)
			return Reference.MODID + ":textures/models/armor/magic_layer_1-1.png";
		else if(counter >= 4 && counter < 8)
			return Reference.MODID + ":textures/models/armor/magic_layer_1-2.png";
		else if(counter >= 8 && counter < 12)
			return Reference.MODID + ":textures/models/armor/magic_layer_1-3.png";
		else if(counter >= 12 && counter < 16)
			return Reference.MODID + ":textures/models/armor/magic_layer_1-4.png";
		else if(counter >= 16 && counter < 20)
			return Reference.MODID + ":textures/models/armor/magic_layer_1-5.png";
		else if(counter >= 20 && counter < 24)
			return Reference.MODID + ":textures/models/armor/magic_layer_1-4.png";
		else if(counter >= 24 && counter < 28)
			return Reference.MODID + ":textures/models/armor/magic_layer_1-3.png";
		else if(counter >= 28 && counter < 32)
			return Reference.MODID + ":textures/models/armor/magic_layer_1-2.png";
		else// if(counter >= 32 && counter < 36)
			return Reference.MODID + ":textures/models/armor/magic_layer_1-1.png";
	}


	@Override
	public void registerModels() 
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}
}