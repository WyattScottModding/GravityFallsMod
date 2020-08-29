package items;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import entity.EntityGnome;
import handlers.KeyBindings;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import network.MessageProcessBattery;
import network.MessageUpdatePlayerMotionClient;
import network.MessageUpdatePlayerPosition;
import network.Messages;

public class LeafBlower extends ItemSword implements IHasModel
{
	public LeafBlower(String name, ToolMaterial material)
	{
		super(material);
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsitems);
		this.setMaxDamage(100);

		this.addPropertyOverride(new ResourceLocation("on"), new IItemPropertyGetter()
		{
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
			{
				//Set the NBT to a new NBT if it is null
				NBTTagCompound nbt = new NBTTagCompound();

				if(stack.getTagCompound() != null)
					nbt = stack.getTagCompound();

				boolean clicked = false;

				if(nbt.hasKey("clicked"))
					clicked = nbt.getBoolean("clicked");

				return entityIn != null && (entityIn.getHeldItemMainhand() == stack || entityIn.getHeldItemOffhand() == stack) && clicked ? 1.0F : 0.0F;
			}
		});


		ItemInit.ITEMS.add(this);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) 
	{
		ItemStack stack = playerIn.getHeldItem(handIn);

		//Set the NBT to a new NBT if it is null
		NBTTagCompound nbt = new NBTTagCompound();

		if(stack.getTagCompound() != null)
			nbt = stack.getTagCompound();

		int counter = 4;
		boolean clicked = false;

		if(nbt.hasKey("counter"))
			counter = nbt.getInteger("counter");
		if(nbt.hasKey("clicked"))
			clicked = nbt.getBoolean("clicked");

		if(clicked && counter == 0)
		{
			clicked = false;
			counter = 4;
		}
		else if(!clicked && counter == 0)
		{
			clicked = true;
			counter = 4;
		}

		nbt.setInteger("counter", counter);
		nbt.setBoolean("clicked", clicked);
		stack.setTagCompound(nbt);

		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{
		//Set the NBT to a new NBT if it is null
		NBTTagCompound nbt = new NBTTagCompound();

		if(stack.getTagCompound() != null)
			nbt = stack.getTagCompound();

		int counter = 4;
		boolean clicked = false;

		if(nbt.hasKey("counter"))
			counter = nbt.getInteger("counter");
		if(nbt.hasKey("clicked"))
			clicked = nbt.getBoolean("clicked");

		if(counter > 0)
			counter--;

		if(entityIn instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entityIn;
			boolean flag = player.capabilities.isCreativeMode;

			if((player.getHeldItemMainhand() == stack || player.getHeldItemOffhand() == stack) && clicked && (stack.getItemDamage() < 100 || flag))
			{
				if(worldIn.getWorldTime() % 60 == 0 && !flag)
					stack.damageItem(1, player);

				Entity entity = getMouseOver(player, worldIn);

				if(entity != null)
				{
					entity.rotationYaw = player.rotationYaw;
					entity.rotationPitch = player.rotationPitch;

					double f = 2.0;

					if(entity instanceof EntityGnome)
						f = 5.0;

					float yaw = player.rotationYaw;
					float pitch = player.rotationPitch;

					double x = (double)(-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
					double y = (double)(-MathHelper.sin((pitch) / 180.0F * (float)Math.PI) * f);
					double z = (double)(MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);

					entity.motionX = x;
					entity.motionY = y;
					entity.motionZ = z;
					
					if(entity instanceof EntityPlayerMP)
					{
						Messages.INSTANCE.sendTo(new MessageUpdatePlayerMotionClient(x, y, z, player.rotationYaw, player.rotationPitch), (EntityPlayerMP) entity);
					}
					
					if(entity instanceof EntityLivingBase && entity instanceof EntityGnome)
						((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.INSTANT_DAMAGE, 2, 0));					

					entity = null;
				}
			}
		}
		if(KeyBindings.BATTERY.isDown())
		{
			Messages.INSTANCE.sendToServer(new MessageProcessBattery(5));
		}

		nbt.setInteger("counter", counter);
		nbt.setBoolean("clicked", clicked);
		stack.setTagCompound(nbt);

		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}

	public Entity getMouseOver(EntityPlayer player, World world)
	{
		BlockPos pos = player.getPosition().add(0, player.eyeHeight, 0);

		float yaw = player.rotationYaw;
		float pitch = player.rotationPitch;

		List<Entity> list = null;

		for(int f = 1; f <= 7; f++)
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

			for(int j = 0; j < list.size(); j++)
			{
				Entity entity = list.get(j);

				if(entity != null)
					return entity;
			}

		}
		return null;
	}

	@Override
	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}	
}