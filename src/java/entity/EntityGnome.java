package entity;

import java.util.List;

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
import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
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

public class EntityGnome extends EntityPigZombie
{

	private static final DataParameter<Float> DATA_HEALTH_ID = EntityDataManager.<Float>createKey(EntityWolf.class, DataSerializers.FLOAT);


	public EntityGnome(World par1World)
	{
		super(par1World);
		this.setSize(0.3F, 0.85F);
	}


	@Override
	protected void applyEntityAttributes() 
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0D);

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);

		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);;

		this.getEntityAttribute(SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(0.5D);

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
	protected SoundEvent getDeathSound() 
	{
		return SoundsHandler.ENTITY_GNOME_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn)
	{
		if(this.isAngry())
			this.playSound(SoundsHandler.ENTITY_GNOME_ANGRY, 1.0F, 1.0F);

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

	@Override
	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
	{
		//this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
	}

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

}
