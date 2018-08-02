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

public class StrengthChestplate extends ItemArmor implements IHasModel
{
	private int counter = 0;
	
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
		if(counter < 18)
			counter++;
		else if(counter == 18)
			counter = 0;
		
		if(entityIn instanceof EntityPlayer && RegistryHandler.getStrength())
		{
			EntityPlayer player = (EntityPlayer)entityIn;

			player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 5, 5));
			player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 5, 2));;
		}
		

		super.onUpdate(stack, world, entityIn, itemSlot, isSelected);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) 
	{		
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
