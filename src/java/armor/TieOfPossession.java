package armor;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import handlers.RegistryHandler;
import init.BlockInit;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityZombieHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class TieOfPossession extends ItemArmor implements IHasModel
{
	public EntityLivingBase possessedEntity;

	public TieOfPossession(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) 
	{
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsarmor);

		ItemInit.ITEMS.add(this);
	}

	@Override
	public void registerModels() 
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}


	@Override
	public void onUpdate(ItemStack stack, World world, Entity entityIn, int itemSlot, boolean isSelected) 
	{	
		if(possessedEntity != null && possessedEntity.isDead)
			possessedEntity = null;
		
		if(entityIn instanceof EntityPlayer && possessedEntity != null)
		{
			EntityPlayer player = (EntityPlayer) entityIn;

			if(player.getArmorInventoryList().toString().contains("tie"))
			{
				float f = 0.5F;
				float yaw1 = possessedEntity.rotationYaw;
				float pitch = possessedEntity.rotationPitch;

				double motionX1 = (double)(-MathHelper.sin(yaw1 / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
				double motionZ1 = (double)(MathHelper.cos(yaw1 / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);

				float yaw2 = possessedEntity.rotationYaw + 90;

				if(yaw2 > 180)
					yaw2 -= 360;

				double motionX2 = (double)(-MathHelper.sin(yaw2 / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
				double motionZ2 = (double)(MathHelper.cos(yaw2 / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);

				float yaw3 = possessedEntity.rotationYaw - 90;

				if(yaw3 < -180)
					yaw3 += 360;

				double motionX3 = (double)(-MathHelper.sin(yaw3 / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
				double motionZ3 = (double)(MathHelper.cos(yaw3 / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);


				//Move
				if(Keyboard.isKeyDown(Keyboard.KEY_LEFT))
				{
					possessedEntity.motionX = motionX3;
					possessedEntity.motionZ = motionZ3;
				}
				else if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
				{
					possessedEntity.motionX = motionX2;
					possessedEntity.motionZ = motionZ2;
				}
				if(Keyboard.isKeyDown(Keyboard.KEY_UP))
				{
					if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
					{
						possessedEntity.motionX = motionX1 + motionX2;
						possessedEntity.motionZ = motionZ1 + motionZ2;
					}
					else if(Keyboard.isKeyDown(Keyboard.KEY_LEFT))
					{
						possessedEntity.motionX = motionX1 + motionX3;
						possessedEntity.motionZ = motionZ1 + motionZ3;
					}
					else
					{
						possessedEntity.motionX = motionX1;
						possessedEntity.motionZ = motionZ1;
					}
				}
				else if(Keyboard.isKeyDown(Keyboard.KEY_DOWN))
				{
					if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
					{
						possessedEntity.motionX = -motionX1 + motionX2;
						possessedEntity.motionZ = -motionZ1 + motionZ2;
					}
					else if(Keyboard.isKeyDown(Keyboard.KEY_LEFT))
					{
						possessedEntity.motionX = -motionX1 + motionX3;
						possessedEntity.motionZ = -motionZ1 + motionZ3;
					}
					else
					{
						possessedEntity.motionX = -motionX1;
						possessedEntity.motionZ = -motionZ1;
					}
				}
				
				possessedEntity.stepHeight = 1;

				//Turn
				if(Keyboard.isKeyDown(Keyboard.KEY_J))
					possessedEntity.rotationYaw -= 6;
				else if(Keyboard.isKeyDown(Keyboard.KEY_K))
					possessedEntity.rotationYaw += 6;
				
				possessedEntity.setPositionAndUpdate(possessedEntity.posX, possessedEntity.posY, possessedEntity.posZ);

				//Attack
				if(Keyboard.isKeyDown(Keyboard.KEY_H))
				{
					EntityLivingBase entityLiving = getMouseOver(possessedEntity, world);
					
					if(entityLiving != null)
					{
						//entityLiving.attackEntityFrom(DamageSource.GENERIC, 3.0F);
						possessedEntity.attackEntityAsMob(entityLiving);
					}
				}
			}
			
			//Explode if Creeper
			if(possessedEntity instanceof EntityCreeper)
			{
				EntityCreeper creeper = (EntityCreeper) possessedEntity;
				
				 if (!world.isRemote && Keyboard.isKeyDown(Keyboard.KEY_M))
			        {
			            boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(world, creeper);
			            float f = creeper.getPowered() ? 2.0F : 1.0F;
			            creeper.world.createExplosion(creeper, creeper.posX, creeper.posY, creeper.posZ, (float)1.5 * f, flag);
			            creeper.setDead();
			            world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, creeper.posX,creeper.posY, creeper.posZ, 1, 1, 1, 0);
			            
			            possessedEntity = null;
			        }
			}

		}
		super.onUpdate(stack, world, entityIn, itemSlot, isSelected);
	}
	
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) 
	{
		EntityLivingBase entity = getMouseOver(player, world);

		if(entity != null)
		{
			if(possessedEntity != null && possessedEntity != entity && (possessedEntity.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() instanceof TieOfPossession2))
				possessedEntity.setItemStackToSlot(EntityEquipmentSlot.CHEST, ItemStack.EMPTY);

			possessedEntity = entity;

			possessedEntity.addPotionEffect(new PotionEffect(MobEffects.GLOWING, 10, 1));

			if(possessedEntity.getItemStackFromSlot(EntityEquipmentSlot.CHEST).isEmpty())
				possessedEntity.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(ItemInit.TIE_OF_POSSESSION2));
		}

		return super.onItemRightClick(world, player, hand);
	}

	public EntityLivingBase getMouseOver(EntityLivingBase player, World world)
	{
		Vec3d lookVec = player.getLookVec();

		BlockPos pos = player.getPosition();

		float yaw = player.rotationYaw;
		float pitch = player.rotationPitch;

		for(int f = 1; f <= 5; f++)
		{
			double x = (double)(-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
			double y = (double)(-MathHelper.sin((pitch) / 180.0F * (float)Math.PI) * f);
			double z = (double)(MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);


			AxisAlignedBB entityPos = new AxisAlignedBB(pos.getX() + x, pos.getY() + y, pos.getZ() + z, pos.getX() + x + 1, pos.getY() + y + 1, pos.getZ() + z + 1);

			List<Entity> list = world.getEntitiesInAABBexcluding(player, entityPos, Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>()
			{
				public boolean apply(@Nullable Entity p_apply_1_)
				{
					return p_apply_1_ != null && p_apply_1_.canBeCollidedWith();
				}
			}));

			for(int j = 0; j < list.size(); ++j)
			{
				Entity entity = list.get(j);
				if(entity instanceof EntityLivingBase)
				{
					EntityLivingBase mob = (EntityLivingBase) entity;

					return mob;
				}
			}
		}
		return null;
	}


}