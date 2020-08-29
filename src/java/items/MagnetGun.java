package items;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import entity.EntitySecurityDroid;
import handlers.KeyBindings;
import handlers.SoundsHandler;
import init.BlockInit;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import network.MessageKillDroid;
import network.MessageProcessMemoryWipe;
import network.Messages;

public class MagnetGun extends Item implements IHasModel
{
	public MagnetGun(String name)
	{
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsitems);

		ItemInit.ITEMS.add(this);

		this.addPropertyOverride(new ResourceLocation("fired"), new IItemPropertyGetter()
		{
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
			{
				//Set the NBT to a new NBT if it is null
				NBTTagCompound nbt = new NBTTagCompound();

				if(stack.getTagCompound() != null)
					nbt = stack.getTagCompound();

				boolean active = false;

				if(nbt.hasKey("active"))
					active = nbt.getBoolean("active");

				return entityIn != null && (entityIn.getHeldItemMainhand() == stack || entityIn.getHeldItemOffhand() == stack) && active ? 1.0F : 0.0F;
			}
		});
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entityIn, int itemSlot, boolean isSelected) 
	{
		//Set the NBT to a new NBT if it is null
		NBTTagCompound nbt = new NBTTagCompound();

		if(stack.getTagCompound() != null)
			nbt = stack.getTagCompound();

		boolean active = false;
		int soundCounter = 0;
		int empCooldown = 0;

		double prevY1 = -5;
		double prevY2 = -5;
		double prevY3 = -5;
		double prevY4 = -5;

		double prevX1 = -5;
		double prevX2 = -5;
		double prevX3 = -5;
		double prevX4 = -5;

		double prevZ1 = -5;
		double prevZ2 = -5;
		double prevZ3 = -5;
		double prevZ4 = -5;

		if(nbt.hasKey("active"))
			active = nbt.getBoolean("active");
		if(nbt.hasKey("soundCounter"))
			soundCounter = nbt.getInteger("soundCounter");
		if(nbt.hasKey("empCooldown"))
			empCooldown = nbt.getInteger("empCooldown");

		if(nbt.hasKey("prevY1"))
			prevY1 = nbt.getDouble("prevY1");
		if(nbt.hasKey("prevY2"))
			prevY2 = nbt.getDouble("prevY2");
		if(nbt.hasKey("prevY3"))
			prevY3 = nbt.getDouble("prevY3");
		if(nbt.hasKey("prevY4"))
			prevY4 = nbt.getDouble("prevY4");

		if(nbt.hasKey("prevX1"))
			prevX1 = nbt.getDouble("prevX1");
		if(nbt.hasKey("prevX2"))
			prevX2 = nbt.getDouble("prevX2");
		if(nbt.hasKey("prevX3"))
			prevX3 = nbt.getDouble("prevX3");
		if(nbt.hasKey("prevX4"))
			prevX4 = nbt.getDouble("prevX4");

		if(nbt.hasKey("prevZ1"))
			prevZ1 = nbt.getDouble("prevZ1");
		if(nbt.hasKey("prevZ2"))
			prevZ2 = nbt.getDouble("prevZ2");
		if(nbt.hasKey("prevZ3"))
			prevZ3 = nbt.getDouble("prevZ3");
		if(nbt.hasKey("prevZ4"))
			prevZ4 = nbt.getDouble("prevZ4");

		//System.out.println("Active = " + active);

		if(soundCounter == 4)
			soundCounter = 0;
		else
			soundCounter++;

		if(empCooldown < 100)
			empCooldown++;

		if (entityIn instanceof EntityPlayer && ((EntityPlayer) entityIn).getHeldItemMainhand() == stack)
		{
			EntityPlayer player = (EntityPlayer)entityIn;

			//Kills security droids
			if(KeyBindings.ITEM1.isDown() && empCooldown >= 10)
			{
				player.playSound(SoundsHandler.EMP, 1, 1);
				Messages.INSTANCE.sendToServer(new MessageKillDroid());
				empCooldown = 0;

				//Particle effect
				float pitch = player.rotationPitch;
				float yaw = player.rotationYaw;

				float f = 1.5F;
				
				double x = (double)(-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
				double y = (double)(-MathHelper.sin((pitch) / 180.0F * (float)Math.PI) * f);
				double z = (double)(MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
				
				world.spawnParticle(EnumParticleTypes.SWEEP_ATTACK, player.posX + x - 1, player.posY + player.eyeHeight + y, player.posZ + z, x, y, z);
			}

			RayTraceResult blockPosition = player.rayTrace(500, 1.0F);
			IBlockState block = world.getBlockState(blockPosition.getBlockPos());
			Block blockType = block.getBlock();

			if(active)
			{
				//Sound Effect
				if(soundCounter == 4)
					world.playSound(player, player.posX, player.posY, player.posZ, SoundsHandler.ITEM_MAGNETGUN, SoundCategory.PLAYERS, 0.4F, 1.0F);

				//The gun is only attracted to objects that contain iron
				if(blockType == Blocks.IRON_BLOCK || blockType == Blocks.IRON_BARS || blockType == Blocks.IRON_ORE || blockType == Blocks.IRON_DOOR || blockType == Blocks.DETECTOR_RAIL || blockType == Blocks.RAIL || blockType == Blocks.ACTIVATOR_RAIL || blockType == Blocks.ANVIL || blockType == Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE || blockType == Blocks.PISTON || blockType == Blocks.STICKY_PISTON  || blockType == BlockInit.URANIUM_TANK || blockType == BlockInit.URANIUM_TANK_FILLED || blockType == BlockInit.URANIUM_TANK_HALFFILLED || blockType == Blocks.HOPPER || blockType == BlockInit.METAL_TREE)
				{
					float yaw = player.rotationYaw;
					float pitch = player.rotationPitch;

					//Amount of movement
					float f = 1.2F;

					player.motionX = (double)(-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
					player.motionY = (double)(-MathHelper.sin((pitch) / 180.0F * (float)Math.PI) * f);
					player.motionZ = (double)(MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);

					player.isAirBorne = true;

					if(player.posY == prevY1 && player.posY == prevY2 && player.posY == prevY3 && player.posY == prevY4 && !player.onGround)
					{
						f = .1F;
						player.motionX = (double)(-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
						player.motionZ = (double)(MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
					}

					prevY4 = prevY3;
					prevY3 = prevY2;
					prevY2 = prevY1;
					prevY1 = player.posY;


					BlockPos pos1 = new BlockPos(player.posX, player.posY, player.posZ + 1);
					BlockPos pos2 = new BlockPos(player.posX, player.posY, player.posZ - 1);
					BlockPos pos3 = new BlockPos(player.posX + 1, player.posY, player.posZ);
					BlockPos pos4 = new BlockPos(player.posX - 1, player.posY, player.posZ);

					boolean xMovement = world.getBlockState(pos3).getBlock() != Blocks.AIR || world.getBlockState(pos4).getBlock() != Blocks.AIR;


					if(xMovement && player.posX == prevX1 && player.posX == prevX2 && player.posX == prevX3 && player.posX == prevX4 && !player.onGround)
					{
						f = .2F;
						player.motionZ = (double)(MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
						player.motionY = (double)(-MathHelper.sin((pitch) / 180.0F * (float)Math.PI) * f);
					}

					prevX4 = prevX3;
					prevX3 = prevX2;
					prevX2 = prevX1;
					prevX1 = player.posX;

					boolean zMovement = world.getBlockState(pos1).getBlock() != Blocks.AIR || world.getBlockState(pos2).getBlock() != Blocks.AIR;


					if(zMovement && player.posZ == prevZ1 && player.posZ == prevZ2 && player.posZ == prevZ3 && player.posZ == prevZ4 && !player.onGround)
					{
						f = .2F;
						player.motionX = (double)(-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
						player.motionY = (double)(-MathHelper.sin((pitch) / 180.0F * (float)Math.PI) * f);
					}

					prevZ4 = prevZ3;
					prevZ3 = prevZ2;
					prevZ2 = prevZ1;
					prevZ1 = player.posZ;

				}

				//If it is an iron trapdoor, remove it
				if(blockType == Blocks.IRON_TRAPDOOR)
				{
					IBlockState state = Blocks.AIR.getDefaultState();
					world.setBlockState(blockPosition.getBlockPos(), state);
					world.destroyBlock(blockPosition.getBlockPos(), true);
					spawnItemStack(world, blockPosition.getBlockPos().getX(), blockPosition.getBlockPos().getY(), blockPosition.getBlockPos().getZ(), new ItemStack(Blocks.IRON_TRAPDOOR));
				}
			}
		}

		nbt.setBoolean("active", active);
		nbt.setInteger("soundCounter", soundCounter);
		nbt.setInteger("empCooldown", empCooldown);

		nbt.setDouble("prevY1", prevY1);
		nbt.setDouble("prevY2", prevY2);
		nbt.setDouble("prevY3", prevY3);
		nbt.setDouble("prevY4", prevY4);

		nbt.setDouble("prevX1", prevX1);
		nbt.setDouble("prevX2", prevX2);
		nbt.setDouble("prevX3", prevX3);
		nbt.setDouble("prevX4", prevX4);

		nbt.setDouble("prevZ1", prevZ1);
		nbt.setDouble("prevZ2", prevZ2);
		nbt.setDouble("prevZ3", prevZ3);
		nbt.setDouble("prevZ4", prevZ4);

		stack.setTagCompound(nbt);

		super.onUpdate(stack, world, entityIn, itemSlot, isSelected);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer entityLiving, EnumHand handIn)
	{
		ItemStack stack = entityLiving.getHeldItem(handIn);

		//Set the NBT to a new NBT if it is null
		NBTTagCompound nbt = new NBTTagCompound();

		if(stack.getTagCompound() != null)
			nbt = stack.getTagCompound();

		entityLiving.setActiveHand(handIn);
		boolean active = nbt.getBoolean("active");

		if (entityLiving instanceof EntityPlayer)
		{
			nbt.setBoolean("active", !active);
			stack.setTagCompound(nbt);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
		}

		return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
	}

	public static void spawnItemStack(World worldIn, double x, double y, double z, ItemStack stack)
	{
		Random RANDOM = new Random();
		
		float f = RANDOM.nextFloat() * 0.8F + 0.1F;
		float f1 = RANDOM.nextFloat() * 0.8F + 0.1F;
		float f2 = RANDOM.nextFloat() * 0.8F + 0.1F;

		while (!stack.isEmpty())
		{
			EntityItem entityitem = new EntityItem(worldIn, x + (double)f, y + (double)f1, z + (double)f2, stack.splitStack(RANDOM.nextInt(21) + 10));
			float f3 = 0.05F;
			entityitem.motionX = RANDOM.nextGaussian() * 0.05000000074505806D;
			entityitem.motionY = RANDOM.nextGaussian() * 0.05000000074505806D + 0.20000000298023224D;
			entityitem.motionZ = RANDOM.nextGaussian() * 0.05000000074505806D;
			worldIn.spawnEntity(entityitem);
		}
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.BOW;
	}

	@Override
	public int getItemEnchantability()
	{
		return 0;
	}

	@Override
	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}
}