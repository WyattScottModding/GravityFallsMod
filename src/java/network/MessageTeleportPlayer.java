package network;

import commands.Teleport;
import io.netty.buffer.ByteBuf;
import main.GravityFalls;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageTeleportPlayer implements IMessage{

	private int posX;
	private int posY;
	private int posZ;
	
	public MessageTeleportPlayer() {
	}

	//Bytes must be in the same order when reading and writing

	public MessageTeleportPlayer(int x, int y, int z) 
	{
		posX = x;
		posY = y;
		posZ = z;
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(posX);
		buf.writeInt(posY);
		buf.writeInt(posZ);
	}

	@Override
	public void fromBytes(ByteBuf buf) {		
		posX = buf.readInt();
		posY = buf.readInt();
		posZ = buf.readInt();
	}

	public static class Handler implements IMessageHandler<MessageTeleportPlayer, IMessage>
	{
		@Override
		public IMessage onMessage(MessageTeleportPlayer message, MessageContext ctx) {
			GravityFalls.proxy.addScheduledTaskClient(() -> handle(message, ctx));
			
			return null;
		}
		
		private void handle(MessageTeleportPlayer message, MessageContext ctx) {

			NetHandlerPlayServer serverHandler = ctx.getServerHandler();
			EntityPlayerMP player = serverHandler.player;
						
			Teleport.teleportToDimension(player, 0, message.posX, message.posY, message.posZ);
		}
	}
}