package items;

import javax.annotation.Nullable;

import entity.EntityForget;
import init.BlockInit;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MagnetGun extends ItemBow implements IHasModel{

	public boolean active = false;
	public double prevY1 = -5;
	public double prevY2 = -5;
	public double prevY3 = -5;
	public double prevY4 = -5;
	
	public double prevX1 = -5;
	public double prevX2 = -5;
	public double prevX3 = -5;
	public double prevX4 = -5;
	
	public double prevZ1 = -5;
	public double prevZ2 = -5;
	public double prevZ3 = -5;
	public double prevZ4 = -5;


	public MagnetGun(String name)
	{
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallstab);

		ItemInit.ITEMS.add(this);

		this.addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter()
		{
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
			{
				if (entityIn == null)
				{
					return 0.0F;
				}
				else
				{
					return entityIn.getActiveItemStack().getItem() != Items.BOW ? 0.0F : (float)(stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) / 20.0F;
				}
			}
		});
		this.addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter()
		{
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
			{
				return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
			}
		});
	}


	@Override
	public void onUpdate(ItemStack stack, World world, Entity entityIn, int itemSlot, boolean isSelected) 
	{
		if (entityIn instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entityIn;

			RayTraceResult blockPosition = player.rayTrace(100, 1.0F);

			IBlockState block = world.getBlockState(blockPosition.getBlockPos());

			Block blockType = block.getBlock();


			if(active)
			{
				//The gun is only attracted to objects that contain iron
				if(blockType == Blocks.IRON_BLOCK || blockType == Blocks.IRON_BARS || blockType == Blocks.IRON_ORE || blockType == Blocks.IRON_DOOR || blockType == Blocks.IRON_TRAPDOOR || blockType == Blocks.DETECTOR_RAIL || blockType == Blocks.RAIL || blockType == Blocks.ACTIVATOR_RAIL || blockType == Blocks.ANVIL || blockType == Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE || blockType == Blocks.PISTON || blockType == Blocks.STICKY_PISTON  || blockType == BlockInit.URANIUM_TANK || blockType == BlockInit.URANIUM_TANK_FILLED || blockType == BlockInit.URANIUM_TANK_HALFFILLED || blockType == Blocks.HOPPER || blockType == BlockInit.METAL_TREE)
				{
					float yaw = player.rotationYaw;
					float pitch = player.rotationPitch;


					//Amount of movement
					float f = 1.2F;

					player.motionX = (double)(-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
					player.motionY = (double)(-MathHelper.sin((pitch) / 180.0F * (float)Math.PI) * f);
					player.motionZ = (double)(MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
					
					player.isAirBorne = true;
					
					if(player.posY == this.prevY1 && player.posY == this.prevY2 && player.posY == this.prevY3 && player.posY == this.prevY4 && !player.onGround)
					{
						f = .1F;
						player.motionX = (double)(-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
						player.motionZ = (double)(MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
					}
					
					this.prevY4 = this.prevY3;
					this.prevY3 = this.prevY2;
					this.prevY2 = this.prevY1;
					this.prevY1 = player.posY;
					
					
					BlockPos pos1 = new BlockPos(player.posX, player.posY, player.posZ + 1);
					BlockPos pos2 = new BlockPos(player.posX, player.posY, player.posZ - 1);
					BlockPos pos3 = new BlockPos(player.posX + 1, player.posY, player.posZ);
					BlockPos pos4 = new BlockPos(player.posX - 1, player.posY, player.posZ);
					
					boolean xMovement = world.getBlockState(pos3).getBlock() != Blocks.AIR || world.getBlockState(pos4).getBlock() != Blocks.AIR;
					
					
					if(xMovement && player.posX == this.prevX1 && player.posX == this.prevX2 && player.posX == this.prevX3 && player.posX == this.prevX4 && !player.onGround)
					{
						f = .2F;
						player.motionZ = (double)(MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
						player.motionY = (double)(-MathHelper.sin((pitch) / 180.0F * (float)Math.PI) * f);
					}
					
					this.prevX4 = this.prevX3;
					this.prevX3 = this.prevX2;
					this.prevX2 = this.prevX1;
					this.prevX1 = player.posX;
					
					boolean zMovement = world.getBlockState(pos1).getBlock() != Blocks.AIR || world.getBlockState(pos2).getBlock() != Blocks.AIR;
					
					
					if(zMovement && player.posZ == this.prevZ1 && player.posZ == this.prevZ2 && player.posZ == this.prevZ3 && player.posZ == this.prevZ4 && !player.onGround)
					{
						f = .2F;
						player.motionX = (double)(-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
						player.motionY = (double)(-MathHelper.sin((pitch) / 180.0F * (float)Math.PI) * f);
					}
					
					this.prevZ4 = this.prevZ3;
					this.prevZ3 = this.prevZ2;
					this.prevZ2 = this.prevZ1;
					this.prevZ1 = player.posZ;
					
				}
			
			
			
			}
		}
		super.onUpdate(stack, world, entityIn, itemSlot, isSelected);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer entityLiving, EnumHand handIn)
	{
		ItemStack itemstack = entityLiving.getHeldItem(handIn);

		entityLiving.setActiveHand(handIn);

		if (entityLiving instanceof EntityPlayer)
		{

			if (!worldIn.isRemote)
			{
				ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, worldIn, entityLiving, handIn, false);
				if (ret != null) return ret;

				active = true;
			}

			//     worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
		}
		
		return (ActionResult<ItemStack>) new ActionResult(EnumActionResult.FAIL, itemstack);

		//return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, new ItemStack(ItemInit.MAGNET_GUN));
	}



	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft)
	{
		if (entityLiving instanceof EntityPlayer)
		{
			EntityPlayer entityplayer = (EntityPlayer) entityLiving;
			net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, worldIn, entityplayer, 1, true);
		}
		active = false;
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

	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}

}
