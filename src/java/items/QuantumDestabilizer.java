package items;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import init.BlockInit;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Enchantments;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import network.MessageOpenScope;
import network.Messages;

public class QuantumDestabilizer extends ItemBow implements IHasModel
{
	public int counter = 0;
	public boolean aiming = false;

	public QuantumDestabilizer(String name)
	{
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsitems);

		ItemInit.ITEMS.add(this);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) 
	{
		if(!worldIn.isRemote && entityIn instanceof EntityPlayer && counter == 0 && aiming)
		{
			EntityPlayer player = (EntityPlayer) entityIn;

			boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, stack) > 0;
			boolean hasItemStack = player.inventory.hasItemStack(new ItemStack(BlockInit.HIDDEN_ELEMENT));

			int i = this.getMaxItemUseDuration(stack);
			i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, player, i, hasItemStack || flag);
			if (i < 0) 
				return;

			if (hasItemStack || flag)
			{
				ItemStack itemstack = findAmmo(player);

				float f = getArrowVelocity(i);

				if((double)f >= 1.0D)
				{
					boolean flag1 = player.capabilities.isCreativeMode;

					if (!worldIn.isRemote)
					{
						getMouseOver(player, worldIn);

						if(!flag1)
							itemstack.shrink(1);
					}

				}
			}		
			player.closeScreen();
			aiming = false;

		}
		if(counter != 0)
			counter--;

		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
	{

	}

	public void getMouseOver(EntityPlayer player, World world)
	{
		BlockPos pos = player.getPosition().add(0, player.eyeHeight, 0);

		float yaw = player.rotationYaw;
		float pitch = player.rotationPitch;

		for(int f = 1; f <= 40; f++)
		{
			double x = (double)(-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
			double y = (double)(-MathHelper.sin((pitch) / 180.0F * (float)Math.PI) * f);
			double z = (double)(MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);


			AxisAlignedBB entityPos = new AxisAlignedBB(pos.getX() + x, pos.getY() + y, pos.getZ() + z, pos.getX() + x + 1, pos.getY() + y + 1, pos.getZ() + z + 1);

			List<Entity> list = world.getEntitiesInAABBexcluding(player, entityPos, Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>()
			{
				public boolean apply(@Nullable Entity p_apply_1_)
				{
					return p_apply_1_ != null && p_apply_1_.canBeCollidedWith();
				}
			}));

			for(int j = 0; j < list.size(); ++j)
			{
				Entity entity = list.get(j);
				entity.onKillCommand();
			}
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		//	boolean flag = playerIn.inventory.hasItemStack(new ItemStack(Items.DIAMOND));
		boolean flag = true;

		if(!worldIn.isRemote)
		{
			counter = 60;
			aiming = true;
			if(playerIn instanceof EntityPlayerMP) {
				EntityPlayerMP serverPlayer = (EntityPlayerMP) playerIn;
				Messages.INSTANCE.sendTo(new MessageOpenScope(),  serverPlayer);

			}
		}


		//	ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, worldIn, playerIn, handIn, flag);
		//	if (ret != null) 
		//		return ret;

		if (!playerIn.capabilities.isCreativeMode && !flag)
		{
			return flag ? new ActionResult(EnumActionResult.PASS, itemstack) : new ActionResult(EnumActionResult.FAIL, itemstack);
		}
		else
		{
			playerIn.setActiveHand(handIn);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
		}
	}

	public float getZoom(EntityLivingBase entity) {
		return 1-MathHelper.clamp(this.getMaxItemUseDuration(null) - entity.getItemInUseCount(), 0, 20)/60;//zooms from normal fov to 2/3 normal fov in one second
	}//this is just an example zoom function

	public static float getArrowVelocity(int charge)
	{
		float f = (float)charge / 20.0F;
		f = (f * f + f * 2.0F) / 3.0F;

		if (f > 1.0F)
		{
			f = 1.0F;
		}

		return f;
	}

	private ItemStack findAmmo(EntityPlayer player)
	{
		if (player.inventory.getCurrentItem().areItemsEqualIgnoreDurability(player.getHeldItem(EnumHand.OFF_HAND), new ItemStack(BlockInit.HIDDEN_ELEMENT)))
		{
			return player.getHeldItem(EnumHand.OFF_HAND);
		}
		if (player.inventory.getCurrentItem().areItemsEqualIgnoreDurability(player.getHeldItem(EnumHand.MAIN_HAND), new ItemStack(BlockInit.HIDDEN_ELEMENT)))
		{
			return player.getHeldItem(EnumHand.MAIN_HAND);
		}
		else
		{
			for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
			{
				ItemStack itemstack = player.inventory.getStackInSlot(i);

				if (player.inventory.getCurrentItem().areItemsEqualIgnoreDurability(itemstack, new ItemStack(BlockInit.HIDDEN_ELEMENT)))
				{
					return itemstack;
				}
			}

			return ItemStack.EMPTY;
		}
	}


	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.BOW;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 100;
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