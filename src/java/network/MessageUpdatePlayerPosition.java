package network;

import java.util.List;

import commands.Teleport;
import init.PotionInit;
import io.netty.buffer.ByteBuf;
import main.ConfigHandler;
import main.GravityFalls;
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

public class MessageUpdatePlayerPosition implements IMessage{

	private double x;
	private double y;
	private double z;
	private float yaw;
	private float pitch;
	
	public MessageUpdatePlayerPosition() {
	}

	//Bytes must be in the same order when reading and writing

	public MessageUpdatePlayerPosition(double x, double y, double z, float yaw, float pitch) 
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.yaw = yaw;
		this.pitch = pitch;
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
		buf.writeFloat(yaw);
		buf.writeFloat(pitch);
	}

	@Override
	public void fromBytes(ByteBuf buf) {		
		x = buf.readDouble();
		y = buf.readDouble();
		z = buf.readDouble();
		yaw = buf.readFloat();
		pitch = buf.readFloat();
	}

	public static class Handler implements IMessageHandler<MessageUpdatePlayerPosition, IMessage>
	{
		@Override
		public IMessage onMessage(MessageUpdatePlayerPosition message, MessageContext ctx) {
			GravityFalls.proxy.addScheduledTaskClient(() -> handle(message, ctx));

			return null;
		}

		private void handle(MessageUpdatePlayerPosition message, MessageContext ctx) {

			NetHandlerPlayServer serverHandler = ctx.getServerHandler();
			EntityPlayerMP player = serverHandler.player;
			
			player.setPositionAndRotation(message.x, message.y, message.z, message.yaw, message.pitch);
			//Messages.INSTANCE.sendTo(new MessageUpdatePlayerMotionClient(message.x, message.y, message.z, message.yaw, message.pitch),  player);
		}
	}
}