package entity;

import handlers.LootTableHandler;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityKeyhole extends EntityWeirdMob
{

	public EntityKeyhole(World par1World)
	{
		super(par1World);
		this.setSize(0.5F, 0.5F);
		this.experienceValue = 100;
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

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(150.0D);

		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);

		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);;
	}
}