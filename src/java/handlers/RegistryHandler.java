package handlers;

import commands.CommandDimensionTeleport;
import dimensions.DimensionTheFuture;
import entity.EntityRegistry;
import init.BiomeInit;
import init.BlockInit;
import init.DimensionInit;
import init.ItemInit;
import init.PotionInit;
import items.QuantumDestabilizer;
import main.ConfigHandler;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundEventAccessor;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import proxy.ClientProxy;
import updates.GlobnarUpdate;
import updates.PlayerUpdate;
import updates.WeirdmageddonEvent;
import worldgen.WorldGenCustomStructures;
import worldgen.WorldGenOres;

@EventBusSubscriber
public class RegistryHandler 
{
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

	@SubscribeEvent
	public static void onFutureLoadEvent(WorldEvent.Load event)
	{		
		World world = event.getWorld();
		if(world.provider.getDimension() == ConfigHandler.THE_FUTURE) {		
			DimensionTheFuture.babySpawned = false;
			GlobnarUpdate.messageSent = false;
			GlobnarUpdate.recievedTimeWish = false;
		}
	}

	@SubscribeEvent
	public static void onFutureUnLoadEvent(WorldEvent.Unload event)
	{	
		World world = event.getWorld();
		if(world.provider.getDimension() == ConfigHandler.THE_FUTURE) {		
			DimensionTheFuture.babySpawned = false;
			GlobnarUpdate.messageSent = false;
			GlobnarUpdate.recievedTimeWish = false;
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