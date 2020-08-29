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

public class MessageProcessLightSweaterClick implements IMessage{


	public MessageProcessLightSweaterClick() {
	}


	@Override
	public void toBytes(ByteBuf buf) 
	{

	}

	@Override
	public void fromBytes(ByteBuf buf) {		
	
	}

	public static class Handler implements IMessageHandler<MessageProcessLightSweaterClick, IMessage>
	{
		@Override
		public IMessage onMessage(MessageProcessLightSweaterClick message, MessageContext ctx) {
			GravityFalls.proxy.addScheduledTaskClient(() -> handle(message, ctx));

			return null;
		}

		private void handle(MessageProcessLightSweaterClick message, MessageContext ctx) {

			NetHandlerPlayServer serverHandler = ctx.getServerHandler();
			EntityPlayerMP player = serverHandler.player;
			World world = player.world;
			ItemStack stack = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
			
			//Set the NBT to a new NBT if it is null
			NBTTagCompound stackNBT = new NBTTagCompound();

			if(stack.getTagCompound() != null)
				stackNBT = stack.getTagCompound();
			
			boolean clicked = false;
			int counter = 0;

			if(stackNBT.hasKey("clicked"))
				clicked = stackNBT.getBoolean("clicked");
			if(stackNBT.hasKey("counter"))
				counter = stackNBT.getInteger("counter");

			if(clicked && counter == 0)
			{
				clicked = false;
				counter = 10;
			}
			else if(!clicked && counter == 0 && stack != null && stack.getItemDamage() <= 99)
			{
				clicked = true;
				counter = 10;
			}
			
			stackNBT.setBoolean("clicked", clicked);
			stackNBT.setInteger("counter", counter);

			stack.setTagCompound(stackNBT);
		}
	}
}