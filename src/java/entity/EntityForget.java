package entity;

import javax.annotation.Nullable;

import handlers.RegistryHandler;
import items.MemoryGun;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIZombieAttack;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityElderGuardian;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.passive.EntityZombieHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityShulkerBullet;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import potions.CustomPotions;

public class EntityForget extends EntityFireball
{

	private EntityLivingBase owner;
	public Entity entity;
	int ticksAlive;
	private int ticksInAir;
	public double accelerationX;
	public double accelerationY;
	public double accelerationZ;
	public MemoryGun hit;
	public World world;

	public EntityForget(World worldIn)
	{
		super(worldIn);
		this.setSize(40.0F, 20.0F);
		this.world = worldIn;
	}

	public EntityForget(World worldIn, double x, double y, double z)
	{
		super(worldIn);
		this.setSize(40.0F, 20.0F);
		this.setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
		this.setPosition(x, y, z);
		this.accelerationX = 1.0D;
		this.accelerationY = 1.0D;
		this.accelerationZ = 1.0D;
		this.world = worldIn;
	}


	@Override
	public float getBrightness()
	{
		return 0.0F;
	}

	@Override
	protected void onImpact(RayTraceResult result) 
	{
		if (result.entityHit instanceof EntityLiving && !(result.entityHit instanceof EntityBill) && !(result.entityHit instanceof EntitySecurityDroid))
		{
			entity = (EntityLivingBase)result.entityHit;

			EntityLiving entityLiving = (EntityLiving) result.entityHit;
			((EntityLivingBase)result.entityHit).addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 6000));
			((EntityLivingBase)result.entityHit).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 6000));
			((EntityLivingBase)result.entityHit).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 6000));
			((EntityLivingBase)result.entityHit).addPotionEffect(new PotionEffect(MobEffects.GLOWING, 20));

			entityLiving.setNoAI(true);
			entityLiving.setSilent(true);
			
			if(entity instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) entity;
				
				for(int i = 0; i < player.inventory.getSizeInventory(); i++)
				{
					if(player.inventory.getStackInSlot(i).isEmpty())
						player.inventory.removeStackFromSlot(i);
				}
			}
			
			if(world.isRemote)
			{
				for(int i = 0; i < world.playerEntities.size(); i++)
				{
					if(world.playerEntities.get(i) instanceof EntityPlayerMP)
					{

						entityLiving.targetTasks.taskEntries.iterator().remove();
						while(entityLiving.targetTasks.taskEntries.iterator().hasNext())
						{
							entityLiving.targetTasks.taskEntries.iterator().next();
							entityLiving.targetTasks.taskEntries.iterator().remove();


						}
					}
					result.entityHit.removeTrackingPlayer((EntityPlayerMP)world.playerEntities.get(i));



					//Use EntityLiving to erase the memory of all entites

				}
			}
		}


		this.setDead();

	}

	public Entity getEntityHit()
	{
		return entity;
	}

	public void shoot(Entity shooter, float pitch, float yaw, float velocity, float inaccuracy)
	{
		float f = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
		float f1 = -MathHelper.sin(pitch * 0.017453292F);
		float f2 = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
		this.shoot((double)f, (double)f1, (double)f2, velocity, inaccuracy, shooter);
		this.motionX += shooter.motionX;
		this.motionZ += shooter.motionZ;

		if (!shooter.onGround)
		{
			this.motionY += shooter.motionY;
		}
	}

	public void shoot(double x, double y, double z, float velocity, float inaccuracy, Entity shooter)
	{
		float f = MathHelper.sqrt(x * x + y * y + z * z);
		x = x / (double)f;
		y = y / (double)f;
		z = z / (double)f;
		x = x * (double)velocity;
		y = y * (double)velocity;
		z = z * (double)velocity;
		float f1 = MathHelper.sqrt(x * x + z * z);
		this.rotationYaw = (float)(MathHelper.atan2(x, z) * (180D / Math.PI));
		this.rotationPitch = (float)(MathHelper.atan2(y, (double)f1) * (180D / Math.PI));
		this.prevRotationYaw = this.rotationYaw;
		this.prevRotationPitch = this.rotationPitch;
		this.setVelocity(x, y, z);
	}



	protected EnumParticleTypes getParticleType()
	{
		return EnumParticleTypes.CLOUD;
	}

	protected float getMotionFactor()
	{
		return 2.0F;
	}

	@Override
	public void onUpdate()
	{
		if (this.world.isRemote || (this.shootingEntity == null || !this.shootingEntity.isDead) && this.world.isBlockLoaded(new BlockPos(this)))
		{
			super.onUpdate();

			++this.ticksInAir;
			RayTraceResult raytraceresult = ProjectileHelper.forwardsRaycast(this, true, this.ticksInAir >= 25, this.shootingEntity);

			if (raytraceresult != null && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult))
			{
				this.onImpact(raytraceresult);
			}

			this.posX += this.motionX;
			this.posY += this.motionY;
			this.posZ += this.motionZ;
			//     ProjectileHelper.rotateTowardsMovement(this, 0.2F);
			float f = this.getMotionFactor();

			if (this.isInWater())
			{
				for (int i = 0; i < 4; ++i)
				{
					float f1 = 0.25F;
					this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * 0.25D, this.posY - this.motionY * 0.25D, this.posZ - this.motionZ * 0.25D, this.motionX, this.motionY, this.motionZ);
				}

				f = 0.8F;
			}

			this.motionX += this.accelerationX;
			this.motionY += this.accelerationY;
			this.motionZ += this.accelerationZ;
			this.motionX *= (double)f;
			this.motionY *= (double)f;
			this.motionZ *= (double)f;
			this.world.spawnParticle(this.getParticleType(), this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
			this.setPosition(this.posX, this.posY, this.posZ);
		}
		else
		{
			this.setDead();
		}

	}

	@Override
	protected boolean isFireballFiery()
	{
		return false;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		if (this.isEntityInvulnerable(source))
		{
			return false;
		}
		else
		{
			this.markVelocityChanged();

			if (source.getTrueSource() != null)
			{
				Vec3d vec3d = source.getTrueSource().getLookVec();

				if (vec3d != null)
				{
					this.motionX = vec3d.x;
					this.motionY = vec3d.y;
					this.motionZ = vec3d.z;
					this.accelerationX = this.motionX * 0.1D;
					this.accelerationY = this.motionY * 0.1D;
					this.accelerationZ = this.motionZ * 0.1D;
				}

				if (source.getTrueSource() instanceof EntityLivingBase)
				{
					this.shootingEntity = (EntityLivingBase)source.getTrueSource();
				}

				return true;
			}
			else
			{
				return false;
			}
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{

	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{

	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender()
	{
		return 0;
	}


}
