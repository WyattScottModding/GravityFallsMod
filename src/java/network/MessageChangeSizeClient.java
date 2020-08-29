package network;

import java.util.List;
import java.util.UUID;

import commands.Teleport;
import init.PotionInit;
import io.netty.buffer.ByteBuf;
import main.ConfigHandler;
import main.GravityFalls;
import net.minecraft.client.entity.EntityOtherPlayerMP;
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

public class MessageChangeSizeClient implements IMessage{

	private int size;
	private long uuid1, uuid2;
	private boolean removeEffect;

	public MessageChangeSizeClient() {
	}

	//Bytes must be in the same order when reading and writing

	public MessageChangeSizeClient(int size, long uuid1, long uuid2, boolean removeEffect) 
	{
		this.size = size;
		this.uuid1 = uuid1;
		this.uuid2 = uuid2;
		this.removeEffect = removeEffect;
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(size);
		buf.writeLong(uuid1);
		buf.writeLong(uuid2);
		buf.writeBoolean(removeEffect);
	}

	@Override
	public void fromBytes(ByteBuf buf) {		
		size = buf.readInt();
		uuid1 = buf.readLong();
		uuid2 = buf.readLong();
		removeEffect = buf.readBoolean();
	}

	public static class Handler implements IMessageHandler<MessageChangeSizeClient, IMessage>
	{
		@Override
		public IMessage onMessage(MessageChangeSizeClient message, MessageContext ctx) {
			GravityFalls.proxy.addScheduledTaskClient(() -> handle(message, ctx));

			return null;
		}

		private void handle(MessageChangeSizeClient message, MessageContext ctx) {

			EntityPlayer clientPlayer = GravityFalls.proxy.getClientPlayer();
			World world = clientPlayer.world;
			EntityPlayer otherPlayer = world.getPlayerEntityByUUID(new UUID(message.uuid1, message.uuid2));
			
			otherPlayer.removePotionEffect(PotionInit.GROWTH_EFFECT);

			if(!message.removeEffect)
			{
				PotionEffect effect = new PotionEffect(PotionInit.GROWTH_EFFECT, 10000000, message.size, true, false);
				effect.setPotionDurationMax(true);
				otherPlayer.addPotionEffect(effect);
			}
		}
	}
}