package handlers;

import java.util.List;

import containers.ContainerCartInventory;
import containers.ContainerFordWorkbench;
import containers.ContainerUraniumFurnace;
import entity.EntityGolfCart;
import gui.GuiBook1;
import gui.GuiBook2;
import gui.GuiBook3;
import gui.GuiComputer;
import gui.GuiFordWorkbench;
import gui.GuiGolfCart;
import gui.GuiReturnDevice;
import gui.GuiScope;
import gui.GuiUraniumFurnace;
import items.GolfCart;
import main.ConfigHandler;
import main.GravityFalls;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import tileEntities.TileEntityUraniumFurnace;

public class GuiHandler implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		if(ID == ConfigHandler.GUI_URANIUM_FURNACE)
			return new ContainerUraniumFurnace(player.inventory, (TileEntityUraniumFurnace)world.getTileEntity(new BlockPos(x, y, z)));
		else if(ID == ConfigHandler.FORD_WORKBENCH)
			return new ContainerFordWorkbench(player.inventory, world, new BlockPos(x, y, z));
		else if(ID == ConfigHandler.GOLF_CART)
		{
			if(player.getRidingEntity() != null && player.getRidingEntity() instanceof EntityGolfCart)
				return new ContainerCartInventory(player.inventory, ((EntityGolfCart)player.getRidingEntity()).inventory, ((EntityGolfCart)player.getRidingEntity()), player);

			EntityGolfCart golfCart = getMouseOver(player, world);

			if(golfCart != null)
				return new ContainerCartInventory(player.inventory, golfCart.inventory, golfCart, player);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		if(ID == ConfigHandler.GUI_URANIUM_FURNACE)
			return new GuiUraniumFurnace(new ContainerUraniumFurnace(player.inventory, (TileEntityUraniumFurnace)world.getTileEntity(new BlockPos(x, y, z))));
		else if(ID == ConfigHandler.GUI_JOURNAL1)
			return new GuiBook1();
		else if(ID == ConfigHandler.GUI_JOURNAL2)
			return new GuiBook2();
		else if(ID == ConfigHandler.GUI_JOURNAL3)
			return new GuiBook3();
		else if(ID == ConfigHandler.SCOPE)
			return new GuiScope();
		else if(ID == ConfigHandler.COMPUTER)
			return new GuiComputer();
		else if(ID == ConfigHandler.RETURN_DEVICE)
			return new GuiReturnDevice();
		else if(ID == ConfigHandler.FORD_WORKBENCH)
			return new GuiFordWorkbench(player.inventory, player.world);
		else if(ID == ConfigHandler.GOLF_CART)
		{
			if(player.getRidingEntity() != null && player.getRidingEntity() instanceof EntityGolfCart)
			{
				EntityGolfCart golfCart = (EntityGolfCart) player.getRidingEntity();
				return new GuiGolfCart((new ContainerCartInventory(player.inventory, golfCart.inventory, golfCart, player)));
			}

			EntityGolfCart golfCart = getMouseOver(player, world);

			if(golfCart != null)
				return new GuiGolfCart((new ContainerCartInventory(player.inventory, golfCart.inventory, golfCart, player)));

		}
		return null;
	}

	public static void register()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(GravityFalls.instance, new GuiHandler());
	}

	public EntityGolfCart getMouseOver(EntityPlayer player, World world)
	{
		BlockPos pos = player.getPosition().add(0, player.eyeHeight, 0);

		float yaw = player.rotationYaw;
		float pitch = player.rotationPitch;

		List<Entity> list = null;
		EntityGolfCart closestEntity = null;

		for(int f = 1; f <= 5; f++)
		{
			double x = (double)(-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
			double y = (double)(-MathHelper.sin((pitch) / 180.0F * (float)Math.PI) * f);
			double z = (double)(MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);


			AxisAlignedBB entityPos;

			if(f < 10)
				entityPos = new AxisAlignedBB(pos.getX() + x, pos.getY() + y, pos.getZ() + z, pos.getX() + x + 1, pos.getY() + y + 1, pos.getZ() + z + 1);
			else
				entityPos = new AxisAlignedBB(pos.getX() + x - 1, pos.getY() + y - 1, pos.getZ() + z - 1, pos.getX() + x + 2, pos.getY() + y + 2, pos.getZ() + z + 2);

			list = world.getEntitiesWithinAABBExcludingEntity(player, entityPos);


			for(int j = 0; j < list.size(); ++j)
			{
				Entity entity = list.get(j);

				if(entity instanceof EntityGolfCart)
				{
					EntityGolfCart golfCart = (EntityGolfCart) entity;

					float distance = player.getDistance(golfCart);

					if(closestEntity == null)
						closestEntity = golfCart;
					else if(distance < player.getDistance(closestEntity))
						closestEntity = golfCart;					
				}
			}
		}
		return closestEntity;
	}
}