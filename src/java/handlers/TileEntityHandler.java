package handlers;

import net.minecraftforge.fml.common.registry.GameRegistry;
import tileEntities.TileEntityBook1;
import tileEntities.TileEntityComputer;
import tileEntities.TileEntityLightSource;
import tileEntities.TileEntityUraniumFurnace;

public class TileEntityHandler 
{
	public static void registerTileEntites()
	{
		GameRegistry.registerTileEntity(TileEntityUraniumFurnace.class, "uranium_furnace");
		GameRegistry.registerTileEntity(TileEntityBook1.class, "book1");
		GameRegistry.registerTileEntity(TileEntityLightSource.class, "lightsource");
		GameRegistry.registerTileEntity(TileEntityComputer.class, "computer");

	}
}
