package entity;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

import handlers.LootTableHandler;
import handlers.SoundsHandler;
import init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityDonkey;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityMule;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.passive.HorseArmorType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityUnicorn extends EntityHorse implements net.minecraftforge.common.IShearable
{
	private static final UUID ATTACK_SPEED_BOOST_MODIFIER_UUID = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
	private static final AttributeModifier ATTACK_SPEED_BOOST_MODIFIER = (new AttributeModifier(ATTACK_SPEED_BOOST_MODIFIER_UUID, "Attacking speed boost", 0.05D, 0)).setSaved(false);
	protected static final IAttribute SPAWN_REINFORCEMENTS_CHANCE = (new RangedAttribute((IAttribute)null, "zombie.spawnReinforcements", 0.0D, 0.0D, 1.0D)).setDescription("Spawn Reinforcements Chance");
	private int angerLevel;
    private UUID angerTargetUUID;
    
    private static final UUID ARMOR_MODIFIER_UUID = UUID.fromString("556E1665-8B10-40C8-8F9D-CF9B1667F295");
    private static final DataParameter<Integer> HORSE_VARIANT = EntityDataManager.<Integer>createKey(EntityUnicorn.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> HORSE_ARMOR = EntityDataManager.<Integer>createKey(EntityUnicorn.class, DataSerializers.VARINT);
    private static final DataParameter<ItemStack> HORSE_ARMOR_STACK = EntityDataManager.<ItemStack>createKey(EntityUnicorn.class, DataSerializers.ITEM_STACK);
    private static final String[] HORSE_TEXTURES = new String[] {"textures/entity/horse/horse_white.png", "textures/entity/horse/horse_creamy.png", "textures/entity/horse/horse_chestnut.png", "textures/entity/horse/horse_brown.png", "textures/entity/horse/horse_black.png", "textures/entity/horse/horse_gray.png", "textures/entity/horse/horse_darkbrown.png"};
    private static final String[] HORSE_TEXTURES_ABBR = new String[] {"hwh", "hcr", "hch", "hbr", "hbl", "hgr", "hdb"};
    private static final String[] HORSE_MARKING_TEXTURES = new String[] {null, "textures/entity/horse/horse_markings_white.png", "textures/entity/horse/horse_markings_whitefield.png", "textures/entity/horse/horse_markings_whitedots.png", "textures/entity/horse/horse_markings_blackdots.png"};
    private static final String[] HORSE_MARKING_TEXTURES_ABBR = new String[] {"", "wo_", "wmo", "wdo", "bdo"};

    //Keeps track of shearing
    private static final DataParameter<Boolean> SHEARED = EntityDataManager.<Boolean>createKey(EntityUnicorn.class, DataSerializers.BOOLEAN);
    private EntityAIEatGrass entityAIEatGrass;
    private int unicornTimer;
    
	public EntityUnicorn(World par1World)
	{
		super(par1World);
		this.setSize(1.0F, 1.9F);
		this.experienceValue = 10;
	}
	
	public EntityUnicorn(World par1World, double x, double y, double z)
	{
		super(par1World);
		this.setSize(1.0F, 1.9F);
		this.experienceValue = 10;
		this.setPosition(x, y, z);
	}
	
	@Override
	protected void initEntityAI()
	{
		this.entityAIEatGrass = new EntityAIEatGrass(this);
		
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIAttackMelee(this, 1.0D, true));
		this.tasks.addTask(2, new EntityAIMate(this, 1.0D));
		this.tasks.addTask(3, new EntityAIFollowParent(this, 1.0D));
		this.tasks.addTask(4, this.entityAIEatGrass);
		this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(6, new EntityAIWanderAvoidWater(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		
		this.applyEntityAI();
	}
	
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(SHEARED, false);
    }

	protected void applyEntityAI() 
	{
		this.targetTasks.addTask(1, new EntityUnicorn.AIHurtByAggressor(this));
		this.targetTasks.addTask(2, new EntityUnicorn.AITargetAggressor(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] {}));
	}

	@Override
	protected void applyEntityAttributes() 
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
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
	
	@Override
	public void onLivingUpdate() {
		
		if (this.world.isRemote)
        {
            this.unicornTimer = Math.max(0, this.unicornTimer - 1);
        }
		
		super.onLivingUpdate();
	}
	
    /**
     * Handler for {@link World#setEntityState}
     */
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
        if (id == 10)
        {
            this.unicornTimer = 40;
        }
        else
        {
            super.handleStatusUpdate(id);
        }
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
		
		this.unicornTimer = this.entityAIEatGrass.getEatingGrassTimer();
	}

	@Override
	public boolean getCanSpawnHere()
	{
		return true;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn)
	{
		return SoundEvents.ENTITY_HORSE_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() 
	{
		return SoundEvents.ENTITY_HORSE_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn)
	{
		this.playSound(SoundEvents.ENTITY_HORSE_STEP, 0.15F, 1.0F);
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundsHandler.ENTITY_UNICORN_AMBIENT;
	}

	@Override
	protected ResourceLocation getLootTable()
	{
		return LootTableHandler.UNICORN;
	}

	static class AIHurtByAggressor extends EntityAIHurtByTarget
	{
		public AIHurtByAggressor(EntityUnicorn p_i45828_1_)
		{
			super(p_i45828_1_, true);
		}

		protected void setEntityAttackTarget(EntityCreature creatureIn, EntityLivingBase entityLivingBaseIn)
		{
			super.setEntityAttackTarget(creatureIn, entityLivingBaseIn);

			if (creatureIn instanceof EntityUnicorn)
			{
				((EntityUnicorn)creatureIn).becomeAngryAt(entityLivingBaseIn);
			}
		}
	}

	static class AITargetAggressor extends EntityAINearestAttackableTarget<EntityPlayer>
	{
		public AITargetAggressor(EntityUnicorn p_i45829_1_)
		{
			super(p_i45829_1_, EntityPlayer.class, true);
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		public boolean shouldExecute()
		{
			return ((EntityUnicorn)this.taskOwner).isAngry() && super.shouldExecute();
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
        compound.setInteger("Variant", this.getHorseVariant());

        if (!this.horseChest.getStackInSlot(1).isEmpty())
        {
            compound.setTag("ArmorItem", this.horseChest.getStackInSlot(1).writeToNBT(new NBTTagCompound()));
        }
        
        compound.setBoolean("Sheared", this.getSheared());
        
		compound.setShort("Anger", (short)this.angerLevel);

		if (this.angerTargetUUID != null)
			compound.setString("HurtBy", this.angerTargetUUID.toString());
		else
			compound.setString("HurtBy", "");
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

		
		this.setHorseVariant(compound.getInteger("Variant"));

        if (compound.hasKey("ArmorItem", 10))
        {
            ItemStack itemstack = new ItemStack(compound.getCompoundTag("ArmorItem"));

            if (!itemstack.isEmpty() && isArmor(itemstack))
            {
                this.horseChest.setInventorySlotContents(1, itemstack);
            }
        }

        this.updateHorseSlots();
		
		this.setSheared(compound.getBoolean("Sheared"));
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
	
	 /**
     * Returns true if the mob is currently able to mate with the specified mob.
     */
    public boolean canMateWith(EntityAnimal otherAnimal)
    {
        if (otherAnimal == this)
        {
            return false;
        }
        else if (!(otherAnimal instanceof EntityDonkey) && !(otherAnimal instanceof EntityHorse))
        {
            return false;
        }
        else
        {
            return this.canMate();
        }
    }
    
    /**
     * Return true if the horse entity ready to mate. (no rider, not riding, tame, adult, not steril...)
     */
    protected boolean canMate()
    {
        return !this.isBeingRidden() && !this.isRiding() && this.isTame() && !this.isChild() && this.getHealth() >= this.getMaxHealth() && this.isInLove();
    }
    
    @Override
    public boolean isChild() {
    	return false;
    }
    
    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner, natural spawning etc, but not called
     * when entity is reloaded from nbt. Mainly used for initializing attributes and inventory
     */
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
        int i;

        if (livingdata instanceof EntityHorse.GroupData)
        {
            i = ((EntityHorse.GroupData)livingdata).variant;
        }
        else
        {
            i = this.rand.nextInt(7);
            livingdata = new EntityHorse.GroupData(i);
        }

        return livingdata;
    }
    
    public EntityAgeable createChild(EntityAgeable ageable)
    {
        AbstractHorse abstracthorse;

        if (ageable instanceof EntityDonkey)
        {
            abstracthorse = new EntityMule(this.world);
        }
        else
        {
            EntityUnicorn entityUnicorn = (EntityUnicorn)ageable;
            abstracthorse = new EntityUnicorn(this.world);
        }

        this.setOffspringAttributes(ageable, abstracthorse);
        return abstracthorse;
    }

    public boolean wearsArmor()
    {
        return true;
    }

    public boolean isArmor(ItemStack stack)
    {
        return HorseArmorType.isHorseArmor(stack);
    }
    
    /**
     * returns true if a sheeps wool has been sheared
     */
    public boolean getSheared()
    {
        return this.dataManager.get(SHEARED);
    }
    
    public void setSheared(boolean sheared)
    {
    	this.dataManager.set(SHEARED, sheared);
    }

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
		return !this.getSheared() && !this.isChild();
	}

	@Override
    public java.util.List<ItemStack> onSheared(ItemStack item, net.minecraft.world.IBlockAccess world, BlockPos pos, int fortune)
    {
        this.setSheared(true);
        int i = 4 + this.rand.nextInt(5);

        java.util.List<ItemStack> ret = new java.util.ArrayList<ItemStack>();
        for (int j = 0; j < i; ++j)
            ret.add(new ItemStack(Item.getItemFromBlock(BlockInit.UNICORNHAIR), 1));

        this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
        return ret;
    }
	
	@SideOnly(Side.CLIENT)
    public float getHeadRotationPointY(float p_70894_1_)
    {
        if (this.unicornTimer <= 0)
        {
            return 0.0F;
        }
        else if (this.unicornTimer >= 4 && this.unicornTimer <= 36)
        {
            return 1.0F;
        }
        else
        {
            return this.unicornTimer < 4 ? ((float)this.unicornTimer - p_70894_1_) / 4.0F : -((float)(this.unicornTimer - 40) - p_70894_1_) / 4.0F;
        }
    }

    @SideOnly(Side.CLIENT)
    public float getHeadRotationAngleX(float p_70890_1_)
    {
        if (this.unicornTimer > 4 && this.unicornTimer <= 36)
        {
            float f = ((float)(this.unicornTimer - 4) - p_70890_1_) / 32.0F;
            return ((float)Math.PI / 5F) + ((float)Math.PI * 7F / 100F) * MathHelper.sin(f * 28.7F);
        }
        else
        {
            return this.unicornTimer > 0 ? ((float)Math.PI / 5F) : this.rotationPitch * 0.017453292F;
        }
    }
    
    /**
     * This function applies the benefits of growing back wool and faster growing up to the acting entity. (This
     * function is used in the AIEatGrass)
     */
    @Override
    public void eatGrassBonus()
    {
        this.setSheared(false);

        if (this.isChild())
        {
            this.addGrowth(60);
        }
    }
}