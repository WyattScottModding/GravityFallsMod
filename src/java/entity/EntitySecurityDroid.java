package entity;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import handlers.LootTableHandler;
import handlers.SoundsHandler;
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
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityPigZombie;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntitySecurityDroid extends EntityPigZombie
{
	public static final net.minecraft.entity.ai.attributes.IAttribute REACH_DISTANCE = new net.minecraft.entity.ai.attributes.RangedAttribute(null, "generic.reachDistance", 20.0D, 0.0D, 1024.0D).setShouldWatch(true);
	private static final DataParameter<Float> DATA_HEALTH_ID = EntityDataManager.<Float>createKey(EntityWolf.class, DataSerializers.FLOAT);
	private static final UUID ATTACK_SPEED_BOOST_MODIFIER_UUID = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
	private static final AttributeModifier ATTACK_SPEED_BOOST_MODIFIER = (new AttributeModifier(ATTACK_SPEED_BOOST_MODIFIER_UUID, "Attacking speed boost", 0.05D, 0)).setSaved(false);
	private int angerLevel;
	private UUID angerTargetUUID;
	private boolean pullEntity = false;

	public EntitySecurityDroid(World par1World)
	{
		super(par1World);
		this.setSize(3.0F, 6.0F);
		this.stepHeight = 10;
	}
	
	@Override
	protected void applyEntityAI() 
	{
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));

        super.applyEntityAI();
	}


	@Override
	protected void applyEntityAttributes() 
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(150.0D);

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);

		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);;

		this.getEntityAttribute(SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(0.0D);

		this.getAttributeMap().registerAttribute(REACH_DISTANCE);
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
		return null;
	}

	@Override
	protected SoundEvent getDeathSound() 
	{
		return SoundsHandler.ENTITY_DROID_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn)
	{

	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundsHandler.ENTITY_DROID_AMBIENT;
	}


	@Override
	protected ResourceLocation getLootTable()
	{
		return LootTableHandler.DROID;

	}

	@Override
	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
	{
		//this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
	}

	@Override
	public void onUpdate()
	{	
		if(this.attackingPlayer != null)
		{
			EntityPlayer player = this.attackingPlayer;

			if(world.getWorldTime() % 100 == 0)
			{
				if(pullEntity)
					pullEntity = false;
				else
					pullEntity = true;
			}

//			if(pullEntity)
	//			pullEntity(player);
		}
		super.onUpdate();
	}

	public void pullEntity(EntityPlayer player)
	{
		double droidX = this.posX;
		double droidY = this.posY;
		double droidZ = this.posZ;

		double playerX = player.posX;
		double playerY = player.posY;
		double playerZ = player.posZ;

		if(droidX < playerX)
			player.motionX = -1.0F;
		else if(droidX > playerX)
			player.motionX = 1.0F;
		else
			player.motionX = 0.0F;

		if(droidY < playerY)
			player.motionY = -1.0F;
		else if(droidY > playerY)
			player.motionY = 1.0F;
		else
			player.motionY = 0.0F;

		if(droidZ < playerZ)
			player.motionZ = -1.0F;
		else if(droidZ > playerZ)
			player.motionZ = 1.0F;
		else
			player.motionZ = 0.0F;
	}

}
