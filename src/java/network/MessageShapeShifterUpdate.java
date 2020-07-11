package network;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import commands.Teleport;
import entity.EntityShapeShifter;
import io.netty.buffer.ByteBuf;
import main.GravityFalls;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.server.SPacketEntityMetadata;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import proxy.ClientProxy;

public class MessageShapeShifterUpdate implements IMessage{

	private int entity;
	private double posX;
	private double posY;
	private double posZ;
	
	public MessageShapeShifterUpdate() {
	}

	//Bytes must be in the same order when reading and writing

	public MessageShapeShifterUpdate(int entity, double posX, double posY, double posZ) 
	{
		this.entity = entity;
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(entity);
		buf.writeDouble(posX);
		buf.writeDouble(posY);
		buf.writeDouble(posZ);
	}

	@Override
	public void fromBytes(ByteBuf buf) {		
		entity = buf.readInt();
		posX = buf.readDouble();
		posY = buf.readDouble();
		posZ = buf.readDouble();
	}

	public static class Handler implements IMessageHandler<MessageShapeShifterUpdate, IMessage>
	{
		@Override
		public IMessage onMessage(MessageShapeShifterUpdate message, MessageContext ctx) {
			GravityFalls.proxy.addScheduledTaskClient(() -> handle(message, ctx));
			
			return null;
		}
		
		private void handle(MessageShapeShifterUpdate message, MessageContext ctx) {

			EntityPlayer player = GravityFalls.proxy.getClientPlayer();
			World world = player.world;
			AxisAlignedBB entityPos = new AxisAlignedBB(message.posX - 0.5, message.posY - 0.5, message.posZ - 0.5, message.posX + 0.5, message.posY + 0.5, message.posZ + 0.5);

			List<Entity> list = world.getEntitiesInAABBexcluding(player, entityPos, Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>()
			{
				public boolean apply(@Nullable Entity p_apply_1_)
				{
					return p_apply_1_ != null && p_apply_1_.canBeCollidedWith();
				}
			}));
			
			for(Entity element : list) {
				if(element instanceof EntityShapeShifter) {
					EntityShapeShifter shapeShifter = (EntityShapeShifter) element;
					
					//shapeShifter.currentEntity = shapeShifter.intToEntity(message.entity);
					shapeShifter.setEntity(shapeShifter.intToEntity(message.entity));
				}
			}
		}
	}
}