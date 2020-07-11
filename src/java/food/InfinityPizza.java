package food;

import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class InfinityPizza extends ItemFood implements IHasModel{

	public final int itemUseDuration;
	private boolean alwaysEdible;
	public boolean count = false;
	public EntityLivingBase player;

	public InfinityPizza(String name, int amount, float saturation, boolean isWolfFood) 
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
		stack.grow(1);
		return super.onItemUseFinish(stack, worldIn, entityLiving);
	}

	public boolean getCount()
	{
		return count;
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