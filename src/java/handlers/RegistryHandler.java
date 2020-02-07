package handlers;

import commands.CommandDimensionTeleport;
import compatibilities.CapabilitiesHandler;
import entity.EntityRegistry;
import init.AttachAttributes;
import init.BiomeInit;
import init.BlockInit;
import init.DimensionInit;
import init.ItemInit;
import init.PotionInit;
import items.QuantumDestabilizer;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import proxy.ClientProxy;
import updates.GlobnarUpdate;
import updates.PlayerUpdate;
import updates.RaiseDeadUpdate;
import updates.WeirdmageddonEvent;
import worldgen.WorldGenCustomStructures;
import worldgen.WorldGenOres;


@EventBusSubscriber
public class RegistryHandler 
{
	public static WorldGenCustomStructures structures = null;
	public static WorldGenOres ores = null;
	public static boolean weirdmageddon = false;
	private static boolean endEvent = false;

	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(ItemInit.ITEMS.toArray(new Item[0]));
	}

	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(BlockInit.BLOCKS.toArray(new Block[0]));
		TileEntityHandler.registerTileEntites();
	}


	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event)
	{
		for(Item item : ItemInit.ITEMS)
		{
			if(item instanceof IHasModel)
			{
				((IHasModel)item).registerModels();
			}
		}

		for(Block block : BlockInit.BLOCKS)
		{
			if(block instanceof IHasModel)
			{
				((IHasModel)block).registerModels();
			}
		}
	}


	public static void preInitRegistries()
	{
		PotionInit.registerPotions();

		DimensionInit.registerDimensions();
		BiomeInit.registerBiomes();

		EntityRegistry.registerEntities();
		RenderHandler.registerEntityRenders();

		ClientProxy.registerKeyBinds();
	}

	public static void initRegistries()
	{
		SoundsHandler.registerSounds();
		NetworkRegistry.INSTANCE.registerGuiHandler(GravityFalls.instance, new GuiHandler());
	}


	public static void generationRegistries()
	{
		//GameRegistry.registerWorldGenerator(new WorldGenTrees(), 0);

		structures = new WorldGenCustomStructures();
		ores = new WorldGenOres();

		GameRegistry.registerWorldGenerator(ores, 0);
		GameRegistry.registerWorldGenerator(structures, 1);
	}

	public static void serverRegistries(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandDimensionTeleport());
		//event.registerServerCommand(new CommandLocate2());
	}

	@SubscribeEvent
	public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event)
	{		
		if (event.getEntityLiving() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.getEntity();
			World world = player.world;

			if(!world.isRemote)
			{
				PlayerUpdate.init(world, player);
				GlobnarUpdate.init(world, player);
				//RaiseDeadUpdate.init(world, player);
			}
		}

		if(event.getEntity() instanceof EntityLiving)
		{
			EntityLiving entityLiving = (EntityLiving) event.getEntity();

			World world = entityLiving.world;

			PlayerUpdate.initEntityLiving(world, entityLiving);
		}

		if(weirdmageddon)
			WeirdmageddonEvent.startEvent(event.getEntity().world);

		if(endEvent) {
			WeirdmageddonEvent.endEvent(event.getEntity().world);
			endEvent = false;
		}
	}

	//For the zooming effect of the Quantum Destabilizer
	@SubscribeEvent
	public void zoom(FOVUpdateEvent event) {
		if(event.getEntity().getActiveItemStack() != null)
			if(event.getEntity().getActiveItemStack().getItem() == ItemInit.QUANTTUM_DESTABILIZER) {
				event.setNewfov(event.getFov()*(((QuantumDestabilizer)event.getEntity().getActiveItemStack().getItem()).getZoom(event.getEntity())));

			}
	}

	public static void startWeirdMageddon() {
		weirdmageddon = true;
	}

	public static void endWeirdMageddon() {
		endEvent = true;
		weirdmageddon = false;
	}
}