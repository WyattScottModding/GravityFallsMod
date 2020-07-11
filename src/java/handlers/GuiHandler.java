package handlers;

import containers.ContainerFordWorkbench;
import containers.ContainerUraniumFurnace;
import gui.GuiBook1;
import gui.GuiBook2;
import gui.GuiBook3;
import gui.GuiComputer;
import gui.GuiFordWorkbench;
import gui.GuiReturnDevice;
import gui.GuiScope;
import gui.GuiUraniumFurnace;
import main.ConfigHandler;
import main.GravityFalls;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import tileEntities.TileEntityFordWorkbench;
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
		return null;
	}
	
	public static void register()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(GravityFalls.instance, new GuiHandler());
	}
}