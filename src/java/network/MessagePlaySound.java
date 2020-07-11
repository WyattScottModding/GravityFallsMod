package network;

import handlers.SoundsHandler;
import io.netty.buffer.ByteBuf;
import main.GravityFalls;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessagePlaySound implements IMessage{
	
	private short song;

	public MessagePlaySound() {
	}
	
	public MessagePlaySound(short song) {
		this.song = song;
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeShort(song);
	}

	@Override
	public void fromBytes(ByteBuf buf) {	
		this.song = buf.readShort();
	}

	public static class Handler implements IMessageHandler<MessagePlaySound, IMessage>
	{
		@Override
		public IMessage onMessage(MessagePlaySound message, MessageContext ctx) {
			GravityFalls.proxy.addScheduledTaskClient(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(MessagePlaySound message, MessageContext ctx) {
			EntityPlayer player = GravityFalls.proxy.getClientPlayer();
			
			if(message.song == 0)
				player.playSound(SoundsHandler.PORTAL_WORKING, 5.0F, 1.0F);
			else if(message.song == 1)
				player.playSound(SoundsHandler.PORTAL_FINISHED, 5.0F, 1.0F);
			else if(message.song == 2)
				player.playSound(SoundsHandler.STARTING_THE_PORTAL, 5.0F, 1.0F);
			else if(message.song == 3)
				player.playSound(SoundsHandler.ALIEN_SHIP, 1.0F, 1.0F);
		}
	}
}
