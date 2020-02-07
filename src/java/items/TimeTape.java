package items;

import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import commands.Teleport;
import handlers.KeyBindings;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityZombieHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class TimeTape extends Item implements IHasModel
{
	public BlockPos pos;

	public TimeTape(String name)
	{
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsitems);

		ItemInit.ITEMS.add(this);
	}

	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) 
	{
		if(!world.isRemote)
		{
			if(player.dimension == 0)
			{
				pos = player.getPosition();

				Teleport.teleportToDimension(player, 2, -18.5, 61, -18.5);
				
				for(Entity element : findEntities(player, world))
				{
					if(element instanceof EntityPlayer)
					{
						EntityPlayer player2 = (EntityPlayer) element;
						Teleport.teleportToDimension(player2, 2, -18.5, 61, -18.5);
					}
				}
			}
			else if(player.dimension == 2)
			{
				if(pos != null)
				{
					Teleport.teleportToDimension(player, 0, pos.getX(), pos.getY(), pos.getZ());

					for(Entity element : findEntities(player, world))
					{
						if(element instanceof EntityPlayer)
						{
							EntityPlayer player2 = (EntityPlayer) element;
							Teleport.teleportToDimension(player2, 0, pos.getX(), pos.getY(), pos.getZ());
						}
					}
				}
				else if(player.bedLocation != null)
				{
					BlockPos bedPos = player.bedLocation;
					Teleport.teleportToDimension(player, 0, bedPos.getX(), bedPos.getY(), bedPos.getZ());

					for(Entity element : findEntities(player, world))
					{
						if(element instanceof EntityPlayer)
						{
							EntityPlayer player2 = (EntityPlayer) element;
							Teleport.teleportToDimension(player2, 0, bedPos.getX(), bedPos.getY(), bedPos.getZ());
						}
					}
				}
				else
				{
					Teleport.teleportToDimension(player, 0, 0, 67, 0);

					for(Entity element : findEntities(player, world))
					{
						if(element instanceof EntityPlayer)
						{
							EntityPlayer player2 = (EntityPlayer) element;
							Teleport.teleportToDimension(player2, 0, 0, 67, 0);
						}
					}
				}
			}
		}

		return super.onItemRightClick(world, player, hand);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) 
	{
		if(entityIn instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entityIn;

			if(player.getHeldItemMainhand().isItemEqual(new ItemStack(ItemInit.TIME_TAPE)))
			{
				if(KeyBindings.ITEM1.isDown())
				{
					if(Minecraft.getMinecraft().gameSettings.keyBindForward.isKeyDown())
					{
						worldIn.setWorldTime(worldIn.getWorldTime() + 100);
					}
					if(Minecraft.getMinecraft().gameSettings.keyBindBack.isKeyDown())
					{
						worldIn.setWorldTime(worldIn.getWorldTime() - 100);
					}
				}
			}
		}

		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}


	public List<Entity> findEntities(EntityPlayer player, World world)
	{
		BlockPos pos = player.getPosition();

		AxisAlignedBB entityPos = new AxisAlignedBB(pos.getX() - 1, pos.getY() - 1, pos.getZ() - 1, pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);

		List<Entity> list = world.getEntitiesInAABBexcluding(player, entityPos, Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>()
		{
			public boolean apply(@Nullable Entity p_apply_1_)
			{
				return p_apply_1_ != null && p_apply_1_.canBeCollidedWith();
			}
		}));

		return list;

	}
}
