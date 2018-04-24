package armor;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import handlers.BlockHandler;
import handlers.RegistryHandler;
import init.BlockInit;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
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
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class MysticAmulet extends ItemArmor implements IHasModel
{
	public boolean active = false;

	public RayTraceResult blockPositionOld;

	public RayTraceResult blockPositionNew;

	public ArrayList<BlockHandler> blocks = new ArrayList<BlockHandler>();

	public IBlockState block;

	public Block blockType;

	public Entity entity;

	public MysticAmulet(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) 
	{
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsmagic);

		ItemInit.ITEMS.add(this);

	}



	@Override
	public void onUpdate(ItemStack stack, World world, Entity entityIn, int itemSlot, boolean isSelected) 
	{
		if(entityIn instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer)entityIn;


			if(player.getArmorInventoryList().toString().contains("mysticamulet"))
			{
				if(entity == null && Keyboard.isKeyDown(Keyboard.KEY_G) && getMouseOver(player, world))
				{

				}
				else if(Keyboard.isKeyDown(Keyboard.KEY_G) && entity != null)
				{
					RayTraceResult blockPosition = player.rayTrace(5, 1.0F);

					entity.moveToBlockPosAndAngles(blockPosition.getBlockPos(), player.rotationYaw * -1, player.rotationPitch * -1);
					/*
					entity.posX = blockPosition.getBlockPos().getX();
					entity.posY = blockPosition.getBlockPos().getY();
					entity.posZ = blockPosition.getBlockPos().getZ();
					entity.rotationYaw = player.rotationYaw * -1;
					entity.rotationPitch = player.rotationPitch * -1;
					 */

				}
				else if(entity == null)
				{
					if(!Keyboard.isKeyDown(Keyboard.KEY_G))
					{
						active = false;

						if(block != null && blockPositionNew != null)
						{
							world.setBlockState(blockPositionNew.getBlockPos(), block);
							block = null;
							blockPositionNew = null;
							blockPositionOld = null;
							blockType = null;
						}
					}

					if(Keyboard.isKeyDown(Keyboard.KEY_G) && !active)
					{
						blockPositionOld = player.rayTrace(5, 1.0F);

						block = world.getBlockState(blockPositionOld.getBlockPos());

						blockType = block.getBlock();

						//	blockType.removedByPlayer(block, world, blockPositionOld.getBlockPos(), player, false);

						active = true;
					}
					else if(active)
					{
						blockPositionNew = player.rayTrace(5, 1.0F);

						BlockPos blockPosOld = blockPositionOld.getBlockPos();
						BlockPos blockPosNew = blockPositionNew.getBlockPos();


						if(blockPosOld.getX() == blockPosNew.getX() && blockPosOld.getY() == blockPosNew.getY() && blockPosOld.getZ() == blockPosNew.getZ() && world.getBlockState(blockPosNew).getBlock() == Blocks.AIR)
						{
							IBlockState spaceBlock = world.getBlockState(blockPosNew);

							BlockHandler newBlock = new BlockHandler(spaceBlock.getBlock(), blockPosNew);

							blocks.add(newBlock);

							world.setBlockState(blockPositionNew.getBlockPos(), block);
						}
						else if(world.getBlockState(blockPosOld) == block && blockPositionOld.getBlockPos() != blockPositionNew.getBlockPos())
						{
							blockType.removedByPlayer(block, world, blockPositionOld.getBlockPos(), player, false);
						}
						blockPositionOld = blockPositionNew;
					}	

					if(blocks != null)
					{
						for(int i = 0; i < blocks.size(); i++)
						{
							if(blocks.get(i).getBlock() != Blocks.AIR)
								blockType.removedByPlayer(blocks.get(i).getBlock().getDefaultState(), world, blocks.get(i).blockPos(), player, false);
							else
								blocks.remove(i);
						}
					}
				}
				if(!Keyboard.isKeyDown(Keyboard.KEY_G))
				{
					entity = null;
				}
				if(entity != null && Keyboard.isKeyDown(Keyboard.KEY_C))
				{
					RayTraceResult blockPosition = player.rayTrace(1, 1.0F);

					int blockX = blockPosition.getBlockPos().getX();
					int blockY = blockPosition.getBlockPos().getY();
					int blockZ = blockPosition.getBlockPos().getZ();

					int playerX = (int) player.posX;
					int playerY = (int) player.posY;
					int playerZ = (int) player.posZ;

					double distanceX = Math.sqrt(Math.pow(blockX - playerX, 2));
					double distanceY = Math.sqrt(Math.pow(blockY - playerY, 2));
					double distanceZ = Math.sqrt(Math.pow(blockZ - playerZ, 2));

					entity.motionX = distanceX;
					entity.motionY = distanceY;
					entity.motionZ = distanceZ;

					if(entity instanceof EntityLivingBase)
					{
						EntityLivingBase entityLiving = (EntityLivingBase) entity;
						if(entityLiving instanceof EntitySkeleton || entityLiving instanceof EntityZombie || entityLiving instanceof EntityZombieHorse)
							entityLiving.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 2, 0));
						else
							entityLiving.addPotionEffect(new PotionEffect(MobEffects.INSTANT_DAMAGE, 2, 0));					
					}
					entity = null;
				}
			}
		}

		super.onUpdate(stack, world, entityIn, itemSlot, isSelected);
	}


	public boolean getMouseOver(EntityPlayer player, World world)
	{
		Vec3d lookVec = player.getLookVec();

		BlockPos pos = player.getPosition();

		float yaw = player.rotationYaw;
		float pitch = player.rotationPitch;

		List<Entity> list = null;

		for(int f = 1; f <= 40; f++)
		{
			double x = (double)(-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
			double y = (double)(-MathHelper.sin((pitch) / 180.0F * (float)Math.PI) * f);
			double z = (double)(MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);


			AxisAlignedBB entityPos = new AxisAlignedBB(pos.getX() + x, pos.getY() + y, pos.getZ() + z, pos.getX() + x + 1, pos.getY() + y + 1, pos.getZ() + z + 1);

			list = world.getEntitiesInAABBexcluding(player, entityPos, Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>()
			{
				public boolean apply(@Nullable Entity p_apply_1_)
				{
					return p_apply_1_ != null && p_apply_1_.canBeCollidedWith();
				}
			}));

			for(int j = 0; j < list.size(); ++j)
			{
				Entity entity = list.get(j);

				this.entity = entity;

				RayTraceResult blockPosition = player.rayTrace(5, 1.0F);

				entity.moveToBlockPosAndAngles(blockPosition.getBlockPos(), player.rotationYaw * -1, player.rotationPitch * -1);
				
			}

		}
		if(list != null)
			return list.size() != 0;
		else
			return false;
	}



	@Override
	public void registerModels() 
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}
}