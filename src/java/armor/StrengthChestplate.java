package armor;

import org.lwjgl.input.Keyboard;

import handlers.RegistryHandler;
import init.BlockInit;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
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

	public StrengthChestplate(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) 
	{
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallstab);

		ItemInit.ITEMS.add(this);

	}

	

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entityIn, int itemSlot, boolean isSelected) 
	{
		if(entityIn instanceof EntityPlayer && RegistryHandler.getStrength())
		{
			EntityPlayer player = (EntityPlayer)entityIn;

			player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 5, 5));
			player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 5, 2));;
						
		}
		

		super.onUpdate(stack, world, entityIn, itemSlot, isSelected);
	}




	@Override
	public void registerModels() 
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");

	}

}
