package handlers;

import net.minecraftforge.fml.common.registry.GameRegistry;
import tileEntities.TileEntityBlockTeleporter;
import tileEntities.TileEntityBook2;
import tileEntities.TileEntityCipherWheel;
import tileEntities.TileEntityComputer;
import tileEntities.TileEntityCryogenicTube;
import tileEntities.TileEntityCrystal;
import tileEntities.TileEntityCursedDoor;
import tileEntities.TileEntityFordWorkbench;
import tileEntities.TileEntityLightSource;
import tileEntities.TileEntityPortalLever;
import tileEntities.TileEntityUraniumFurnace;

public class TileEntityHandler 
{
	public static void registerTileEntites()
	{
		GameRegistry.registerTileEntity(TileEntityUraniumFurnace.class, "uranium_furnace");
		GameRegistry.registerTileEntity(TileEntityLightSource.class, "lightsource");
		GameRegistry.registerTileEntity(TileEntityComputer.class, "computer");
		GameRegistry.registerTileEntity(TileEntityCursedDoor.class, "cursed_door");
		GameRegistry.registerTileEntity(TileEntityPortalLever.class, "portal_lever");
		GameRegistry.registerTileEntity(TileEntityBlockTeleporter.class, "block_teleporter");
		GameRegistry.registerTileEntity(TileEntityCryogenicTube.class, "cryogenic_tube");
		GameRegistry.registerTileEntity(TileEntityCrystal.class, "crystal");
		GameRegistry.registerTileEntity(TileEntityBook2.class, "book2");
		GameRegistry.registerTileEntity(TileEntityFordWorkbench.class, "ford_workbench");
		GameRegistry.registerTileEntity(TileEntityCipherWheel.class, "cipher_wheel");

	}
}