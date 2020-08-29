package network;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import commands.Teleport;
import init.PotionInit;
import io.netty.buffer.ByteBuf;
import main.ConfigHandler;
import main.GravityFalls;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageMoveEntity implements IMessage{

	private double xMotion;
	private double yMotion;
	private double zMotion;
	private float yaw;
	private boolean explode;
	private long num1;
	private long num2;

	public MessageMoveEntity() {
	}

	//Bytes must be in the same order when reading and writing

	public MessageMoveEntity(double xMotion, double yMotion, double zMotion, float yaw, boolean explode, long num1, long num2) 
	{
		this.xMotion = xMotion;
		this.yMotion = yMotion;
		this.zMotion = zMotion;
		this.yaw = yaw;
		this.explode = explode;
		this.num1 = num1;
		this.num2 = num2;
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeDouble(xMotion);
		buf.writeDouble(yMotion);
		buf.writeDouble(zMotion);
		buf.writeFloat(yaw);
		buf.writeBoolean(explode);
		buf.writeLong(num1);
		buf.writeLong(num2);
	}

	@Override
	public void fromBytes(ByteBuf buf) {		
		xMotion = buf.readDouble();
		yMotion = buf.readDouble();
		zMotion = buf.readDouble();
		yaw = buf.readFloat();
		explode = buf.readBoolean();
		num1 = buf.readLong();
		num2 = buf.readLong();
	}

	public static class Handler implements IMessageHandler<MessageMoveEntity, IMessage>
	{
		@Override
		public IMessage onMessage(MessageMoveEntity message, MessageContext ctx) {
			GravityFalls.proxy.addScheduledTaskClient(() -> handle(message, ctx));

			return null;
		}

		private void handle(MessageMoveEntity message, MessageContext ctx) {

			NetHandlerPlayServer serverHandler = ctx.getServerHandler();
			EntityPlayerMP player = serverHandler.player;
			World world = player.world;

			EntityLivingBase possessedEntity = getEntity(player, player.world, new UUID(message.num1, message.num2));
			
			if(possessedEntity != null)
			{
				if(message.explode && possessedEntity instanceof EntityCreeper)
				{
					EntityCreeper creeper = (EntityCreeper) possessedEntity;

					boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(world, creeper);
					float f = creeper.getPowered() ? 2.0F : 1.0F;
					creeper.world.createExplosion(creeper, creeper.posX, creeper.posY, creeper.posZ, (float)1.5 * f, flag);
					creeper.setDead();
				}
				
				//If another player is being moved
				if(possessedEntity instanceof EntityPlayerMP)
				{
					Messages.INSTANCE.sendTo(new MessageUpdatePlayerMotionClient(message.xMotion, message.yMotion, message.zMotion, message.yaw, possessedEntity.rotationPitch),  (EntityPlayerMP) possessedEntity);
				}
				else
				{
					possessedEntity.motionX = message.xMotion;
					possessedEntity.motionY = message.yMotion;
					possessedEntity.motionZ = message.zMotion;
					possessedEntity.rotationYaw = message.yaw;
				}
			}
		}

		public EntityLivingBase getEntity(EntityPlayer player, World world, UUID uuid)
		{
			BlockPos pos = player.getPosition();
			List<Entity> list = null;
			int RANGE = ConfigHandler.TIE_RANGE;
			
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