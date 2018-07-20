package entity;

import handlers.LootTableHandler;
import handlers.SoundsHandler;
import init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityEightBall extends EntitySpider
{

	private static final DataParameter<Float> DATA_HEALTH_ID = EntityDataManager.<Float>createKey(EntityWolf.class, DataSerializers.FLOAT);


	public EntityEightBall(World par1World)
	{
		super(par1World);
		this.setSize(3.0F, 6.0F);
		this.fallDistance = 0;
	}


	@Override
	protected void applyEntityAttributes() 
	{

		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(500.0D);

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);

		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7.0D);;
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


	@Override
	protected ResourceLocation getLootTable()
	{
		return null;

	}

	@Override
	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
	{
		//this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
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
