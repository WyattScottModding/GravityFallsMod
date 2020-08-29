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

public class MessageKillDroid implements IMessage{

	public MessageKillDroid() {
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
	}

	@Override
	public void fromBytes(ByteBuf buf) {		
	}

	public static class Handler implements IMessageHandler<MessageKillDroid, IMessage>
	{
		@Override
		public IMessage onMessage(MessageKillDroid message, MessageContext ctx) {
			GravityFalls.proxy.addScheduledTaskClient(() -> handle(message, ctx));

			return null;
		}

		private void handle(MessageKillDroid message, MessageContext ctx) {

			NetHandlerPlayServer serverHandler = ctx.getServerHandler();
			EntityPlayerMP player = serverHandler.player;
			World world = player.world;
			
			getMouseOver(player, world);
		}
		public void getMouseOver(EntityPlayer player, World world)
		{
			BlockPos pos = player.getPosition().add(0, player.eyeHeight, 0);

			float yaw = player.rotationYaw;
			float pitch = player.rotationPitch;

			for(int f = 1; f <= 80; f++)
			{
				double x = (double)(-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
				double y = (double)(-MathHelper.sin((pitch) / 180.0F * (float)Math.PI) * f);
				double z = (double)(MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);


				AxisAlignedBB entityPos = new AxisAlignedBB(pos.getX() + x - .5, pos.getY() + y - .5, pos.getZ() + z - .5, pos.getX() + x + .5, pos.getY() + y + .5, pos.getZ() + z + .5);

				List<Entity> list = world.getEntitiesInAABBexcluding(player, entityPos, Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>()
				{
					public boolean apply(@Nullable Entity p_apply_1_)
					{
						return p_apply_1_ != null && p_apply_1_.canBeCollidedWith();
					}
				}));

				for(int j = 0; j < list.size(); ++j)
				{
					Entity entity = list.get(j);
					if(entity instanceof EntityLivingBase)
					{
						if(entity instanceof EntitySecurityDroid)
							entity.onKillCommand();		
					}
				}
			}
		}
	}
}