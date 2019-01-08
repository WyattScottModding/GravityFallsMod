package updates;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import blocks.LightSource;
import init.BlockInit;
import init.ItemInit;
import init.PotionInit;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.client.event.sound.SoundEvent.SoundSourceEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class PlayerUpdate 
{

	public static boolean clicked = false;
	public static boolean magicClicked = false;
	public static float scale = 1.0F;
	public static World world = null;

	public static void init(World world, EntityPlayer player)
	{
		NBTTagCompound nbtPlayer = player.getEntityData();
		scale = 1.0F;

		if(nbtPlayer.hasKey("scale"))
			scale = nbtPlayer.getFloat("scale");
		else
			nbtPlayer.setFloat("scale", 1.0F);

		//System.out.println("Scale: " + scale);

		armorRender(player);
		uraniumUpdate(player, world);
		flashlightRender(player);
		heightRender(player);

		setWorld(world);

		if(scale > 1 && Mouse.isButtonDown(0))
			extendedReach(player, world, (int) (scale * 4));
	}

	public static void setWorld(World world2)
	{
		world = world2;
	}

	public static void initEntityLiving(World world, EntityLivingBase entity)
	{
		uraniumUpdate(entity, world);

		if(entity.isPotionActive(PotionInit.FREEZE_EFFECT))
			freezePotion(entity, world);
	}

	public static void armorRender(EntityPlayer player)
	{
		boolean speed = false;

		//Amulet
		if(player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == ItemInit.MYSTIC_AMULET)
		{
			player.capabilities.allowFlying = true;
		}
		else if(!player.capabilities.isCreativeMode)
		{
			player.capabilities.allowFlying = false;
		}

		//Mabel
		boolean mabelArmor = false;

		if(player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == ItemInit.MABEL_SHOES)
		{
			if(player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() == ItemInit.MABEL_PANTS)
			{
				if(player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == ItemInit.MABEL_SWEATER)
				{
					if(player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == ItemInit.MABEL_BANDANA)
					{
						mabelArmor = true;
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

		if(player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == ItemInit.PINE_SHOES)
		{
			if(player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() == ItemInit.PINE_PANTS)
			{
				if(player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == ItemInit.PINE_SHIRT)
				{
					if(player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == ItemInit.PINE_HAT)
					{
						dipperArmor = true;
					}
				}
			}
		}

		if(dipperArmor)
		{
			player.addPotionEffect(new PotionEffect(MobEffects.LUCK, 10, 1));
		}
	}

	public static void uraniumUpdate(EntityLivingBase entity, World world)
	{
		boolean hasArmor = false;

		if(entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == ItemInit.RUBBER_HAT)
		{
			if(entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == ItemInit.RUBBER_CHESTPLATE)
			{
				if(entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem() == ItemInit.RUBBER_LEGGINGS)
				{
					if(entity.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem() == ItemInit.RUBBER_BOOTS)
					{
						hasArmor = true;
					}
				}
			}
		}

		boolean playerIsCreative = false;

		if(entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;
			playerIsCreative = player.capabilities.isCreativeMode;
		}

		if(!playerIsCreative && !hasArmor)
		{
			Block block1 = world.getBlockState(entity.getPosition()).getBlock();
			Block block2 = world.getBlockState(entity.getPosition().down()).getBlock();
			Block block3 = world.getBlockState(entity.getPosition().up()).getBlock();
			Block block4 = world.getBlockState(entity.getPosition().east()).getBlock();
			Block block5 = world.getBlockState(entity.getPosition().west()).getBlock();
			Block block6 = world.getBlockState(entity.getPosition().north()).getBlock();
			Block block7 = world.getBlockState(entity.getPosition().south()).getBlock();
			Block block8 = world.getBlockState(entity.getPosition().up().up()).getBlock();
			Block block9 = world.getBlockState(entity.getPosition().up().north()).getBlock();
			Block block10 = world.getBlockState(entity.getPosition().up().south()).getBlock();
			Block block11 = world.getBlockState(entity.getPosition().up().west()).getBlock();
			Block block12 = world.getBlockState(entity.getPosition().up().east()).getBlock();
			Block block13 = world.getBlockState(entity.getPosition().north().west()).getBlock();
			Block block14 = world.getBlockState(entity.getPosition().north().east()).getBlock();
			Block block15 = world.getBlockState(entity.getPosition().south().west()).getBlock();
			Block block16 = world.getBlockState(entity.getPosition().south().east()).getBlock();


			List<Block> blocks = new ArrayList<Block>();
			blocks.add(block1);
			blocks.add(block2);
			blocks.add(block3);
			blocks.add(block4);
			blocks.add(block5);
			blocks.add(block6);
			blocks.add(block7);
			blocks.add(block8);
			blocks.add(block9);
			blocks.add(block10);
			blocks.add(block11);
			blocks.add(block12);
			blocks.add(block13);
			blocks.add(block14);
			blocks.add(block15);
			blocks.add(block16);



			for(Block element : blocks)
			{
				if(element == BlockInit.URANIUM || element == BlockInit.URANIUM_TANK_FILLED || element == BlockInit.URANIUM_TANK_HALFFILLED)
				{
					entity.addPotionEffect(new PotionEffect(MobEffects.POISON, 10, 10));
				}
			}

			if(entity instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) entity;

				boolean uraniumOre = player.inventory.hasItemStack(new ItemStack(BlockInit.URANIUM));
				boolean uraniumBucket = player.inventory.hasItemStack(new ItemStack(ItemInit.URANIUM_BUCKET));
				boolean uraniumTankFilled = player.inventory.hasItemStack(new ItemStack(BlockInit.URANIUM_TANK_FILLED));
				boolean uraniumTankHalfFilled = player.inventory.hasItemStack(new ItemStack(BlockInit.URANIUM_TANK_HALFFILLED));

				if(uraniumOre || uraniumBucket || uraniumTankFilled || uraniumTankHalfFilled)
				{
					player.addPotionEffect(new PotionEffect(MobEffects.POISON, 10, 10));
				}
			}
		}
	}

	public static void flashlightRender(EntityPlayer player)
	{
		if (!player.world.isRemote && clicked)
		{
			if ((player.getHeldItemMainhand() != null && LightSource.isLightEmittingItem(player.getHeldItemMainhand().getItem())) || (player.getHeldItemOffhand() != null && LightSource.isLightEmittingItem(player.getHeldItemOffhand().getItem())))
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
		if(world == null)
			world = player.world;

		NBTTagCompound nbtPlayer = player.getEntityData();
		scale = 1.0F;

		if(nbtPlayer.hasKey("scale"))
			scale = nbtPlayer.getFloat("scale");
		else
			nbtPlayer.setFloat("scale", 1.0F);

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

		float height = scale * 1.8F;

		player.stepHeight = height / 2;
		player.height = height;
		player.width = height / 3;
		player.eyeHeight = .9F * (height);
		player.renderOffsetY = (float) (height - 1.8);

	}


	public static void playerRender(PlayerTickEvent event)
	{
		EntityPlayer player = event.player;

		if(world == null)
			world = player.world;

		NBTTagCompound nbtPlayer = player.getEntityData();
		scale = 1.0F;

		if(nbtPlayer.hasKey("scale"))
			scale = nbtPlayer.getFloat("scale");
		else
			nbtPlayer.setFloat("scale", 1.0F);

		if(scale != 1.0F)
		{
			float height = scale * 1.8F;

			//System.out.println("Render Height: " + height);
			player.getEntityBoundingBox().setMaxY(player.getEntityBoundingBox().minY + (height));
		}
	}


	public static void onPlayerRenderPre(RenderPlayerEvent.Pre event)
	{				
		NBTTagCompound nbtPlayer = event.getEntityPlayer().getEntityData();
		scale = 1.0F;

		if(nbtPlayer.hasKey("scale"))
			scale = nbtPlayer.getFloat("scale");
		else
			nbtPlayer.setFloat("scale", 1.0F);

		GL11.glPushMatrix();
		GL11.glScaled(scale, scale, scale);
	}

	public static void onPlayerRenderPost(RenderPlayerEvent.Post event)
	{		
		GL11.glPopMatrix();
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
	public void sounds(PlaySoundEvent event)
	{
		//System.out.println("Sound Event Activated: " + event.getName());
		if(world != null && event.getName().equals("record.discogirl"))
		{
			int posX = (int) event.getSound().getXPosF();
			int posY= (int) event.getSound().getYPosF();
			int posZ = (int) event.getSound().getZPosF();

			BlockPos blockpos = new BlockPos(posX, posY, posZ);

			//System.out.println("Sound Played at: " + blockpos.toString());

			AxisAlignedBB entityPos = new AxisAlignedBB(posX - 4 , posY - 4, posZ - 4, posX + 4, posY + 4, posZ + 4);

			List<Entity> list = world.getEntitiesWithinAABB(EntityLivingBase.class, entityPos);

			for(int j = 0; j < list.size(); ++j)
			{
				Entity entity = list.get(j);
				if(entity instanceof EntityAnimal)
				{
					((EntityAnimal) entity).setInLove(null);
				}
			}
		}
	}

	public static void freezePotion(EntityLivingBase entity, World world)
	{
		entity.setAIMoveSpeed(0.0F);
		entity.jumpMovementFactor = 0.0F;
		entity.rotationPitch = entity.prevRotationPitch;
		entity.rotationYaw = entity.prevRotationYaw;
		entity.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 5, 10));
		entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 5, 10));

		if(entity instanceof EntityLiving)
			((EntityLiving) entity).setCanPickUpLoot(false);
	}
}