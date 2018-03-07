package handlers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Multimap;

import armor.FireHelmet;
import armor.RegenerationLegs;
import armor.SpeedBoots;
import armor.StrengthChestplate;
import entity.EntityRegistry;
import init.BiomeInit;
import init.BlockInit;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import main.Reference;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import worldgen.WorldGenCustomStructures;
import worldgen.WorldGenOres;

@EventBusSubscriber
public class RegistryHandler 
{
	public static boolean fire = false;
	public static boolean strength = false;
	public static boolean regenerate = false;
	public static boolean speed = false;



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
		EntityRegistry.registerEntities();
		RenderHandler.registerEntityRenders();
		GameRegistry.registerWorldGenerator(new WorldGenCustomStructures(), 0);


	}

	public static void initRegistries()
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(GravityFalls.instance, new GuiHandler());
		//NetworkRegistry.INSTANCE.newSimpleChannel(GravityFalls.NETWORK_CHANNEL_NAME);
		SoundsHandler.registerSounds();
	}


	public static void otherRegistries()
	{
		GameRegistry.registerWorldGenerator(new WorldGenOres(), 0);
		//GameRegistry.registerWorldGenerator(new WorldGenTrees(), 0);

		BiomeInit.registerBiomes();
	}


	@SubscribeEvent
	public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event)
	{
		//Radiation
		boolean rubberArmor = false;



		if ((event.getEntity() instanceof EntityPlayer))
		{
			EntityPlayer player = (EntityPlayer)event.getEntity();



			//IAttribute REACH_DISTANCE = new RangedAttribute(IAttribute player.getAttributeMap().get, Reference.MODID + ":ReachDistance", 20.0, 0.0, 1000.0).setShouldWatch(true);

			/*
			ArrayList list = new ArrayList();

			for(int i = 0; i < player.getAttributeMap().getAllAttributes().size(); i++);
			{
				list.add(player.getAttributeMap().getAllAttributes().toArray());
			}

			for(int i = 0; i < list.size(); i++)
			{
				System.out.println(list.get(i));
			}
			 */

			//player.getAttributeMap().registerAttribute(REACH_DISTANCE);

			if(player.getArmorInventoryList().toString().length() > 91)
			{
				if(player.getArmorInventoryList().toString().substring(8, 19).equals("rubberboots"))
				{
					if(player.getArmorInventoryList().toString().substring(30, 44).equals("rubberleggings"))
					{
						if(player.getArmorInventoryList().toString().substring(55, 71).equals("rubberchestplate"))
						{
							if(player.getArmorInventoryList().toString().substring(82, 91).equals("rubberhat"))
							{
								rubberArmor = true;

							}
						}
					}
				}
			}
			if(!rubberArmor)
			{
				if (player.inventory.hasItemStack(new ItemStack(BlockInit.URANIUM_TANK_FILLED))) 
				{
					player.addPotionEffect(new PotionEffect(Potion.getPotionById(19), 10, 10));
				}
				if (player.inventory.hasItemStack(new ItemStack(BlockInit.URANIUM)))
				{
					player.addPotionEffect(new PotionEffect(Potion.getPotionById(19), 10, 10));
				}
				if (player.inventory.hasItemStack(new ItemStack(ItemInit.URANIUM_BUCKET)))
				{
					player.addPotionEffect(new PotionEffect(Potion.getPotionById(19), 10, 10));

				}
			}

			//OP Armor

			if(player.getArmorInventoryList().iterator().next().getItem() instanceof SpeedBoots)
				speed = true;
			else
				speed = false;


			if(player.getArmorInventoryList().toString().contains("regenerationlegs"))
				regenerate = true;
			else
				regenerate = false;


			if(player.getArmorInventoryList().toString().contains("strengthchestplate"))
				strength = true;
			else
				strength = false;


			if(player.getArmorInventoryList().toString().contains("firehelmet"))
				fire = true;
			else
				fire = false;


			/*
			//Speed Boots
			if(player.getArmorInventoryList().toString().length() > 8)
			{
				if(player.getArmorInventoryList().toString().substring(8, 18).equals("speedboots"))
					speed = true;
				else
					speed = false;
			}
			 */
		}

		boolean mabelArmor = false;

		boolean dipperArmor = false;

		if ((event.getEntity() instanceof EntityPlayer))
		{
			EntityPlayer player = (EntityPlayer)event.getEntity();

			//Mabel
			if(player.getArmorInventoryList().toString().length() > 85)
			{
				if(player.getArmorInventoryList().toString().contains("mabelshoes"))
				{
					if(player.getArmorInventoryList().toString().contains("mabelpants"))
					{
						if(player.getArmorInventoryList().toString().contains("mabelsweater"))
						{
							if(player.getArmorInventoryList().toString().contains("mabelbandana"))
							{
								mabelArmor = true;

							}
						}
					}
				}
			}
			if(mabelArmor)
			{
				player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 10, 1));
			}


			//Dipper
			if(player.getArmorInventoryList().toString().length() > 60)
			{
				if(player.getArmorInventoryList().toString().contains("pineshoes"))
				{
					if(player.getArmorInventoryList().toString().contains("pinepants"))
					{
						if(player.getArmorInventoryList().toString().contains("pinevest"))
						{
							if(player.getArmorInventoryList().toString().contains("pinehat"))
							{
								dipperArmor = true;
							}
						}
					}
				}
			}

			if(dipperArmor)
			{
				player.addPotionEffect(new PotionEffect(MobEffects.LUCK, 10, 1));
			}


			//Gives strength if the player is huge
			if(player.height > 9)
				player.addPotionEffect(new PotionEffect(Potion.getPotionById(5),10, 2));
			else if(player.height > 5)
				player.addPotionEffect(new PotionEffect(Potion.getPotionById(5), 10, 1));
	//		if(player.height < 5)
	//			player.removeActivePotionEffect(Potion.getPotionById(5));

		}

	}

	public static boolean getFire()
	{
		return fire;
	}

	public static boolean getStrength()
	{
		return strength;
	}

	public static boolean getRegenerate()
	{
		return regenerate;
	}

	public static boolean getSpeed()
	{
		return speed;
	}

	/*
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(LivingUpdateEvent event)
	{
		EntityLivingBase theEntityLiving = event.getEntityLiving();

		if (!theEntityLiving.world.isRemote)
		{
			if (theEntityLiving.isBurning())
			{
				int blockX = MathHelper.floor(theEntityLiving.posX);
				int blockY = MathHelper.floor(theEntityLiving.posY-0.2D - event.getEntityLiving().getYOffset());
				int blockZ = MathHelper.floor(theEntityLiving.posZ);

				BlockPos blockLocation = new BlockPos(blockX, blockY, blockZ).up();
				Block blockAtLocation = theEntityLiving.world.getBlockState(blockLocation).getBlock();

				if (blockAtLocation == Blocks.AIR)
				{
					theEntityLiving.world.setBlockState(
							blockLocation, 
							BlockInit.LIGHT_SOURCE.getDefaultState()
							);
				}        		
			}
		}
	}
	 */
	public static EntityLivingBase getClosestEntityLiving(World parWorld, BlockPos parPos, double parMaxDistance)
	{
		if (parMaxDistance <= 0.0D)
		{
			return null;
		}

		EntityLivingBase closestLiving = null;
		double distanceSq = parMaxDistance*parMaxDistance;
		AxisAlignedBB aabb = new AxisAlignedBB(
				parPos.getX()-parMaxDistance,
				parPos.getY()-parMaxDistance,
				parPos.getZ()-parMaxDistance,
				parPos.getX()+parMaxDistance,
				parPos.getY()+parMaxDistance,
				parPos.getZ()+parMaxDistance
				);
		List<EntityLivingBase> listEntitiesInRange = parWorld.getEntitiesWithinAABB(EntityLivingBase.class, aabb);
		Iterator<EntityLivingBase> iterator = listEntitiesInRange.iterator();
		while (iterator.hasNext())
		{
			EntityLivingBase next = iterator.next();
			if (getDistanceSq(next.getPosition(), parPos) < distanceSq)
			{
				closestLiving = next;
			}		
		}
		return closestLiving;
	}

	protected static double getDistanceSq(BlockPos parPos1, BlockPos parPos2)
	{
		return (  (parPos1.getX()-parPos2.getX())*(parPos1.getX()-parPos2.getX())
				+ (parPos1.getY()-parPos2.getY())*(parPos1.getY()-parPos2.getY())
				+ (parPos1.getZ()-parPos2.getZ())*(parPos1.getZ()-parPos2.getZ()));
	}





}