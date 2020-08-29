package network;

import java.util.List;
import java.util.UUID;

import commands.Teleport;
import init.PotionInit;
import io.netty.buffer.ByteBuf;
import main.ConfigHandler;
import main.GravityFalls;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageChangeSize implements IMessage{

	private int num;
	private long uuid1, uuid2;

	public MessageChangeSize() {
	}

	//Bytes must be in the same order when reading and writing

	public MessageChangeSize(int num, long uuid1, long uuid2) 
	{
		this.num = num;
		this.uuid1 = uuid1;
		this.uuid2 = uuid2;
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(num);
		buf.writeLong(uuid1);
		buf.writeLong(uuid2);
	}

	@Override
	public void fromBytes(ByteBuf buf) {		
		num = buf.readInt();
		uuid1 = buf.readLong();
		uuid2 = buf.readLong();
	}

	public static class Handler implements IMessageHandler<MessageChangeSize, IMessage>
	{
		@Override
		public IMessage onMessage(MessageChangeSize message, MessageContext ctx) {
			GravityFalls.proxy.addScheduledTaskClient(() -> handle(message, ctx));

			return null;
		}

		private void handle(MessageChangeSize message, MessageContext ctx) {

			NetHandlerPlayServer serverHandler = ctx.getServerHandler();
			EntityPlayerMP player = null;
			if(serverHandler.player.world.getPlayerEntityByUUID(new UUID(message.uuid1, message.uuid2)) instanceof EntityPlayerMP)
				player = (EntityPlayerMP) serverHandler.player.world.getPlayerEntityByUUID(new UUID(message.uuid1, message.uuid2));
			
			if(player == null)
				return;
			
			ItemStack stack = player.getHeldItemMainhand();


			//Set the NBT to a new NBT if it is null
			NBTTagCompound nbt = new NBTTagCompound();

			if(stack.getTagCompound() != null)
				nbt = stack.getTagCompound();

			int height = 0;
			boolean clicked = false;
			int entityHeight = 0;

			if(nbt.hasKey("height"))
				height = nbt.getInteger("height");
			if(nbt.hasKey("clicked"))
				clicked = nbt.getBoolean("clicked");
			if(nbt.hasKey("entityHeight"))
				entityHeight = nbt.getInteger("entityHeight");

			//If the player is changing size
			if(message.num == 1)
			{
				player.removePotionEffect(PotionInit.GROWTH_EFFECT);

				clicked = true;
				stack.damageItem(1, player);
				height = 1;
				
				Messages.INSTANCE.sendToAll(new MessageChangeSizeClient(height, message.uuid1, message.uuid2, true));
			}
			//If the player is growing
			else if(message.num == 2)
			{
				if(player.height < ConfigHandler.SIZE_MAX)
				{
					height += 2;

					clicked = true;
					stack.damageItem(1, player);
				}
				else
					clicked = false;

				player.removePotionEffect(PotionInit.GROWTH_EFFECT);

				PotionEffect effect = new PotionEffect(PotionInit.GROWTH_EFFECT, 10000000, height, true, false);
				effect.setPotionDurationMax(true);
				player.addPotionEffect(effect);
				
				Messages.INSTANCE.sendToAll(new MessageChangeSizeClient(height, message.uuid1, message.uuid2, false));
			}
			//If the player is shrinking
			else if(message.num == 3)
			{
				if(player.height > ConfigHandler.SIZE_MIN)
				{
					height -= 2;

					clicked = true;
					stack.damageItem(1, player);
				}
				else
					clicked = false;

				player.removePotionEffect(PotionInit.GROWTH_EFFECT);

				PotionEffect effect = new PotionEffect(PotionInit.GROWTH_EFFECT, 10000000, height, true, false);
				effect.setPotionDurationMax(true);
				player.addPotionEffect(effect);
				
				Messages.INSTANCE.sendToAll(new MessageChangeSizeClient(height, message.uuid1, message.uuid2, false));
			}
			else if(message.num == 4)
			{
				clicked = false;
			}
			//Changing the size of anther entity
			else if(message.num == 5)
			{
				EntityLivingBase entityLiving = getMouseOver(player, player.world);

				if(entityLiving != null)
				{
					if(entityLiving.height <= ConfigHandler.SIZE_MAX) {
	
						entityHeight += 10;
						clicked = true;
						stack.damageItem(1, player);
					}
					else
						clicked = false;
	
					if(entityLiving instanceof EntityPlayerMP)
					{					
						//Tells all other players that this player is changing size
						Messages.INSTANCE.sendToAll(new MessageChangeSizeClient(entityHeight, message.uuid1, message.uuid2, false));
					}
	
					entityLiving.removePotionEffect(PotionInit.GROWTH_EFFECT);
	
					PotionEffect effect = new PotionEffect(PotionInit.GROWTH_EFFECT, 10000000, entityHeight, true, false);
					effect.setPotionDurationMax(true);
					entityLiving.addPotionEffect(effect);
				}
			}
			//Changing the size of anther entity
			else if(message.num == 6)
			{
				EntityLivingBase entityLiving = getMouseOver(player, player.world);

				if(entityLiving != null)
				{
					if(entityLiving.height >= ConfigHandler.SIZE_MIN) {
	
						entityHeight -= 10;
						clicked = true;
						stack.damageItem(1, player);
					}
					else
						clicked = false;
	
					if(entityLiving instanceof EntityPlayerMP)
					{
						//Tells all other players that this player is changing size
						Messages.INSTANCE.sendToAll(new MessageChangeSizeClient(entityHeight, message.uuid1, message.uuid2, false));
					}
	
					entityLiving.removePotionEffect(PotionInit.GROWTH_EFFECT);
	
					PotionEffect effect = new PotionEffect(PotionInit.GROWTH_EFFECT, 10000000, entityHeight, true, false);
					effect.setPotionDurationMax(true);
					entityLiving.addPotionEffect(effect);
				}
			}

			nbt.setInteger("height", height);
			nbt.setBoolean("clicked", clicked);
			nbt.setInteger("entityHeight", entityHeight);
			stack.setTagCompound(nbt);
		}

		public EntityLivingBase getMouseOver(EntityPlayer player, World world)
		{
			BlockPos pos = player.getPosition().add(0, player.eyeHeight, 0);

			float yaw = player.rotationYaw;
			float pitch = player.rotationPitch;

			List<Entity> list = null;
			EntityLivingBase closestEntity = null;

			for(int f = 1; f <= 25; f++)
			{
				double x = (double)(-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
				double y = (double)(-MathHelper.sin((pitch) / 180.0F * (float)Math.PI) * f);
				double z = (double)(MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);


				AxisAlignedBB entityPos;

				if(f < 10)
					entityPos = new AxisAlignedBB(pos.getX() + x, pos.getY() + y, pos.getZ() + z, pos.getX() + x + 1, pos.getY() + y + 1, pos.getZ() + z + 1);
				else
					entityPos = new AxisAlignedBB(pos.getX() + x - 1, pos.getY() + y - 1, pos.getZ() + z - 1, pos.getX() + x + 2, pos.getY() + y + 2, pos.getZ() + z + 2);

				list = world.getEntitiesWithinAABBExcludingEntity(player, entityPos);


				for(int j = 0; j < list.size(); ++j)
				{
					Entity entity = list.get(j);

					if(entity instanceof EntityLivingBase)
					{
						EntityLivingBase entityLiving = (EntityLivingBase) entity;

						float distance = player.getDistance(entityLiving);

						if(closestEntity == null)
							closestEntity = entityLiving;
						else if(distance < player.getDistance(closestEntity))
							closestEntity = entityLiving;
					}
				}
			}
			return closestEntity;
		}
	}
}