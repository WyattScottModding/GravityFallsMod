package network;

import handlers.SoundsHandler;
import io.netty.buffer.ByteBuf;
import main.ConfigHandler;
import main.GravityFalls;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessagePlayPortalSound implements IMessage{

	public MessagePlayPortalSound() {
	}

	//Bytes must be in the same order when reading and writing

	public MessagePlayPortalSound(ByteBuf buf) 
	{
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
	}

	@Override
	public void fromBytes(ByteBuf buf) {		
	}

	public static class Handler implements IMessageHandler<MessagePlayPortalSound, IMessage>
	{
		@Override
		public IMessage onMessage(MessagePlayPortalSound message, MessageContext ctx) {
			GravityFalls.proxy.addScheduledTaskClient(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(MessagePlayPortalSound message, MessageContext ctx) {
			EntityPlayer player = GravityFalls.proxy.getClientPlayer();

			player.playSound(SoundsHandler.PORTAL_FINISHED, 5.0F, 1.0F);
		}

	}
}
