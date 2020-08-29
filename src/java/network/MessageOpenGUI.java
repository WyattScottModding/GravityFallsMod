package network;

import java.util.List;

import commands.Teleport;
import init.BlockInit;
import init.PotionInit;
import io.netty.buffer.ByteBuf;
import main.ConfigHandler;
import main.GravityFalls;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageOpenGUI implements IMessage{

	private int id;
	
	public MessageOpenGUI() {
	}

	//Bytes must be in the same order when reading and writing

	public MessageOpenGUI(int id) 
	{
		this.id = id;
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(id);
	}

	@Override
	public void fromBytes(ByteBuf buf) {		
		id = buf.readInt();
	}

	public static class Handler implements IMessageHandler<MessageOpenGUI, IMessage>
	{
		@Override
		public IMessage onMessage(MessageOpenGUI message, MessageContext ctx) {
			GravityFalls.proxy.addScheduledTaskClient(() -> handle(message, ctx));

			return null;
		}

		private void handle(MessageOpenGUI message, MessageContext ctx) {

			EntityPlayer player = GravityFalls.proxy.getClientPlayer();
			World world = player.world;
			
			//Computer
			if(message.id == 1)
			{
				GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
				
				if(gameSettings.keyBindSprint.isKeyDown())
				{
					player.openGui(GravityFalls.instance, ConfigHandler.COMPUTER, player.world, (int) player.posX, (int) player.posY, (int) player.posZ);
				}
			}
		}
	}
}