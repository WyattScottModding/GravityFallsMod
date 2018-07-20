package entity;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import handlers.LootTableHandler;
import handlers.SoundsHandler;
import init.BlockInit;
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
import net.minecraft.entity.ai.EntityAIBeg;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILeapAtTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
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
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityBill extends EntityPigZombie
{
	private static final DataParameter<Float> DATA_HEALTH_ID = EntityDataManager.<Float>createKey(EntityWolf.class, DataSerializers.FLOAT);
	private static final UUID ATTACK_SPEED_BOOST_MODIFIER_UUID = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
	private static final AttributeModifier ATTACK_SPEED_BOOST_MODIFIER = (new AttributeModifier(ATTACK_SPEED_BOOST_MODIFIER_UUID, "Attacking speed boost", 0.05D, 0)).setSaved(false);
	private int angerLevel;
    private UUID angerTargetUUID;
	int level = 1;

	public EntityBill(World par1World)
	{
		super(par1World);
		this.setSize(2.0F, 2.0F);
		this.isImmuneToFire = true;
		this.experienceValue = 100;
		this.stepHeight = 10;
	}
	
	@Override
	 protected void initEntityAI()
	    {
	        this.tasks.addTask(0, new EntityAISwimming(this));
	        this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
	        this.tasks.addTask(3, new EntityAIWander(this, 1.0D));
	        this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
	        this.tasks.addTask(5, new EntityAILookIdle(this));
	        this.tasks.addTask(6, new EntityAIMoveTowardsRestriction(this, 0.4));
	        this.targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));	        
	    }


	@Override
	protected void applyEntityAttributes() 
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1000.0D);

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6D);

		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0D);
		
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(300.0D);
		
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
	public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) 
	{
		return super.applyPlayerInteraction(player, vec, hand);
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
		return SoundsHandler.ENTITY_BILL_DEATH;
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
		return LootTableHandler.BILL;

	}

	@Override
	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
	{
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
		
		for(int i = 3; i >= -10; i--)
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

}
