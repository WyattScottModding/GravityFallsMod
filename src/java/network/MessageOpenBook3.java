package network;

import io.netty.buffer.ByteBuf;
import main.ConfigHandler;
import main.GravityFalls;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageOpenBook3 implements IMessage{

	public MessageOpenBook3() {
	}

	//Bytes must be in the same order when reading and writing

	public MessageOpenBook3(ByteBuf buf) 
	{
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
	}

	@Override
	public void fromBytes(ByteBuf buf) {		
	}

	public static class Handler implements IMessageHandler<MessageOpenBook3, IMessage>
	{
		@Override
		public IMessage onMessage(MessageOpenBook3 message, MessageContext ctx) {
			GravityFalls.proxy.addScheduledTaskClient(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(MessageOpenBook3 message, MessageContext ctx) {
			EntityPlayer player = GravityFalls.proxy.getClientPlayer();
			player.openGui(GravityFalls.instance, ConfigHandler.GUI_JOURNAL3, player.world, (int) player.posX, (int) player.posY, (int) player.posZ);
		}

	}
}
