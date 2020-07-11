package proxy;

import com.google.common.util.concurrent.ListenableFuture;

import handlers.KeyBindings;
import handlers.KeyInputHandler;
import main.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class ClientProxy extends CommonProxy
{

	@Override
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
	}

	
	public void registerItemRenderer(Item item, int meta, String id)
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));

	}

	public void registerBlockRenderers()
	{
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
	}
	

	@Override
	public void registerVariantRenderer(Item item, int meta, String filename, String id) 
	{
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(new ResourceLocation(Reference.MODID, filename), id));
	}

	@Override
	public void registerRenderers()
	{
		//RenderingRegistry.registerEntityRenderingHandler(EntitySpear.class, new RenderSnowball(Minecraft.getMinecraft().getRenderManager(), ItemInit.SPEAR, Minecraft.getMinecraft().getRenderItem()));
	}
	
	public static void registerKeyBinds()
	{
		FMLCommonHandler.instance().bus().register(new KeyInputHandler());
		for(KeyBindings key : KeyBindings.values())
		{
			ClientRegistry.registerKeyBinding(key.getkeybind());
		}
	}

	@Override
	public ListenableFuture<Object> addScheduledTaskClient(Runnable runnableToSchedule) {
		return Minecraft.getMinecraft().addScheduledTask(runnableToSchedule);
	}
	
	@Override
	public EntityPlayer getClientPlayer() {
		return Minecraft.getMinecraft().player;
	}

}