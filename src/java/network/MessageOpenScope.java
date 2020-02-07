package network;

import io.netty.buffer.ByteBuf;
import main.ConfigHandler;
import main.GravityFalls;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageOpenScope implements IMessage{

	public MessageOpenScope() {
	}

	//Bytes must be in the same order when reading and writing

	public MessageOpenScope(ByteBuf buf) 
	{
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
	}

	@Override
	public void fromBytes(ByteBuf buf) {		
	}

	public static class Handler implements IMessageHandler<MessageOpenScope, IMessage>
	{
		@Override
		public IMessage onMessage(MessageOpenScope message, MessageContext ctx) {
			GravityFalls.proxy.addScheduledTaskClient(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(MessageOpenScope message, MessageContext ctx) {
			EntityPlayer player = GravityFalls.proxy.getClientPlayer();
			player.openGui(GravityFalls.instance, ConfigHandler.SCOPE, player.world, (int) player.posX, (int) player.posY, (int) player.posZ);
		}

	}
}
