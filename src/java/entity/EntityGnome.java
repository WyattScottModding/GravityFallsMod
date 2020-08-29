package entity;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import handlers.LootTableHandler;
import handlers.SoundsHandler;
import net.minecraft.block.Block;
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
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityGnome extends EntityMob
{
	protected static final IAttribute SPAWN_REINFORCEMENTS_CHANCE = (new RangedAttribute((IAttribute)null, "zombie.spawnReinforcements", 0.0D, 0.0D, 1.0D)).setDescription("Spawn Reinforcements Chance");

	private static final UUID ATTACK_SPEED_BOOST_MODIFIER_UUID = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
	private static final AttributeModifier ATTACK_SPEED_BOOST_MODIFIER = (new AttributeModifier(ATTACK_SPEED_BOOST_MODIFIER_UUID, "Attacking speed boost", 0.05D, 0)).setSaved(false);
	private int angerLevel;
	private UUID angerTargetUUID;
	public final int variant;

	public EntityGnome(World par1World)
	{
		super(par1World);
		this.setSize(0.3F, 0.8F);
		this.experienceValue = 4;
		this.variant = new Random().nextInt(4) + 1;
	}

	@Override
	protected void initEntityAI()
	{
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, true));
		this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.applyEntityAI();
	}


	protected void applyEntityAI() 
	{
		this.targetTasks.addTask(1, new EntityGnome.AIHurtByAggressor(this));
		this.targetTasks.addTask(2, new EntityGnome.AITargetAggressor(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] {}));
	}

	@Override
	protected void applyEntityAttributes() 
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0D);

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);

		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);;

		this.getAttributeMap().registerAttribute(SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(0.5D);
	}

	@Override
	protected void updateAITasks()
	{
		IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);

		if (this.isAngry())
		{
			if (!this.isChild() && !iattributeinstance.hasModifier(ATTACK_SPEED_BOOST_MODIFIER))
			{
				iattributeinstance.applyModifier(ATTACK_SPEED_BOOST_MODIFIER);
			}

			--this.angerLevel;
		}
		else if (iattributeinstance.hasModifier(ATTACK_SPEED_BOOST_MODIFIER))
		{
			iattributeinstance.removeModifier(ATTACK_SPEED_BOOST_MODIFIER);
		}


		if (this.angerLevel > 0 && this.angerTargetUUID != null && this.getRevengeTarget() == null)
		{
			EntityPlayer entityplayer = this.world.getPlayerEntityByUUID(this.angerTargetUUID);
			this.setRevengeTarget(entityplayer);
			this.attackingPlayer = entityplayer;
			this.recentlyHit = this.getRevengeTimer();
		}
	}


	@Override
	public boolean getCanSpawnHere()
	{
		return true;
	}


	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn)
	{
		return SoundsHandler.ENTITY_GNOME_HURT;
	}
	
	@Override
	protected void playStepSound(BlockPos pos, Block blockIn)
	{
		this.playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 1.0F);
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundsHandler.ENTITY_GNOME_AMBIENT;
	}


	@Override
	protected ResourceLocation getLootTable()
	{
		return LootTableHandler.GNOME;

	}

	/*
	 * Turns into monster when enough are threatened 
	@Override
	public void onUpdate()
	{
		AxisAlignedBB entityArea = new AxisAlignedBB(this.getPosition().getX() - 10, this.getPosition().getY() - 10, this.getPosition().getZ() - 10, this.getPosition().getX() + 10, this.getPosition().getY() + 10, this.getPosition().getZ() + 10);

		if(this.world.getTotalWorldTime() % 5 == 0)
		{
			List<Entity> list = world.getEntitiesInAABBexcluding(this, entityArea, Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>()
			{
				public boolean apply(@Nullable Entity p_apply_1_)
				{
					return p_apply_1_ != null && p_apply_1_.canBeCollidedWith();
				}
			}));


			double averageX = 0;
			double averageY = 0;
			double averageZ = 0;

			for(int i = 0; i < list.size(); i++)
			{
				if(list.get(i) instanceof EntityGnome && !world.isRemote)
				{
					averageX += list.get(i).getPosition().getX();
					averageY += list.get(i).getPosition().getY();
					averageZ += list.get(i).getPosition().getZ();
				}
				else
				{
					list.remove(i);
				}
			}

			if(list.size() >= 10)
			{
				int count = list.size();

				int entity = (int) (this.world.getTotalWorldTime() % count);

				averageX /= count - 1;
				averageY /= count - 1;
				averageZ /= count - 1;
				
				System.out.println("averageX: " + averageX);
				System.out.println("averageY: " + averageY);
				System.out.println("averageZ: " + averageZ);
				System.out.println("list count: " + count);

				System.out.println(list.get(entity).toString());
				 
				if((int)list.get(entity).posX != (int)averageX && (int)list.get(entity).posY!= (int)averageY && (int)list.get(entity).posZ != (int)averageZ)
				{
					//list.get(entity).motionX = (averageX - list.get(entity).posX) / 100;
					//list.get(entity).motionY = (averageY - list.get(entity).posY) / 100;
					//list.get(entity).motionZ = (averageZ - list.get(entity).posZ) / 100;
					list.get(entity).setPosition(averageX, averageY, averageZ);
				}

			}

			//	for(int j = 0; j < list.size(); ++j)
			//	{
			//		Entity entity = list.get(j);
			//		entity.onKillCommand();
			//	}
		}

		super.onUpdate();
	}
	*/

	static class AIHurtByAggressor extends EntityAIHurtByTarget
	{
		public AIHurtByAggressor(EntityGnome p_i45828_1_)
		{
			super(p_i45828_1_, true);
		}

		protected void setEntityAttackTarget(EntityCreature creatureIn, EntityLivingBase entityLivingBaseIn)
		{
			super.setEntityAttackTarget(creatureIn, entityLivingBaseIn);

			if (creatureIn instanceof EntityGnome)
			{
				((EntityGnome)creatureIn).becomeAngryAt(entityLivingBaseIn);
			}
		}
	}

	static class AITargetAggressor extends EntityAINearestAttackableTarget<EntityPlayer>
	{
		public AITargetAggressor(EntityGnome p_i45829_1_)
		{
			super(p_i45829_1_, EntityPlayer.class, true);
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		public boolean shouldExecute()
		{
			return ((EntityGnome)this.taskOwner).isAngry() && super.shouldExecute();
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
}