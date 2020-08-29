package network;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import commands.Teleport;
import entity.EntityBill;
import init.BlockInit;
import init.ItemInit;
import init.PotionInit;
import io.netty.buffer.ByteBuf;
import main.ConfigHandler;
import main.GravityFalls;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityZombieHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageMysticAmulet implements IMessage{

	int num1;

	public MessageMysticAmulet() {
	}

	public MessageMysticAmulet(int num1) {
		this.num1 = num1;
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(num1);
	}

	@Override
	public void fromBytes(ByteBuf buf) {		
		num1 = buf.readInt();
	}

	public static class Handler implements IMessageHandler<MessageMysticAmulet, IMessage>
	{
		@Override
		public IMessage onMessage(MessageMysticAmulet message, MessageContext ctx) {
			GravityFalls.proxy.addScheduledTaskClient(() -> handle(message, ctx));

			return null;
		}

		private void handle(MessageMysticAmulet message, MessageContext ctx) {

			NetHandlerPlayServer serverHandler = ctx.getServerHandler();
			EntityPlayerMP player = serverHandler.player;
			World world = player.world;
			ItemStack stack = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);

			//Set the NBT to a new NBT if it is null
			NBTTagCompound nbt = new NBTTagCompound();

			if(stack.getTagCompound() != null)
				nbt = stack.getTagCompound();

			//Get NBT values
			boolean thrown = false;
			boolean active = false;
			int timer = -100;
			Entity entity = null;

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

			double f = 5.0;
			float yaw = player.rotationYaw;
			float pitch = player.rotationPitch;

			if(entity != null)
			{
				if(message.num1 == 1)
				{
					active = true;

					//Makes the entity glow (if it is a living entity)
					if(entity instanceof EntityLivingBase)
					{
						EntityLivingBase entityLiving = (EntityLivingBase) entity;
						entityLiving.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 10, 0));
					}

					//Calculate the position the entity should be at in relation to the player
					double posX = (double)(-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f) + player.posX;
					double posY = (double)(-MathHelper.sin((pitch) / 180.0F * (float)Math.PI) * f) + player.posY + player.eyeHeight;
					double posZ = (double)(MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f) + player.posZ;

					entity.moveToBlockPosAndAngles(new BlockPos(posX, posY, posZ), player.rotationYaw * -1, player.rotationPitch * -1);
					entity.motionX = 0;
					entity.motionY = 0;
					entity.motionZ = 0;
					entity.fallDistance = 0;
					
					if(entity instanceof EntityPlayerMP)
					{
						Messages.INSTANCE.sendTo(new MessageUpdatePlayerPositionClient(posX, posY, posZ, player.rotationYaw, player.rotationPitch),  (EntityPlayerMP) entity);
						Messages.INSTANCE.sendTo(new MessageUpdatePlayerMotionClient(entity.motionX, entity.motionY, entity.motionZ, player.rotationYaw, player.rotationPitch),  (EntityPlayerMP) entity);
					}
				}
				else if(message.num1 == 2)
				{
					entity = null;
					active = false;
				}
				else if(message.num1 == 3)
				{
					entity.motionX = (double)(-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
					entity.motionY = (double)(-MathHelper.sin((pitch) / 180.0F * (float)Math.PI) * f);
					entity.motionZ = (double)(MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);

					if(entity instanceof EntityLivingBase)
					{
						EntityLivingBase entityLiving = (EntityLivingBase) entity;
						if(entityLiving instanceof EntitySkeleton || entityLiving instanceof EntityZombie || entityLiving instanceof EntityZombieHorse)
							entityLiving.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 2, 0));
						else
							entityLiving.addPotionEffect(new PotionEffect(MobEffects.INSTANT_DAMAGE, 2, 0));					
					}
					if(entity instanceof EntityPlayerMP)
					{
						Messages.INSTANCE.sendTo(new MessageUpdatePlayerMotionClient(entity.motionX, entity.motionY, entity.motionZ, player.rotationYaw, player.rotationPitch),  (EntityPlayerMP) entity);
					}

					entity = null;
					thrown = true;
					active = false;
				}
			}

			if(entity != null && entity.isDead)
				entity = null;

			nbt.setDouble("timer", timer);
			nbt.setBoolean("thrown", thrown);
			nbt.setBoolean("active", active);

			//If the entity is null or the amulet it not active, disconnect the entity
			if(entity == null || !active) {
				nbt.setLong("num1", -4);
				nbt.setLong("num2", -4);
			}

			stack.setTagCompound(nbt);
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
						if(entity instanceof EntityLiving)
						{
							return entity;
						}
					}
				}
			}
			return null;
		}
		public EntityLivingBase getEntity(EntityPlayer player, World world, UUID uuid)
		{
			BlockPos pos = player.getPosition();
			List<Entity> list = null;
			int RANGE = 8;


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
	}
}