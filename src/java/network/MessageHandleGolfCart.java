package network;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import commands.Teleport;
import entity.EntityGolfCart;
import init.BlockInit;
import init.ItemInit;
import init.PotionInit;
import io.netty.buffer.ByteBuf;
import items.FlashLight;
import items.ItemBasic;
import main.ConfigHandler;
import main.GravityFalls;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageHandleGolfCart implements IMessage{

	private int direction;
	public MessageHandleGolfCart() {
	}

	//Bytes must be in the same order when reading and writing

	public MessageHandleGolfCart(int direction) 
	{
		this.direction = direction;
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeInt(direction);
	}

	@Override
	public void fromBytes(ByteBuf buf) {		
		direction = buf.readInt();
	}

	public static class Handler implements IMessageHandler<MessageHandleGolfCart, IMessage>
	{
		@Override
		public IMessage onMessage(MessageHandleGolfCart message, MessageContext ctx) {
			GravityFalls.proxy.addScheduledTaskClient(() -> handle(message, ctx));

			return null;
		}

		private void handle(MessageHandleGolfCart message, MessageContext ctx) {

			NetHandlerPlayServer serverHandler = ctx.getServerHandler();
			EntityPlayerMP player = serverHandler.player;
			World world = player.world;

			Entity entity = player.getRidingEntity();

			//Runs if the player is riding a golf cart and is the driver of the golf cart
			if(entity instanceof EntityGolfCart && player != null && ((EntityGolfCart) entity).getPassengers().get(0) == player)
			{
				EntityGolfCart golfCart = (EntityGolfCart) entity;
				
				//Key Up
				if(message.direction == 1)
				{
					if(golfCart.acceleration < 10)
						golfCart.acceleration += 0.1;
					else
						golfCart.acceleration = 1.0;
				}
				//Key left Shift
				else if(message.direction == 2)
				{
					//dismountEntity(player, golfCart, world);
					player.dismountRidingEntity();
				}
				else if(message.direction == 3)
				{
					golfCart.travel(player.moveStrafing, 0, player.moveForward);
				}
			}
		}

		public void dismountEntity(Entity entityIn, EntityGolfCart golfCart, World world)
		{
			double d1 = entityIn.posX;
			double d13 = entityIn.getEntityBoundingBox().minY + (double)entityIn.height;
			double d14 = entityIn.posZ;
			EnumFacing enumfacing1 = entityIn.getAdjustedHorizontalFacing();

			if (enumfacing1 != null)
			{
				EnumFacing enumfacing = enumfacing1.rotateY();
				int[][] aint1 = new int[][] {{0, 1}, {0, -1}, { -1, 1}, { -1, -1}, {1, 1}, {1, -1}, { -1, 0}, {1, 0}, {0, 1}};
				double d5 = Math.floor(golfCart.posX) + 0.5D;
				double d6 = Math.floor(golfCart.posZ) + 0.5D;
				double d7 = golfCart.getEntityBoundingBox().maxX - golfCart.getEntityBoundingBox().minX;
				double d8 = golfCart.getEntityBoundingBox().maxZ - golfCart.getEntityBoundingBox().minZ;
				AxisAlignedBB axisalignedbb = new AxisAlignedBB(d5 - d7 / 2.0D, entityIn.getEntityBoundingBox().minY, d6 - d8 / 2.0D, d5 + d7 / 2.0D, Math.floor(entityIn.getEntityBoundingBox().minY) + (double)golfCart.height, d6 + d8 / 2.0D);

				for (int[] aint : aint1)
				{
					double d9 = (double)(enumfacing1.getFrontOffsetX() * aint[0] + enumfacing.getFrontOffsetX() * aint[1]);
					double d10 = (double)(enumfacing1.getFrontOffsetZ() * aint[0] + enumfacing.getFrontOffsetZ() * aint[1]);
					double d11 = d5 + d9;
					double d12 = d6 + d10;
					AxisAlignedBB axisalignedbb1 = axisalignedbb.offset(d9, 0.0D, d10);

					if (!world.collidesWithAnyBlock(axisalignedbb1))
					{
						if (world.getBlockState(new BlockPos(d11, golfCart.posY, d12)).isSideSolid(world, new BlockPos(d11, golfCart.posY, d12), EnumFacing.UP))
						{
							golfCart.setPositionAndUpdate(d11, golfCart.posY + 1.0D, d12);
							return;
						}

						BlockPos blockpos = new BlockPos(d11, golfCart.posY - 1.0D, d12);

						if (world.getBlockState(blockpos).isSideSolid(world, blockpos, EnumFacing.UP) || world.getBlockState(blockpos).getMaterial() == Material.WATER)
						{
							d1 = d11;
							d13 = golfCart.posY + 1.0D;
							d14 = d12;
						}
					}
					else if (!world.collidesWithAnyBlock(axisalignedbb1.offset(0.0D, 1.0D, 0.0D)) && world.getBlockState(new BlockPos(d11, golfCart.posY + 1.0D, d12)).isSideSolid(world, new BlockPos(d11, golfCart.posY + 1.0D, d12), EnumFacing.UP))
					{
						d1 = d11;
						d13 = golfCart.posY + 2.0D;
						d14 = d12;
					}
				}
			}

			golfCart.setPositionAndUpdate(d1, d13, d14);
		}
	}
}