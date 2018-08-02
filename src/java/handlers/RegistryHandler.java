package handlers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

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
import init.PotionInit;
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
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.entity.RenderCow;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.command.CommandEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
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
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
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
	public static boolean magicClicked = false;

	public static boolean billSummoned = false;

	public static float yaw = 0.0F;
	public static float pitch = 0.0F;
	public static boolean foundAngles = false;

	public static float scale = 1.0F;
	public static NBTTagCompound nbt = new NBTTagCompound();

	public static boolean messageSent = false;
	public static List<ItemStack> inventoryStacks = null;
	public static boolean inGlobnar = false;


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
	public static void onEffectRegister(RegistryEvent.Register<Potion> event)
	{
		event.getRegistry().registerAll(PotionInit.POTIONS.toArray(new Potion[0]));
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
		event.registerServerCommand(new CommandEffect());

	}


	@SubscribeEvent
	public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event)
	{		
		if (event.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.getEntity();
			RegistryHandler.world = player.world;

			armorRender(player);
			uraniumRender(player);
			flashlightRender(player);
			heightRender(player);
			globnarRender(player);

			if(scale > 1 && Mouse.isButtonDown(0))
				extendedReach(player, player.world, (int) (scale * 4));

			//Portal
			if(portalActive)
			{
				System.out.println("Portal Active");
			}


		}

		if(event.getEntity() instanceof EntityLiving)
		{
			EntityLiving entityLiving = (EntityLiving) event.getEntity();

			potionRender(entityLiving);
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

	public static void magicFlashlightRender(EntityPlayer player)
	{
		if (!player.world.isRemote && magicClicked)
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
		//Changes the player's stats when different sizes
		if(scale > 4)
		{
			player.addPotionEffect(new PotionEffect(Potion.getPotionById(5),10, 2));
			player.addPotionEffect(new PotionEffect(Potion.getPotionById(1),10, 2));
		}
		else if(scale > 2)
		{
			player.addPotionEffect(new PotionEffect(Potion.getPotionById(5), 10, 1));
			player.addPotionEffect(new PotionEffect(Potion.getPotionById(1),10, 1));
		}
		else if(scale < 0.5)
		{
			player.addPotionEffect(new PotionEffect(Potion.getPotionById(10), 10, 1));
		}
		
		float height = 1.8F;
		
		if(nbt.hasKey("height"))
		{
			height = nbt.getFloat("height");
		
			player.stepHeight = height / 2;
			player.height = height;
			player.width = height / 3;
			player.eyeHeight = .9F * (height);
			player.renderOffsetY = (float) (height - 1.8);
		}


	}

	@SubscribeEvent
	public static void playerRener(PlayerTickEvent event)
	{
		EntityPlayer player = event.player;
		//		player.getCollisionBoundingBox().setMaxY(player.height);
	}

	public static void potionRender(EntityLiving entity)
	{
		if(!foundAngles)
		{
			yaw = entity.rotationYaw;
			pitch = entity.rotationPitch;
			foundAngles = true;
		}

		if(entity.isPotionActive(PotionInit.POTION_FREEZE))
		{
			if(entity.getActivePotionEffect(PotionInit.POTION_FREEZE).getDuration() == 0)
			{
				entity.removePotionEffect(PotionInit.POTION_FREEZE);
				foundAngles = false;
				return;
			}
			else
			{
				entity.setAIMoveSpeed(0.0F);
				entity.jumpMovementFactor = 0.0F;
				entity.setInWeb();
				entity.setCanPickUpLoot(false);
				entity.rotationYaw = yaw;
				entity.rotationPitch = pitch;
			}
		}
	}


	@SubscribeEvent
	public static void onPlayerRenderPre(RenderPlayerEvent.Pre event)
	{				
		GL11.glPushMatrix();
		GL11.glScaled(scale, scale, scale);
	}

	public static void extendedReach(EntityPlayer player, World world, int reach)
	{
		Vec3d lookVec = player.getLookVec();

		BlockPos pos = player.getPosition();

		float yaw = player.rotationYaw;
		float pitch = player.rotationPitch;

		int flag = 0;

		if(player.capabilities.isCreativeMode)
			flag = 1;

		for(int f = 4 + flag; f <= reach; f++)
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
				player.attackTargetEntityWithCurrentItem(entity);
				entity.setDead();
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerRenderPost(RenderPlayerEvent.Post event)
	{		
		GL11.glPopMatrix();
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

	public static void globnarRender(EntityPlayer player)
	{
		if(player.dimension == 2)
		{
			inGlobnar = true;
			if(!messageSent)
			{
				ITextComponent msg = new TextComponentString("Welcome to Globnar!");
				player.sendMessage(msg);
				msg = new TextComponentString("You must defeat Time Baby to win the Time Wish!");
				player.sendMessage(msg);
				messageSent = true;

				/*
				for(int i = 0; i < 27; i++)
				{
					if(player.inventory.getStackInSlot(i) != null)
					{
						inventoryStacks.add(player.inventory.getStackInSlot(i));
						player.inventory.removeStackFromSlot(i);
					}
				}
				int j  = player.inventory.getSlotFor(new ItemStack(ItemInit.TIME_TAPE));
				if(j != -1)
					player.inventory.removeStackFromSlot(j);
				 */
			}
		}
		else
		{
			inGlobnar = false;
			messageSent = false;
		}

		if(!inGlobnar && inventoryStacks != null)
		{
			for(int i = 0; i < 27; i++)
			{
				player.inventory.add(i, inventoryStacks.get(i));
			}
			inventoryStacks = null;
		}
	}


}