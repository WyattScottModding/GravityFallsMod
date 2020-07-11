package proxy;

import com.google.common.util.concurrent.ListenableFuture;

import compatibilities.Capabilities;
import compatibilities.CapabilitiesHandler;
import init.AttachAttributes;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CommonProxy {
	  
	public void registerItemRenderer(Item item, int meta, String id)
	{

	}

	public void registerBlockRenderer(Block block, int meta, String id)
	{

	}
	public void registerVariantRenderer(Item item, int meta, String filename, String id) 
	{

	}

	public EntityPlayer getPlayerEntityFromContext(MessageContext ctx) 
	{
		return ctx.getServerHandler().player;
	}

	public void registerRenderers()
	{
		
	}

	public void registerTextureFiles() 
	{
		
	}

	public void registerResourceLocations() 
	{
		
	}
	
	public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule) {
		throw new IllegalStateException("This should only be called from the client side");
	}
	
	public EntityPlayer getClientPlayer() {
		throw new IllegalStateException("This should only be called from the client side");
	}
	
	public void preInit(FMLPreInitializationEvent event)
	{
		Capabilities.init();
	}
	
	public void init(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new CapabilitiesHandler());
		MinecraftForge.EVENT_BUS.register(new AttachAttributes());
	}

}
