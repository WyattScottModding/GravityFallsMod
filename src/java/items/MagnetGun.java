package items;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;

import entity.EntityForget;
import entity.EntitySecurityDroid;
import handlers.KeyBindings;
import handlers.SoundsHandler;
import init.BlockInit;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStainedGlassPane;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityZombieHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntityBeacon;
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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MagnetGun extends ItemBow implements IHasModel
{

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

	private final List<TileEntityBeacon.BeamSegment> beamSegments = Lists.<TileEntityBeacon.BeamSegment>newArrayList();

	int soundCounter = 0;

	int attackCooldown = 0;

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
				if(entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack)
					return !active ? 0.0F : 1.0F;
				else
					return 0.0F;
			}
		});

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
				//	if(attackCooldown == 100)
				//	{
				getMouseOver(player, world);
				attackCooldown = 0;
				//	}
			}

			RayTraceResult blockPosition = player.rayTrace(500, 1.0F);

			IBlockState block = world.getBlockState(blockPosition.getBlockPos());

			Block blockType = block.getBlock();


			if(active)
			{
				//Sound Effect
				if(soundCounter == 4)
					world.playSound(player, player.posX, player.posY, player.posZ, SoundsHandler.ITEM_MAGNETGUN, SoundCategory.PLAYERS, 1.0F, 1.0F);



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

					System.out.println("X: " + player.motionX + "Y: " + player.motionY + "Z: " + player.motionZ);

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
		super.onUpdate(stack, world, entityIn, itemSlot, isSelected);
	}

	private void drawLine(double xPos, double yPos, double zPos) 
	{
	//	GlStateManager.depthFunc(519);
	//	GlStateManager.enableBlend();
	//	GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
	//	GlStateManager.disableTexture2D();
	//	GlStateManager.depthMask(false);
		// some functions to set up the vertices
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
		bufferbuilder.pos(xPos, yPos, zPos).color(1, 1, 1, 0.0F).endVertex();
		bufferbuilder.pos(xPos + 2, yPos, zPos + 2).color(1, 1, 1, 0.0F).endVertex();
		tessellator.draw();
		// a long chain of vertices
	//	GlStateManager.depthMask(true);
	//	GlStateManager.enableTexture2D();
	//	GlStateManager.disableBlend();
		//GlStateManager.depthFunc(515);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer entityLiving, EnumHand handIn)
	{
		ItemStack itemstack = entityLiving.getHeldItem(handIn);

		entityLiving.setActiveHand(handIn);

		if (entityLiving instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entityLiving;
			if (!world.isRemote)
			{
				ActionResult<ItemStack> ret = net.minecraftforge.event.ForgeEventFactory.onArrowNock(itemstack, world, entityLiving, handIn, false);
				if (ret != null) return ret;

				active = true;

			}

			//     worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
		}

		return (ActionResult<ItemStack>) new ActionResult(EnumActionResult.FAIL, itemstack);

		//return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, new ItemStack(ItemInit.MAGNET_GUN));
	}

	public void getMouseOver(EntityPlayer player, World world)
	{

		Vec3d lookVec = player.getLookVec();

		BlockPos pos = player.getPosition();

		float yaw = player.rotationYaw;
		float pitch = player.rotationPitch;

		for(int f = 1; f <= 80; f++)
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