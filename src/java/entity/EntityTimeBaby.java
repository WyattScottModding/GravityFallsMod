package entity;

import java.util.UUID;

import javax.annotation.Nullable;

import handlers.LootTableHandler;
import handlers.SoundsHandler;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
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
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityTimeBaby extends EntityCreature implements IMob
{
	private int angerLevel;
	private UUID angerTargetUUID;
	int level = 1;

	public EntityTimeBaby(World par1World)
	{
		super(par1World);
		this.setSize(1.6F, 7.8F);
		this.isImmuneToFire = true;
		this.experienceValue = 100;
		this.stepHeight = 4;
	}

	public EntityTimeBaby(World par1World, double x, double y, double z)
	{
		super(par1World);
		this.setSize(1.6F, 7.8F);
		this.isImmuneToFire = true;
		this.experienceValue = 100;
		this.stepHeight = 4;
		this.setPosition(x, y, z);
	}

	@Override
	protected void initEntityAI()
	{
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, true));
		this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.applyEntityAI();
	}

	protected void applyEntityAI() 
	{
		this.targetTasks.addTask(1, new EntityTimeBaby.AIHurtByAggressor(this));
		this.targetTasks.addTask(2, new EntityTimeBaby.AITargetAggressor(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] {}));
	}

	@Override
	protected void applyEntityAttributes() 
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(400.0D);

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);

		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
		
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(100.0D);	
	}

	@Override
	public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) 
	{
		return super.applyPlayerInteraction(player, vec, hand);
	}
	
    /**
     * Called when the entity is attacked.
     */
	@Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        return this.isEntityInvulnerable(source) ? false : super.attackEntityFrom(source, amount);
    }
	
	@Override
    public void onLivingUpdate()
    {
        this.updateArmSwingProgress();
        float f = this.getBrightness();

        if (f > 0.5F)
        {
            this.idleTime += 2;
        }

        super.onLivingUpdate();
    }

	@Override
	public boolean canTrample(World world, Block block, BlockPos pos, float fallDistance) 
	{
		return true;
	}

	@Override
	public void enablePersistence() 
	{
		super.enablePersistence();
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
		return SoundsHandler.ENTITY_TIMEBABY_CRYING;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn)
	{
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return null;
	}


	@Override
	protected ResourceLocation getLootTable()
	{
		return LootTableHandler.BABY;
	}

	@Override
	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
	{
	}

	@Override
	public void onUpdate() 
	{
		super.onUpdate();

		if(!world.isRemote && world.getWorldTime() % 300 == 0) {
			teleportRandomly();

			if(world.getWorldTime() % 600 == 0) {
				
				//Heal
				if(this.getHealth() <= 298)
					this.setHealth(this.getHealth() + 2);
			}
		}
	}

	protected boolean teleportRandomly()
	{
		double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * 64.0D;
		double d1 = this.posY + (double)(this.rand.nextInt(64) - 32);
		double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * 64.0D;
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

	public void setTarget(EntityLivingBase entity)
	{
		this.setAttackTarget(entity);
		this.setRevengeTarget(entity);
	}

	static class AIHurtByAggressor extends EntityAIHurtByTarget
	{
		public AIHurtByAggressor(EntityTimeBaby p_i45828_1_)
		{
			super(p_i45828_1_, true);
		}

		protected void setEntityAttackTarget(EntityCreature creatureIn, EntityLivingBase entityLivingBaseIn)
		{
			super.setEntityAttackTarget(creatureIn, entityLivingBaseIn);

			if (creatureIn instanceof EntityTimeBaby)
			{
				((EntityTimeBaby)creatureIn).becomeAngryAt(entityLivingBaseIn);
			}
		}
	}

	static class AITargetAggressor extends EntityAINearestAttackableTarget<EntityPlayer>
	{
		public AITargetAggressor(EntityTimeBaby p_i45829_1_)
		{
			super(p_i45829_1_, EntityPlayer.class, true);
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		public boolean shouldExecute()
		{
			return ((EntityTimeBaby)this.taskOwner).isAngry() && super.shouldExecute();
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
	
	@Override
	public boolean attackEntityAsMob(Entity entityIn)
    {
        float f = (float)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
        int i = 0;

        if (entityIn instanceof EntityLivingBase)
        {
            f += EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((EntityLivingBase)entityIn).getCreatureAttribute());
            i += EnchantmentHelper.getKnockbackModifier(this);
        }

        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), f);

        if (flag)
        {
            if (i > 0 && entityIn instanceof EntityLivingBase)
            {
                ((EntityLivingBase)entityIn).knockBack(this, (float)i * 0.5F, (double)MathHelper.sin(this.rotationYaw * 0.017453292F), (double)(-MathHelper.cos(this.rotationYaw * 0.017453292F)));
                this.motionX *= 0.6D;
                this.motionZ *= 0.6D;
            }

            int j = EnchantmentHelper.getFireAspectModifier(this);

            if (j > 0)
            {
                entityIn.setFire(j * 4);
            }

            if (entityIn instanceof EntityPlayer)
            {
                EntityPlayer entityplayer = (EntityPlayer)entityIn;
                ItemStack itemstack = this.getHeldItemMainhand();
                ItemStack itemstack1 = entityplayer.isHandActive() ? entityplayer.getActiveItemStack() : ItemStack.EMPTY;

                if (!itemstack.isEmpty() && !itemstack1.isEmpty() && itemstack.getItem().canDisableShield(itemstack, itemstack1, entityplayer, this) && itemstack1.getItem().isShield(itemstack1, entityplayer))
                {
                    float f1 = 0.25F + (float)EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;

                    if (this.rand.nextFloat() < f1)
                    {
                        entityplayer.getCooldownTracker().setCooldown(itemstack1.getItem(), 100);
                        this.world.setEntityState(entityplayer, (byte)30);
                    }
                }
            }

            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

}