package entity;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import init.PotionInit;
import main.Reference;
import models.ModelBill;
import models.ModelEightBall;
import models.ModelEyeBat;
import models.ModelEyeBatHuge;
import models.ModelGnome;
import models.ModelHideBehind;
import models.ModelKeyhole;
import models.ModelShapeShifter;
import models.ModelTimeBaby;
import models.ModelTimeCopDundgren;
import models.ModelTimeCopLolph;
import models.ModelUnicorn;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBat;
import net.minecraft.client.model.ModelBlaze;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.model.ModelEnderman;
import net.minecraft.client.model.ModelEvokerFangs;
import net.minecraft.client.model.ModelGhast;
import net.minecraft.client.model.ModelHorse;
import net.minecraft.client.model.ModelLlama;
import net.minecraft.client.model.ModelOcelot;
import net.minecraft.client.model.ModelParrot;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRabbit;
import net.minecraft.client.model.ModelSheep1;
import net.minecraft.client.model.ModelShulker;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.model.ModelSquid;
import net.minecraft.client.model.ModelVex;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.model.ModelWither;
import net.minecraft.client.model.ModelWolf;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.model.ModelZombieVillager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIBeg;
import net.minecraft.entity.ai.EntityAIFollowOwner;
import net.minecraft.entity.ai.EntityAIFollowParent;
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
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityShapeShifterOld extends EntityTameable// implements IMob
{	

	public ModelBase currentModel = new ModelShapeShifter();
	public String texture = Reference.MODID + ":textures/entity/shapeshifter.png";
	public EntityLivingBase currentEntity = this;

	public EntityList entityList = new EntityList();
	public boolean hasPlayer = false;

	private int angerLevel;
	private UUID angerTargetUUID;


	public EntityShapeShifterOld(World par1World)
	{
		super(par1World);
		this.setSize(0.8F, 1.8F);
		this.experienceValue = 40;
		this.stepHeight = 2;
		entityList.add(this);
		this.setTamed(false);
		//this.initEntityAI();
	}

	
	@Override
	protected void initEntityAI()
	{
        this.aiSit = new EntityAISit(this);
        this.tasks.addTask(2, this.aiSit);

		this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAITempt(this, 1.25D, Items.WHEAT, false));
		this.tasks.addTask(4, new EntityAIFollowParent(this, 1.25D));
		this.tasks.addTask(5, new EntityAIAttackMelee(this, 1.0D, true));
		this.tasks.addTask(6, new EntityAIMate(this, 1.0D));
		this.tasks.addTask(7, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(8, new EntityAIWanderAvoidWater(this, 1.0D));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));

		this.applyEntityAI();
	}

	protected void applyEntityAI() 
	{
		this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
		this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
		this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true, new Class[0]));

		//this.targetTasks.addTask(1, new EntityShapeShifter.AIHurtByAggressor(this));
		//this.targetTasks.addTask(2, new EntityShapeShifter.AITargetAggressor(this));
	}

	@Override
	protected void applyEntityAttributes() 
	{
		super.applyEntityAttributes();

		if(this.isChild()) {
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
			this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
			this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
		}
		else {
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D);
			this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.2D);
			this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
		}

	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		return this.isEntityInvulnerable(source) ? false : super.attackEntityFrom(source, amount);
	}

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

	@Override
	public EntityShapeShifterOld createChild(EntityAgeable ageable)
	{
		EntityShapeShifterOld shapeshifter = new EntityShapeShifterOld(this.world);
		UUID uuid = this.getOwnerId();

		if (uuid != null)
		{
			shapeshifter.setOwnerId(uuid);
			shapeshifter.setTamed(true);
		}

		return shapeshifter;
	}

	@Override
	public boolean canBreatheUnderwater() {
		return currentEntity instanceof EntitySquid;
	}

	private void setEntity(EntityLivingBase entity) {

		if(!(entity instanceof EntityPigZombie))
			this.setSize(entity.width, entity.height);

		if(this.isPotionActive(PotionInit.GROWTH_EFFECT))
			this.removePotionEffect(PotionInit.GROWTH_EFFECT);

		currentEntity = entity;

		currentModel.isChild = this.isChild();
	}

	@Override
	public void onUpdate() {
		
		//System.out.println("Is sitting: " + this.isSitting());
		
		if(isChild()) {
			if(world.getWorldTime() % 500 == 0) {
				changeModel();

				//if(!isTamed())
				//	findPlayer();

				//Heal
				if(this.getHealth() <= 98)
					this.setHealth(this.getHealth() + 2);
			}
		}
		else {
			if(world.getWorldTime() % 100 == 0) {
				changeModel();

				//if(!isTamed())
				//	findPlayer();

				//Heal
				if(this.getHealth() <= 199)
					this.setHealth(this.getHealth() + 1);
			}
		}
		if(world.getWorldTime() % 5 == 0) {
			addEntity();
			defence();
		}

		if(currentEntity instanceof EntityEyeBat || currentEntity instanceof EntityEyeBatHuge || currentEntity instanceof EntityBat)
			this.motionY *= 0.6000000238418579D;

		super.onUpdate();
	}

	private void findPlayer() {
		int x = (int) this.posX;
		int y = (int) this.posY;
		int z = (int) this.posZ;

		AxisAlignedBB bb = new AxisAlignedBB(x - 5, y - 5, z - 5, x + 5, y + 5, z + 5);

		List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(this, bb);

		for(Entity entity : list) {
			if(entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) entity;
				hasPlayer = true;
				currentModel = new ModelPlayer(0, true);

				setEntity(player);
			}
		}
	}


	private void defence() {

		if(entityList.containsEntity(new EntityPigZombie(world))) {
			if(this.isBurning()) {
				currentModel = new ModelZombie(1, true);
				texture = "minecraft:textures/entity/zombie_pigman.png";
				currentEntity = new EntityPigZombie(world);

				currentModel.isChild = this.isChild();
				this.isImmuneToFire = true;
			}	
		}
		else
			this.isImmuneToFire = false;


		if(inWater) {
			if(entityList.containsEntity(new EntitySquid(world))) {
				currentModel = new ModelSquid();
				currentEntity = new EntitySquid(world);

				currentModel.isChild = this.isChild();
			}
		}
	}

	public EntityLivingBase addEntity()
	{
		BlockPos pos = this.getPosition();

		float yaw = this.rotationYaw;
		float pitch = this.rotationPitch;

		List<Entity> list = null;
		EntityLivingBase closestEntity = null;

		for(int f = 1; f <= 25; f++)
		{
			double x = (double)(-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
			double y = (double)(-MathHelper.sin((pitch) / 180.0F * (float)Math.PI) * f);
			double z = (double)(MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);


			AxisAlignedBB entityPos;

			if(f < 10)
				entityPos = new AxisAlignedBB(pos.getX() + x, pos.getY() + y, pos.getZ() + z, pos.getX() + x + 1, pos.getY() + y + 1, pos.getZ() + z + 1);
			else
				entityPos = new AxisAlignedBB(pos.getX() + x - 1, pos.getY() + y - 1, pos.getZ() + z - 1, pos.getX() + x + 2, pos.getY() + y + 2, pos.getZ() + z + 2);

			list = world.getEntitiesWithinAABBExcludingEntity(this, entityPos);


			for(int j = 0; j < list.size(); ++j)
			{
				Entity entity = list.get(j);

				if(entity instanceof EntityPlayer) {
					hasPlayer = true;
				}
				if(entity instanceof EntityLivingBase)
				{
					EntityLivingBase entityLiving = (EntityLivingBase) entity;

					if(this.canEntityBeSeen(entityLiving)) {
						entityList.add(entityLiving);
					}
				}
			}
		}
		return closestEntity;
	}

	public boolean canEntityBeSeen(Entity entityIn)
	{
		return this.world.rayTraceBlocks(new Vec3d(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ), new Vec3d(entityIn.posX, entityIn.posY + (double)entityIn.getEyeHeight(), entityIn.posZ), false, true, false) == null;
	}

	private void changeModel() {
		if(!entityList.isEmpty()) {
			EntityLivingBase entity = getEntity();

			if(entity instanceof EntityBill)
				currentModel = new ModelBill();
			else if(entity instanceof EntityEightBall)
				currentModel = new ModelEightBall();
			else if(entity instanceof EntityEyeBat)
				currentModel = new ModelEyeBat();
			else if(entity instanceof EntityEyeBatHuge)
				currentModel = new ModelEyeBatHuge();
			else if(entity instanceof EntityGnome)
				currentModel = new ModelGnome();
			else if(entity instanceof EntityHideBehind)
				currentModel = new ModelHideBehind();
			else if(entity instanceof EntityKeyhole)
				currentModel = new ModelKeyhole();
			else if(entity instanceof EntityTimeBaby)
				currentModel = new ModelTimeBaby();
			else if(entity instanceof EntityTimeCopDundgren)
				currentModel = new ModelTimeCopDundgren();
			else if(entity instanceof EntityTimeCopLolph)
				currentModel = new ModelTimeCopLolph();
			else if(entity instanceof EntityUnicorn)
				currentModel = new ModelUnicorn();

			if(hasPlayer)
				currentModel = new ModelPlayer(0.0F, true);

			else if(entity instanceof EntityBat)
				currentModel = new ModelBat();
			else if(entity instanceof EntityBlaze)
				currentModel = new ModelBlaze();
			else if(entity instanceof EntityChicken)
				currentModel = new ModelChicken();
			else if(entity instanceof EntityCow)
				currentModel = new ModelCow();
			else if(entity instanceof EntityCreeper)
				currentModel = new ModelCreeper();
			else if(entity instanceof EntityEnderman)
				currentModel = new ModelEnderman(0);
			else if(entity instanceof EntityGhast)
				currentModel = new ModelGhast();
			else if(entity instanceof EntityHorse)
				currentModel = new ModelHorse();
			else if(entity instanceof EntityEvoker)
				currentModel = new ModelEvokerFangs();
			else if(entity instanceof EntityVex)
				currentModel = new ModelVex();
			else if(entity instanceof EntityOcelot)
				currentModel = new ModelOcelot();
			else if(entity instanceof EntityLlama)
				currentModel = new ModelLlama(0);
			else if(entity instanceof EntityParrot)
				currentModel = new ModelParrot();
			else if(entity instanceof EntityPig)
				currentModel = new ModelPig();
			else if(entity instanceof EntityRabbit)
				currentModel = new ModelRabbit();
			else if(entity instanceof EntitySheep)
				currentModel = new ModelSheep1();
			else if(entity instanceof EntityShulker)
				currentModel = new ModelShulker();
			else if(entity instanceof EntitySkeleton)
				currentModel = new ModelSkeleton();
			else if(entity instanceof EntitySlime)
				currentModel = new ModelSlime(0);
			else if(entity instanceof EntitySpider)
				currentModel = new ModelSpider();
			else if(entity instanceof EntitySquid)
				currentModel = new ModelSquid();
			else if(entity instanceof EntityVillager)
				currentModel = new ModelVillager(0);
			else if(entity instanceof EntityWither)
				currentModel = new ModelWither(0);
			else if(entity instanceof EntityWolf)
				currentModel = new ModelWolf();
			else if(entity instanceof EntityPigZombie) {
				currentModel = new ModelZombie();
				texture = "minecraft:textures/entity/zombie_pigman.png";
				this.setSize(0.6F, 1.95F);
			}
			else if(entity instanceof EntityZombieVillager)
				currentModel = new ModelZombieVillager();
			else if(entity instanceof EntityZombie) {
				currentModel = new ModelZombie();
				texture = "minecraft:textures/entity/zombie/zombie.png";
			}
			else {
				entityList.remove(entity);
				return;
			}
			//System.out.println("Model Changed");


			setEntity(entity);

			//this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(currentEntity.getAIMoveSpeed() * 1.2);
		}
		else {
			currentModel = new ModelShapeShifter();
			currentEntity = this;

			if(this.isChild())
				currentModel.isChild = true;
		}
	}

	private EntityLivingBase getEntity() {
		int random = (int) (Math.random() * entityList.size());
		if(entityList.get(random) != null)
			return entityList.get(random);
		else {
			entityList.remove(random);
			return getEntity();
		}
	}

	@Override
	public boolean hitByEntity(Entity entityIn) {

		changeModel();

		return super.hitByEntity(entityIn);
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

		compound.setBoolean("bill", entityList.contains(new EntityCreeper(world)));
		compound.setBoolean("eightball", entityList.contains(new EntityEightBall(world)));
		compound.setBoolean("eyebat", entityList.contains(new EntityEyeBat(world)));
		compound.setBoolean("eyebathuge", entityList.contains(new EntityEyeBatHuge(world)));
		compound.setBoolean("gnome", entityList.contains(new EntityGnome(world)));
		compound.setBoolean("hidebehind", entityList.contains(new EntityHideBehind(world)));
		compound.setBoolean("keyhole", entityList.contains(new EntityKeyhole(world)));
		compound.setBoolean("timebaby", entityList.contains(new EntityTimeBaby(world)));
		compound.setBoolean("timecopdundgren", entityList.contains(new EntityTimeCopDundgren(world)));
		compound.setBoolean("timecoplolph", entityList.contains(new EntityTimeCopLolph(world)));
		compound.setBoolean("unicorn", entityList.contains(new EntityUnicorn(world)));

		compound.setBoolean("bat", entityList.contains(new EntityBat(world)));
		compound.setBoolean("blaze", entityList.contains(new EntityBlaze(world)));
		compound.setBoolean("chicken", entityList.contains(new EntityChicken(world)));
		compound.setBoolean("cow", entityList.contains(new EntityCow(world)));
		compound.setBoolean("creeper", entityList.contains(new EntityCreeper(world)));
		compound.setBoolean("enderman", entityList.contains(new EntityEnderman(world)));
		compound.setBoolean("ghast", entityList.contains(new EntityGhast(world)));
		compound.setBoolean("horse", entityList.contains(new EntityHorse(world)));
		compound.setBoolean("evoker", entityList.contains(new EntityEvoker(world)));
		compound.setBoolean("vex", entityList.contains(new EntityVex(world)));
		compound.setBoolean("ocelot", entityList.contains(new EntityOcelot(world)));
		compound.setBoolean("llama", entityList.contains(new EntityLlama(world)));
		compound.setBoolean("parrot", entityList.contains(new EntityParrot(world)));
		compound.setBoolean("pig", entityList.contains(new EntityPig(world)));
		compound.setBoolean("rabbit", entityList.contains(new EntityRabbit(world)));
		compound.setBoolean("sheep", entityList.contains(new EntitySheep(world)));
		compound.setBoolean("shulker", entityList.contains(new EntityShulker(world)));
		compound.setBoolean("skeleton", entityList.contains(new EntitySkeleton(world)));
		compound.setBoolean("slime", entityList.contains(new EntitySlime(world)));
		compound.setBoolean("spider", entityList.contains(new EntitySpider(world)));
		compound.setBoolean("villager", entityList.contains(new EntityVillager(world)));
		compound.setBoolean("wither", entityList.contains(new EntityWither(world)));
		compound.setBoolean("wolf", entityList.contains(new EntityWolf(world)));
		compound.setBoolean("pigzombie", entityList.contains(new EntityPigZombie(world)));
		compound.setBoolean("zombievillager", entityList.contains(new EntityZombieVillager(world)));
		compound.setBoolean("zombie", entityList.contains(new EntityZombie(world)));	


		compound.setString("currentEntity", currentEntity.toString().substring(0, currentEntity.toString().indexOf("[")).toLowerCase());
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);

		this.angerLevel = compound.getShort("Anger");
		String s = compound.getString("HurtBy");

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

		if(compound.getBoolean("bill")) 
			entityList.add(new EntityCreeper(world));
		if(compound.getBoolean("eightball")) 
			entityList.add(new EntityEightBall(world));
		if(compound.getBoolean("eyebat")) 
			entityList.add(new EntityEyeBat(world));
		if(compound.getBoolean("eyebathuge")) 
			entityList.add(new EntityEyeBatHuge(world));
		if(compound.getBoolean("gnome")) 
			entityList.add(new EntityGnome(world));
		if(compound.getBoolean("hidebehind")) 
			entityList.add(new EntityHideBehind(world));
		if(compound.getBoolean("keyhole")) 
			entityList.add(new EntityKeyhole(world));
		if(compound.getBoolean("timebaby")) 
			entityList.add(new EntityTimeBaby(world));
		if(compound.getBoolean("timecopdundgren")) 
			entityList.add(new EntityTimeCopDundgren(world));
		if(compound.getBoolean("timecoplolph")) 
			entityList.add(new EntityTimeCopLolph(world));
		if(compound.getBoolean("unicorn")) 
			entityList.add(new EntityUnicorn(world));

		if(compound.getBoolean("bat")) 
			entityList.add(new EntityBat(world));
		if(compound.getBoolean("blaze")) 
			entityList.add(new EntityBlaze(world));
		if(compound.getBoolean("chicken")) 
			entityList.add(new EntityChicken(world));
		if(compound.getBoolean("cow")) 
			entityList.add(new EntityCow(world));
		if(compound.getBoolean("creeper")) 
			entityList.add(new EntityCreeper(world));
		if(compound.getBoolean("enderman")) 
			entityList.add(new EntityEnderman(world));
		if(compound.getBoolean("ghast")) 
			entityList.add(new EntityGhast(world));
		if(compound.getBoolean("horse")) 
			entityList.add(new EntityHorse(world));
		if(compound.getBoolean("evoker")) 
			entityList.add(new EntityEvoker(world));
		if(compound.getBoolean("vex"))
			entityList.add(new EntityVex(world));
		if(compound.getBoolean("ocelot")) 
			entityList.add(new EntityOcelot(world));
		if(compound.getBoolean("llama")) 
			entityList.add(new EntityLlama(world));
		if(compound.getBoolean("parrot"))
			entityList.add(new EntityParrot(world));
		if(compound.getBoolean("pig")) 
			entityList.add(new EntityPig(world));
		if(compound.getBoolean("rabbit")) 
			entityList.add(new EntityRabbit(world));
		if(compound.getBoolean("sheep")) 
			entityList.add(new EntitySheep(world));
		if(compound.getBoolean("shulker")) 
			entityList.add(new EntityShulker(world));
		if(compound.getBoolean("skeleton")) 
			entityList.add(new EntitySkeleton(world));
		if(compound.getBoolean("slime")) 
			entityList.add(new EntitySlime(world));
		if(compound.getBoolean("spider")) 
			entityList.add(new EntitySpider(world));
		if(compound.getBoolean("villager")) 
			entityList.add(new EntityVillager(world));
		if(compound.getBoolean("wither")) 
			entityList.add(new EntityWither(world));
		if(compound.getBoolean("wolf")) 
			entityList.add(new EntityWolf(world));
		if(compound.getBoolean("pigzombie"))
			entityList.add(new EntityPigZombie(world));
		if(compound.getBoolean("zombievillager"))
			entityList.add(new EntityZombieVillager(world));
		if(compound.getBoolean("zombie"))
			entityList.add(new EntityZombie(world));	



		String string = compound.getString("currentEntity");

		if(string.equals("bill")) {
			currentEntity = new EntityBill(world);
			currentModel = new ModelBill();
		}
		else if(string.equals("eightball")) {
			currentEntity = new EntityEightBall(world);
			currentModel = new ModelEightBall();
		}
		else if(string.equals("eyebat")) {
			currentEntity = new EntityEyeBat(world);
			currentModel = new ModelEyeBat();
		}
		else if(string.equals("eyebathuge")) {
			currentEntity = new EntityEyeBatHuge(world);
			currentModel = new ModelEyeBatHuge();
		}
		else if(string.equals("gnome")) {
			currentEntity = new EntityGnome(world);
			currentModel = new ModelGnome();
		}
		else if(string.equals("hidebehind")) {
			currentEntity = new EntityHideBehind(world);
			currentModel = new ModelHideBehind();
		}
		else if(string.equals("keyhole")) {
			currentEntity = new EntityKeyhole(world);
			currentModel = new ModelKeyhole();
		}
		else if(string.equals("timebaby")) {
			currentEntity = new EntityTimeBaby(world);
			currentModel = new ModelTimeBaby();
		}
		else if(string.equals("timecopdundgren")) {
			currentEntity = new EntityTimeCopDundgren(world);
			currentModel = new ModelTimeCopDundgren();
		}
		else if(string.equals("timecoplolph")) {
			currentEntity = new EntityTimeCopLolph(world);
			currentModel = new ModelTimeCopLolph();
		}
		else if(string.equals("unicorn")) {
			currentEntity = new EntityUnicorn(world);
			currentModel = new ModelUnicorn();
		}

		else if(string.equals("bat")) {
			currentEntity = new EntityBat(world);
			currentModel = new ModelBat();
		}
		else if(string.equals("blaze")) {
			currentEntity = new EntityBlaze(world);
			currentModel = new ModelBlaze();
		}
		else if(string.equals("chicken")) {
			currentEntity = new EntityChicken(world);
			currentModel = new ModelChicken();
		}
		else if(string.equals("cow")) {
			currentEntity = new EntityCow(world);
			currentModel = new ModelCow();
		}
		else if(string.equals("creeper")) {
			currentEntity = new EntityCreeper(world);
			currentModel = new ModelCreeper();
		}
		else if(string.equals("enderman")) {
			currentEntity = new EntityEnderman(world);
			currentModel = new ModelEnderman(0);
		}
		else if(string.equals("ghast")) {
			currentEntity = new EntityGhast(world);
			currentModel = new ModelGhast();
		}
		else if(string.equals("horse")) {
			currentEntity = new EntityHorse(world);
			currentModel = new ModelHorse();
		}
		else if(string.equals("evoker")) {
			currentEntity = new EntityEvoker(world);
			currentModel = new ModelEvokerFangs();
		}
		else if(string.equals("vex")) {
			currentEntity = new EntityVex(world);
			currentModel = new ModelVex();
		}
		else if(string.equals("ocelot")) {
			currentEntity = new EntityOcelot(world);
			currentModel = new ModelOcelot();
		}
		else if(string.equals("llama")) {
			currentEntity = new EntityLlama(world);
			currentModel = new ModelLlama(0);
		}
		else if(string.equals("parrot")) {
			currentEntity = new EntityParrot(world);
			currentModel = new ModelParrot();
		}
		else if(string.equals("pig")) {
			currentEntity = new EntityPig(world);
			currentModel = new ModelPig();
		}
		else if(string.equals("rabbit")) {
			currentEntity = new EntityRabbit(world);
			currentModel = new ModelRabbit();
		}
		else if(string.equals("sheep")) {
			currentEntity = new EntitySheep(world);
			currentModel = new ModelSheep1();
		}
		else if(string.equals("shulker")) {
			currentEntity = new EntityShulker(world);
			currentModel = new ModelShulker();
		}
		else if(string.equals("skeleton")) {
			currentEntity = new EntitySkeleton(world);
			currentModel = new ModelSkeleton();
		}
		else if(string.equals("slime")) {
			currentEntity = new EntitySlime(world);
			currentModel = new ModelSlime(0);
		}
		else if(string.equals("spider")) {
			currentEntity = new EntitySpider(world);
			currentModel = new ModelSpider();
		}
		else if(string.equals("villager")) {
			currentEntity = new EntityVillager(world);
			currentModel = new ModelVillager(0);
		}
		else if(string.equals("wither")) {
			currentEntity = new EntityWither(world);
			currentModel = new ModelWither(0);
		}
		else if(string.equals("wolf")) {
			currentEntity = new EntityWolf(world);
			currentModel = new ModelWolf();
		}
		else if(string.equals("pigzombie")) {
			currentEntity = new EntityPigZombie(world);
			currentModel = new ModelZombie();
			texture = "minecraft:textures/entity/zombie_pigman.png";
			this.setSize(0.6F, 1.95F);
		}
		else if(string.equals("zombievillager")) {
			currentEntity = new EntityZombieVillager(world);
			currentModel = new ModelZombieVillager();
		}
		else if(string.equals("zombie")) {
			currentEntity = new EntityZombie(world);
			currentModel = new ModelZombie();
			texture = "minecraft:textures/entity/zombie.png";
		}

		if(currentEntity != null && !(currentEntity instanceof EntityPigZombie)) {
			this.setSize(currentEntity.width, currentEntity.height);
			this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(currentEntity.getAIMoveSpeed() * 1.2);
		}

		if(this.isPotionActive(PotionInit.GROWTH_EFFECT))
			this.removePotionEffect(PotionInit.GROWTH_EFFECT);
	}

	/*
	@Override
	protected SoundEvent getDeathSound() 
	{
		return SoundEvents.ENTITY_WITHER_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn)
	{

	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundEvents.ENTITY_WITHER_AMBIENT;
	}
	 */


	static class AIHurtByAggressor extends EntityAIHurtByTarget
	{
		public AIHurtByAggressor(EntityShapeShifterOld p_i45828_1_)
		{
			super(p_i45828_1_, true);
		}

		protected void setEntityAttackTarget(EntityCreature creatureIn, EntityLivingBase entityLivingBaseIn)
		{
			super.setEntityAttackTarget(creatureIn, entityLivingBaseIn);

			if (creatureIn instanceof EntityShapeShifterOld)
			{
				((EntityShapeShifterOld)creatureIn).becomeAngryAt(entityLivingBaseIn);
			}
		}
	}

	static class AITargetAggressor extends EntityAINearestAttackableTarget<EntityPlayer>
	{
		public AITargetAggressor(EntityShapeShifterOld p_i45829_1_)
		{
			super(p_i45829_1_, EntityPlayer.class, true);
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		public boolean shouldExecute()
		{
			return ((EntityShapeShifterOld)this.taskOwner).isAngry() && super.shouldExecute();
		}
	}

	/**
	 * Returns true if the mob is currently able to mate with the specified mob.
	 */
	@Override
	public boolean canMateWith(EntityAnimal otherAnimal)
	{
		if (otherAnimal == this)
		{
			return false;
		}
		else if (!this.isTamed())
		{
			return false;
		}
		else if (!(otherAnimal instanceof EntityShapeShifterOld))
		{
			return false;
		}
		else
		{
			EntityShapeShifterOld shapeShifter = (EntityShapeShifterOld)otherAnimal;

			if (!shapeShifter.isTamed())
			{
				return false;
			}
			else
			{
				return this.isInLove() && shapeShifter.isInLove();
			}
		}
	}

	/**
	 * Sets the active target the Task system uses for tracking
	 */
	public void setAttackTarget(@Nullable EntityLivingBase entitylivingbaseIn)
	{
		super.setAttackTarget(entitylivingbaseIn);

		if (entitylivingbaseIn == null)
		{
			this.setAngry(false);
		}
		else if (!this.isTamed())
		{
			this.setAngry(true);
		}
	}

	/**
	 * Sets whether this wolf is angry or not.
	 */
	public void setAngry(boolean angry)
	{
		byte b0 = ((Byte)this.dataManager.get(TAMED)).byteValue();

		if (angry)
		{
			this.dataManager.set(TAMED, Byte.valueOf((byte)(b0 | 2)));
		}
		else
		{
			this.dataManager.set(TAMED, Byte.valueOf((byte)(b0 & -3)));
		}
	}

	/*
	public boolean processInteract(EntityPlayer player, EnumHand hand)
	{
		ItemStack itemstack = player.getHeldItem(hand);

		if (!this.isTamed() && itemstack.getItem() == Items.POTATO && !this.isAngry())
		{
			if (!player.capabilities.isCreativeMode)
			{
				itemstack.shrink(1);
			}

			if (!this.world.isRemote)
			{
				if (this.rand.nextInt(3) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player))
				{
					this.setOwnerId(player.getUniqueID());
					//this.setTamedBy(player);
					this.navigator.clearPath();
					this.setAttackTarget((EntityLivingBase)null);
					//this.setHealth(200.0F);
					this.playTameEffect(true);
					this.world.setEntityState(this, (byte)7);
				}
				else
				{
					this.playTameEffect(false);
					this.world.setEntityState(this, (byte)6);
				}
			}

			return true;
		}

		return super.processInteract(player, hand);
	}
	 */

	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return stack.getItem() == Items.POTATO;
	}

	@Override
	public boolean shouldAttackEntity(EntityLivingBase target, EntityLivingBase owner)
	{
		if (!(target instanceof EntityBill))
		{
			if (target instanceof EntityShapeShifterOld)
			{
				EntityShapeShifterOld shapeshifter = (EntityShapeShifterOld)target;

				if (shapeshifter.isTamed() && shapeshifter.getOwner() == owner)
				{
					return false;
				}
			}

			if (target instanceof EntityPlayer && owner instanceof EntityPlayer && !((EntityPlayer)owner).canAttackPlayer((EntityPlayer)target))
			{
				return false;
			}
			else
			{
				return !(target instanceof AbstractHorse) || !((AbstractHorse)target).isTame();
			}
		}
		else
		{
			return false;
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