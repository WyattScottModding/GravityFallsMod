package armor;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import entity.EntityBill;
import handlers.BlockHandler;
import handlers.KeyBindings;
import handlers.RegistryHandler;
import init.BlockInit;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import main.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityZombieHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MysticAmulet extends ItemArmor implements IHasModel
{
	public boolean active = false;

	public RayTraceResult blockPositionOld;

	public RayTraceResult blockPositionNew;

	public ArrayList<BlockHandler> blocks = new ArrayList<BlockHandler>();

	public IBlockState block;

	public Block blockType;

	public Entity entity;

	private boolean thrown = false;
	private int timer = -100;

	public boolean flying = false;


	public MysticAmulet(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) 
	{
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsarmor);

		this.addPropertyOverride(new ResourceLocation("on"), new IItemPropertyGetter()
		{
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
			{
				return (active && entityIn.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == ItemInit.MYSTIC_AMULET) ? 0.0F : 1.0F;
			}
		});

		ItemInit.ITEMS.add(this);
	}


	@Override
	public void onUpdate(ItemStack stack, World world, Entity entityIn, int itemSlot, boolean isSelected) 
	{
		if(entityIn instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entityIn;

			if(player.getArmorInventoryList().toString().contains("mysticamulet"))
			{
				if(entity != null)
				{
					active = true;
				}
				else if(player.capabilities.isFlying)
				{
					if(!player.capabilities.isCreativeMode)
					{
						active = true;
					}
				}
				else
					active = false;
			}

			/*
			if(!player.capabilities.isCreativeMode)
			{
				if(player.getArmorInventoryList().toString().contains("mysticamulet") && testGround(player, world))
				{
					player.capabilities.allowFlying = true;
				}
				else if(player.getArmorInventoryList().toString().contains("mysticamulet") && player.capabilities.isFlying)
				{
					player.capabilities.allowFlying = false;
					player.motionY = -0.2;
				}
				if(player.capabilities.isFlying && player.getArmorInventoryList().toString().contains("mysticamulet"))
				{
					player.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 10, 1));
					player.capabilities.disableDamage = false;
				}
			}
			 */

			if(thrown)
			{
				timer++;
				if(timer == 30)
				{
					timer = 0;
					thrown = false;

					blockPositionOld = null;
					blockPositionNew = null;
					blockType = null;
					block = null;
				}
			}

			if(player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == ItemInit.MYSTIC_AMULET && !thrown && !world.isRemote)
			{
				if(entity == null && KeyBindings.ITEM1.isDown() && getMouseOver(player, world))
				{

				}
				else if(KeyBindings.ITEM1.isDown() && entity != null && !(entity instanceof EntityBill))
				{
					if(entity instanceof EntityLivingBase)
					{
						EntityLivingBase entityLiving = (EntityLivingBase) entity;
						entityLiving.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 10, 1));
					}

					RayTraceResult blockPosition = player.rayTrace(5, 1.0F);

					BlockPos blockPos = blockPosition.getBlockPos();

					//entity.moveToBlockPosAndAngles(blockPos, player.rotationYaw * -1, player.rotationPitch * -1);

					if((int)entity.posX > blockPos.getX())
						entity.motionX = -1;
					else if((int)entity.posX < blockPos.getX())
						entity.motionX = 1;
					else if((int)entity.posX == blockPos.getX())
						entity.motionX = 0;

					if((int)entity.posY > blockPos.getY())
						entity.motionY = -1;
					else if((int)entity.posY < blockPos.getY())
						entity.motionY = 1;
					else if((int)entity.posY == blockPos.getY())
						entity.motionY = 0;

					if((int)entity.posZ > blockPos.getZ())
						entity.motionZ = -1;
					else if((int)entity.posZ < blockPos.getZ())
						entity.motionZ = 1;
					else if((int)entity.posZ == blockPos.getZ())
						entity.motionZ = 0;

					entity.rotationYaw = -player.rotationYaw;
					entity.rotationPitch = -player.rotationPitch;

				}

				if(!KeyBindings.ITEM1.isDown())
				{
					entity = null;
				}
				if(entity != null && KeyBindings.ITEM2.isDown())
				{
					RayTraceResult blockPosition = player.rayTrace(1, 1.0F);

					double f = 5.0;

					float yaw = player.rotationYaw;
					float pitch = player.rotationPitch;

					entity.motionX = (double)(-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
					entity.motionY = (double)(-MathHelper.sin((pitch) / 180.0F * (float)Math.PI) * f);
					entity.motionZ = (double)(MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);

					if(entity instanceof EntityLivingBase)
					{
						EntityLivingBase entityLiving = (EntityLivingBase) entity;
						if(entityLiving instanceof EntitySkeleton || entityLiving instanceof EntityZombie || entityLiving instanceof EntityZombieHorse)
							entityLiving.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 2, 0));
						else
							entityLiving.addPotionEffect(new PotionEffect(MobEffects.INSTANT_DAMAGE, 2, 0));					
					}
					entity = null;
					thrown = true;
				}
			}
		}


		super.onUpdate(stack, world, entityIn, itemSlot, isSelected);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) 
	{		
		if(active)
			return Reference.MODID + ":textures/models/armor/amuletglowing_layer_1.png";
		else
			return Reference.MODID + ":textures/models/armor/amulet_layer_1.png";
	}



	public boolean getMouseOver(EntityPlayer player, World world)
	{
		Vec3d lookVec = player.getLookVec();

		BlockPos pos = player.getPosition();

		float yaw = player.rotationYaw;
		float pitch = player.rotationPitch;

		List<Entity> list = null;

		for(int f = 1; f <= 40; f++)
		{
			double x = (double)(-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
			double y = (double)(-MathHelper.sin((pitch) / 180.0F * (float)Math.PI) * f);
			double z = (double)(MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);


			AxisAlignedBB entityPos = new AxisAlignedBB(pos.getX() + x, pos.getY() + y, pos.getZ() + z, pos.getX() + x + 1, pos.getY() + y + 1, pos.getZ() + z + 1);

			list = world.getEntitiesInAABBexcluding(player, entityPos, Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>()
			{
				public boolean apply(@Nullable Entity p_apply_1_)
				{
					return p_apply_1_ != null && p_apply_1_.canBeCollidedWith();
				}
			}));

			for(int j = 0; j < list.size(); ++j)
			{
				Entity entity = list.get(j);

				this.entity = entity;

				RayTraceResult blockPosition = player.rayTrace(5, 1.0F);

				entity.moveToBlockPosAndAngles(blockPosition.getBlockPos(), player.rotationYaw * -1, player.rotationPitch * -1);
			}

		}
		if(list != null)
			return list.size() != 0;
		else
			return false;
	}

	public boolean testGround(EntityPlayer player, World world)
	{
		boolean ground = false;

		BlockPos pos1 = player.getPosition();
		BlockPos pos2 = pos1.down();
		BlockPos pos3 = pos2.down();
		BlockPos pos4 = pos3.down();
		BlockPos pos5 = pos4.down();
		BlockPos pos6 = pos5.down();
		BlockPos pos7 = pos6.down();

		if(world.getBlockState(pos7).getBlock() != Blocks.AIR)
			ground = true;
		else if(world.getBlockState(pos6).getBlock() != Blocks.AIR)
			ground = true;
		else if(world.getBlockState(pos5).getBlock() != Blocks.AIR)
			ground = true;
		else if(world.getBlockState(pos4).getBlock() != Blocks.AIR)
			ground = true;
		else if(world.getBlockState(pos3).getBlock() != Blocks.AIR)
			ground = true;
		else if(world.getBlockState(pos2).getBlock() != Blocks.AIR)
			ground = true;
		else if(world.getBlockState(pos1).getBlock() != Blocks.AIR)
			ground = true;

		return ground;
	}


	@Override
	public void registerModels() 
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}

}