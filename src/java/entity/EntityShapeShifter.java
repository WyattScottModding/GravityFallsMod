package entity;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import init.PotionInit;
import io.netty.buffer.ByteBuf;
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
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBlaze;
import net.minecraft.client.model.ModelChicken;
import net.minecraft.client.model.ModelCow;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.model.ModelEnderman;
import net.minecraft.client.model.ModelGhast;
import net.minecraft.client.model.ModelLlama;
import net.minecraft.client.model.ModelMagmaCube;
import net.minecraft.client.model.ModelOcelot;
import net.minecraft.client.model.ModelParrot;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRabbit;
import net.minecraft.client.model.ModelShulker;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.model.ModelSquid;
import net.minecraft.client.model.ModelVex;
import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.model.ModelWither;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.model.ModelZombieVillager;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityLlama;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import network.MessageShapeShifterModel;
import network.MessageShapeShifterUpdate;
import network.Messages;

public class EntityShapeShifter extends EntityAnimal implements IEntityAdditionalSpawnData
{	
	public ModelBase currentModel = new ModelShapeShifter();
	public String texture = Reference.MODID + ":textures/entity/shapeshifter.png";

	public EntityLivingBase currentEntity = this;

	public EntityList entityList = new EntityList();

	private int angerLevel;
	private UUID angerTargetUUID;

	//Flying creature stuff
	private BlockPos spawnPosition;


	public EntityShapeShifter(World par1World)
	{
		super(par1World);
		this.setSize(1.8F, 2.8F);
		this.experienceValue = 40;
		this.stepHeight = 1;
		entityList.add(this);
	}

	public EntityShapeShifter(World par1World, double x, double y, double z)
	{
		super(par1World);
		this.setSize(1.8F, 2.8F);
		this.experienceValue = 40;
		this.stepHeight = 1;
		this.setPosition(x, y, z);
	}

	@Override
	protected void initEntityAI()
	{
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, true));
		this.tasks.addTask(3, new EntityAIMate(this, 1.0D));
		this.tasks.addTask(4, new EntityAITempt(this, 1.25D, Items.POTATO, false));
		this.tasks.addTask(5, new EntityAIFollowParent(this, 1.25D));
		this.tasks.addTask(7, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(8, new EntityAIWanderAvoidWater(this, 1.0D));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));

		this.applyEntityAI();
	}

	protected void applyEntityAI() 
	{
		this.targetTasks.addTask(1, new EntityShapeShifter.AIHurtByAggressor(this));
		this.targetTasks.addTask(2, new EntityShapeShifter.AITargetAggressor(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] {}));
	}

	@Override
	public EntityShapeShifter createChild(EntityAgeable ageable)
	{
		return new EntityShapeShifter(this.world);
	}

	@Override
	protected void applyEntityAttributes() 
	{
		super.applyEntityAttributes();

		if(this.isChild()) {
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
			this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
			this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
		}
		else {
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D);
			this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
			this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
		}

		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0D);
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

	public void setEntity(EntityLivingBase entity) {

		if(this.isPotionActive(PotionInit.GROWTH_EFFECT))
			this.removePotionEffect(PotionInit.GROWTH_EFFECT);
		if(this.isPotionActive(PotionInit.SHAPESHIFTER_EFFECT))
			this.removePotionEffect(PotionInit.SHAPESHIFTER_EFFECT);

		if(entity instanceof EntitySilverfish || entity instanceof EntityPigZombie) {
			this.setSize(0.8F, 1.8F);
			this.height = 1.8F;
			this.width = 0.8F;
		}
		else if(entity instanceof EntitySlime) {
			this.setSize(0.51F, 0.51F);
			this.height = 0.51F;
			this.width = 0.51F;
		}
		else {
			this.setSize(entity.width, entity.height);
			this.height = entity.height;
			this.width = entity.width;
		}

		if(entity instanceof EntityPigZombie)
			texture = "minecraft:textures/entity/zombie_pigman.png";
		else if(entity instanceof EntityZombie)
			texture = "minecraft:textures/entity/zombie/zombie.png";

		this.addPotionEffect(new PotionEffect(PotionInit.SHAPESHIFTER_EFFECT, 999999, (int)(this.height - 2.8F) * 100, true, false));

		currentEntity = entity;

		if(!world.isRemote) {

			Messages.INSTANCE.sendToAllTracking(new MessageShapeShifterUpdate(entityToInt(currentEntity), this.posX, this.posY, this.posZ), new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 100));
			Messages.INSTANCE.sendToAllTracking(new MessageShapeShifterModel(modelToInt(currentModel), this.posX, this.posY, this.posZ), new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 100));
		}
	}

	@Override
	public void onUpdate() {
		if(currentEntity instanceof EntityVex)
			this.noClip = true;

		super.onUpdate();

		this.noClip = false;

		this.updateArmSwingProgress();
		float f = this.getBrightness();

		if (f > 0.5F)
		{
			this.idleTime += 2;
		}

		if(isChild()) {
			if(world.getWorldTime() % 600 == 0) {
				changeModel();

				//Heal
				if(this.getHealth() <= 98)
					this.setHealth(this.getHealth() + 2);
			}
		}
		else {
			if(world.getWorldTime() % 600 == 0) {
				changeModel();

				//Heal
				if(this.getHealth() <= 199)
					this.setHealth(this.getHealth() + 1);
			}
		}

		if(world.getWorldTime() % 5 == 0) {
			addEntity();
			defence();	

			if(world.getWorldTime() % 4 == 0)
				findEntity();

			if(!world.isRemote) {

				Messages.INSTANCE.sendToAllTracking(new MessageShapeShifterUpdate(entityToInt(currentEntity), this.posX, this.posY, this.posZ), new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 100));
				Messages.INSTANCE.sendToAllTracking(new MessageShapeShifterModel(modelToInt(currentModel), this.posX, this.posY, this.posZ), new TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 100));
			}
		}

		if(currentEntity instanceof EntityEyeBat || currentEntity instanceof EntityEyeBatHuge || currentEntity instanceof EntityVex || currentEntity instanceof EntityGhast || currentEntity instanceof EntityWither || currentEntity instanceof EntityBlaze || currentEntity instanceof EntityParrot)
			this.motionY *= 0.6000000238418579D;
		else if(currentEntity instanceof EntityEnderman) {
			if(this.world.isRemote)
			{
				for (int i = 0; i < 2; ++i)
				{
					this.world.spawnParticle(EnumParticleTypes.PORTAL, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D);
				}
			}
			if (this.isWet())
			{
				this.attackEntityFrom(DamageSource.DROWN, 1.0F);

				if(world.getWorldTime() % 2 == 0)
					this.changeModel();
				else
					teleportRandomly();
			}
			if(world.getWorldTime() % 300 == 0)
				teleportRandomly();
		}
		else if(currentEntity instanceof EntityBlaze) {
			if (this.isWet())
			{
				this.attackEntityFrom(DamageSource.DROWN, 1.0F);
				this.changeModel();
			}

			if (this.world.isRemote)
			{
				for (int i = 0; i < 2; ++i)
				{
					this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + (this.rand.nextDouble() - 0.5D) * (double)this.width, this.posY + this.rand.nextDouble() * (double)this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
					this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
				}
			}
		}
		else if(currentEntity instanceof EntityWither) {
			for (int l = 0; l < 3; ++l)
			{
				double d10 = this.getHeadX(l);
				double d2 = this.getHeadY(l);
				double d4 = this.getHeadZ(l);
				this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D);

				if (this.world.rand.nextInt(4) == 0)
				{
					this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.699999988079071D, 0.699999988079071D, 0.5D);
				}
			}
		}
		if(currentEntity instanceof EntityPigZombie || currentEntity instanceof EntityGhast || currentEntity instanceof EntityMagmaCube || currentEntity instanceof EntityWither || currentEntity instanceof EntityBlaze)
			this.isImmuneToFire = true;
		else
			this.isImmuneToFire = false;

		//Changes to another entity if suffocating
		if(this.isEntityInsideOpaqueBlock()) {
			if(entityList.containsEntity(new EntityVex(world))) {
				currentModel = new ModelVex();
				setEntity(new EntityVex(world));
			}
			else if(entityList.containsEntity(new EntityEnderman(world))) {
				currentModel = new ModelEnderman(0);
				setEntity(new EntityEnderman(world));
				teleportRandomly();
			}
			else if(entityList.containsEntity(new EntitySlime(world))) {
				currentModel = new ModelSlime(0);
				setEntity(new EntitySlime(world));
			}
			else if(entityList.containsEntity(new EntityGnome(world))) {
				currentModel = new ModelGnome();
				setEntity(new EntityGnome(world));
			}
			else if(entityList.containsEntity(new EntityPig(world))) {
				currentModel = new ModelPig();
				setEntity(new EntityPig(world));
			}
			else if(entityList.containsEntity(new EntityRabbit(world))) {
				currentModel = new ModelRabbit();
				setEntity(new EntityRabbit(world));
			}
			else if(entityList.containsEntity(new EntityParrot(world))) {
				currentModel = new ModelParrot();
				setEntity(new EntityParrot(world));
			}
			else if(entityList.containsEntity(new EntityOcelot(world))) {
				currentModel = new ModelOcelot();
				setEntity(new EntityOcelot(world));
			}
			else if(entityList.containsEntity(new EntityKeyhole(world))) {
				currentModel = new ModelKeyhole();
				setEntity(new EntityKeyhole(world));
			}
			else if(entityList.containsEntity(new EntityMagmaCube(world))) {
				currentModel = new ModelMagmaCube();
				setEntity(new EntityMagmaCube(world));
			}
			else if(entityList.containsEntity(new EntityEyeBat(world))) {
				currentModel = new ModelEyeBat();
				setEntity(new EntityEyeBat(world));
			}
		}
	}

	private double getHeadX(int p_82214_1_)
	{
		if (p_82214_1_ <= 0)
		{
			return this.posX;
		}
		else
		{
			float f = (this.renderYawOffset + (float)(180 * (p_82214_1_ - 1))) * 0.017453292F;
			float f1 = MathHelper.cos(f);
			return this.posX + (double)f1 * 1.3D;
		}
	}

	private double getHeadY(int p_82208_1_)
	{
		return p_82208_1_ <= 0 ? this.posY + 3.0D : this.posY + 2.2D;
	}

	private double getHeadZ(int p_82213_1_)
	{
		if (p_82213_1_ <= 0)
		{
			return this.posZ;
		}
		else
		{
			float f = (this.renderYawOffset + (float)(180 * (p_82213_1_ - 1))) * 0.017453292F;
			float f1 = MathHelper.sin(f);
			return this.posZ + (double)f1 * 1.3D;
		}
	}

	@Override
	protected void updateAITasks() {
		super.updateAITasks();

		if(currentEntity instanceof EntityEyeBat || currentEntity instanceof EntityEyeBatHuge || currentEntity instanceof EntityVex || currentEntity instanceof EntityGhast || currentEntity instanceof EntityWither || currentEntity instanceof EntityBlaze || currentEntity instanceof EntityParrot)
		{
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

	private void findEntity() {
		int x = (int) this.posX;
		int y = (int) this.posY;
		int z = (int) this.posZ;

		AxisAlignedBB bb = new AxisAlignedBB(x - 3, y - 3, z - 3, x + 3, y + 3, z + 3);

		List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(this, bb);

		for(Entity entity : list) {
			if(entity instanceof EntityLivingBase) {
				EntityLivingBase entityLiving = (EntityLivingBase) entity;

				entityList.add(entityLiving);
			}
		}
	}

	public boolean isBurning()
	{
		boolean flag = this.world != null;
		return !this.isImmuneToFire && (flag && this.getFlag(0));
	}

	private void defence() {
		if(this.isBurning()) {
			if(entityList.containsEntity(new EntityPigZombie(world))) {
				currentModel = new ModelZombie();
				texture = "minecraft:textures/entity/zombie_pigman.png";
				setEntity(new EntityPigZombie(world));
			}
			else if(entityList.containsEntity(new EntityMagmaCube(world))) {
				currentModel = new ModelMagmaCube();
				setEntity(new EntityMagmaCube(world));
			}
			else if(entityList.containsEntity(new EntityBlaze(world))) {
				currentModel = new ModelBlaze();
				setEntity(new EntityBlaze(world));
			}
			else if(entityList.containsEntity(new EntityGhast(world))) {
				currentModel = new ModelGhast();
				setEntity(new EntityGhast(world));
			}
			else if(entityList.containsEntity(new EntityWither(world))) {
				currentModel = new ModelWither(0);
				setEntity(new EntityWither(world));
			}
		}
		if(inWater) {
			if(entityList.containsEntity(new EntitySquid(world))) {
				currentModel = new ModelSquid();
				setEntity(new EntitySquid(world));
			}
		}
	}

	@Override
	public boolean canBreatheUnderwater() {
		return currentEntity instanceof EntitySquid;
	}

	public EntityLivingBase addEntity()
	{
		BlockPos pos = this.getPosition();

		float yaw = this.rotationYaw;
		float pitch = this.rotationPitch;

		List<Entity> list = null;
		EntityLivingBase closestEntity = null;

		for(int f = 0; f <= 25; f++)
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

				if(entity instanceof EntityPlayer)
					entityList.add(new EntitySilverfish(world));
				else if(entity instanceof EntityLivingBase)
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

			if(entity instanceof EntitySilverfish)
				currentModel = new ModelPlayer(0.0F, true);
			else if(entity instanceof EntityBill)
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
			else if(entity instanceof EntityUnicorn)
				currentModel = new ModelUnicorn();
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
			else if(entity instanceof EntityMagmaCube)
				currentModel = new ModelMagmaCube();
			else {
				entityList.remove(entity);
				changeModel();
				return;
			}
			setEntity(entity);

		}
		else {
			currentModel = new ModelShapeShifter();
			currentEntity = this;
			entityList.add(this);
		}
	}

	private EntityLivingBase getEntity() {
		int random = (int) (Math.random() * entityList.size());

		if(entityList.get(random) != null) {
			return entityList.get(random);
		}
		else {
			entityList.remove(random);
			return getEntity();
		}
	}

	@Override
	public boolean hitByEntity(Entity entityIn) {

		if((int)(Math.random() * 2) == 0)
			changeModel();

		return super.hitByEntity(entityIn);
	}

	@Override
	public boolean getCanSpawnHere()
	{
		return true;
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);

		compound.setShort("Anger", (short)this.angerLevel);

		if (this.angerTargetUUID != null)
			compound.setString("HurtBy", this.angerTargetUUID.toString());
		else
			compound.setString("HurtBy", "");

		compound.setBoolean("bill", entityList.containsEntity(new EntityCreeper(world)));
		compound.setBoolean("eightball", entityList.containsEntity(new EntityEightBall(world)));
		compound.setBoolean("eyebat", entityList.containsEntity(new EntityEyeBat(world)));
		compound.setBoolean("eyebathuge", entityList.containsEntity(new EntityEyeBatHuge(world)));
		compound.setBoolean("gnome", entityList.containsEntity(new EntityGnome(world)));
		compound.setBoolean("hidebehind", entityList.containsEntity(new EntityHideBehind(world)));
		compound.setBoolean("keyhole", entityList.containsEntity(new EntityKeyhole(world)));
		compound.setBoolean("timebaby", entityList.containsEntity(new EntityTimeBaby(world)));
		compound.setBoolean("unicorn", entityList.containsEntity(new EntityUnicorn(world)));

		compound.setBoolean("blaze", entityList.containsEntity(new EntityBlaze(world)));
		compound.setBoolean("chicken", entityList.containsEntity(new EntityChicken(world)));
		compound.setBoolean("cow", entityList.containsEntity(new EntityCow(world)));
		compound.setBoolean("creeper", entityList.containsEntity(new EntityCreeper(world)));
		compound.setBoolean("enderman", entityList.containsEntity(new EntityEnderman(world)));
		compound.setBoolean("ghast", entityList.containsEntity(new EntityGhast(world)));
		compound.setBoolean("vex", entityList.containsEntity(new EntityVex(world)));
		compound.setBoolean("ocelot", entityList.containsEntity(new EntityOcelot(world)));
		compound.setBoolean("llama", entityList.containsEntity(new EntityLlama(world)));
		compound.setBoolean("parrot", entityList.containsEntity(new EntityParrot(world)));
		compound.setBoolean("pig", entityList.containsEntity(new EntityPig(world)));
		compound.setBoolean("rabbit", entityList.containsEntity(new EntityRabbit(world)));
		compound.setBoolean("shulker", entityList.containsEntity(new EntityShulker(world)));
		compound.setBoolean("skeleton", entityList.containsEntity(new EntitySkeleton(world)));
		compound.setBoolean("slime", entityList.containsEntity(new EntitySlime(world)));
		compound.setBoolean("spider", entityList.containsEntity(new EntitySpider(world)));
		compound.setBoolean("villager", entityList.containsEntity(new EntityVillager(world)));
		compound.setBoolean("wither", entityList.containsEntity(new EntityWither(world)));
		compound.setBoolean("pigzombie", entityList.containsEntity(new EntityPigZombie(world)));
		compound.setBoolean("zombievillager", entityList.containsEntity(new EntityZombieVillager(world)));
		compound.setBoolean("zombie", entityList.containsEntity(new EntityZombie(world)));	
		compound.setBoolean("magmacube", entityList.containsEntity(new EntityMagmaCube(world)));	
		compound.setBoolean("player", entityList.containsEntity(new EntitySilverfish(world)));	

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
		if(compound.getBoolean("unicorn")) 
			entityList.add(new EntityUnicorn(world));

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
		if(compound.getBoolean("pigzombie"))
			entityList.add(new EntityPigZombie(world));
		if(compound.getBoolean("zombievillager"))
			entityList.add(new EntityZombieVillager(world));
		if(compound.getBoolean("zombie"))
			entityList.add(new EntityZombie(world));	
		if(compound.getBoolean("magmacube"))
			entityList.add(new EntityMagmaCube(world));	
		if(compound.getBoolean("player"))
			entityList.add(new EntitySilverfish(world));	


		String string = "";
		if(compound.hasKey("currentEntity"))
			string = compound.getString("currentEntity").substring(6);


		if(string.equals("bill")) {
			currentEntity = new EntityShapeShifter(world);
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
		else if(string.equals("unicorn")) {
			currentEntity = new EntityUnicorn(world);
			currentModel = new ModelUnicorn();
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
		else if(string.equals("magmacube")) {
			currentEntity = new EntityMagmaCube(world);
			currentModel = new ModelMagmaCube();
		}
		else if(string.equals("silverfish")) {
			currentEntity = new EntitySilverfish(world);
			currentModel = new ModelPlayer(0.0F, true);
		}

		setEntity(currentEntity);
	}

	@Override
	protected SoundEvent getDeathSound() 
	{
		return SoundEvents.ENTITY_GENERIC_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn)
	{
		this.playSound(SoundEvents.ENTITY_POLAR_BEAR_STEP, 0.15F, 1.0F);
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return null;
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn)
	{
		return SoundEvents.ENTITY_GENERIC_HURT;
	}

	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return stack.getItem() == Items.POTATO;
	}

	static class AIHurtByAggressor extends EntityAIHurtByTarget
	{
		public AIHurtByAggressor(EntityShapeShifter p_i45828_1_)
		{
			super(p_i45828_1_, true);
		}

		protected void setEntityAttackTarget(EntityCreature creatureIn, EntityLivingBase entityLivingBaseIn)
		{
			super.setEntityAttackTarget(creatureIn, entityLivingBaseIn);

			if (creatureIn instanceof EntityShapeShifter)
			{
				((EntityShapeShifter)creatureIn).becomeAngryAt(entityLivingBaseIn);
			}
		}
	}

	static class AITargetAggressor extends EntityAINearestAttackableTarget<EntityPlayer>
	{
		public AITargetAggressor(EntityShapeShifter p_i45829_1_)
		{
			super(p_i45829_1_, EntityPlayer.class, true);
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		public boolean shouldExecute()
		{
			return ((EntityShapeShifter)this.taskOwner).isAngry() && super.shouldExecute();
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

	public int entityToInt(EntityLivingBase entity) {

		if(entity instanceof EntitySilverfish)
			return 0;
		else if(entity instanceof EntityBill)
			return 1;
		else if(entity instanceof EntityBlaze)
			return 2;
		else if(entity instanceof EntityChicken)
			return 3;
		else if(entity instanceof EntityCow)
			return 4;
		else if(entity instanceof EntityCreeper)
			return 5;
		else if(entity instanceof EntityEightBall)
			return 6;
		else if(entity instanceof EntityEnderman)
			return 7;
		else if(entity instanceof EntityEyeBat)
			return 8;
		else if(entity instanceof EntityEyeBatHuge)
			return 9;
		else if(entity instanceof EntityGhast)
			return 10;
		else if(entity instanceof EntityGnome)
			return 11;
		else if(entity instanceof EntityHideBehind)
			return 12;
		else if(entity instanceof EntityKeyhole)
			return 13;
		else if(entity instanceof EntityLlama)
			return 14;
		else if(entity instanceof EntityMagmaCube)
			return 15;
		else if(entity instanceof EntityOcelot)
			return 16;
		else if(entity instanceof EntityParrot)
			return 17;
		else if(entity instanceof EntityPig)
			return 18;
		else if(entity instanceof EntityPigZombie)
			return 19;
		else if(entity instanceof EntityRabbit)
			return 20;
		else if(entity instanceof EntityShulker)
			return 21;
		else if(entity instanceof EntitySkeleton)
			return 22;
		else if(entity instanceof EntitySlime)
			return 23;
		else if(entity instanceof EntitySpider)
			return 24;
		else if(entity instanceof EntitySquid)
			return 25;
		else if(entity instanceof EntityTimeBaby)
			return 26;
		else if(entity instanceof EntityUnicorn)
			return 29;
		else if(entity instanceof EntityVex)
			return 30;
		else if(entity instanceof EntityVillager)
			return 31;
		else if(entity instanceof EntityWither)
			return 32;
		else if(entity instanceof EntityZombie)
			return 33;
		else if(entity instanceof EntityZombieVillager)
			return 34;
		else 
			return -1;
	}

	public EntityLivingBase intToEntity(int num) {

		switch(num) {
		case 0:
			return new EntitySilverfish(world);
		case 1:
			return new EntityBill(world);
		case 2:
			return new EntityBlaze(world);
		case 3:
			return new EntityChicken(world);
		case 4:
			return new EntityCow(world);
		case 5:
			return new EntityCreeper(world);
		case 6:
			return new EntityEightBall(world);
		case 7:
			return new EntityEnderman(world);
		case 8:
			return new EntityEyeBat(world);
		case 9:
			return new EntityEyeBatHuge(world);
		case 10:
			return new EntityGhast(world);
		case 11:
			return new EntityGnome(world);
		case 12:
			return new EntityHideBehind(world);
		case 13:
			return new EntityKeyhole(world);
		case 14:
			return new EntityLlama(world);
		case 15:
			return new EntityMagmaCube(world);
		case 16:
			return new EntityOcelot(world);
		case 17:
			return new EntityParrot(world);
		case 18:
			return new EntityPig(world);
		case 19:
			return new EntityPigZombie(world);
		case 20:
			return new EntityRabbit(world);
		case 21:
			return new EntityShulker(world);
		case 22:
			return new EntitySkeleton(world);
		case 23:
			return new EntitySlime(world);
		case 24:
			return new EntitySpider(world);
		case 25:
			return new EntitySquid(world);
		case 26:
			return new EntityTimeBaby(world);
		case 29:
			return new EntityUnicorn(world);
		case 30:
			return new EntityVex(world);
		case 31:
			return new EntityVillager(world);
		case 32:
			return new EntityWither(world);
		case 33:
			return new EntityZombie(world);		
		case 34:
			return new EntityZombieVillager(world);		
		default:
			return new EntityShapeShifter(world);
		}
	}

	public int modelToInt(ModelBase model) {

		if(model instanceof ModelPlayer)
			return 0;
		else if(model instanceof ModelBill)
			return 1;
		else if(model instanceof ModelBlaze)
			return 2;
		else if(model instanceof ModelChicken)
			return 3;
		else if(model instanceof ModelCow)
			return 4;
		else if(model instanceof ModelCreeper)
			return 5;
		else if(model instanceof ModelEightBall)
			return 6;
		else if(model instanceof ModelEnderman)
			return 7;
		else if(model instanceof ModelEyeBat)
			return 8;
		else if(model instanceof ModelEyeBatHuge)
			return 9;
		else if(model instanceof ModelGhast)
			return 10;
		else if(model instanceof ModelGnome)
			return 11;
		else if(model instanceof ModelHideBehind)
			return 12;
		else if(model instanceof ModelKeyhole)
			return 13;
		else if(model instanceof ModelLlama)
			return 14;
		else if(model instanceof ModelMagmaCube)
			return 15;
		else if(model instanceof ModelOcelot)
			return 16;
		else if(model instanceof ModelParrot)
			return 17;
		else if(model instanceof ModelPig)
			return 18;
		else if(model instanceof ModelZombie) {
			texture = "minecraft:textures/entity/zombie_pigman.png";
			return 19;
		}
		else if(model instanceof ModelRabbit)
			return 20;
		else if(model instanceof ModelShulker)
			return 21;
		else if(model instanceof ModelSkeleton)
			return 22;
		else if(model instanceof ModelSlime)
			return 23;
		else if(model instanceof ModelSpider)
			return 24;
		else if(model instanceof ModelSquid)
			return 25;
		else if(model instanceof ModelTimeBaby)
			return 26;
		else if(model instanceof ModelUnicorn)
			return 29;
		else if(model instanceof ModelVex)
			return 30;
		else if(model instanceof ModelVillager)
			return 31;
		else if(model instanceof ModelWither)
			return 32;
		else if(model instanceof ModelZombie) {
			texture = "minecraft:textures/entity/zombie/zombie.png";
			return 33;
		}
		else if(model instanceof ModelZombieVillager)
			return 34;
		else 
			return -1;
	}

	public ModelBase intToModel(int num) {

		switch(num) {
		case 0:
			return new ModelPlayer(0, true);
		case 1:
			return new ModelBill();
		case 2:
			return new ModelBlaze();
		case 3:
			return new ModelChicken();
		case 4:
			return new ModelCow();
		case 5:
			return new ModelCreeper();
		case 6:
			return new ModelEightBall();
		case 7:
			return new ModelEnderman(0);
		case 8:
			return new ModelEyeBat();
		case 9:
			return new ModelEyeBatHuge();
		case 10:
			return new ModelGhast();
		case 11:
			return new ModelGnome();
		case 12:
			return new ModelHideBehind();
		case 13:
			return new ModelKeyhole();
		case 14:
			return new ModelLlama(0);
		case 15:
			return new ModelMagmaCube();
		case 16:
			return new ModelOcelot();
		case 17:
			return new ModelParrot();
		case 18:
			return new ModelPig();
		case 19:
			return new ModelZombie();
		case 20:
			return new ModelRabbit();
		case 21:
			return new ModelShulker();
		case 22:
			return new ModelSkeleton();
		case 23:
			return new ModelSlime(0);
		case 24:
			return new ModelSpider();
		case 25:
			return new ModelSquid();
		case 26:
			return new ModelTimeBaby();
		case 29:
			return new ModelUnicorn();
		case 30:
			return new ModelVex(0);
		case 31:
			return new ModelVillager(0);
		case 32:
			return new ModelWither(0);
		case 33:
			return new ModelZombie();		
		case 34:
			return new ModelZombieVillager();		
		default:
			return new ModelShapeShifter();
		}
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeInt(this.entityToInt(currentEntity));
		buffer.writeInt(this.modelToInt(currentModel));		
	}

	@Override
	public void readSpawnData(ByteBuf additionalData) {
		currentEntity = this.intToEntity(additionalData.readInt());
		currentModel = this.intToModel(additionalData.readInt());
	}
}