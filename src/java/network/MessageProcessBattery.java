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
import items.FlashLight;
import items.ItemBasic;
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

public class MessageProcessBattery implements IMessage{

	private int item;

	public MessageProcessBattery() {
	}

	//Bytes must be in the same order when reading and writing

	public MessageProcessBattery(int item) 
	{
		this.item = item;
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(item);

	}

	@Override
	public void fromBytes(ByteBuf buf) {		
		item = buf.readInt();

	}

	public static class Handler implements IMessageHandler<MessageProcessBattery, IMessage>
	{
		@Override
		public IMessage onMessage(MessageProcessBattery message, MessageContext ctx) {
			GravityFalls.proxy.addScheduledTaskClient(() -> handle(message, ctx));

			return null;
		}

		private void handle(MessageProcessBattery message, MessageContext ctx) {

			NetHandlerPlayServer serverHandler = ctx.getServerHandler();
			EntityPlayerMP player = serverHandler.player;
			World world = player.world;
			ItemStack chestStack = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
			ItemStack stack = player.getHeldItemMainhand();
			ItemStack itemStack = findAmmo(player);

			//Process the Flashlight
			if(message.item == 1)
			{
				if(itemStack.getItem() == ItemInit.BATTERY && player.getHeldItemMainhand().getItem() == ItemInit.FLASHLIGHT && stack.getItemDamage() >= 50)
				{
					stack.setItemDamage(stack.getItemDamage() - 50);
					itemStack.shrink(1);
				}
			}
			//Process the Light Sweater
			else if(message.item == 2)
			{
				if(itemStack.getItem() == ItemInit.BATTERY && chestStack.getItem() == ItemInit.LIGHT_SWEATER && stack.getItemDamage() >= 50)
				{
					chestStack.setItemDamage(chestStack.getItemDamage() - 50);
					itemStack.shrink(1);
				}
			}
			//Process the Magic Flashlight
			else if(message.item == 3)
			{
				if(itemStack.getItem() == ItemInit.BATTERY && player.getHeldItemMainhand().getItem() == ItemInit.MAGICFLASHLIGHT && stack.getItemDamage() >= 1000)
				{
					stack.setItemDamage(stack.getItemDamage() - 1000);
					itemStack.shrink(1);
				}
			}
			//Process the Memory Gun
			else if(message.item == 4)
			{
				if(itemStack.getItem() == ItemInit.BATTERY && player.getHeldItemMainhand().getItem() == ItemInit.MEMORY_GUN && stack.getItemDamage() >= 5)
				{
					stack.setItemDamage(stack.getItemDamage() - 5);
					itemStack.shrink(1);
				}
			}
			//Process the Leaf Blower
			else if(message.item == 5)
			{
				if(itemStack.getItem() == ItemInit.BATTERY && player.getHeldItemMainhand().getItem() == ItemInit.LEAFBLOWER && stack.getItemDamage() >= 50)
				{
					stack.setItemDamage(stack.getItemDamage() - 50);
					itemStack.shrink(1);
				}
			}
			//Process the Laser Arm Cannon
			else if(message.item == 6)
			{
				if(itemStack.getItem() == ItemInit.BATTERY && player.getHeldItemMainhand().getItem() == ItemInit.LASER_ARM_CANNON && stack.getItemDamage() >= 10)
				{
					stack.setItemDamage(stack.getItemDamage() - 10);
					itemStack.shrink(1);
				}
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