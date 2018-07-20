package handlers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Multimap;

import armor.FireHelmet;
import armor.RegenerationLegs;
import armor.SpeedBoots;
import armor.StrengthChestplate;
import blocks.LightSource;
import commands.CommandDimensionTeleport;
import entity.EntityRegistry;
import init.BiomeInit;
import init.BlockInit;
import init.DimensionInit;
import init.ItemInit;
import items.FlashLight;
import main.GravityFalls;
import main.IHasModel;
import main.Reference;
import models.ModelSize;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.entity.RenderCow;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemCompass;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;
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

	public static World world = null;
	
	public static boolean portalActive = false;
	public static int countdown = 18000;
	
	public static boolean clicked = false;

	public static boolean billSummoned = false;


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
		GameRegistry.registerWorldGenerator(new WorldGenOres(), 0);

		BiomeInit.registerBiomes();
		DimensionInit.registerDimensions();

		EntityRegistry.registerEntities();
		RenderHandler.registerEntityRenders();

	}

	public static void initRegistries()
	{
		SoundsHandler.registerSounds();
		NetworkRegistry.INSTANCE.registerGuiHandler(GravityFalls.instance, new GuiHandler());
	}


	public static void otherRegistries()
	{
		//GameRegistry.registerWorldGenerator(new WorldGenTrees(), 0);
		GameRegistry.registerWorldGenerator(new WorldGenCustomStructures(), 0);
	}

	public static void serverRegistries(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new CommandDimensionTeleport());
	}


	@SubscribeEvent
	public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event)
	{		
		if ((event.getEntity() instanceof EntityPlayer))
		{
			EntityPlayer player = (EntityPlayer)event.getEntity();
			RegistryHandler.world = player.world;
			
			armorRender(player);
			uraniumRender(player);
			flashlightRender(player);
			heightRender(player);
			

			//Portal
			if(portalActive)
			{
				System.out.println("Portal Active");
			}
		}
	}
	
	
	public static void uraniumRender(EntityPlayer player)
	{
		boolean rubberArmor = false;
		
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
			if (player.inventory.hasItemStack(new ItemStack(BlockInit.URANIUM_TANK_HALFFILLED))) 
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
	}
	

	public static void armorRender(EntityPlayer player)
	{
		//Amulet
		if(player.getArmorInventoryList().toString().contains("mysticamulet"))
		{
			player.capabilities.allowFlying = true;
		}
		else if(!player.capabilities.isCreativeMode)
		{
			player.capabilities.allowFlying = false;
		}
		
		//Mabel
		boolean mabelArmor = false;

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
		boolean dipperArmor = false;

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
	}
	
	public static void flashlightRender(EntityPlayer player)
	{
		if (!player.world.isRemote && clicked)
	    {
	        if (player.getHeldItemMainhand() != null)
	        {
	            if (LightSource.isLightEmittingItem(player.getHeldItemMainhand().getItem()))
	            {
	                int blockX = MathHelper.floor(player.posX);
	                int blockY = MathHelper.floor(player.posY-0.2D - player.getYOffset());
	                int blockZ = MathHelper.floor(player.posZ);
	                // place light at head level
	                BlockPos blockLocation = new BlockPos(blockX, blockY, blockZ).up();
	                if (player.world.getBlockState(blockLocation).getBlock() == Blocks.AIR)
	                {
	                    player.world.setBlockState(blockLocation, BlockInit.LIGHT_SOURCE.getDefaultState());
	                    
	                }
	                else if (player.world.getBlockState(blockLocation.add(player.getLookVec().x, 
	                            player.getLookVec().y, player.getLookVec().z)).getBlock() == Blocks.AIR)
	                {

	                    player.world.setBlockState(blockLocation.add(player.getLookVec().x, player.getLookVec().y, 
	                               player.getLookVec().z), BlockInit.LIGHT_SOURCE.getDefaultState());
	                    
	                }
	            }
	        }
	    }
	}
	
	public static void heightRender(EntityPlayer player)
	{
		//Gives strength if the player is huge
		if(player.height > 9)
			player.addPotionEffect(new PotionEffect(Potion.getPotionById(5),10, 2));
		else if(player.height > 5)
			player.addPotionEffect(new PotionEffect(Potion.getPotionById(5), 10, 1));
		//		if(player.height < 5)
		//			player.removeActivePotionEffect(Potion.getPotionById(5));

		//Increases player reach distance depending on how big they are
		//	if(player.height > 2)
		//		getMouseOver(player, player.world, (int) player.height * 3);

	}

	
	@SubscribeEvent
	public static void onPlayerRender(RenderPlayerEvent.Pre event)
	{
		EntityPlayer player = event.getEntityPlayer();
		
		event.getRenderer().getMainModel();
				

	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onRenderLiving(RenderLivingEvent.Pre event)
	{
		if(event.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.getEntity();
			
			
			event.setCanceled(true);
			
			final RenderPlayer modelRenderer = new RenderPlayer(event.getRenderer().getRenderManager());
			modelRenderer.doRender((AbstractClientPlayer) player, event.getX(), event.getY(), event.getZ(), 0F, 0F);
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
	
	public static boolean getPortal()
	{
		return portalActive;
	}
	
	public static void setPortal(boolean portal)
	{
		portalActive = portal;
	}
	
	public static int getCountdown()
	{
		return countdown;
	}


	public static void getMouseOver(EntityPlayer player, World world, int reach)
	{

		Vec3d lookVec = player.getLookVec();

		BlockPos pos = player.getPosition();

		float yaw = player.rotationYaw;
		float pitch = player.rotationPitch;

		for(int f = 0; f <= reach; f++)
		{
			double x = (double)(-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
			double y = (double)(-MathHelper.sin((pitch) / 180.0F * (float)Math.PI) * f);
			double z = (double)(MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);


			AxisAlignedBB entityPos = new AxisAlignedBB(pos.getX() + x, pos.getY() + y, pos.getZ() + z, pos.getX() + x + 1, pos.getY() + y + 1, pos.getZ() + z + 1);

			List<Entity> list = world.getEntitiesInAABBexcluding(player, entityPos, Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>()
			{
				public boolean apply(@Nullable Entity p_apply_1_)
				{
					return p_apply_1_ != null && p_apply_1_.canBeCollidedWith();
				}
			}));

			for(int j = 0; j < list.size(); ++j)
			{
				Entity entity = list.get(j);
				if(player instanceof EntityPlayerMP)
				{					
					EntityPlayerMP entityplayer = (EntityPlayerMP) player;


					if(entity instanceof EntityLivingBase)
					{
						EntityLivingBase mob = (EntityLivingBase) entity;

						mob.addPotionEffect(new PotionEffect(MobEffects.INSTANT_DAMAGE, 1, 0));
					}
				}				
			}
		}
	}

	@SubscribeEvent
	public void changeSize(PlayerTickEvent event)
	{
		EntityPlayer player = event.player;
		//	player.boundingBox.maxY = player.boundingBox.minY + (height);

	}
	/*
	
	ModelBase value;

	@SubscribeEvent
	public void onRenderPlayerPre(RenderPlayerEvent.Pre pre) {
		value = ObfuscationReflectionHelper.getPrivateValue(RenderEntity.class, pre.getRenderer(), new String[] { "mainModel", "field_77045_g" });
		int type = 0;
		if(EntityPlayer.getPlayerEntity(pre.getEntityPlayer()) != null) {
			type = EntityPlayer.getPlayerEntity(pre.getEntityPlayer());
		}
		switch (type) {
		case 0:
			ObfuscationReflectionHelper.setPrivateValue(RenderEntity.class, pre.getRenderer(), new ModelSize(type), new String[] { "mainModel", "field_77045_g" });
			break;
		default:
			break;
		}
	}

	@SubscribeEvent
	public void onRenderPlayerPost(RenderPlayerEvent.Post post) {
		ObfuscationReflectionHelper.setPrivateValue(RenderEntity.class, post.getRenderer(), value, new String[] { "mainModel", "field_77045_g" });
	}

	}
	 */
	@SideOnly(Side.CLIENT)
	private static double getFrameRotation(EntityItemFrame p_185094_1_)
	{
		return (double)MathHelper.wrapDegrees(180 + p_185094_1_.facingDirection.getHorizontalIndex() * 90);
	}

	@SideOnly(Side.CLIENT)
	private static double getSpawnToAngle(World p_185092_1_, Entity p_185092_2_)
	{
		BlockPos blockpos = p_185092_1_.getSpawnPoint();
		return Math.atan2((double)blockpos.getZ() - p_185092_2_.posZ, (double)blockpos.getX() - p_185092_2_.posX);
	}

}