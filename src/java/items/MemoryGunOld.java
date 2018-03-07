package items;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import entity.EntityForget;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityShulkerBullet;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFirework;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MemoryGunOld extends ItemBow implements IHasModel{

	public EntityForget forget = null;

	public MemoryGunOld(String name)
	{
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallstab);

		ItemInit.ITEMS.add(this);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) 
	{
		if (entityIn instanceof EntityPlayerMP && forget != null)
		{
			EntityPlayerMP entityPlayer = (EntityPlayerMP)entityIn;
			Entity entityHit = forget.getEntityHit();
			if(entityHit != null && entityPlayer != null)
				entityHit.removeTrackingPlayer(entityPlayer);

		}
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer entityLiving, EnumHand handIn)
	{

		entityLiving.setActiveHand(handIn);


		if (entityLiving instanceof EntityPlayer)
		{
			EntityPlayer entityplayer = (EntityPlayer)entityLiving;
			boolean flag = entityplayer.capabilities.isCreativeMode;
			ItemStack itemstack = this.findAmmo(entityplayer);
			
			if (!itemstack.isEmpty() || flag)
			{
				if (!worldIn.isRemote)
				{
					 if (itemstack.isEmpty())
		                {
		                    itemstack = new ItemStack(Items.ARROW);
		                }
					
					EntityForget entityforget = new EntityForget(worldIn, entityplayer.posX + Math.sin(-entityplayer.rotationYaw * Math.PI / 180) * 1.5, entityplayer.posY + .5 + Math.sin(-entityplayer.rotationPitch * Math.PI / 180) * 1.5, entityplayer.posZ + Math.cos(-entityplayer.rotationYaw * Math.PI / 180) * 1.5);


					entityforget.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 3.0F, 0.0F);
					entityforget.isInvisible();
					entityforget.isInvisibleToPlayer(entityplayer);
					worldIn.spawnEntity(entityforget);
					forget = entityforget;

					itemstack.shrink(1);
				}
			}

			//     worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, new ItemStack(ItemInit.MEMORY_GUN));
	}


	private ItemStack findAmmo(EntityPlayer player)
	{
		if (this.isArrow(player.getHeldItem(EnumHand.OFF_HAND)))
		{
			return player.getHeldItem(EnumHand.OFF_HAND);
		}
		else if (this.isArrow(player.getHeldItem(EnumHand.MAIN_HAND)))
		{
			return player.getHeldItem(EnumHand.MAIN_HAND);
		}
		else
		{
			for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
			{
				ItemStack itemstack = player.inventory.getStackInSlot(i);

				if (this.isArrow(itemstack))
				{
					return itemstack;
				}
			}

			return ItemStack.EMPTY;
		}
	}


	@Override
	protected boolean isArrow(ItemStack stack)
	{
		return stack.getItem() instanceof LightBulb;
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


	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}
}