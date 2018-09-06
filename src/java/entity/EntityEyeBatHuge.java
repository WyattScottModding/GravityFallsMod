package entity;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import handlers.LootTableHandler;
import handlers.SoundsHandler;
import init.BlockInit;
import init.ItemInit;
import init.PotionInit;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIBeg;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityEyeBatHuge extends EntityGhast
{
	public static EntityPlayer frozenPlayer = null;
	public static boolean frozen = false;
	public static int frozenCounter = 0;


	private static final DataParameter<Float> DATA_HEALTH_ID = EntityDataManager.<Float>createKey(EntityWolf.class, DataSerializers.FLOAT);

	private BlockPos spawnPosition;

	
	public EntityEyeBatHuge(World par1World)
	{
		super(par1World);
		this.setSize(8.0F, 5.0F);
	}


	@Override
	protected void applyEntityAttributes() 
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);

		//	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1D);

		//this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(15.0D);
	}

	@Override
	protected void initEntityAI() 
	{
		this.tasks.addTask(7, new EntityEyeBatHuge.AIFreezeBeam(this));
		this.tasks.addTask(5, new EntityEyeBatHuge.AIRandomFly(this));
		this.tasks.addTask(7, new EntityEyeBatHuge.AILookAround(this));
		this.targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));

	}

	@Override
	protected void updateAITasks()
	{
		super.updateAITasks();
		BlockPos blockpos = new BlockPos(this);
		BlockPos blockpos1 = blockpos.up();

		if (this.spawnPosition != null && (!this.world.isAirBlock(this.spawnPosition) || this.spawnPosition.getY() < 1))
		{
			this.spawnPosition = null;
		}

		if (this.spawnPosition == null || this.rand.nextInt(30) == 0 || this.spawnPosition.distanceSq((double)((int)this.posX), (double)((int)this.posY), (double)((int)this.posZ)) < 4.0D)
		{
			this.spawnPosition = new BlockPos((int)this.posX + this.rand.nextInt(7) - this.rand.nextInt(7), (int)this.posY + this.rand.nextInt(6) - 2, (int)this.posZ + this.rand.nextInt(7) - this.rand.nextInt(7));
		}

		double d0 = (double)this.spawnPosition.getX() + 0.5D - this.posX;
		double d1 = (double)this.spawnPosition.getY() + 0.1D - this.posY;
		double d2 = (double)this.spawnPosition.getZ() + 0.5D - this.posZ;
		this.motionX += (Math.signum(d0) * 0.5D - this.motionX) * 0.10000000149011612D;
		this.motionY += (Math.signum(d1) * 0.699999988079071D - this.motionY) * 0.10000000149011612D;
		this.motionZ += (Math.signum(d2) * 0.5D - this.motionZ) * 0.10000000149011612D;
		float f = (float)(MathHelper.atan2(this.motionZ, this.motionX) * (180D / Math.PI)) - 90.0F;
		float f1 = MathHelper.wrapDegrees(f - this.rotationYaw);
		this.moveForward = 0.5F;
		this.rotationYaw += f1;
	}


	@Override
	public boolean getCanSpawnHere()
	{
		return true;
	}


	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn)
	{

		return null;
	}

	@Override
	protected SoundEvent getDeathSound() 
	{
		return null;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn)
	{
		this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 1.0F);
	}

	@Override
	public SoundEvent getAmbientSound() 
	{
		return null;
	}

	@Override
	protected ResourceLocation getLootTable()
	{
		return null;

	}

	@Override
	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
	{
		//this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
	}

	@Override
	public void onUpdate() 
	{
		unicornDefence();

		super.onUpdate();
	}

	public void unicornDefence()
	{
		Block blockNorth = world.getBlockState(this.getPosition().north()).getBlock();
		Block blockSouth = world.getBlockState(this.getPosition().south()).getBlock();
		Block blockEast = world.getBlockState(this.getPosition().east()).getBlock();
		Block blockWest = world.getBlockState(this.getPosition().west()).getBlock();

		Block hair = BlockInit.UNICORNHAIR;


		if(blockNorth == hair && blockSouth == hair && blockEast == hair && blockWest == hair)
		{
			this.motionX = 0.0;
			this.motionY = 0.0;
			this.motionZ = 0.0;
		}

		for(int i = 3; i >= -15; i--)
		{	
			Block blockNorth2 = world.getBlockState(this.getPosition().north().add(0, i, 0)).getBlock();
			Block blockSouth2 = world.getBlockState(this.getPosition().south().add(0, i, 0)).getBlock();
			Block blockEast2 = world.getBlockState(this.getPosition().east().add(0, i, 0)).getBlock();
			Block blockWest2 = world.getBlockState(this.getPosition().west().add(0, i, 0)).getBlock();

			if(blockNorth2 == hair)
			{
				this.motionZ = 3.0;
			}
			if(blockSouth2 == hair)
			{
				this.motionZ = -3.0;
			}
			if(blockWest2 == hair)
			{
				this.motionX = 3.0;
			}
			if(blockEast2 == hair)
			{
				this.motionX = -3.0;
			}
		}
	}



	static class AIFreezeBeam extends EntityAIBase
	{
		private final EntityEyeBatHuge parentEntity;
		public int attackTimer;

		public AIFreezeBeam(EntityEyeBatHuge eyebat)	
		{
			this.parentEntity = eyebat;
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		public boolean shouldExecute()
		{
			return this.parentEntity.getAttackTarget() != null;
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		public void startExecuting()
		{
			this.attackTimer = 0;
		}

		/**
		 * Reset the task's internal state. Called when this task is interrupted by another one
		 */
		public void resetTask()
		{
			this.parentEntity.setAttacking(false);
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		public void updateTask()
		{
			EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();

			if(entitylivingbase instanceof EntityPlayer)
			{
				frozenPlayer = (EntityPlayer) entitylivingbase;
			}
			double d0 = 64.0D;

			if (entitylivingbase.getDistanceSq(this.parentEntity) < 4096.0D && this.parentEntity.canEntityBeSeen(entitylivingbase))
			{
				World world = this.parentEntity.world;
				++this.attackTimer;

				if (this.attackTimer == 10)
				{
					world.playEvent((EntityPlayer)null, 1015, new BlockPos(this.parentEntity), 0);
				}

				if (this.attackTimer == 20)
				{
					double d1 = 4.0D;
					Vec3d vec3d = this.parentEntity.getLook(1.0F);
					double d2 = entitylivingbase.posX - (this.parentEntity.posX + vec3d.x * 4.0D);
					double d3 = entitylivingbase.getEntityBoundingBox().minY + (double)(entitylivingbase.height / 2.0F) - (0.5D + this.parentEntity.posY + (double)(this.parentEntity.height / 2.0F));
					double d4 = entitylivingbase.posZ - (this.parentEntity.posZ + vec3d.z * 4.0D);
					world.playEvent((EntityPlayer)null, 1016, new BlockPos(this.parentEntity), 0);

					entitylivingbase.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 80, 50));
					entitylivingbase.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 80, 50));
					frozen = true;
					frozenCounter = 80;
					

					/*
					EntityFreezeBeam entityfreezebeam = new EntityFreezeBeam(world, this.parentEntity, d2, d3, d4);


					entityfreezebeam.posX = this.parentEntity.posX + vec3d.x * 4.0D;
					entityfreezebeam.posY = this.parentEntity.posY + (double)(this.parentEntity.height / 2.0F) + 0.5D;
					entityfreezebeam.posZ = this.parentEntity.posZ + vec3d.z * 4.0D;
					world.spawnEntity(entityfreezebeam);
					 */
					this.attackTimer = -40;
				}
			}
			else if (this.attackTimer > 0)
			{
				--this.attackTimer;
			}

			this.parentEntity.setAttacking(this.attackTimer > 10);
		}
	}

	static class AIRandomFly extends EntityAIBase
	{
		private final EntityEyeBatHuge parentEntity;

		public AIRandomFly(EntityEyeBatHuge eyebat)
		{
			this.parentEntity = eyebat;
			this.setMutexBits(1);
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		public boolean shouldExecute()
		{
			EntityMoveHelper entitymovehelper = this.parentEntity.getMoveHelper();

			if (!entitymovehelper.isUpdating())
			{
				return true;
			}
			else
			{
				double d0 = entitymovehelper.getX() - this.parentEntity.posX;
				double d1 = entitymovehelper.getY() - this.parentEntity.posY;
				double d2 = entitymovehelper.getZ() - this.parentEntity.posZ;
				double d3 = d0 * d0 + d1 * d1 + d2 * d2;
				return d3 < 1.0D || d3 > 3600.0D;
			}
		}

		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		public boolean shouldContinueExecuting()
		{
			return false;
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		public void startExecuting()
		{
			Random random = this.parentEntity.getRNG();
			double d0 = this.parentEntity.posX + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
			double d1 = this.parentEntity.posY + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
			double d2 = this.parentEntity.posZ + (double)((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
			this.parentEntity.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
		}
	}

	static class EyeBatHugeMoveHelper extends EntityMoveHelper
	{
		private final EntityEyeBatHuge parentEntity;
		private int courseChangeCooldown;

		public EyeBatHugeMoveHelper(EntityEyeBatHuge eyebat)
		{
			super(eyebat);
			this.parentEntity = eyebat;
		}

		public void onUpdateMoveHelper()
		{
			if (this.action == EntityMoveHelper.Action.MOVE_TO)
			{
				double d0 = this.posX - this.parentEntity.posX;
				double d1 = this.posY - this.parentEntity.posY;
				double d2 = this.posZ - this.parentEntity.posZ;
				double d3 = d0 * d0 + d1 * d1 + d2 * d2;

				if (this.courseChangeCooldown-- <= 0)
				{
					this.courseChangeCooldown += this.parentEntity.getRNG().nextInt(5) + 2;
					d3 = (double)MathHelper.sqrt(d3);

					if (this.isNotColliding(this.posX, this.posY, this.posZ, d3))
					{
						this.parentEntity.motionX += d0 / d3 * 0.1D;
						this.parentEntity.motionY += d1 / d3 * 0.1D;
						this.parentEntity.motionZ += d2 / d3 * 0.1D;
					}
					else
					{
						this.action = EntityMoveHelper.Action.WAIT;
					}
				}
			}
		}

		/**
		 * Checks if entity bounding box is not colliding with terrain
		 */
		private boolean isNotColliding(double x, double y, double z, double p_179926_7_)
		{
			double d0 = (x - this.parentEntity.posX) / p_179926_7_;
			double d1 = (y - this.parentEntity.posY) / p_179926_7_;
			double d2 = (z - this.parentEntity.posZ) / p_179926_7_;
			AxisAlignedBB axisalignedbb = this.parentEntity.getEntityBoundingBox();

			for (int i = 1; (double)i < p_179926_7_; ++i)
			{
				axisalignedbb = axisalignedbb.offset(d0, d1, d2);

				if (!this.parentEntity.world.getCollisionBoxes(this.parentEntity, axisalignedbb).isEmpty())
				{
					return false;
				}
			}

			return true;
		}
	}

	static class AILookAround extends EntityAIBase
	{
		private final EntityEyeBatHuge parentEntity;

		public AILookAround(EntityEyeBatHuge eyebat)
		{
			this.parentEntity = eyebat;
			this.setMutexBits(2);
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		public boolean shouldExecute()
		{
			return true;
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		public void updateTask()
		{
			if (this.parentEntity.getAttackTarget() == null)
			{
				this.parentEntity.rotationYaw = -((float)MathHelper.atan2(this.parentEntity.motionX, this.parentEntity.motionZ)) * (180F / (float)Math.PI);
				this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
			}
			else
			{
				EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();
				double d0 = 64.0D;

				if (entitylivingbase.getDistanceSq(this.parentEntity) < 4096.0D)
				{
					double d1 = entitylivingbase.posX - this.parentEntity.posX;
					double d2 = entitylivingbase.posZ - this.parentEntity.posZ;
					this.parentEntity.rotationYaw = -((float)MathHelper.atan2(d1, d2)) * (180F / (float)Math.PI);
					this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
				}
			}
		}
	}

	@Override
	public void onDeath(DamageSource cause) 
	{
		frozen = false;
		super.onDeath(cause);
	}

	@Override
	public void onEntityUpdate() 
	{

		if(frozenPlayer != null)
		{
			if(frozen)
			{

				frozenPlayer.motionY = -100;
				frozenPlayer.rotationPitch = 0.0F;
				frozenPlayer.rotationYaw = 0.0F;
				frozenCounter --;
			}
			if(frozenCounter == 0)
				frozen = false;
		}
		super.onEntityUpdate();
	}

	public static void freezeRender(EntityPlayer player)
	{		
		player.addPotionEffect(new PotionEffect(PotionInit.POTION_FREEZE, 200, 0));
	}

	public void setFrozenPlayer(EntityPlayer player)
	{
		this.frozenPlayer = player;
	}

}
