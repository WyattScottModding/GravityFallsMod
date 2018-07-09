package handlers;

import containers.ContainerBook1;
import containers.ContainerComputer;
import containers.ContainerUraniumFurnace;
import gui.GuiBook1;
import gui.GuiBook2;
import gui.GuiBook3;
import gui.GuiComputer;
import gui.GuiScope;
import gui.GuiUraniumFurnace;
import main.GravityFalls;
import main.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import tileEntities.TileEntityBook1;
import tileEntities.TileEntityComputer;
import tileEntities.TileEntityUraniumFurnace;

public class GuiHandler implements IGuiHandler
{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		if(ID == Reference.GUI_URANIUM_FURNACE)
			return new ContainerUraniumFurnace(player.inventory, (TileEntityUraniumFurnace)world.getTileEntity(new BlockPos(x, y, z)));
		else if(ID == Reference.GUI_JOURNAL1)
			return new ContainerBook1(player.inventory, (TileEntityBook1)world.getTileEntity(new BlockPos(x, y, z)));
		else if(ID == Reference.GUI_JOURNAL2)
			return new ContainerBook1(player.inventory, (TileEntityBook1)world.getTileEntity(new BlockPos(x, y, z)));
		else if(ID == Reference.GUI_JOURNAL3)
			return new ContainerBook1(player.inventory, (TileEntityBook1)world.getTileEntity(new BlockPos(x, y, z)));
		else if(ID == Reference.SCOPE)
			return new ContainerBook1(player.inventory, (TileEntityBook1)world.getTileEntity(new BlockPos(x, y, z)));
		else if(ID == Reference.COMPUTER)
			return new ContainerComputer(player.inventory, (TileEntityComputer)world.getTileEntity(new BlockPos(x, y, z)));
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
	{
		if(ID == Reference.GUI_URANIUM_FURNACE)
			return new GuiUraniumFurnace(player.inventory, (TileEntityUraniumFurnace)world.getTileEntity(new BlockPos(x, y, z)));
		else if(ID == Reference.GUI_JOURNAL1)
			return new GuiBook1(player.inventory, (TileEntityBook1)world.getTileEntity(new BlockPos(x, y, z)));
		else if(ID == Reference.GUI_JOURNAL2)
			return new GuiBook2(player.inventory, (TileEntityBook1)world.getTileEntity(new BlockPos(x, y, z)));
		else if(ID == Reference.GUI_JOURNAL3)
			return new GuiBook3(player.inventory, (TileEntityBook1)world.getTileEntity(new BlockPos(x, y, z)));
		else if(ID == Reference.SCOPE)
			return new GuiScope(player.inventory, (TileEntityBook1)world.getTileEntity(new BlockPos(x, y, z)));
		else if(ID == Reference.COMPUTER)
			return new GuiComputer(player.inventory, (TileEntityComputer)world.getTileEntity(new BlockPos(x, y, z)));
		return null;
	}
	
	public static void register()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(GravityFalls.instance, new GuiHandler());
	}
}