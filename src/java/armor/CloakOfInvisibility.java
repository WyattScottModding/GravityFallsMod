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


			if(player.getArmorInventoryList().toString().contains("cloak"))
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
