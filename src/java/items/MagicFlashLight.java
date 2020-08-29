package items;

import java.util.List;

import javax.annotation.Nullable;

import entity.EntityGideonBot;
import handlers.KeyBindings;
import init.ItemInit;
import init.PotionInit;
import main.ConfigHandler;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import network.MessageChangeSize;
import network.MessageChangeSizeClient;
import network.MessagePlaySound;
import network.MessageProcessBattery;
import network.MessageTeleportPlayer;
import network.Messages;

public class MagicFlashLight extends ItemSword implements IHasModel
{
	public MagicFlashLight(String name)
	{
		super(ToolMaterial.WOOD);
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsitems);
		this.setMaxDamage(2000);

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

				return entityIn != null && entityIn.getHeldItemMainhand() == stack && clicked ? 1.0F : 0.0F;
			}
		});

		ItemInit.ITEMS.add(this);
	}

	@Override
	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) 
	{
		if(worldIn.isRemote)
		{
			//Set the NBT to a new NBT if it is null
			NBTTagCompound nbt = new NBTTagCompound();
	
			if(stack.getTagCompound() != null)
				nbt = stack.getTagCompound();
	
			int height = 0;
			boolean clicked = false;
	
			if(nbt.hasKey("height"))
				height = nbt.getInteger("height");
			if(nbt.hasKey("clicked"))
				clicked = nbt.getBoolean("clicked");
	
			if(entityIn instanceof EntityPlayer && !Minecraft.getMinecraft().gameSettings.keyBindUseItem.isKeyDown())
			{
				EntityPlayer player = (EntityPlayer) entityIn;
	
				if(player.getHeldItemMainhand() == stack)
				{
					long uuid1 = player.getUniqueID().getMostSignificantBits();
					long uuid2 = player.getUniqueID().getLeastSignificantBits();
	
					if(stack.getItemDamage() <= 1999) {
	
						if(KeyBindings.ITEM1.isDown() && KeyBindings.ITEM2.isDown())
						{
							//Server Side
							Messages.INSTANCE.sendToServer(new MessageChangeSize(1, uuid1, uuid2));
							
							System.out.println("Player:  " + uuid1);
						}
						else if(KeyBindings.ITEM1.isDown())
						{
							//Server Side
							Messages.INSTANCE.sendToServer(new MessageChangeSize(2, uuid1, uuid2));	
						}
						else if(KeyBindings.ITEM2.isDown())
						{
							//Server Side
							Messages.INSTANCE.sendToServer(new MessageChangeSize(3, uuid1, uuid2));
						}
						else 
						{
							//Server Side
							Messages.INSTANCE.sendToServer(new MessageChangeSize(4, uuid1, uuid2));
						}
					}
	
					if(KeyBindings.BATTERY.isDown())
					{
						//Server Side
						Messages.INSTANCE.sendToServer(new MessageProcessBattery(3));
					}
				}
			}
	
			nbt.setInteger("height", height);
			nbt.setBoolean("clicked", clicked);
			stack.setTagCompound(nbt);
	
			super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
		}
	}


	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn)
	{
		if(world.isRemote)
		{
			ItemStack stack = player.getHeldItem(handIn);
	
			if (stack.getItemDamage() <= 1999)
			{
				EntityLivingBase entityLiving = getMouseOver(player, world);
	
				//Set the NBT to a new NBT if it is null
				NBTTagCompound nbt = new NBTTagCompound();
	
				if(stack.getTagCompound() != null)
					nbt = stack.getTagCompound();
	
				boolean clicked = false;
				int entityHeight = 0;
	
				if(nbt.hasKey("clicked"))
					clicked = nbt.getBoolean("clicked");
				if(nbt.hasKey("entityHeight"))
					entityHeight = nbt.getInteger("entityHeight");
				
				long uuid1 = player.getUniqueID().getMostSignificantBits();
				long uuid2 = player.getUniqueID().getLeastSignificantBits();
	
				if(entityLiving != null && entityLiving != player && !(entityLiving instanceof EntityGideonBot)) {
					if(KeyBindings.ITEM1.isDown()) 
					{
						//Server Side
						Messages.INSTANCE.sendToServer(new MessageChangeSize(5, uuid1, uuid2));
					}
					else if(KeyBindings.ITEM2.isDown())  
					{
						//Server Side
						Messages.INSTANCE.sendToServer(new MessageChangeSize(6, uuid1, uuid2));
					}
					else 
					{
						//Server Side
						Messages.INSTANCE.sendToServer(new MessageChangeSize(4, uuid1, uuid2));
					}
				}	
				else
				{
					//Server Side
					Messages.INSTANCE.sendToServer(new MessageChangeSize(4, uuid1, uuid2));
				}
	
				nbt.setInteger("entityHeight", entityHeight);
				nbt.setBoolean("clicked", clicked);
				stack.setTagCompound(nbt);
			}
		}
		return super.onItemRightClick(world, player, handIn);
	}

	public EntityLivingBase getMouseOver(EntityPlayer player, World world)
	{
		BlockPos pos = player.getPosition().add(0, player.eyeHeight, 0);

		float yaw = player.rotationYaw;
		float pitch = player.rotationPitch;

		List<Entity> list = null;
		EntityLivingBase closestEntity = null;

		for(int f = 1; f <= 25; f++)
		{
			double x = (double)(-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
			double y = (double)(-MathHelper.sin((pitch) / 180.0F * (float)Math.PI) * f);
			double z = (double)(MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);


			AxisAlignedBB entityPos;

			if(f < 10)
				entityPos = new AxisAlignedBB(pos.getX() + x, pos.getY() + y, pos.getZ() + z, pos.getX() + x + 1, pos.getY() + y + 1, pos.getZ() + z + 1);
			else
				entityPos = new AxisAlignedBB(pos.getX() + x - 1, pos.getY() + y - 1, pos.getZ() + z - 1, pos.getX() + x + 2, pos.getY() + y + 2, pos.getZ() + z + 2);

			list = world.getEntitiesWithinAABBExcludingEntity(player, entityPos);


			for(int j = 0; j < list.size(); ++j)
			{
				Entity entity = list.get(j);

				if(entity instanceof EntityLivingBase)
				{
					EntityLivingBase entityLiving = (EntityLivingBase) entity;

					float distance = player.getDistance(entityLiving);

					if(closestEntity == null)
						closestEntity = entityLiving;
					else if(distance < player.getDistance(closestEntity))
						closestEntity = entityLiving;
				}
			}
		}
		return closestEntity;
	}
}