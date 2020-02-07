package updates;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import blocks.LightSource;
import init.BlockInit;
import init.ItemInit;
import init.PotionInit;
import main.ConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class PlayerUpdate 
{

	public static boolean clicked = false;
	public static boolean magicClicked = false;

	public static void init(World world, EntityPlayer player)
	{
		armorRender(player);
		uraniumUpdate(player, world);
		nightmareRealm(player, world);
	}

	public static void initEntityLiving(World world, EntityLivingBase entity)
	{
		if(!world.isRemote)
			uraniumUpdate(entity, world);

		if(entity.isPotionActive(PotionInit.FREEZE_EFFECT))
			freezePotion(entity, world);
	}

	public static void armorRender(EntityPlayer player)
	{		
		Item head = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem();
		Item chest = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem();
		Item legs = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem();
		Item feet = player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem();


		//Amulet
		if(chest == ItemInit.MYSTIC_AMULET)
		{
			player.capabilities.allowFlying = true;
			player.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 10, 1));
		}
		else if(!player.capabilities.isCreativeMode)
		{
			player.capabilities.allowFlying = false;
		}

		//Mabel
		boolean mabelArmor = false;

		if(feet == ItemInit.MABEL_SHOES)
		{
			if(legs == ItemInit.MABEL_PANTS)
			{
				if(chest == ItemInit.MABEL_SWEATER || chest == ItemInit.LIGHT_SWEATER)
				{
					if(head == ItemInit.MABEL_BANDANA)
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

		if(feet == ItemInit.PINE_SHOES)
		{
			if(legs == ItemInit.PINE_PANTS)
			{
				if(chest == ItemInit.PINE_SHIRT)
				{
					if(head == ItemInit.PINE_HAT)
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
		Item head = entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem();
		Item chest = entity.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem();
		Item legs = entity.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem();
		Item feet = entity.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem();

		boolean hasArmor = false;

		if(head == ItemInit.RUBBER_HAT)
		{
			if(chest == ItemInit.RUBBER_CHESTPLATE)
			{
				if(legs == ItemInit.RUBBER_LEGGINGS)
				{
					if(feet == ItemInit.RUBBER_BOOTS)
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
					entity.addPotionEffect(new PotionEffect(PotionInit.RADIATION_EFFECT, 2000, 0));
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
					player.addPotionEffect(new PotionEffect(PotionInit.RADIATION_EFFECT, 2000, 0));
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

	public static void extendedReach(EntityPlayer player, World world, int reach)
	{
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


	public static void freezePotion(EntityLivingBase entity, World world)
	{
		entity.setAIMoveSpeed(0.0F);
		entity.setJumping(false);
		entity.rotationPitch = entity.prevRotationPitch;
		entity.rotationYaw = entity.prevRotationYaw;
		entity.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 5, 10));
		entity.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 5, 10));

		if(entity instanceof EntityLiving)
			((EntityLiving) entity).setCanPickUpLoot(false);
	}

	public static void nightmareRealm(EntityPlayer player, World world) {

		if(player.dimension == ConfigHandler.NIGHTMARE_REALM) 
		{
			WorldServer worldServer = world.getMinecraftServer().getWorld(ConfigHandler.NIGHTMARE_REALM);

			Block block = worldServer.getBlockState(player.getPosition()).getBlock();
			if(block == Blocks.WATER || block == Blocks.FLOWING_WATER) {
				player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 600, 0));
			}
		}
	}

	public static void sizePotion(EntityLivingBase entity, World world) {
		System.out.println("Potion Active");
		entity.removePotionEffect(PotionInit.GROWTH_EFFECT);
		entity.removePotionEffect(PotionInit.NORMAL_EFFECT);
	}
}