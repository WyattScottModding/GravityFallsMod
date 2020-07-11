package items;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import entity.EntityBill;
import entity.EntityList;
import entity.EntitySecurityDroid;
import entity.EntityShapeShifter;
import handlers.KeyBindings;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import models.ModelShapeShifter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MemoryGun extends ItemBow implements IHasModel
{
	public MemoryGun(String name)
	{
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsitems);
		this.setMaxDamage(10);
		this.addPropertyOverride(new ResourceLocation("fullMemoryWipe"), new IItemPropertyGetter()
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

					boolean fullMemoryWipe = false;

					if(nbt.hasKey("fullMemoryWipe"))
						fullMemoryWipe = nbt.getBoolean("fullMemoryWipe");

					if(fullMemoryWipe)
					{
						return 0.1F;
					}
				}
				return 0.0F;
			}
		});

		ItemInit.ITEMS.add(this);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) 
	{
		//Set the NBT to a new NBT if it is null
		NBTTagCompound nbt = new NBTTagCompound();

		if(stack.getTagCompound() != null)
			nbt = stack.getTagCompound();

		boolean fired = false;
		int cooldown = 10;
		boolean fullMemoryWipe = false;
		boolean isDown = false;

		if(nbt.hasKey("fired"))
			fired = nbt.getBoolean("fired");
		if(nbt.hasKey("cooldown"))
			cooldown = nbt.getInteger("cooldown");
		if(nbt.hasKey("fullMemoryWipe"))
			fullMemoryWipe = nbt.getBoolean("fullMemoryWipe");
		if(nbt.hasKey("isDown"))
			cooldown = nbt.getInteger("isDown");

		if(cooldown > 0)
			cooldown--;

		if (entityIn instanceof EntityPlayer && !worldIn.isRemote)
		{
			EntityPlayer player = (EntityPlayer)entityIn;
			boolean flag = player.capabilities.isCreativeMode;

			if(KeyBindings.ITEM1.isDown()) {
				if(!isDown) {
					if(fullMemoryWipe)
						fullMemoryWipe = false;
					else
						fullMemoryWipe = true;
				}
				isDown = true;
			}
			else
				isDown = false;

			if(fired && (stack.getItemDamage() < 10 || flag))
			{
				if(!flag)
					stack.damageItem(1, player);

				fired = false;
			}

			if(stack.getItemDamage() >= 5 && KeyBindings.BATTERY.isDown())
			{
				ItemStack itemstack = findAmmo(player);

				if(itemstack.getItem() instanceof ItemBasic && player.getHeldItemMainhand().getItem() instanceof MemoryGun)
				{
					stack.setItemDamage(stack.getItemDamage() - 5);
					itemstack.shrink(1);
				}
			}
		}

		nbt.setInteger("cooldown", cooldown);
		nbt.setBoolean("fired", fired);
		nbt.setBoolean("fullMemoryWipe", fullMemoryWipe);
		nbt.setBoolean("isDown", isDown);

		stack.setTagCompound(nbt);

		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer entityLiving, EnumHand handIn)
	{
		entityLiving.setActiveHand(handIn);
		ItemStack stack = entityLiving.getHeldItemMainhand();

		boolean fired = false;
		int cooldown = 10;
		boolean fullMemoryWipe = false;
		boolean isDown = false;

		//Set the NBT to a new NBT if it is null
		NBTTagCompound nbt = new NBTTagCompound();

		if(stack.getTagCompound() != null)
			nbt = stack.getTagCompound();

		if(nbt.hasKey("fired"))
			fired = nbt.getBoolean("fired");
		if(nbt.hasKey("cooldown"))
			cooldown = nbt.getInteger("cooldown");
		if(nbt.hasKey("fullMemoryWipe"))
			fullMemoryWipe = nbt.getBoolean("fullMemoryWipe");
		if(nbt.hasKey("isDown"))
			cooldown = nbt.getInteger("isDown");

		if (entityLiving instanceof EntityPlayer && stack.getItem() instanceof MemoryGun && cooldown == 0)
		{
			EntityPlayer entityplayer = (EntityPlayer)entityLiving;

			if (stack.getItemDamage() < 10)
			{
				if (!worldIn.isRemote)
				{
					//EntityForget entityforget = new EntityForget(worldIn, entityplayer, entityplayer.posX + ((double)entityplayer.width * .5), entityplayer.posY + (double)entityplayer.eyeHeight - (double)entityplayer.getYOffset() - .4, entityplayer.posZ + (double)entityplayer.width * .5, fullMemoryWipe);

					//entityforget.shoot(entityplayer, entityplayer.rotationPitch, entityplayer.rotationYaw, 0.0F, 3.0F, 0.0F);
					//worldIn.spawnEntity(entityforget);
					fired = true;
					cooldown = 10;

					Entity entity = getMouseOver(entityplayer, worldIn);
					memoryWipe(worldIn, entity, fullMemoryWipe);
				}
			}
			//     worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
		}

		nbt.setInteger("cooldown", cooldown);
		nbt.setBoolean("fired", fired);
		nbt.setBoolean("fullMemoryWipe", fullMemoryWipe);
		nbt.setBoolean("isDown", isDown);

		stack.setTagCompound(nbt);

		return new ActionResult<ItemStack>(EnumActionResult.PASS, entityLiving.getHeldItem(handIn));
	}

	public Entity getMouseOver(EntityPlayer player, World world)
	{
		BlockPos pos = player.getPosition();

		float yaw = player.rotationYaw;
		float pitch = player.rotationPitch;

		List<Entity> list = null;

		//Searches for entities up to 40 blocks away
		for(int f = 1; f <= 10; f++)
		{
			double x = (double)(-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
			double y = (double)(-MathHelper.sin((pitch) / 180.0F * (float)Math.PI) * f);
			double z = (double)(MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);


			AxisAlignedBB entityPos = new AxisAlignedBB(pos.getX() + x - 1, pos.getY() + y - 1, pos.getZ() + z - 1, pos.getX() + x + 1, pos.getY() + y + 1, pos.getZ() + z + 1);

			list = world.getEntitiesInAABBexcluding(player, entityPos, Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>()
			{
				public boolean apply(@Nullable Entity p_apply_1_)
				{
					return p_apply_1_ != null && p_apply_1_.canBeCollidedWith();
				}
			}));

			for(int j = 0; j < list.size(); j++)
			{
				Entity entity = list.get(j);

				if(entity != null)
					return entity;
			}
		}
		return null;
	}


	public void memoryWipe(World world, Entity entity, boolean fullMemoryWipe) {
		if(!(entity instanceof EntityBill) && !(entity instanceof EntitySecurityDroid) && !(entity instanceof EntityWither))
		{
			if(entity instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) entity;

				ItemStack stack = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
				ItemArmor armor = null;
				
				if(stack.getItem() instanceof ItemArmor)
				{
					armor = (ItemArmor) stack.getItem();
				}

				ArmorMaterial material = ArmorMaterial.IRON;

				if(armor == null || !(armor.getArmorMaterial() == material))
				{
					player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 300));
					player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 6000));
					player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 6000));
					player.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 30));
				}

				boolean searching = true;
				for(int i = 0; searching; i++)
				{
					int j = (int)(Math.random() * player.inventory.getSizeInventory());

					if(!player.inventory.getStackInSlot(j).isEmpty())
					{
						player.inventory.removeStackFromSlot(j);
						searching = false;
					}

					if(i == player.inventory.getSizeInventory())
						searching = false;
				}
			}
			else if(entity instanceof EntityLiving)
			{
				EntityLiving entityLiving = (EntityLiving) entity;
				ItemStack stack = entityLiving.getItemStackFromSlot(EntityEquipmentSlot.HEAD);

				ItemArmor armor = null;

				if(fullMemoryWipe)
					entityLiving.setNoAI(true);

				entityLiving.tasks.taskEntries.removeAll(entityLiving.tasks.taskEntries);
				entityLiving.targetTasks.taskEntries.removeAll(entityLiving.targetTasks.taskEntries);

				if(stack.getItem() instanceof ItemArmor)
				{
					armor = (ItemArmor) stack.getItem();
				}

				ArmorMaterial material = ArmorMaterial.IRON;

				if(armor == null || !(armor.getArmorMaterial() == material))
				{
					entityLiving.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 300));
					entityLiving.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 6000));
					entityLiving.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 6000));
					entityLiving.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 30));

					if(entityLiving instanceof EntityShapeShifter)
					{
						EntityShapeShifter shapeShifter = (EntityShapeShifter) entityLiving;

						shapeShifter.entityList = new EntityList();
						shapeShifter.entityList.add(new EntityShapeShifter(world));
						shapeShifter.currentEntity = new EntityShapeShifter(world);
						shapeShifter.currentModel = new ModelShapeShifter();
					}
					if(entityLiving instanceof EntityCreature)
					{
						EntityCreature creature = (EntityCreature) entityLiving;

						entityLiving.tasks.addTask(0, new EntityAISwimming(creature));
						entityLiving.tasks.addTask(1, new EntityAIWanderAvoidWater(creature, 1.0D));
						entityLiving.tasks.addTask(2, new EntityAILookIdle(creature));
						entityLiving.tasks.addTask(2, new EntityAIWatchClosest(creature, EntityPlayer.class, 8.0F));

						if(entityLiving instanceof EntityZombie) {
							EntityZombie zombie = (EntityZombie) entityLiving;
							zombie.setArmsRaised(true);
						}
						else if(entityLiving instanceof EntitySkeleton) {
							EntitySkeleton skeleton = (EntitySkeleton) entityLiving;
							skeleton.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.EMPTY);
							skeleton.setSwingingArms(false);
						}
					}
				}
			}
		}
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