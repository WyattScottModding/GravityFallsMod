package items;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import entity.EntityTimeCopDundgren;
import entity.EntityTimeCopLolph;
import handlers.KeyBindings;
import handlers.SoundsHandler;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LaserArmCannon extends ItemSword implements IHasModel
{
	public LaserArmCannon(String name, ToolMaterial material)
	{
		super(material);
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsitems);
		this.setMaxDamage(20);
		this.addPropertyOverride(new ResourceLocation("fired"), new IItemPropertyGetter()
		{
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
			{				
				if(entityIn != null && (entityIn.getHeldItemMainhand() == stack || entityIn.getHeldItemOffhand() == stack))
				{
					//Set the NBT to a new NBT if it is null
					NBTTagCompound nbt = new NBTTagCompound();

					if(stack.getTagCompound() != null)
						nbt = stack.getTagCompound();
					
					int cooldown = 30;
					boolean charging = false;
					
					if(nbt.hasKey("cooldown"))
						cooldown = nbt.getInteger("cooldown");
					if(nbt.hasKey("charging"))
						charging = nbt.getBoolean("charging");
					
					if(charging)
					{
						if(cooldown >= 0 && cooldown < 5)
							return 0.1F;
						else if(cooldown >= 5 && cooldown < 10)
							return 0.5F;
						else if(cooldown >= 10 && cooldown < 15)
							return 0.4F;
						else if(cooldown >= 15 && cooldown < 20)
							return 0.3F;
						else if(cooldown >= 20 && cooldown < 25)
							return 0.2F;
					}

					return 0.6F;
				}
				return 0.1F;
			}
		});

		ItemInit.ITEMS.add(this);
	}

	@Override
	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) 
	{
		ItemStack stack = player.getHeldItem(handIn);

		//Set the NBT to a new NBT if it is null
		NBTTagCompound nbt = new NBTTagCompound();

		if(stack.getTagCompound() != null)
			nbt = stack.getTagCompound();
		
		int cooldown = 30;
		boolean charging = false;
		
		if(nbt.hasKey("cooldown"))
			cooldown = nbt.getInteger("cooldown");
		
		
		if(cooldown == 0) {

			charging = false;
			worldIn.playSound(player, player.getPosition(), SoundsHandler.ITEM_LASER_ARM_CANNON, SoundCategory.PLAYERS, 1, 1);
		}

		nbt.setInteger("cooldown", cooldown);
		nbt.setBoolean("charging", charging);
		stack.setTagCompound(nbt);
		
		return super.onItemRightClick(worldIn, player, handIn);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		//Set the NBT to a new NBT if it is null
		NBTTagCompound nbt = new NBTTagCompound();

		if(stack.getTagCompound() != null)
			nbt = stack.getTagCompound();
		
		int cooldown = 30;
		boolean charging = false;
		
		if(nbt.hasKey("cooldown"))
			cooldown = nbt.getInteger("cooldown");
		if(nbt.hasKey("charging"))
			charging = nbt.getBoolean("charging");
		
		if(cooldown > 0)
			cooldown--;

		if(entityIn instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entityIn;
			boolean flag = player.capabilities.isCreativeMode;

			if(!charging && (stack.getItemDamage() < 20 || flag) && player.getHeldItemMainhand().getItem() instanceof LaserArmCannon)
			{
				if(!worldIn.isRemote) {
					if(!flag)
						stack.damageItem(1, player);

					getMouseOver(player, worldIn);
					charging = true;
					cooldown = 30;
				}
			}

			if(stack.getItemDamage() >= 10 &&  KeyBindings.BATTERY.isDown())
			{
				if(player.getHeldItemMainhand().getItem() instanceof LaserArmCannon)
				{
					ItemStack itemstack = findAmmo(player);

					if(isBattery(itemstack))
					{
						stack.setItemDamage(stack.getItemDamage() - 10);

						itemstack.shrink(1);
					}
				}
			}
		}
		else if(entityIn instanceof EntityTimeCopDundgren || entityIn instanceof EntityTimeCopLolph)
		{
			EntityLivingBase entityLiving = (EntityPlayer) entityIn;

			if(!charging)
			{
				getMouseOver(entityLiving, worldIn);
				charging = true;
				cooldown = 30;
			}
		}

		nbt.setInteger("cooldown", cooldown);
		nbt.setBoolean("charging", charging);
		stack.setTagCompound(nbt);
		
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	public void getMouseOver(EntityLivingBase player, World world)
	{
		BlockPos pos = player.getPosition().add(0, player.getEyeHeight(), 0);

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

					if(mob.isEntityUndead())
						mob.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 4, 1));
					else
						mob.addPotionEffect(new PotionEffect(MobEffects.INSTANT_DAMAGE, 4, 1));
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
		return stack.getItem() instanceof ItemBasic;
	}
}