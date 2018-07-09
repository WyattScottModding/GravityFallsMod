package items;

import java.util.List;

import javax.annotation.Nullable;

import org.apache.http.impl.conn.Wire;
import org.lwjgl.input.Keyboard;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityZombieHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class LaserArmCannon extends ItemSword implements IHasModel
{
	public boolean fired = false;
	public int cooldown = 30;

	public LaserArmCannon(String name, ToolMaterial material)
	{
		super(material);
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsitems);
		this.setMaxDamage(20);

		ItemInit.ITEMS.add(this);
	}

	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) 
	{
		if (!worldIn.isRemote && cooldown == 0)
		{
			fired = true;
		}

		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		if(cooldown > 0)
			cooldown--;

		if(!worldIn.isRemote && entityIn instanceof EntityPlayer)
		{

			EntityPlayer player = (EntityPlayer) entityIn;
			boolean flag = player.capabilities.isCreativeMode;

			if(fired && (stack.getItemDamage() < 20 || flag))
			{
				if(!flag)
					stack.damageItem(1, player);

				getMouseOver(player, worldIn);
				fired = false;
				cooldown = 30;
			}

			if(stack.getItemDamage() >= 10 &&  Keyboard.isKeyDown(Keyboard.KEY_R))
			{
				if(player.getHeldItemMainhand().isItemEqual(new ItemStack(ItemInit.LASER_ARM_CANNON)))
				{
					ItemStack itemstack = findAmmo(player);

					if(isBattery(itemstack))
					{
						stack.damageItem(-10, player);

						System.out.print("Battery Found");
						
						itemstack.shrink(1);
					}
				}
			}
		}
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	public void getMouseOver(EntityPlayer player, World world)
	{

		Vec3d lookVec = player.getLookVec();

		BlockPos pos = player.getPosition();

		float yaw = player.rotationYaw;
		float pitch = player.rotationPitch;

		for(int f = 1; f <= 80; f++)
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
				if(entity instanceof EntityLivingBase)
				{
					EntityLivingBase mob = (EntityLivingBase) entity;

					if(mob instanceof EntitySkeleton || mob instanceof EntityZombie || mob instanceof EntityZombieHorse)
						mob.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 2, 0));
					else
						mob.addPotionEffect(new PotionEffect(MobEffects.INSTANT_DAMAGE, 2, 0));
				}
			}
		}

	}


	private ItemStack findAmmo(EntityPlayer player)
	{
		if (player.inventory.getCurrentItem().areItemsEqualIgnoreDurability(player.getHeldItem(EnumHand.OFF_HAND), new ItemStack(ItemInit.BATTERY)))
		{
			return player.getHeldItem(EnumHand.OFF_HAND);
		}
		if (player.inventory.getCurrentItem().areItemsEqualIgnoreDurability(player.getHeldItem(EnumHand.MAIN_HAND), new ItemStack(ItemInit.BATTERY)))
		{
			return player.getHeldItem(EnumHand.MAIN_HAND);
		}
		else
		{
			for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
			{
				ItemStack itemstack = player.inventory.getStackInSlot(i);

				if (	player.inventory.getCurrentItem().areItemsEqualIgnoreDurability(itemstack, new ItemStack(ItemInit.BATTERY)))
				{
					return itemstack;
				}
			}

			return ItemStack.EMPTY;
		}
	}
	
	protected boolean isBattery(ItemStack stack)
	{
		return stack.getItem() instanceof Battery;
	}
}
