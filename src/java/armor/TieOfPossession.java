package armor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import entity.EntityBill;
import entity.EntityShapeShifter;
import handlers.KeyBindings;
import init.ItemInit;
import main.ConfigHandler;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import network.MessageMoveEntity;
import network.MessageTeleportPlayer;
import network.Messages;

public class TieOfPossession extends ItemArmor implements IHasModel
{
	public TieOfPossession(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) 
	{
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsarmor);

		ItemInit.ITEMS.add(this);
	}

	@Override
	public void registerModels() 
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {

		//List of mobs that can't be controlled
		ArrayList<EntityLivingBase> entities = new ArrayList<EntityLivingBase>();
		entities.add(new EntityBill(world));
		entities.add(new EntityDragon(world));
		entities.add(new EntityWither(world));

		//Set the NBT to a new NBT if it is null
		NBTTagCompound nbt = new NBTTagCompound();

		if(stack.getTagCompound() != null)
			nbt = stack.getTagCompound();

		EntityLivingBase possessedEntity = null;

		if(nbt.hasKey("num1") && nbt.hasKey("num2")) 
			possessedEntity = getEntity(player, world, new UUID(nbt.getLong("num1"), nbt.getLong("num2")), false);


		if(possessedEntity != null && possessedEntity.isDead)
			possessedEntity = null;

		if(possessedEntity != null && !entities.contains(possessedEntity))
		{
			if(player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == ItemInit.TIE_OF_POSSESSION)
			{
				//Movement Logic
				float f = 0.4F;
				float yaw1 = possessedEntity.rotationYaw;
				float pitch = possessedEntity.rotationPitch;

				double motionX1 = (double)(-MathHelper.sin(yaw1 / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
				double motionZ1 = (double)(MathHelper.cos(yaw1 / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);

				float yaw2 = possessedEntity.rotationYaw + 90;

				if(yaw2 > 180)
					yaw2 -= 360;

				double motionX2 = (double)(-MathHelper.sin(yaw2 / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
				double motionZ2 = (double)(MathHelper.cos(yaw2 / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);

				float yaw3 = possessedEntity.rotationYaw - 90;

				if(yaw3 < -180)
					yaw3 += 360;

				double motionX3 = (double)(-MathHelper.sin(yaw3 / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
				double motionZ3 = (double)(MathHelper.cos(yaw3 / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);

				double x = possessedEntity.motionX;
				double y = possessedEntity.motionY;
				double z = possessedEntity.motionZ;

				//Move
				if(world.isRemote)
				{
					if(KeyBindings.LEFT.isDown())
					{	
						x = motionX3;
						z = motionZ3;
					}
					else if(KeyBindings.RIGHT.isDown())
					{
						x = motionX2;
						z = motionZ2;
					}
					if(KeyBindings.UP.isDown())
					{
						if(KeyBindings.RIGHT.isDown())
						{
							x = motionX1 + motionX2;
							z = motionZ1 + motionZ2;
						}
						else if(KeyBindings.LEFT.isDown())
						{
							x = motionX1 + motionX3;
							z = motionZ1 + motionZ3;
						}
						else
						{
							x = motionX1;
							z = motionZ1;
						}
					}
					else if(KeyBindings.DOWN.isDown())
					{
						if(KeyBindings.RIGHT.isDown())
						{
							x = -motionX1 + motionX2;
							z = -motionZ1 + motionZ2;
						}
						else if(KeyBindings.LEFT.isDown())
						{
							x = -motionX1 + motionX3;
							z = -motionZ1 + motionZ3;
						}
						else
						{
							x = -motionX1;
							z = -motionZ1;
						}
					}
					
					if(possessedEntity != null && possessedEntity instanceof EntityPlayerSP)
					{
						possessedEntity.motionX = x;
						possessedEntity.motionY = y;
						possessedEntity.motionZ = z;
						
						if(KeyBindings.ARMOR1.isDown())
							possessedEntity.rotationYaw = possessedEntity.rotationYaw - 25;
						else if(KeyBindings.ARMOR2.isDown())
							possessedEntity.rotationYaw = possessedEntity.rotationYaw + 25;
					}
					
					//Move Entity
					if(KeyBindings.ARMOR1.isDown())
						Messages.INSTANCE.sendToServer(new MessageMoveEntity(x, y, z, possessedEntity.rotationYaw - 25, false, nbt.getLong("num1"), nbt.getLong("num2")));
					else if(KeyBindings.ARMOR2.isDown())
						Messages.INSTANCE.sendToServer(new MessageMoveEntity(x, y, z, possessedEntity.rotationYaw + 25, false, nbt.getLong("num1"), nbt.getLong("num2")));
					else if(KeyBindings.DOWN.isDown() || KeyBindings.UP.isDown() || KeyBindings.LEFT.isDown() || KeyBindings.RIGHT.isDown())
						Messages.INSTANCE.sendToServer(new MessageMoveEntity(x, y, z, possessedEntity.rotationYaw, false, nbt.getLong("num1"), nbt.getLong("num2")));
				}

				possessedEntity.stepHeight = 1;
			}

			//Explode if Creeper
			if(possessedEntity instanceof EntityCreeper)
			{
				EntityCreeper creeper = (EntityCreeper) possessedEntity;

				if (world.isRemote && KeyBindings.ARMOR3.isDown())
				{
					Messages.INSTANCE.sendToServer(new MessageMoveEntity(possessedEntity.motionX, possessedEntity.motionY, possessedEntity.motionZ, possessedEntity.rotationYaw, true, nbt.getLong("num1"), nbt.getLong("num2")));
					world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, creeper.posX,creeper.posY, creeper.posZ, 1, 1, 1, 0);

					possessedEntity = null;
				}
			}
		}

		stack.setTagCompound(nbt);

		super.onArmorTick(world, player, stack);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) 
	{
		//Set the NBT to a new NBT if it is null
		NBTTagCompound nbt = new NBTTagCompound();
		ItemStack stack = player.getHeldItemMainhand();

		if(stack.getTagCompound() != null)
			nbt = stack.getTagCompound();

		//Gets the UUID of the previous entity
		long prevNum1 = 0;
		long prevNum2 = 0;
		if(nbt.hasKey("num1") && nbt.hasKey("num2")) {
			prevNum1 = nbt.getLong("num1");
			prevNum2 = nbt.getLong("num2");
		}

		EntityLivingBase entity = getMouseOver(player, world);
		
		//Players in creative mode can't be controlled
		if(entity != null && entity instanceof EntityPlayer && ((EntityPlayer)entity).isCreative())
			entity = null;

		if(entity != null)
		{
			//Entity cannot be controlled if it has something in it's chestplate spot
			if(!world.isRemote && (entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST).isEmpty() || entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == ItemInit.TIE_OF_POSSESSION2)) {
				//Saves the UUID of the entity
				nbt.setLong("num1", entity.getUniqueID().getMostSignificantBits());
				nbt.setLong("num2", entity.getUniqueID().getLeastSignificantBits());

				//Adds effects to entity when clicked
				entity.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 10, 1));

				EntityLivingBase entityPrevious = getEntity(player, world, new UUID(prevNum1, prevNum2), true);

				if(entityPrevious == entity)
				{
					//Removes Tie from previous entity
					if(entityPrevious != null && entityPrevious.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == ItemInit.TIE_OF_POSSESSION2)
					{
						entityPrevious.setItemStackToSlot(EntityEquipmentSlot.CHEST, ItemStack.EMPTY);
						nbt.setLong("num1", 0);
						nbt.setLong("num2", 0);
					}
				}
				else
				{
					//Applies a tie to the entity being controlled.  The tie cannot be taken off until a new entity is being controlled.
					ItemStack tie2 = new ItemStack(ItemInit.TIE_OF_POSSESSION2);
					tie2.addEnchantment(Enchantments.BINDING_CURSE, 1);
					entity.setItemStackToSlot(EntityEquipmentSlot.CHEST, tie2);
				}
			}
			else
				player.playSound(SoundEvents.BLOCK_DISPENSER_FAIL, 1, 1);
		}

		stack.setTagCompound(nbt);

		//Doesn't equip armor if an entity was clicked
		if(entity == null && !player.isCreative())
			return super.onItemRightClick(world, player, hand);
		else {
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
		}
	}

	public EntityLivingBase getMouseOver(EntityLivingBase player, World world)
	{
		Vec3d lookVec = player.getLookVec();

		BlockPos pos = player.getPosition();

		float yaw = player.rotationYaw;
		float pitch = player.rotationPitch;

		for(int f = 1; f <= 5; f++)
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

					return mob;
				}
			}
		}
		return null;
	}

	public EntityLivingBase getEntity(EntityPlayer player, World world, UUID uuid, boolean extendedRange)
	{
		BlockPos pos = player.getPosition();
		List<Entity> list = null;
		int RANGE = ConfigHandler.TIE_RANGE;
		
		if(extendedRange)
			RANGE += 500;

		//Searches for entities in the given range.  Default value is 40 blocks	
		AxisAlignedBB entityPos = new AxisAlignedBB(pos.getX() - RANGE, pos.getY() - RANGE, pos.getZ() - RANGE, pos.getX() + RANGE, pos.getY() + RANGE, pos.getZ() + RANGE);

		list = world.getEntitiesInAABBexcluding(player, entityPos, Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>()
		{
			public boolean apply(@Nullable Entity p_apply_1_)
			{
				return p_apply_1_ != null && p_apply_1_.canBeCollidedWith();
			}
		}));

		if(extendedRange)
			list.addAll(world.playerEntities);

		for(int j = 0; j < list.size(); j++)
		{
			Entity entity = list.get(j);

			//If the entity found is the same as the previous entity
			if(entity != null && entity instanceof EntityLivingBase && entity.getUniqueID().equals(uuid) && entity != player)
				return (EntityLivingBase) entity;
		}
		return null;
	}
}