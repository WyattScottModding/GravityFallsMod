package items;

import java.util.List;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MagnetGun extends ItemBow implements IHasModel
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
		int attackCooldown = 0;

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
		if(nbt.hasKey("attackCooldown"))
			attackCooldown = nbt.getInteger("attackCooldown");

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

		System.out.println("Active = " + active);

		if(soundCounter == 4)
			soundCounter = 0;
		else
			soundCounter++;

		if(attackCooldown != 100)
			attackCooldown++;

		if (entityIn instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entityIn;

			if(KeyBindings.ITEM1.isDown())
			{
				getMouseOver(player, world);
				attackCooldown = 0;
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
					player.addItemStackToInventory(new ItemStack(Blocks.IRON_TRAPDOOR));
				}
			}
		}

		nbt.setBoolean("active", active);
		nbt.setInteger("soundCounter", soundCounter);
		nbt.setInteger("attackCooldown", attackCooldown);

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

		if (entityLiving instanceof EntityPlayer)
		{
			nbt.setBoolean("active", true);
		}

		stack.setTagCompound(nbt);

		return (ActionResult<ItemStack>) new ActionResult(EnumActionResult.SUCCESS, stack);
	}

	public void getMouseOver(EntityPlayer player, World world)
	{
		BlockPos pos = player.getPosition().add(0, player.eyeHeight, 0);

		float yaw = player.rotationYaw;
		float pitch = player.rotationPitch;

		for(int f = 1; f <= 80; f++)
		{
			double x = (double)(-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
			double y = (double)(-MathHelper.sin((pitch) / 180.0F * (float)Math.PI) * f);
			double z = (double)(MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);


			AxisAlignedBB entityPos = new AxisAlignedBB(pos.getX() + x - .5, pos.getY() + y - .5, pos.getZ() + z - .5, pos.getX() + x + .5, pos.getY() + y + .5, pos.getZ() + z + .5);

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
				if(entity instanceof EntityLivingBase)
				{
					EntityLivingBase mob = (EntityLivingBase) entity;

					if(mob instanceof EntitySecurityDroid)
						mob.addPotionEffect(new PotionEffect(MobEffects.INSTANT_DAMAGE, 100, 0));			
				}
			}
		}
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
	{
		//Set the NBT to a new NBT if it is null
		NBTTagCompound nbt = new NBTTagCompound();

		if(stack.getTagCompound() != null)
			nbt = stack.getTagCompound();

		if (entityLiving instanceof EntityPlayer)
		{
			EntityPlayer entityplayer = (EntityPlayer) entityLiving;
			nbt.setBoolean("active", false);
			//net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, entityplayer, 1, true);
		}
		stack.setTagCompound(nbt);
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