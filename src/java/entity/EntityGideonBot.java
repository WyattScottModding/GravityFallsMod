package entity;

import java.util.jar.Attributes;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import init.ItemInit;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.passive.HorseArmorType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityGideonBot extends EntityMob
{
	public EntityPlayer player;
	private static final DataParameter<Float> DATA_HEALTH_ID = EntityDataManager.<Float>createKey(EntityGideonBot.class, DataSerializers.FLOAT);

	World world = null;

	public EntityGideonBot(World worldIn)
	{
		super(worldIn);
		this.setSize(10.0F, 30.0F);
		this.stepHeight = 5;
		this.world = worldIn;
	}

	public EntityGideonBot(World worldIn, double x, double y, double z)
	{
		super(worldIn);
		this.setPosition(x, y, z);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
		this.setSize(10.0F, 30.0F);
		this.stepHeight = 5;
		this.world = worldIn;
	}

	protected void entityInit()
	{
		super.entityInit();
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.8D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(500.0D);
	}


	protected Item getDropItem()
	{
		return ItemInit.GOLF_CART;
	}

	@Override
	public boolean getCanSpawnHere()
	{
		return true;
	}


	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand)
	{
		ItemStack stack = player.getHeldItemMainhand();

		this.player = player;

		this.height = 10F;
		this.width = 10F;


		if(stack != null && stack.getItem() == Items.SPAWN_EGG)
		{
			return super.processInteract(player, hand);
		}
		else
		{
			if(!this.isBeingRidden())
			{
				if(stack != null && stack.interactWithEntity(player, this, hand))
				{
					return true;
				}
				else
				{
					this.mountToCart(player);
					//	Minecraft.getMinecraft().gameSettings.thirdPersonView = 1;
					return true;
				}
			}
			else
			{
				//	Minecraft.getMinecraft().gameSettings.thirdPersonView = 0;
				return super.processInteract(player, hand);
			}
		}
	}

	private void mountToCart(EntityPlayer player)
	{
		player.rotationYaw = this.rotationYaw;
		player.rotationPitch = this.rotationPitch;
		player.startRiding(this);

	}


	public boolean canBeSteered()
	{
		Entity entity = this.getControllingPassenger();
		return entity instanceof EntityLivingBase;	
	}

	@Override
	public void travel(float strafe, float vertical, float forward) 
	{
		if(this.isBeingRidden() && this.canBeSteered())
		{
			EntityLivingBase entitylivingbase = (EntityLivingBase)this.getControllingPassenger();

			this.prevRotationYaw = this.rotationYaw;
			this.rotationPitch = entitylivingbase.rotationPitch;
			this.prevRotationPitch = this.rotationPitch * 0.5f;
			this.setRotation(this.rotationYaw, this.rotationPitch);
			this.renderYawOffset = this.rotationYaw;
			this.rotationYawHead = this.renderYawOffset;
			strafe = 0;
			forward = entitylivingbase.moveForward;

			if(forward <= 0.0f)
			{
				forward *= 0.25f;
			}

			this.jumpMovementFactor = 2.0F;

			if(this.canPassengerSteer())
			{
				this.setAIMoveSpeed((float)this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
				super.travel(strafe, vertical, forward);
			}
			else if(entitylivingbase instanceof EntityPlayer)
			{
				this.motionX = 0.0d;
				this.motionY = 0.0d;
				this.motionZ = 0.0d;
			}

			this.prevLimbSwingAmount = this.limbSwingAmount;
			double d1 = this.posX - this.prevPosX;
			double d0 = this.posZ - this.prevPosZ;
			float f2 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0f;

			if(f2 > 1.0f)
			{
				f2 = 1.0f;
			}

			this.limbSwingAmount += (f2 - this.limbSwingAmount) * 0.4f;
			this.limbSwing += this.limbSwingAmount;
		}
		else
		{
			this.jumpMovementFactor = 0.02f;
			super.travel(strafe, vertical, forward);
		}
	}

	@Nullable
	public Entity getControllingPassenger()
	{
		return this.getPassengers().isEmpty() ? null : (Entity)this.getPassengers().get(0);
	}


	@Override
	protected boolean isMovementBlocked()
	{
		return false;
	}

	@Override
	public boolean canRiderInteract() 
	{
		return true;
	}

	@Override
	public boolean shouldRiderSit() 
	{
		return false;
	}

	@Override
	protected boolean canBeRidden(Entity entityIn) 
	{
		return true;
	}

	@Override
	public boolean isAIDisabled()
	{
		return false;
	}

	@Override
	protected boolean canDespawn()
	{
		return false;
	}

	@Override
	public boolean canBePushed() 
	{
		return false;
	}

	@Override
	public boolean canBeCollidedWith()
	{
		return !this.isDead;
	}


	@Override
	public void onUpdate() 
	{
		//When the player dismounts
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && player != null)
		{
			dismountEntity(player);
			player.dismountRidingEntity();
			player.attemptTeleport(player.posX - 1, player.posY, player.posZ - 1);
			this.attemptTeleport(this.posX, this.posY - 1, this.posZ);
			player.eyeHeight = 1.8F;
			this.setInvisible(false);
			player = null;
		}
		//While the player is riding
		if(player != null)
		{
			player.setHealth(this.getHealth());
			player.fallDistance = 0.0F;
			player.eyeHeight = 10;
			this.setInvisible(true);
			this.rotationYaw = player.rotationYaw;
			this.rotationPitch = player.rotationPitch;

		}
		this.fallDistance = 0.0F;

		if(getAttackingEntity() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) getAttackingEntity();

			if(!player.isCreative())
				this.dropItem(ItemInit.GIDEONBOT, 1);

			this.setDead();
		}

		super.onUpdate();
	}

	public void dismountEntity(Entity entityIn)
	{

		double d1 = entityIn.posX;
		double d13 = entityIn.getEntityBoundingBox().minY + (double)entityIn.height;
		double d14 = entityIn.posZ;
		EnumFacing enumfacing1 = entityIn.getAdjustedHorizontalFacing();

		if (enumfacing1 != null)
		{
			EnumFacing enumfacing = enumfacing1.rotateY();
			int[][] aint1 = new int[][] {{0, 1}, {0, -1}, { -1, 1}, { -1, -1}, {1, 1}, {1, -1}, { -1, 0}, {1, 0}, {0, 1}};
			double d5 = Math.floor(this.posX) + 0.5D;
			double d6 = Math.floor(this.posZ) + 0.5D;
			double d7 = this.getEntityBoundingBox().maxX - this.getEntityBoundingBox().minX;
			double d8 = this.getEntityBoundingBox().maxZ - this.getEntityBoundingBox().minZ;
			AxisAlignedBB axisalignedbb = new AxisAlignedBB(d5 - d7 / 2.0D, entityIn.getEntityBoundingBox().minY, d6 - d8 / 2.0D, d5 + d7 / 2.0D, Math.floor(entityIn.getEntityBoundingBox().minY) + (double)this.height, d6 + d8 / 2.0D);

			for (int[] aint : aint1)
			{
				double d9 = (double)(enumfacing1.getFrontOffsetX() * aint[0] + enumfacing.getFrontOffsetX() * aint[1]);
				double d10 = (double)(enumfacing1.getFrontOffsetZ() * aint[0] + enumfacing.getFrontOffsetZ() * aint[1]);
				double d11 = d5 + d9;
				double d12 = d6 + d10;
				AxisAlignedBB axisalignedbb1 = axisalignedbb.offset(d9, 0.0D, d10);

				if (!this.world.collidesWithAnyBlock(axisalignedbb1))
				{
					if (this.world.getBlockState(new BlockPos(d11, this.posY, d12)).isSideSolid(world, new BlockPos(d11, this.posY, d12), EnumFacing.UP))
					{
						this.setPositionAndUpdate(d11, this.posY + 1.0D, d12);
						return;
					}

					BlockPos blockpos = new BlockPos(d11, this.posY - 1.0D, d12);

					if (this.world.getBlockState(blockpos).isSideSolid(world, blockpos, EnumFacing.UP) || this.world.getBlockState(blockpos).getMaterial() == Material.WATER)
					{
						d1 = d11;
						d13 = this.posY + 1.0D;
						d14 = d12;
					}
				}
				else if (!this.world.collidesWithAnyBlock(axisalignedbb1.offset(0.0D, 1.0D, 0.0D)) && this.world.getBlockState(new BlockPos(d11, this.posY + 1.0D, d12)).isSideSolid(world, new BlockPos(d11, this.posY + 1.0D, d12), EnumFacing.UP))
				{
					d1 = d11;
					d13 = this.posY + 2.0D;
					d14 = d12;
				}
			}
		}

		this.setPositionAndUpdate(d1, d13, d14);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);

	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);

	}

	@Override
	public void enablePersistence() 
	{
		super.enablePersistence();
	}

	@Override
	public void notifyDataManagerChange(DataParameter<?> key)
	{
		super.notifyDataManagerChange(key);
	}


}