package proxy;

import com.google.common.util.concurrent.ListenableFuture;

import compatibilities.Capabilities;
import compatibilities.CapabilitiesHandler;
import customModelTest.Block3DWeb;
import init.AttachAttributes;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class CommonProxy {

	  public static Block3DWeb block3DWeb;  // this holds the unique instance of your block
	  public static ItemBlock itemBlock3DWeb;  // this holds the unique instance of the ItemBlock corresponding to your block

	  
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
		  // each instance of your block should have two names:
	    // 1) a registry name that is used to uniquely identify this block.  Should be unique within your mod.  use lower case.
	    // 2) an 'unlocalised name' that is used to retrieve the text name of your block in the player's language.  For example-
	    //    the unlocalised name might be "water", which is printed on the user's screen as "Wasser" in German or
	    //    "aqua" in Italian.
	    //
	    //    Multiple blocks can have the same unlocalised name - for example
	    //  +----RegistryName----+---UnlocalisedName----+
	    //  |  flowing_water     +       water          |
	    //  |  stationary_water  +       water          |
	    //  +--------------------+----------------------+
	    //
	    block3DWeb = (Block3DWeb)(new Block3DWeb().setUnlocalizedName("mbe05_block_3d_web_unlocalised_name"));
	    block3DWeb.setRegistryName("mbe05_block_3d_web_registry_name");
	    ForgeRegistries.BLOCKS.register(block3DWeb);

	    // We also need to create and register an ItemBlock for this block otherwise it won't appear in the inventory
	    itemBlock3DWeb = new ItemBlock(block3DWeb);
	    itemBlock3DWeb.setRegistryName(block3DWeb.getRegistryName());
	    ForgeRegistries.ITEMS.register(itemBlock3DWeb);
		
		
		Capabilities.init();
	}
	
	public void init(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new CapabilitiesHandler());
		MinecraftForge.EVENT_BUS.register(new AttachAttributes());
	}

}
