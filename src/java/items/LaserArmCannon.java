package items;

import java.util.List;

import javax.annotation.Nullable;

import org.apache.http.impl.conn.Wire;
import org.lwjgl.input.Keyboard;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import akka.japi.pf.FI.Apply;
import entity.EntityGideonBot;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityZombieHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LaserArmCannon extends ItemSword implements IHasModel
{
	public boolean charging = false;
	public int cooldown = 30;


	public LaserArmCannon(String name, ToolMaterial material)
	{
		super(material);
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsitems);
		this.setMaxDamage(20);
		this.addPropertyOverride(new ResourceLocation("fired"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
            	if(cooldown >= 0 && cooldown < 5)
            		return 0.1F;
            	else if(cooldown >= 5 && cooldown < 10)
            		return 0.5F;
            	else if(cooldown >= 10 && cooldown < 15)
            		return 0.4F;
            	else if(cooldown >= 15 && cooldown < 20)
            		return 0.3F;
            	else if(cooldown >= 20 && cooldown < 25)
            		return 0.2F;
            	else//if(cooldown >= 25 && cooldown <= 30)
            		return 0.6F;
            }
        });
		
		ItemInit.ITEMS.add(this);
	}

	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}


	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) 
	{
		if (!worldIn.isRemote && cooldown == 0)
		{
			charging = false;
		}

		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		if(cooldown > 0)
			cooldown--;
		

		if(!worldIn.isRemote && entityIn instanceof EntityPlayer)
		{
		
			EntityPlayer player = (EntityPlayer) entityIn;
			boolean flag = player.capabilities.isCreativeMode;

			if(!charging && (stack.getItemDamage() < 20 || flag) && player.getHeldItemMainhand().getItem() instanceof LaserArmCannon)
			{
				if(!flag)
					stack.damageItem(1, player);

				getMouseOver(player, worldIn);
				charging = true;
				cooldown = 30;
			}

			if(stack.getItemDamage() >= 10 &&  Keyboard.isKeyDown(Keyboard.KEY_R))
			{
				if(player.getHeldItemMainhand().getItem() instanceof LaserArmCannon)
				{
					ItemStack itemstack = findAmmo(player);

					if(isBattery(itemstack))
					{
						stack.setItemDamage(stack.getItemDamage() - 10);

						itemstack.shrink(1);
					}
				}
			}
		}

		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	public void getMouseOver(EntityPlayer player, World world)
	{
		Vec3d lookVec = player.getLookVec();

		BlockPos pos = player.getPosition();

		float yaw = player.rotationYaw;
		float pitch = player.rotationPitch;

		for(int f = 1; f <= 80; f++)
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

					if(mob instanceof EntitySkeleton || mob instanceof EntityZombie || mob instanceof EntityZombieHorse)
						mob.addPotionEffect(new PotionEffect(MobEffects.INSTANT_HEALTH, 2, 0));
					else
						mob.addPotionEffect(new PotionEffect(MobEffects.INSTANT_DAMAGE, 2, 0));
				}
			}
		}

	}


	private ItemStack findAmmo(EntityPlayer player)
	{
		if (player.inventory.getCurrentItem().areItemsEqualIgnoreDurability(player.getHeldItem(EnumHand.OFF_HAND), new ItemStack(ItemInit.BATTERY)))
		{
			return player.getHeldItem(EnumHand.OFF_HAND);
		}
		if (player.inventory.getCurrentItem().areItemsEqualIgnoreDurability(player.getHeldItem(EnumHand.MAIN_HAND), new ItemStack(ItemInit.BATTERY)))
		{
			return player.getHeldItem(EnumHand.MAIN_HAND);
		}
		else
		{
			for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
			{
				ItemStack itemstack = player.inventory.getStackInSlot(i);

				if (	player.inventory.getCurrentItem().areItemsEqualIgnoreDurability(itemstack, new ItemStack(ItemInit.BATTERY)))
				{
					return itemstack;
				}
			}

			return ItemStack.EMPTY;
		}
	}

	protected boolean isBattery(ItemStack stack)
	{
		return stack.getItem() instanceof Battery;
	}

}
