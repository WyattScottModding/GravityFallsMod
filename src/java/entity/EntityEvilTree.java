package entity;

import handlers.LootTableHandler;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityEvilTree extends EntityWeirdMob
{
	private int counter;
	public int texture;
	
	public EntityEvilTree(World par1World)
	{
		super(par1World);
		this.setSize(1.0F, 1.0F);
		this.experienceValue = 0;
		this.counter = 0;
	}
	
	public EntityEvilTree(World world, int x, int y, int z)
	{
		this(world);
		this.setPosition(x, y, z);
	}

	@Override
	protected void initEntityAI()
    {
        super.initEntityAI();
        this.applyEntityAI();
    }
	
	protected void applyEntityAI() 
	{
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] {}));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
	}

	@Override
	protected void applyEntityAttributes() 
	{
		super.applyEntityAttributes();

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.45D);

		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);;
	}
	
	@Override
	public void onUpdate() {
		if(counter >= 0 && counter < 100)
			texture = 0;
		else if(counter >= 100 && counter < 101)
			texture = 1;
		else if(counter >= 101 && counter < 115)
			texture = 2;
		else
			texture = 1;
		
		if(counter < 115)
			counter++;
		else
			counter = 0;
		
		super.onUpdate();
	}

	@Override
	protected SoundEvent getDeathSound() 
	{
		return SoundEvents.BLOCK_WOOD_BREAK;
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundEvents.BLOCK_WOOD_STEP;
	}
	
	@Override
	protected ResourceLocation getLootTable() {
		return LootTableHandler.EVIL_TREE;
	}
}