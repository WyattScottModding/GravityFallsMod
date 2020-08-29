package food;

import handlers.SoundsHandler;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import network.MessagePlaySound;
import network.Messages;

public class SmileDip extends ItemFood implements IHasModel{

	public final int itemUseDuration;
	public EntityLivingBase entity;

	public SmileDip(String name, int amount, float saturation, boolean isWolfFood) 
	{
		super(amount, saturation, isWolfFood);
		this.setMaxStackSize(64);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsitems);

		this.itemUseDuration = 32;
		ItemInit.ITEMS.add(this);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
	{
		int duration = 1740;
		entityLiving.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, duration, 0));
		entityLiving.addPotionEffect(new PotionEffect(MobEffects.SPEED, duration, 0));
		entityLiving.addPotionEffect(new PotionEffect(MobEffects.HASTE, duration, 0));
		entityLiving.heal(2.0F);

		if(entityLiving instanceof EntityPlayer) {

			EntityPlayer player = (EntityPlayer) entityLiving;

			Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord
					.getMusicRecord(SoundsHandler.SMILE_DIP));
		}
		entity = entityLiving;

		return super.onItemUseFinish(stack, worldIn, entityLiving);
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.EAT;
	}

	@Override
	public ItemFood setAlwaysEdible()
	{
		return super.setAlwaysEdible();
	}

	@Override
	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}
}