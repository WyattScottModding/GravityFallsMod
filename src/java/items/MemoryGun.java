package items;

import entity.EntityForget;
import handlers.KeyBindings;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class MemoryGun extends ItemBow implements IHasModel
{
	public EntityForget forget = null;
	public boolean fired = false;
	private int cooldown = 10;

	public MemoryGun(String name)
	{
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsitems);
		this.setMaxDamage(10);

		ItemInit.ITEMS.add(this);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) 
	{
		if(cooldown > 0)
			cooldown--;
		
		if (entityIn instanceof EntityPlayerMP && forget != null)
		{
			EntityPlayerMP player = (EntityPlayerMP)entityIn;
			
			boolean flag = player.capabilities.isCreativeMode;
			
			if(fired && (stack.getItemDamage() < 10 || flag))
			{
				if(!flag)
					stack.damageItem(1, player);

				fired = false;
			}
			
			if(stack.getItemDamage() >= 1 && KeyBindings.BATTERY.isDown())
			{
				ItemStack itemstack = findAmmo(player);

				if(itemstack.getItem() instanceof ItemBasic && player.getHeldItemMainhand().getItem() instanceof MemoryGun)
				{
					stack.setItemDamage(stack.getItemDamage() - 1);

					itemstack.shrink(1);
				}
			}
			
		}
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer entityLiving, EnumHand handIn)
	{
		entityLiving.setActiveHand(handIn);
		
		ItemStack stack = entityLiving.getHeldItemMainhand();

		if (entityLiving instanceof EntityPlayer && stack.getItem() instanceof MemoryGun && cooldown == 0)
		{
			EntityPlayer entityplayer = (EntityPlayer)entityLiving;

			if (stack.getItemDamage() < 10)
			{
				if (!worldIn.isRemote)
				{
				//	EntityForget entityforget = new EntityForget(worldIn, entityplayer.posX + Math.sin(-entityplayer.rotationYaw * Math.PI / 180) * 1.5, entityplayer.posY + .5 + Math.sin(-entityplayer.rotationPitch * Math.PI / 180) * 1.5, entityplayer.posZ + Math.cos(-entityplayer.rotationYaw * Math.PI / 180) * 1.5, entityplayer);
					
					EntityForget entityforget = new EntityForget(worldIn, entityplayer, entityplayer.posX + ((double)entityplayer.width * .5), entityplayer.posY + (double)entityplayer.eyeHeight - (double)entityplayer.getYOffset() - .4, entityplayer.posZ + (double)entityplayer.width * .5);
					
					entityforget.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, 3.0F, 0.0F);
					worldIn.spawnEntity(entityforget);
					forget = entityforget;
					fired = true;
					cooldown = 10;
				}
			}
			//     worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
		}
        return new ActionResult<ItemStack>(EnumActionResult.PASS, entityLiving.getHeldItem(handIn));
	}


	private ItemStack findAmmo(EntityPlayer player)
	{
		if (this.isBattery(player.getHeldItem(EnumHand.OFF_HAND)))
		{
			return player.getHeldItem(EnumHand.OFF_HAND);
		}
		else if (this.isBattery(player.getHeldItem(EnumHand.MAIN_HAND)))
		{
			return player.getHeldItem(EnumHand.MAIN_HAND);
		}
		else
		{
			for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
			{
				ItemStack itemstack = player.inventory.getStackInSlot(i);

				if (this.isBattery(itemstack))
				{
					return itemstack;
				}
			}

			return ItemStack.EMPTY;
		}
	}

	protected boolean isBattery(ItemStack stack)
	{
		return stack.getItem() instanceof ItemBasic;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
	{

	}
	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.NONE;
	}

	@Override
	public int getItemEnchantability()
	{
		return 0;
	}

	@Override
	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}
}