package main;

import java.io.File;

import commands.CommandDimensionTeleport;
import compatibilities.Capabilities;
import entity.EntityRegistry;
import handlers.GuiHandler;
import handlers.RegistryHandler;
import handlers.RenderHandler;
import handlers.SoundsHandler;
import handlers.VillagerTradeHandler;
import init.BiomeInit;
import init.Crafting;
import init.DimensionInit;
import init.PotionInit;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import network.Messages;
import proxy.ClientProxy;
import proxy.CommonProxy;
import tabs.GravityFallsArmor;
import tabs.GravityFallsBlocks;
import tabs.GravityFallsItems;
import worldgen.WorldGenCustomStructures;
import worldgen.WorldGenOres;

@Mod(modid = Reference.MODID, version = Reference.VERSION, name = Reference.NAME, acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS)
public class GravityFalls {

	@Instance
	public static GravityFalls instance;

	@SidedProxy(clientSide = Reference.CLIENTPROXY, serverSide = Reference.SERVERPROXY)
	public static CommonProxy proxy;

	public static final CreativeTabs gravityfallsblocks = new GravityFallsBlocks();
	public static final CreativeTabs gravityfallsitems = new GravityFallsItems();
	public static final CreativeTabs gravityfallsarmor = new GravityFallsArmor();
	
	public static SimpleNetworkWrapper network;
	
    public static final String NETWORK_CHANNEL_NAME = "GravityFalls";

    public static File config;
    
    public static WorldGenCustomStructures structures = null;
	public static WorldGenOres ores = null;
    
    public static File getConfigDir()
    {
    	return config;
    }
    
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		ConfigHandler.registerConfig(event);

		PotionInit.registerPotions();

		DimensionInit.registerDimensions();
		BiomeInit.registerBiomes();

		EntityRegistry.registerEntities();
		RenderHandler.registerEntityRenders();

		ClientProxy.registerKeyBinds();
		
		structures = new WorldGenCustomStructures();
		ores = new WorldGenOres();

		GameRegistry.registerWorldGenerator(ores, 0);
		GameRegistry.registerWorldGenerator(structures, 1);
		
		Capabilities.init();
	}

	@EventHandler
	public void Init(FMLInitializationEvent event)
	{
		Crafting.register();
		Messages.registerMessages(NETWORK_CHANNEL_NAME);

		SoundsHandler.registerSounds();
		NetworkRegistry.INSTANCE.registerGuiHandler(GravityFalls.instance, new GuiHandler());
		
		proxy.init(event);
		
		World.MAX_ENTITY_RADIUS = 20;
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		VillagerTradeHandler.init();
	}
	
	@EventHandler
	public void serverInit(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandDimensionTeleport());
	}
}