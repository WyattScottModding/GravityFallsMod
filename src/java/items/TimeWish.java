package items;

import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class TimeWish extends ItemFood implements IHasModel{

	public final int itemUseDuration;
	
	public TimeWish(String name, int amount, float saturation, boolean isWolfFood) 
	{
		super(amount, saturation, isWolfFood);
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsitems);

		this.itemUseDuration = 32;
		ItemInit.ITEMS.add(this);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
	{
		entityLiving.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 60000, 20));
		entityLiving.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 60000, 20));
		entityLiving.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 60000, 0));
		entityLiving.addPotionEffect(new PotionEffect(MobEffects.HASTE, 60000, 20));
		entityLiving.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 60000, 100));
		entityLiving.addPotionEffect(new PotionEffect(MobEffects.SPEED, 60000, 10));

		entityLiving.heal(2.0F);
		
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
	
	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}
}