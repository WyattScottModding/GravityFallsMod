package entity;

import java.util.Set;

import models.ModelShapeShifter;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityForget extends EntityThrowable
{
	private World world = null;
	private int timer = 50;

	public EntityForget(World worldIn)
	{
		super(worldIn);
		this.world = worldIn;
	}

	public EntityForget(World worldIn, EntityLivingBase throwerIn)
	{
		super(worldIn, throwerIn);
		this.world = worldIn;
	}

	public EntityForget(World worldIn, EntityLivingBase throwerIn, double x, double y, double z)
	{
		super(worldIn, throwerIn);
		this.world = worldIn;

		this.posX = x;
		this.posY = y;
		this.posZ = z;
	}

	public EntityForget(World worldIn, double x, double y, double z)
	{
		super(worldIn, x, y, z);
		this.world = worldIn;
	}

	/**
	 * Handler for {@link World#setEntityState}
	 */

	@SideOnly(Side.CLIENT)
	public void handleStatusUpdate(byte id)
	{
		if (id == 3)
		{
			for (int i = 0; i < 8; ++i)
			{
				this.world.spawnParticle(EnumParticleTypes.CRIT_MAGIC, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	protected void onImpact(RayTraceResult result) 
	{
		if (result.entityHit instanceof EntityLiving && !(result.entityHit instanceof EntityBill) && !(result.entityHit instanceof EntitySecurityDroid))
		{
			EntityLivingBase entity = (EntityLivingBase)result.entityHit;

			EntityLiving entityLiving = (EntityLiving) entity;

			ItemStack stack = entityLiving.getItemStackFromSlot(EntityEquipmentSlot.HEAD);

			ItemArmor armor = null;

			if(stack.getItem() instanceof ItemArmor)
			{
				armor = (ItemArmor) stack.getItem();
			}

			ArmorMaterial material = ArmorMaterial.IRON;

			if(armor == null || !(armor.getArmorMaterial() == material))
			{
				entityLiving.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 6000));
				entityLiving.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 6000));
				entityLiving.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 6000));
				entityLiving.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 20));

				//entityLiving.setNoAI(true);
				//entityLiving.setSilent(true);

				if(entity instanceof EntityShapeShifter)
				{
					EntityShapeShifter shapeShifter = (EntityShapeShifter) entity;

					shapeShifter.entityList = new EntityList();
					shapeShifter.entityList.add(new EntityShapeShifter(world));
					shapeShifter.currentEntity = new EntityShapeShifter(world);
					shapeShifter.currentModel = new ModelShapeShifter();
					shapeShifter.hasPlayer = false;
				}
				else if(entityLiving instanceof EntityMob)
				{
					EntityMob mob = (EntityMob) entityLiving;

					Set <EntityAITasks.EntityAITaskEntry> entries = mob.tasks.taskEntries;					

					if(entries != null && !entries.isEmpty()) {
						for(EntityAITasks.EntityAITaskEntry element : entries) {
							mob.tasks.removeTask(element.action);
						}
					}

					Set <EntityAITasks.EntityAITaskEntry> targetEntries = mob.tasks.taskEntries;					

					if(targetEntries != null && !targetEntries.isEmpty()) {
						for(EntityAITasks.EntityAITaskEntry element : targetEntries) {
							mob.targetTasks.removeTask(element.action);
						}
					}

					mob.tasks.addTask(2, new EntityAILookIdle(mob));
					mob.tasks.addTask(1, new EntityAIWanderAvoidWater(mob, 1.0D));
				}
				else if(entity instanceof EntityPlayer)
				{
					EntityPlayer player = (EntityPlayer) entity;

					if(this.thrower != null && player != this.thrower)
					{
						boolean searching = true;
						for(int i = 0; searching; i++)
						{
							int j = (int)(Math.random() * player.inventory.getSizeInventory());

							if(!player.inventory.getStackInSlot(j).isEmpty())
							{
								player.inventory.removeStackFromSlot(j);
								searching = false;
							}

							if(i == player.inventory.getSizeInventory())
								searching = false;
						}
					}
				}

				if(!world.isRemote)
				{
					for(int i = 0; i < world.playerEntities.size(); i++)
					{
						if(world.playerEntities.get(i) instanceof EntityPlayerMP)
						{
							result.entityHit.removeTrackingPlayer((EntityPlayerMP)world.playerEntities.get(i));
						}
						//Use EntityLiving to erase the memory of all entites
					}
				}
			}
		}

		this.setDead();

	}


	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if(timer == 0)
			this.setDead();

		if (timer > 0 && this.world.isRemote && !this.inGround)
		{
			world.spawnParticle(EnumParticleTypes.CRIT, this.posX + (rand.nextDouble() - 0.5D) * (double)this.width, this.posY + rand.nextDouble() * (double)this.height - (double)this.getYOffset(), this.posZ + (rand.nextDouble() - 0.5D) * (double)this.width, 0.0D, 0.0D, 0.0D);
			timer--;
		}

		Block block = world.getBlockState(this.getPosition()).getBlock();

		if(block.isTopSolid(block.getDefaultState()))
		{
			this.setDead();
		}
	}
}