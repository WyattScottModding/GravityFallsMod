package proxy;

import com.google.common.util.concurrent.ListenableFuture;

import customModelTest.ModelLoader3DWeb;
import handlers.KeyBindings;
import handlers.KeyInputHandler;
import main.Reference;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy
{

	@Override
	public void preInit(FMLPreInitializationEvent event)
	{
		// We need to tell Forge how to map our Block3DWebs's IBlockState to a ModelResourceLocation.
	    // For example, the BlockStone granite variant has a BlockStateMap entry that looks like
	    //   "stone[variant=granite]" (iBlockState)  -> "minecraft:granite#normal" (ModelResourceLocation)
	    // For the 3DWeb block, we ignore the iBlockState completely and always return the same ModelResourceLocation,
	    //   which is done using the anonymous class below
	    StateMapperBase ignoreState = new StateMapperBase() {
	      @Override
	      protected ModelResourceLocation getModelResourceLocation(IBlockState iBlockState) {
	        return new ModelResourceLocation("gravityfalls:mbe05_block_3d_web_statemapper_name");
	      }
	    };
	    ModelLoader.setCustomStateMapper(CommonProxy.block3DWeb, ignoreState);

	    ModelLoaderRegistry.registerLoader(new ModelLoader3DWeb());

	    // This is currently necessary in order to make your block render properly when it is an item (i.e. in the inventory
	    //   or in your hand or thrown on the ground).
	    // Minecraft knows to look for the item model based on the GameRegistry.registerBlock.  However the registration of
	    //  the model for each item is normally done by RenderItem.registerItems(), and this is not currently aware
	    //   of any extra items you have created.  Hence you have to do it manually.
	    // It must be done on client only, and must be done after the block has been created in Common.preinit().

	    ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation("gravityfalls:mbe05_block_3d_web", "inventory");
	    final int DEFAULT_ITEM_SUBTYPE = 0;
	    ModelLoader.setCustomModelResourceLocation(CommonProxy.itemBlock3DWeb, DEFAULT_ITEM_SUBTYPE, itemModelResourceLocation);
		
		super.preInit(event);
	}

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