package handlers;


import javax.xml.ws.handler.MessageContext;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;

public class PacketMakeLight implements IMessage
{
	int x;
	int y;
	int z;
	int oldX;
	int oldY;
	int oldZ;

	public PacketMakeLight() {}

	public PacketMakeLight(int x, int y, int z, int oldX, int oldY, int oldZ)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.oldX = oldX;
		this.oldY = oldY;
		this.oldZ = oldZ;
	}

	public void fromBytes(ByteBuf buf)
	{
		this.x = ByteBufUtils.readVarInt(buf, 5);
		this.y = ByteBufUtils.readVarInt(buf, 5);
		this.z = ByteBufUtils.readVarInt(buf, 5);
		this.oldX = ByteBufUtils.readVarInt(buf, 5);
		this.oldY = ByteBufUtils.readVarInt(buf, 5);
		this.oldZ = ByteBufUtils.readVarInt(buf, 5);
	}

	public void toBytes(ByteBuf buf)
	{
		ByteBufUtils.writeVarInt(buf, this.x, 5);
		ByteBufUtils.writeVarInt(buf, this.y, 5);
		ByteBufUtils.writeVarInt(buf, this.z, 5);
		ByteBufUtils.writeVarInt(buf, this.oldX, 5);
		ByteBufUtils.writeVarInt(buf, this.oldY, 5);
		ByteBufUtils.writeVarInt(buf, this.oldZ, 5);
	}

	public static abstract class HandlerMakeLightClient implements IMessageHandler<PacketMakeLight, IMessage>
	{
		public IMessage onMessage(PacketMakeLight message, MessageContext ctx)
		{
			World world = Minecraft.func_71410_x().field_71441_e;

			world.func_72915_b(EnumSkyBlock.BLOCK, message.x, message.y, message.z, Lantern.instance.getLanternLightValue());
			world.func_147463_c(EnumSkyBlock.BLOCK, message.oldX, message.oldY, message.oldZ);
			world.func_147463_c(EnumSkyBlock.BLOCK, message.x, message.y + 1, message.z);
			world.func_147463_c(EnumSkyBlock.BLOCK, message.x + 1, message.y, message.z);
			world.func_147463_c(EnumSkyBlock.BLOCK, message.x, message.y, message.z + 1);
			world.func_147463_c(EnumSkyBlock.BLOCK, message.x - 1, message.y, message.z);
			world.func_147463_c(EnumSkyBlock.BLOCK, message.x, message.y, message.z - 1);

			return null;
		}

		@Override
		public IMessage onMessage(PacketMakeLight message,
				net.minecraftforge.fml.common.network.simpleimpl.MessageContext ctx) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	public static class HandlerMakeLightServer
	implements IMessageHandler<PacketMakeLight, IMessage>
	{
		public IMessage onMessage(PacketMakeLight message, MessageContext ctx)
		{
			Lantern.network.sendToAll(message);
			return null;
		}
	}
}
