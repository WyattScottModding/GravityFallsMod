package network;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import commands.Teleport;
import entity.EntitySecurityDroid;
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

public class MessageUpdateSpeedBoots implements IMessage{

	int num;
	
	public MessageUpdateSpeedBoots() {
	}
	
	public MessageUpdateSpeedBoots(int num) {
		this.num = num;
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(num);
	}

	@Override
	public void fromBytes(ByteBuf buf) {	
		num = buf.readInt();
	}

	public static class Handler implements IMessageHandler<MessageUpdateSpeedBoots, IMessage>
	{
		@Override
		public IMessage onMessage(MessageUpdateSpeedBoots message, MessageContext ctx) {
			GravityFalls.proxy.addScheduledTaskClient(() -> handle(message, ctx));

			return null;
		}

		private void handle(MessageUpdateSpeedBoots message, MessageContext ctx) {

			NetHandlerPlayServer serverHandler = ctx.getServerHandler();
			EntityPlayerMP player = serverHandler.player;
			World world = player.world;
			ItemStack stack = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
			
			//Set the NBT to a new NBT if it is null
			NBTTagCompound nbt = new NBTTagCompound();

			if(stack.getTagCompound() != null)
				nbt = stack.getTagCompound();
			
			float speed = 1.0F;
			
			if(nbt.hasKey("speed"))
				speed = nbt.getFloat("speed");

			
			if(message.num == 1 && speed < 100)
			{
				speed += .4F;
			}
			else if(message.num == 2 && speed > 2)
			{
				speed -= .4F;
			}
			else if(message.num == 3 && player.isOverWater() && speed > 15 && world.getBlockState(player.getPosition().down()).getBlock() != Blocks.AIR)
			{
				if(world.getBlockState(player.getPosition()).getBlock() == Blocks.WATER)
					player.motionY = 1.0F;
				else
					player.motionY = 0.0F;

				float f = speed / 30;
				float yaw2 = player.rotationYaw;

				player.motionX = (double)(-MathHelper.sin(yaw2 / 180.0F * (float)Math.PI)) * f;
				player.motionZ = (double)(MathHelper.cos(yaw2 / 180.0F * (float)Math.PI)) * f;
			}
			
			nbt.setFloat("speed", speed);
			stack.setTagCompound(nbt);
		}
	}
}