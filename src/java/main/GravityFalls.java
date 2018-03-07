package main;

import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import handlers.GuiHandler;
import handlers.LootTableHandler;
import handlers.RegistryHandler;
import init.BlockInit;
import init.Crafting;
import init.GravityFallsTab;
import food.SmileDip;
import init.ItemInit;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.client.event.GuiScreenEvent.KeyboardInputEvent;
import net.minecraftforge.client.settings.KeyBindingMap;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import proxy.CommonProxy;
import proxy.CommonProxy;

@Mod(modid = Reference.MODID, version = Reference.VERSION, name = Reference.NAME, acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS)

public class GravityFalls {

	@Instance
	public static GravityFalls instance;

	@SidedProxy(clientSide = Reference.CLIENTPROXY, serverSide = Reference.SERVERPROXY)
	public static CommonProxy proxy;

	public static final CreativeTabs gravityfallstab = new GravityFallsTab();

	public static SimpleNetworkWrapper network;
	
    public static final String NETWORK_CHANNEL_NAME = "GravityFalls";



	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		RegistryHandler.preInitRegistries();
		RegistryHandler.otherRegistries();
	}

	@EventHandler
	public void Init(FMLInitializationEvent event)
	{
		Crafting.register();
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

		
		RegistryHandler.initRegistries();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}




}