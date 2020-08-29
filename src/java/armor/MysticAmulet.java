package armor;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import entity.EntityBill;
import entity.EntityList;
import entity.EntityShapeShifter;
import entity.EntityTimeBaby;
import handlers.KeyBindings;
import init.ItemInit;
import main.ConfigHandler;
import main.GravityFalls;
import main.IHasModel;
import main.Reference;
import models.ModelShapeShifter;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityZombieHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import network.MessageMoveEntity;
import network.MessageMysticAmulet;
import network.Messages;

public class MysticAmulet extends ItemArmor implements IHasModel
{
	public MysticAmulet(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) 
	{
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsarmor);

		this.addPropertyOverride(new ResourceLocation("on"), new IItemPropertyGetter()
		{
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
			{
				//Set the NBT to a new NBT if it is null
				NBTTagCompound nbt = new NBTTagCompound();

				if(stack.getTagCompound() != null)
					nbt = stack.getTagCompound();

				boolean active = false;

				if(nbt.hasKey("active"))
					active = nbt.getBoolean("active");

				if(stack != null && entityIn != null && stack.isItemEqual(entityIn.getItemStackFromSlot(EntityEquipmentSlot.CHEST)))
					return (active && entityIn.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == ItemInit.MYSTIC_AMULET) ? 0.0F : 1.0F;
				else
					return 0.0F;
			}
		});

		ItemInit.ITEMS.add(this);
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entityIn, int itemSlot, boolean isSelected) 
	{
		if(entityIn instanceof EntityPlayer)
		{
			//Set the NBT to a new NBT if it is null
			NBTTagCompound nbt = new NBTTagCompound();

			if(stack.getTagCompound() != null)
				nbt = stack.getTagCompound();

			EntityPlayer player = (EntityPlayer)entityIn;
			Entity entity = null;

			//Get NBT values
			boolean thrown = false;
			boolean active = false;
			int timer = -100;

			if(nbt.hasKey("thrown"))
				thrown = nbt.getBoolean("thrown");
			if(nbt.hasKey("timer"))
				timer = nbt.getInteger("timer");
			if(nbt.hasKey("active"))
				active = nbt.getBoolean("active");
			if(nbt.hasKey("num1") && nbt.hasKey("num2")) 
				entity = getEntity(player, world, new UUID(nbt.getLong("num1"), nbt.getLong("num2")));
			if(entity == null) {
				entity = getMouseOver(player, world);
				if(entity != null)
				{
					nbt.setLong("num1", entity.getUniqueID().getMostSignificantBits());
					nbt.setLong("num2", entity.getUniqueID().getLeastSignificantBits());
				}
			}
			//If the mystic amulet is equipped
			boolean chestEquipped = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == ItemInit.MYSTIC_AMULET;

			if(!chestEquipped)
				active = false;

			//Enables the ability to fly
			if(!player.capabilities.isCreativeMode)
			{
				if(chestEquipped && testGround(player, world))
				{
					player.capabilities.allowFlying = true;
				}
				else if(chestEquipped && player.capabilities.isFlying)
				{
					player.capabilities.allowFlying = false;
					player.motionY = -0.2;
				}
				if(player.capabilities.isFlying && chestEquipped)
				{
					player.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 10, 0));
					player.capabilities.disableDamage = false;
				}

				if(!chestEquipped)
					player.capabilities.allowFlying = false;
			}

			if(thrown)
			{
				timer++;
				if(timer == 30)
				{
					timer = 0;
					thrown = false;
				}
			}

			if(chestEquipped && !thrown && world.isRemote && player.getHeldItemMainhand().getItem() != ItemInit.MAGNET_GUN && player.getHeldItemMainhand().getItem() != ItemInit.MAGICFLASHLIGHT)
			{
				double f = 5.0;
				float yaw = player.rotationYaw;
				float pitch = player.rotationPitch;
				
				if(KeyBindings.ITEM1.isDown())
				{
					if(entity != null && entity instanceof EntityPlayer)
					{
						EntityPlayer otherPlayer = (EntityPlayer) entity;
						
						//Calculate the position the entity should be at in relation to the player
						double posX = (double)(-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f) + player.posX;
						double posY = (double)(-MathHelper.sin((pitch) / 180.0F * (float)Math.PI) * f) + player.posY + player.eyeHeight;
						double posZ = (double)(MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f) + player.posZ;

						
						otherPlayer.moveToBlockPosAndAngles(new BlockPos(posX, posY, posZ), player.rotationYaw * -1, player.rotationPitch * -1);
						otherPlayer.motionX = 0;
						otherPlayer.motionY = 0;
						otherPlayer.motionZ = 0;
						otherPlayer.fallDistance = 0;
					}
					Messages.INSTANCE.sendToServer(new MessageMysticAmulet(1));
				}
				//If the person lets go, there is no longer an attached entity
				else
				{
					Messages.INSTANCE.sendToServer(new MessageMysticAmulet(2));
				}
				//Throws the entity
				if(KeyBindings.ITEM2.isDown())
				{					
					if(entity != null && entity instanceof EntityPlayer)
					{
						EntityPlayer otherPlayer = (EntityPlayer) entity;
						
						otherPlayer.motionX = (double)(-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
						otherPlayer.motionY = (double)(-MathHelper.sin((pitch) / 180.0F * (float)Math.PI) * f);
						otherPlayer.motionZ = (double)(MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);

					}
					Messages.INSTANCE.sendToServer(new MessageMysticAmulet(3));
				}
			}

			nbt.setDouble("timer", timer);
			nbt.setBoolean("thrown", thrown);
			nbt.setBoolean("active", active);

			stack.setTagCompound(nbt);
		}
		super.onUpdate(stack, world, entityIn, itemSlot, isSelected);
	}
	
	public EntityLivingBase getEntity(EntityPlayer player, World world, UUID uuid)
	{
		BlockPos pos = player.getPosition();
		List<Entity> list = null;
		int RANGE = 6;


		//Searches for entities in the given range.  Default value is 40 blocks	
		AxisAlignedBB entityPos = new AxisAlignedBB(pos.getX() - RANGE, pos.getY() - RANGE, pos.getZ() - RANGE, pos.getX() + RANGE, pos.getY() + RANGE, pos.getZ() + RANGE);

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

			//If the entity found is the same as the previous entity
			if(entity != null && entity instanceof EntityLivingBase && entity.getUniqueID().equals(uuid) && entity != player)
				return (EntityLivingBase) entity;
		}
		return null;
	}
	
	public Entity getMouseOver(EntityPlayer player, World world)
	{
		BlockPos pos = player.getPosition();

		float yaw = player.rotationYaw;
		float pitch = player.rotationPitch;

		List<Entity> list = null;

		//Searches for entities up to 40 blocks away
		for(int f = 1; f <= 40; f++)
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
				{
					if(entity instanceof EntityLivingBase)
					{
						if(!(entity instanceof EntityBill || entity instanceof EntityTimeBaby))
								return entity;
					}
				}
			}
		}
		return null;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) 
	{
		//Set the NBT to a new NBT if it is null
		NBTTagCompound nbt = new NBTTagCompound();

		if(stack.getTagCompound() != null)
			nbt = stack.getTagCompound();

		boolean active = false;

		if(nbt.hasKey("active"))
			active = nbt.getBoolean("active");

		if(active)
			return Reference.MODID + ":textures/models/armor/amuletglowing_layer_1.png";
		else
			return Reference.MODID + ":textures/models/armor/amulet_layer_1.png";
	}

	public boolean testGround(EntityPlayer player, World world)
	{
		boolean ground = false;

		BlockPos pos1 = player.getPosition();
		BlockPos pos2 = pos1.down();
		BlockPos pos3 = pos2.down();
		BlockPos pos4 = pos3.down();
		BlockPos pos5 = pos4.down();
		BlockPos pos6 = pos5.down();
		BlockPos pos7 = pos6.down();

		if(world.getBlockState(pos7).getBlock() != Blocks.AIR)
			ground = true;
		else if(world.getBlockState(pos6).getBlock() != Blocks.AIR)
			ground = true;
		else if(world.getBlockState(pos5).getBlock() != Blocks.AIR)
			ground = true;
		else if(world.getBlockState(pos4).getBlock() != Blocks.AIR)
			ground = true;
		else if(world.getBlockState(pos3).getBlock() != Blocks.AIR)
			ground = true;
		else if(world.getBlockState(pos2).getBlock() != Blocks.AIR)
			ground = true;
		else if(world.getBlockState(pos1).getBlock() != Blocks.AIR)
			ground = true;

		return ground;
	}
	
	 /**
     * Return whether this item is repairable in an anvil.
     *  
     * @param toRepair the {@code ItemStack} being repaired
     * @param repair the {@code ItemStack} being used to perform the repair
     */
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        return repair.getItem() == Items.DIAMOND;
    }

	@Override
	public void registerModels() 
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}
}