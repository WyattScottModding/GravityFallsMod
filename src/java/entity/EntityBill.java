package entity;

import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import handlers.LootTableHandler;
import handlers.SoundsHandler;
import init.BlockInit;
import init.ItemInit;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityBill extends EntityWeirdMob
{
	private int angerLevel;
	private UUID angerTargetUUID;
	private boolean isWatching;
	private int watchingTimer;

	private boolean billDying;
	private int timeUntilDeath;
	private DamageSource source;

	private static final DataParameter<Integer> TARGET = EntityDataManager.<Integer>createKey(EntityBill.class, DataSerializers.VARINT);

	public EntityBill(World par1World)
	{
		super(par1World);
		this.setSize(0.8F, 2.0F);
		this.isImmuneToFire = true;
		this.experienceValue = 1000;
		this.stepHeight = 3;
		this.isWatching = false;
		this.watchingTimer = 400;
		this.billDying = false;
		this.setTime(-1);
	}

	public EntityBill(World par1World, double x, double y, double z)
	{
		this(par1World);
		this.setPosition(x, y, z);
	}

	@Override
	protected void initEntityAI()
	{
		super.initEntityAI();
		this.applyEntityAI();
	}

	@Override
	protected void entityInit() {
		this.dataManager.register(TARGET, Integer.valueOf(0));
		super.entityInit();
	}

	protected void applyEntityAI() 
	{
		this.targetTasks.addTask(1, new EntityBill.AIHurtByAggressor(this));
		this.targetTasks.addTask(2, new EntityBill.AITargetAggressor(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] {}));
	}

	@Override
	protected void applyEntityAttributes() 
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(500.0D);

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6D);

		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7.0D);

		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(300.0D);
	}
	
	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	/**
	 * Returns the target entity ID if present, or -1 if not @param par1 The target offset, should be from 0-2
	 */
	public int getWatchedTargetId(int head)
	{
		return ((Integer)this.dataManager.get(TARGET)).intValue();
	}

	/**
	 * Updates the target entity ID
	 */
	public void updateWatchedTargetId(int targetOffset, int newId)
	{
		this.dataManager.set(TARGET, Integer.valueOf(newId));
	}

	@Override
	public void onDeath(DamageSource cause) {
		this.source = cause;
	}
	
	private void setTime(int time)
	{
		this.timeUntilDeath = time;
	}
	
	public int getTime()
	{
		return this.timeUntilDeath;
	}

	@Override
	public void onUpdate() {
		
		//When Bill Dies
		if(this.getHealth() == 1 || this.billDying || this.getTime() == 240)
		{
			this.setEntityInvulnerable(true);
			this.setWatching(false);
			this.billDying = true;

			this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.0D);
			this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0D);

			if(this.source != null)
				super.onDeath(this.source);
			if(this.getTime() == -1)
			{
				this.setTime(240);
				world.playSound(this.posX, this.posY, this.posZ, SoundsHandler.ENTITY_BILL_DEATH, SoundCategory.PLAYERS, 1.0F, 1.0F, true);
			}
			else if(this.getTime() > 0)
				this.setTime(this.getTime() - 1);
			else if(this.getTime() == 0)
			{
				if(!world.isRemote)
				{
					//Drops Statue when dead
					world.setBlockToAir(this.getPosition());
					world.setBlockToAir(this.getPosition().up());
					EntityBillStatue entitybillstatue = new EntityBillStatue(world, this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ());
					entitybillstatue.setLocationAndAngles(this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), 0.0F, 0.0F);
					world.spawnEntity(entitybillstatue);
					world.playSound((EntityPlayer)null, entitybillstatue.posX, entitybillstatue.posY, entitybillstatue.posZ, SoundEvents.BLOCK_STONE_PLACE, SoundCategory.BLOCKS, 0.75F, 0.8F);
					world.spawnEntity(new EntityItem(world, this.posX, this.posY, this.posZ, new ItemStack(ItemInit.DISC_MEETAGAIN)));
				}
				this.setTime(-2);
				this.setDead();
			}		
			super.onUpdate();
		}
		else {
			//Bill doesn't take fall damage
			this.fallDistance = 0;

			//Bill can regenerate health
			if(world.getWorldTime() % 40 == 0 && !this.billDying)
			{
				this.setHealth(this.getHealth() + 1);
			}

			//If Bill's health goes too low, he will go into watching mode
			if(this.getHealth() < 400 && !this.isWatching())
			{
				this.setWatching(true);
			}

			if(this.isWatching())
			{
				this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.8D);
				this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.0D);
				this.angerLevel = 0;
				this.watchingTimer--;
				this.setEntityInvulnerable(true);

				if(this.watchingTimer <= 0)
					this.setWatching(false);
			}
			else
			{
				this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6D);
				this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7.0D);
				this.setEntityInvulnerable(false);

				if(this.watchingTimer != 400)
					this.watchingTimer = 400;
			}

			if(world.getWorldTime() % 5000 == 0)
			{
				this.setWatching(true);
			}

			super.onUpdate();
		}
	}

	@Override
	public void onLivingUpdate()
	{
		this.motionY *= 0.6000000238418579D;

		if (!this.world.isRemote && this.getWatchedTargetId(0) > 0)
		{
			Entity entity = this.world.getEntityByID(this.getWatchedTargetId(0));

			if (entity != null)
			{
				if (this.posY < entity.posY || this.posY < entity.posY + 5.0D)
				{
					if (this.motionY < 0.0D)
					{
						this.motionY = 0.0D;
					}

					this.motionY += (0.5D - this.motionY) * 0.6000000238418579D;
				}

				double d0 = entity.posX - this.posX;
				double d1 = entity.posZ - this.posZ;
				double d3 = d0 * d0 + d1 * d1;

				if (d3 > 9.0D)
				{
					double d5 = (double)MathHelper.sqrt(d3);
					this.motionX += (d0 / d5 * 0.5D - this.motionX) * 0.6000000238418579D;
					this.motionZ += (d1 / d5 * 0.5D - this.motionZ) * 0.6000000238418579D;
				}
			}
		}

		if (this.motionX * this.motionX + this.motionZ * this.motionZ > 0.05000000074505806D)
		{
			this.rotationYaw = (float)MathHelper.atan2(this.motionZ, this.motionX) * (180F / (float)Math.PI) - 90.0F;
		}

		super.onLivingUpdate();

		for (int j = 0; j < 2; ++j)
		{
			int k = this.getWatchedTargetId(j + 1);
			Entity entity1 = null;

			if (k > 0)
			{
				entity1 = this.world.getEntityByID(k);
			}

			if (entity1 != null)
			{
				double d11 = this.posX;
				double d12 = this.posY;
				double d13 = this.posZ;
				double d6 = entity1.posX - d11;
				double d7 = entity1.posY + (double)entity1.getEyeHeight() - d12;
				double d8 = entity1.posZ - d13;
				double d9 = (double)MathHelper.sqrt(d6 * d6 + d8 * d8);
				float f = (float)(MathHelper.atan2(d8, d6) * (180D / Math.PI)) - 90.0F;
				float f1 = (float)(-(MathHelper.atan2(d7, d9) * (180D / Math.PI)));
			}
		}
	}

	@Override
	public boolean hitByEntity(Entity entityIn) {
		return this.isWatching();
	}

	public boolean isWatching()
	{
		return this.isWatching;
	}

	public void setWatching(boolean watching)
	{
		this.isWatching = watching;
	}

	@Override
	public void enablePersistence() 
	{
		super.enablePersistence();
	}

	@Override
	protected SoundEvent getDeathSound() 
	{
		return SoundsHandler.ENTITY_BILL_DEATH;
	}

	static class AIHurtByAggressor extends EntityAIHurtByTarget
	{
		public AIHurtByAggressor(EntityBill p_i45828_1_)
		{
			super(p_i45828_1_, true);
		}

		protected void setEntityAttackTarget(EntityCreature creatureIn, EntityLivingBase entityLivingBaseIn)
		{
			super.setEntityAttackTarget(creatureIn, entityLivingBaseIn);

			if (creatureIn instanceof EntityBill)
			{
				((EntityBill)creatureIn).becomeAngryAt(entityLivingBaseIn);
			}
		}
	}

	static class AITargetAggressor extends EntityAINearestAttackableTarget<EntityPlayer>
	{
		public AITargetAggressor(EntityBill p_i45829_1_)
		{
			super(p_i45829_1_, EntityPlayer.class, true);
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		public boolean shouldExecute()
		{
			return ((EntityBill)this.taskOwner).isAngry() && super.shouldExecute();
		}
	}

	/**
	 * Causes this PigZombie to become angry at the supplied Entity (which will be a player).
	 */
	private void becomeAngryAt(Entity p_70835_1_)
	{
		this.angerLevel = 400 + this.rand.nextInt(400);

		if (p_70835_1_ instanceof EntityLivingBase)
		{
			this.setRevengeTarget((EntityLivingBase)p_70835_1_);
		}
	}

	public boolean isAngry()
	{
		return this.angerLevel > 0;
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);
		compound.setShort("Anger", (short)this.angerLevel);

		if (this.angerTargetUUID != null)
		{
			compound.setString("HurtBy", this.angerTargetUUID.toString());
		}
		else
		{
			compound.setString("HurtBy", "");
		}
		
		compound.setBoolean("isDying", billDying);
		compound.setInteger("timeUntilDeath", timeUntilDeath);
		compound.setBoolean("isWatching", isWatching);
		compound.setInteger("watchingTimer", watchingTimer);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);

		if(compound.hasKey("Anger"))
			this.angerLevel = compound.getShort("Anger");

		String s = "";

		if(compound.hasKey("HurtBy"))
			s = compound.getString("HurtBy");

		if (!s.isEmpty())
		{
			this.angerTargetUUID = UUID.fromString(s);
			EntityPlayer entityplayer = this.world.getPlayerEntityByUUID(this.angerTargetUUID);
			this.setRevengeTarget(entityplayer);

			if (entityplayer != null)
			{
				this.attackingPlayer = entityplayer;
				this.recentlyHit = this.getRevengeTimer();
			}
		}
		
		billDying = compound.getBoolean("isDying");
		timeUntilDeath = compound.getInteger("timeUntilDeath");
		isWatching = compound.getBoolean("isWatching");
		watchingTimer = compound.getInteger("watchingTimer");
	}

	/**
	 * Hint to AI tasks that we were attacked by the passed EntityLivingBase and should retaliate. Is not guaranteed to
	 * change our actual active target (for example if we are currently busy attacking someone else)
	 */
	public void setRevengeTarget(@Nullable EntityLivingBase livingBase)
	{
		super.setRevengeTarget(livingBase);

		if (livingBase != null)
		{
			this.angerTargetUUID = livingBase.getUniqueID();
		}
	}

	public boolean getBillDying()
	{
		return this.billDying;
	}
}