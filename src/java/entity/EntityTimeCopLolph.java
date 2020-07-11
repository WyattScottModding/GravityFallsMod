package entity;

import javax.annotation.Nullable;

import com.google.common.base.Function;
import com.google.common.base.Predicate;

import handlers.LootTableHandler;
import init.ItemInit;
import items.LaserArmCannon;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityTimeCopLolph extends EntityMob
{
	public EntityTimeCopLolph(World par1World)
	{
		super(par1World);
		this.setSize(0.9F, 3.0F);
		this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ItemInit.LASER_ARM_CANNON));
		this.experienceValue = 10;
	}

	@Override
	protected void initEntityAI()
	{
        this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));

		this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
	}

	@Override
	protected void applyEntityAttributes() 
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60.0D);

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);

		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);;
	}

	@Override
	public boolean getCanSpawnHere()
	{
		return true;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn)
	{
		return SoundEvents.ENTITY_PLAYER_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() 
	{
		return SoundEvents.ENTITY_PLAYER_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn)
	{
		this.playSound(SoundEvents.ENTITY_POLAR_BEAR_STEP, 0.15F, 1.0F);
	}

	@Override
	protected ResourceLocation getLootTable()
	{
		return LootTableHandler.TIMECOP;
	}

	@Override
	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
	{
		this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ItemInit.LASER_ARM_CANNON));
	}

	@Override
	protected void updateAITasks()
	{
		if((int) (Math.random() * 800) == 0)
			this.teleportRandomly();

		ItemStack stack = new ItemStack(ItemInit.LASER_ARM_CANNON);
		Item item = stack.getItem();
	}

	protected boolean teleportRandomly()
	{
		double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * 32.0D;
		double d1 = this.posY + (double)(this.rand.nextInt(64) - 32);
		double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * 32.0D;
		return this.teleportTo(d0, d1, d2);
	}

	private boolean teleportTo(double x, double y, double z)
	{
		net.minecraftforge.event.entity.living.EnderTeleportEvent event = new net.minecraftforge.event.entity.living.EnderTeleportEvent(this, x, y, z, 0);
		if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return false;
		boolean flag = this.attemptTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ());

		if (flag)
		{
			this.world.playSound((EntityPlayer)null, this.prevPosX, this.prevPosY, this.prevPosZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, this.getSoundCategory(), 1.0F, 1.0F);
			this.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
		}

		return flag;
	}

	static class AIFindPlayer extends EntityAINearestAttackableTarget<EntityPlayer>
	{
		private final EntityTimeCopLolph timeCop;
		/** The player */
		private EntityPlayer player;
		private int aggroTime;
		private int teleportTime;

		public AIFindPlayer(EntityTimeCopLolph timecop)
		{
			super(timecop, EntityPlayer.class, false);
			this.timeCop = timecop;
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		public boolean shouldExecute()
		{
			double d0 = this.getTargetDistance();
			this.player = this.timeCop.world.getNearestAttackablePlayer(this.timeCop.posX, this.timeCop.posY, this.timeCop.posZ, d0, d0, (Function)null, new Predicate<EntityPlayer>()
			{
				public boolean apply(@Nullable EntityPlayer p_apply_1_)
				{
					return false;
				}
			});
			return this.player != null;
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		public void startExecuting()
		{
			this.aggroTime = 5;
			this.teleportTime = 0;
		}

		/**
		 * Reset the task's internal state. Called when this task is interrupted by another one
		 */
		public void resetTask()
		{
			this.player = null;
			super.resetTask();
		}

		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		public boolean shouldContinueExecuting()
		{
			if (this.player != null)
			{
				return false;
			}
			else
			{
				return this.targetEntity != null && ((EntityPlayer)this.targetEntity).isEntityAlive() ? true : super.shouldContinueExecuting();
			}
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		public void updateTask()
		{
			if (this.player != null)
			{
				if (--this.aggroTime <= 0)
				{
					this.targetEntity = this.player;
					this.player = null;
					super.startExecuting();
				}
			}
			else
			{
				if (this.targetEntity != null)
				{
					this.teleportTime = 0;
				}

				super.updateTask();
			}
		}
	}
}