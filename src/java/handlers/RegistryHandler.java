package handlers;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Multimap;

import animations.RenderLaser;
import armor.FireHelmet;
import armor.RegenerationLegs;
import armor.SpeedBoots;
import armor.StrengthChestplate;
import blocks.LightSource;
import commands.CommandDimensionTeleport;
import commands.CommandLocate2;
import commands.Teleport;
import entity.EntityBill;
import entity.EntityRegistry;
import entity.EntityTimeBaby;
import init.BiomeInit;
import init.BlockInit;
import init.DimensionInit;
import init.ItemInit;
import init.PotionInit;
import items.FlashLight;
import items.ReturnDevice;
import items.TimeTape;
import main.GravityFalls;
import main.IHasModel;
import main.Reference;
import models.ModelSize;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.state.IBlockState;
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
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemCompass;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.EnumDifficulty;
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
import net.minecraftforge.fml.client.registry.ClientRegistry;
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
import tileEntities.TileEntityLaser;
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
	public static boolean checkActive = false;
	public static int countdown = -1;

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
	public static boolean recievedTimeWish = false;
	public static boolean timebabySpawned = false;

	public static boolean returnDevice = false;

	public static PortalBlocks portalBlocks = null;
	public static boolean portalControl = false;

	private static boolean setBlocks = false;

	public static boolean raiseDead = false;
	public static boolean zombieMessage = false;


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
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityLaser.class, new RenderLaser());
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
		event.registerServerCommand(new CommandLocate2());
	}


	@SubscribeEvent
	public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event)
	{		
		levitationRender(event.getEntityLiving());

		if (event.getEntityLiving() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)event.getEntity();
			RegistryHandler.world = player.world;

			armorRender(player);
			uraniumRender(player);
			flashlightRender(player);
			heightRender(player);
			globnarRender(player);
			//nightmareRender(player);
			
			if(portalControl)
				portalRender(player);
			raiseDead(player);


			if(scale > 1 && Mouse.isButtonDown(0))
				extendedReach(player, player.world, (int) (scale * 4));

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
			//	EntityTimeBaby timebaby = new EntityTimeBaby(world);
			EntityBill timebaby = null;
			inGlobnar = true;
			if(!messageSent)
			{
				ITextComponent msg = new TextComponentString("Welcome to Globnar!");
				player.sendMessage(msg);
				msg = new TextComponentString("You must defeat Time Baby to win the Time Wish!");
				player.sendMessage(msg);
				msg = new TextComponentString("All Time Tapes will now be confiscated.");
				player.sendMessage(msg);
				messageSent = true;


				//Confiscate all Time Tapes
				for(int i = 0; i < player.inventory.getSizeInventory(); i++)
				{
					if(player.inventory.getStackInSlot(i).getItem() instanceof TimeTape)
					{
						player.inventory.removeStackFromSlot(i);
					}
				}
				recievedTimeWish = false;
			}
			//Spawn Time Baby
			if(!player.world.isRemote && !timebabySpawned)
			{
				timebaby = new EntityBill(player.world, -62.5, 74, -18.5);
				player.world.spawnEntity(timebaby);
				timebaby.changeDimension(2);
				timebabySpawned = true;
			}

			//Give the player a Time Wish and a Time Tape
			if(timebaby != null && timebaby.isDead && !recievedTimeWish && timebabySpawned && !player.world.isRemote)
			{
				EntityItem entityTape = new EntityItem(player.world);
				entityTape.setItem(new ItemStack(ItemInit.TIME_TAPE));
				entityTape.setPosition(-18.5, 61, -18.5);
				player.world.spawnEntity(entityTape);

				EntityItem entityWish = new EntityItem(player.world);
				entityWish.setItem(new ItemStack(ItemInit.TIME_WISH));
				entityWish.setPosition(-18.5, 61, -18.5);
				player.world.spawnEntity(entityWish);

				recievedTimeWish = true;
				timebabySpawned = false;
				ITextComponent msg = new TextComponentString("Time Baby has been defeated!");
				player.sendMessage(msg);
			}
		}
		else
		{
			inGlobnar = false;
			messageSent = false;
		}
		/*
		if(!inGlobnar && inventoryStacks != null)
		{
			for(int i = 0; i < 27; i++)
			{
				player.inventory.add(i, inventoryStacks.get(i));
			}
			inventoryStacks = null;
		}
		 */
	}

	public static void nightmareRender(EntityPlayer player)
	{	
		if(player.dimension == 3)
		{
			if(!returnDevice)
			{
				boolean hasDevice = false;

				for(int i = 0; i < player.inventory.getSizeInventory(); i++)
				{
					if(player.inventory.getStackInSlot(i).getItem() instanceof ReturnDevice)
					{
						hasDevice = true;
					}
				}

				if(!hasDevice && !player.world.isRemote)
				{
					EntityItem entityReturn = new EntityItem(player.world);
					entityReturn.setItem(new ItemStack(ItemInit.RETURN_DEVICE));
					entityReturn.setPosition(player.posX, player.posY, player.posZ);
					player.world.spawnEntity(entityReturn);
					returnDevice = true;
				}
			}
		}
		else
			returnDevice = false;
	}

	public static void portalRender(EntityPlayer player)
	{
		Date date = new Date();		

		if(!checkActive && nbt.hasKey("portalActive"))
		{
			portalActive = nbt.getBoolean("portalActive");
			checkActive = true;
		}
		else
		{
			nbt.setBoolean("portalActive", portalActive);
		}

		if(countdown == -1 && portalActive && !nbt.hasKey("countdown"))
		{
			nbt.setLong("countdown", date.getTime());
		}

		if(nbt.hasKey("countdown"))
			countdown = 36000 - (int)(date.getTime() - nbt.getLong("countdown"));



		if(portalActive)
		{
			if(countdown == 36000)
				setPortal();
			if(countdown > 0)
				countdown--;
			else if(countdown == 0)
				portalOn();

			//760
			if(countdown == 760)
				world.playSound(player, player.getPosition(), SoundsHandler.PORTAL_WORKING, SoundCategory.BLOCKS, 1.0F, 1.0F);
			else if(countdown == 0)
				world.playSound(player, player.getPosition(), SoundsHandler.PORTAL_FINISHED, SoundCategory.BLOCKS, 1.0F, 1.0F);


			/*
			if(portalBlocks != null)
			{
				Block block = world.getBlockState(portalBlocks.getLeverPos()).getBlock();

				if(block != BlockInit.PORTAL_LEVER)
					removePortal();
			}
			 */

		}
		else
		{
			nbt.removeTag("countdown");
			removePortal();
		}
	}	

	public static void levitationRender(EntityLivingBase entity)
	{
		if(portalActive)
		{
			if(countdown < 35500 && countdown > 35460)
				entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 5, 0));
			else if(countdown < 27000 && countdown > 26880)
				entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 5, 0));
			else if(countdown < 20000 && countdown > 19820)
				entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 5, 0));
			else if(countdown < 14000 && countdown > 13760)
				entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 5, 0));
			else if(countdown < 8000 && countdown > 7700)
				entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 5, 0));
			else if(countdown < 6000 && countdown > 5680)
				entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 5, 0));
			else if(countdown < 4000 && countdown > 3660)
				entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 5, 0));
			else if(countdown < 3000 && countdown > 2640)
				entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 5, 0));
			else if(countdown < 2000 && countdown > 1620)
				entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 5, 0));
			else if(countdown < 1500 && countdown > 1100)
				entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 5, 0));
			else if(countdown < 1000 && countdown > 550)
				entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 5, 0));
			else if(countdown < 500 && countdown > 0)
				entity.addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 5, 0));
		}
	}

	public static void portalOn()
	{
		if(portalBlocks != null)
		{
			IBlockState state =  BlockInit.BLOCKTELEPORTER.getDefaultState();

			for(BlockPos element : portalBlocks.getPortal())
			{
				world.setBlockState(element, state);
			}
		}
	}

	public static void setPortal()
	{
		if(portalBlocks != null && !setBlocks)
		{
			IBlockState state =  BlockInit.LIGHT_WHITE.getDefaultState();

			for(BlockPos element : portalBlocks.getTriangle())
			{
				world.setBlockState(element, state);
			}

			state =  BlockInit.LIGHT_BLACK.getDefaultState();

			for(BlockPos element : portalBlocks.getRing())
			{
				world.setBlockState(element, state);
			}

			state =  BlockInit.LIGHT_CYAN.getDefaultState();

			for(BlockPos element : portalBlocks.getCircle())
			{
				world.setBlockState(element, state);
			}

			setBlocks = true;
		}
	}

	public static void removePortal()
	{
		if(portalBlocks != null && setBlocks)
		{
			IBlockState state = Blocks.CONCRETE.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.WHITE);

			for(BlockPos element : portalBlocks.getTriangle())
			{
				world.setBlockState(element, state);
			}

			state = Blocks.CONCRETE.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.BLACK);

			for(BlockPos element : portalBlocks.getRing())
			{
				world.setBlockState(element, state);
			}

			state = Blocks.CONCRETE.getDefaultState().withProperty(BlockColored.COLOR, EnumDyeColor.CYAN);

			for(BlockPos element : portalBlocks.getCircle())
			{
				world.setBlockState(element, state);
			}

			state =  Blocks.AIR.getDefaultState();

			for(BlockPos element : portalBlocks.getPortal())
			{
				world.setBlockState(element, state);
			}
			setBlocks = false;
		}
	}


	public static void raiseDead(EntityPlayer player)
	{
		if(nbt.hasKey("raiseDead"))
		{
			if(nbt.getBoolean("raiseDead"))
			{
				raiseDead = true;
			}
		}
		else
			nbt.setBoolean("raiseDead", raiseDead);
		
		if(player.world.isDaytime())
		{
			raiseDead = false;
			zombieMessage = false;
		}
		
		//Timer based on Difficulty
		boolean zombieSpawn = false;
		
		if(player.world.getDifficulty().equals(EnumDifficulty.PEACEFUL))
			zombieSpawn = false;
		else if(player.world.getDifficulty().equals(EnumDifficulty.EASY))
		{
			if(player.world.getWorldTime() % 30 == 0)
				zombieSpawn = true;
		}
		else if(player.world.getDifficulty().equals(EnumDifficulty.NORMAL))
		{
			if(player.world.getWorldTime() % 20 == 0)
				zombieSpawn = true;
		}
		else if(player.world.getDifficulty().equals(EnumDifficulty.HARD))
		{
			if(player.world.getWorldTime() % 10 == 0)
				zombieSpawn = true;
		}
			
		

		if(raiseDead && !player.world.isRemote && zombieSpawn)
		{
			if(!zombieMessage)
			{
				ITextComponent msg = new TextComponentString("You feel a sharp chill go down your spine.");
				player.sendMessage(msg);
				zombieMessage = true;
			}

			int x = (int) ((Math.random() * 80) - 40 + player.posX);
			int z = (int) ((Math.random() * 80) - 40 + player.posZ);
			int y = findGround(player.world, x, (int) player.posY, z);
			BlockPos pos1 = new BlockPos(x, y + 1, z);
			BlockPos pos2 = new BlockPos(x, y + 2, z);


			if(y != -1 && player.world.getBlockState(pos1).getBlock() == Blocks.AIR && player.world.getBlockState(pos2).getBlock() == Blocks.AIR)
			{
				EntityZombie zombie = new EntityZombie(player.world);
				zombie.setPosition(x, y + 1, z);

				player.world.spawnEntity(zombie);			
			}
		}
		nbt.setBoolean("raiseDead", raiseDead);
		zombieSpawn = false;
	}

	private static int findGround(World world, int x, int playerY, int z)
	{
		int y = playerY + 15;
		boolean foundGround = false;

		while(!foundGround && y-- >= 0)
		{
			if(y < (playerY - 15))
				return -1;

			Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
			foundGround = block.getDefaultState().isSideSolid(world, new BlockPos(x, y, z), EnumFacing.UP);
		}

		if(foundGround)
			return y;

		return -1;
	}
}