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

public class MessageDamageItem implements IMessage{

	public MessageDamageItem() {
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
	}

	@Override
	public void fromBytes(ByteBuf buf) {		
	}

	public static class Handler implements IMessageHandler<MessageDamageItem, IMessage>
	{
		@Override
		public IMessage onMessage(MessageDamageItem message, MessageContext ctx) {
			GravityFalls.proxy.addScheduledTaskClient(() -> handle(message, ctx));

			return null;
		}

		private void handle(MessageDamageItem message, MessageContext ctx) {

			NetHandlerPlayServer serverHandler = ctx.getServerHandler();
			EntityPlayerMP player = serverHandler.player;
			World world = player.world;
			ItemStack stack = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
			ItemStack itemStack = findAmmo(player);

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

			if(clicked && stack.getItemDamage() <= 99)
			{
				stack.setItemDamage(stack.getItemDamage() + 1);
			}
		}

		private ItemStack findAmmo(EntityPlayer player)
		{
			if (player.inventory.getCurrentItem().areItemsEqualIgnoreDurability(player.getHeldItem(EnumHand.OFF_HAND), new ItemStack(ItemInit.BATTERY)))
			{
				return player.getHeldItem(EnumHand.OFF_HAND);
			}
			if (player.inventory.getCurrentItem().areItemsEqualIgnoreDurability(player.getHeldItem(EnumHand.MAIN_HAND), new ItemStack(ItemInit.BATTERY)))
			{
				return player.getHeldItem(EnumHand.MAIN_HAND);
			}
			else
			{
				for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
				{
					ItemStack itemstack = player.inventory.getStackInSlot(i);

					if (player.inventory.getCurrentItem().areItemsEqualIgnoreDurability(itemstack, new ItemStack(ItemInit.BATTERY)))
					{
						return itemstack;
					}
				}
				return ItemStack.EMPTY;
			}
		}
	}
}