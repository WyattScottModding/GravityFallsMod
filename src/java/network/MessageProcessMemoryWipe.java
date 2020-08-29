package network;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import commands.Teleport;
import init.BlockInit;
import init.ItemInit;
import init.PotionInit;
import io.netty.buffer.ByteBuf;
import main.ConfigHandler;
import main.GravityFalls;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageProcessMemoryWipe implements IMessage{

	public MessageProcessMemoryWipe() {
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
	}

	@Override
	public void fromBytes(ByteBuf buf) {		
	}

	public static class Handler implements IMessageHandler<MessageProcessMemoryWipe, IMessage>
	{
		@Override
		public IMessage onMessage(MessageProcessMemoryWipe message, MessageContext ctx) {
			GravityFalls.proxy.addScheduledTaskClient(() -> handle(message, ctx));

			return null;
		}

		private void handle(MessageProcessMemoryWipe message, MessageContext ctx) {

			NetHandlerPlayServer serverHandler = ctx.getServerHandler();
			EntityPlayerMP player = serverHandler.player;
			World world = player.world;
			ItemStack stack = player.getHeldItemMainhand();
			

			//Set the NBT to a new NBT if it is null
			NBTTagCompound nbt = new NBTTagCompound();

			if(stack.getTagCompound() != null)
				nbt = stack.getTagCompound();

			boolean fullMemoryWipe = false;
			boolean isDown = false;

			if(nbt.hasKey("fullMemoryWipe"))
				fullMemoryWipe = nbt.getBoolean("fullMemoryWipe");
			if(nbt.hasKey("isDown"))
				isDown = nbt.getBoolean("isDown");
			
			if(!isDown) {
				if(fullMemoryWipe)
					fullMemoryWipe = false;
				else
					fullMemoryWipe = true;
			}
			isDown = true;
			
			
			nbt.setBoolean("fullMemoryWipe", fullMemoryWipe);
			nbt.setBoolean("isDown", isDown);

			stack.setTagCompound(nbt);
		}
	}
}