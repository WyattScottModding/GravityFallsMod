package main;

import handlers.GuiHandler;
import handlers.RegistryHandler;
import init.Crafting;
import net.minecraft.creativetab.CreativeTabs;
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
import proxy.CommonProxy;
import tabs.GravityFallsArmor;
import tabs.GravityFallsBlocks;
import tabs.GravityFallsItems;

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



	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		RegistryHandler.preInitRegistries();
	}

	@EventHandler
	public void Init(FMLInitializationEvent event)
	{
		RegistryHandler.otherRegistries();
		Crafting.register();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

		
		RegistryHandler.initRegistries();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}
	
	@EventHandler
	public void serverInit(FMLServerStartingEvent event)
	{
		RegistryHandler.serverRegistries(event);
	}




}