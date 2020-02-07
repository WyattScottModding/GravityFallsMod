package network;

import handlers.SoundsHandler;
import io.netty.buffer.ByteBuf;
import main.GravityFalls;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessagePlayCountdown implements IMessage{

	public MessagePlayCountdown() {
	}

	//Bytes must be in the same order when reading and writing

	public MessagePlayCountdown(ByteBuf buf) 
	{
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
	}

	@Override
	public void fromBytes(ByteBuf buf) {		
	}

	public static class Handler implements IMessageHandler<MessagePlayCountdown, IMessage>
	{
		@Override
		public IMessage onMessage(MessagePlayCountdown message, MessageContext ctx) {
			GravityFalls.proxy.addScheduledTaskClient(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(MessagePlayCountdown message, MessageContext ctx) {
			EntityPlayer player = GravityFalls.proxy.getClientPlayer();

			player.playSound(SoundsHandler.PORTAL_WORKING, 5.0F, 1.0F);
		}
	}
}
