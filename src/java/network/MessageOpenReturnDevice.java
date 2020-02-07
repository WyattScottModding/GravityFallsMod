package network;

import io.netty.buffer.ByteBuf;
import main.ConfigHandler;
import main.GravityFalls;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageOpenReturnDevice implements IMessage{

	public MessageOpenReturnDevice() {
	}

	//Bytes must be in the same order when reading and writing

	public MessageOpenReturnDevice(ByteBuf buf) 
	{
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
	}

	@Override
	public void fromBytes(ByteBuf buf) {		
	}

	public static class Handler implements IMessageHandler<MessageOpenReturnDevice, IMessage>
	{
		@Override
		public IMessage onMessage(MessageOpenReturnDevice message, MessageContext ctx) {
			GravityFalls.proxy.addScheduledTaskClient(() -> handle(message, ctx));
			return null;
		}
		
		private void handle(MessageOpenReturnDevice message, MessageContext ctx) {
			EntityPlayer player = GravityFalls.proxy.getClientPlayer();
			player.openGui(GravityFalls.instance, ConfigHandler.RETURN_DEVICE, player.world, (int) player.posX, (int) player.posY, (int) player.posZ);
		}

	}
}
