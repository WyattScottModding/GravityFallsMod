package food;

import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class SmileDip extends ItemFood implements IHasModel{

	private PotionEffect[] effects;
	public final int itemUseDuration;
	private boolean alwaysEdible;
	public boolean count = false;
	public EntityLivingBase player;

	public SmileDip(String name, int amount, float saturation, boolean isWolfFood, PotionEffect...potionEffects) 
	{
		super(amount, saturation, isWolfFood);
		this.setMaxStackSize(64);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallstab);

		this.effects = potionEffects;
		this.itemUseDuration = 32;
		ItemInit.ITEMS.add(this);
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
	{
		for(PotionEffect effect : effects)
		{
			entityLiving.addPotionEffect(effect);
		}
		entityLiving.heal(2.0F);

		count = true;
		player = entityLiving;
		
		return super.onItemUseFinish(stack, worldIn, entityLiving);
	}
	
	public boolean getCount()
	{
		return count;
	}

	public void afterEffects()
	{
		player.addPotionEffect(new PotionEffect(Potion.getPotionById(24), 6000, 0));
		count = false;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.EAT;
	}
	
	@Override
    public ItemFood setAlwaysEdible()
    {
        this.alwaysEdible = true;
        return this;
    }

	@Override
	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}

}
