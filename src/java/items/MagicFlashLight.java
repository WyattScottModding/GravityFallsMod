package items;

import java.util.List;

import javax.annotation.Nullable;

import compatibilities.ISizeCap;
import compatibilities.SizeCapPro;
import entity.EntityGideonBot;
import handlers.KeyBindings;
import init.AttributeInit;
import init.ItemInit;
import init.PotionInit;
import main.ConfigHandler;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MagicFlashLight extends ItemSword implements IHasModel
{
	public int height = 0;

	public double playerX;
	public double playerY;
	public double playerZ;

	public boolean clicked = false;

	private int entityHeight = 0;
	private ItemStack thisStack = null;


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
				return entityIn != null && (entityIn.getHeldItemMainhand() == stack || entityIn.getHeldItemOffhand() == stack) && clicked ? 1.0F : 0.0F;
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
		if(this.thisStack == null)
			thisStack = stack;

		if(entityIn instanceof EntityPlayer && !worldIn.isRemote)
		{
			EntityPlayer player = (EntityPlayer) entityIn;

			//	if(worldIn.getWorldTime() % 20 == 0) {
			//		height = (int) (((player.height / 1.8F) - 1) * 100);
			//	}
			

			if(isSelected && !Minecraft.getMinecraft().gameSettings.keyBindUseItem.isKeyDown())
			{
				if(stack.getItemDamage() <= 1999) {

					if(KeyBindings.ITEM1.isDown() && KeyBindings.ITEM2.isDown())
					{
						//int newHeight = (int) (((player.height / 1.8F) - 1) * -100);
						
						//System.out.println("Current Height: " + player.height);
						//System.out.println("New Height: " + newHeight);

						player.removePotionEffect(PotionInit.GROWTH_EFFECT);
						//player.addPotionEffect(new PotionEffect(PotionInit.GROWTH_EFFECT, 5, newHeight));	
						//player.removePotionEffect(PotionInit.GROWTH_EFFECT);

						//System.out.println("New Player Height: " + player.height);

						clicked = true;
						stack.damageItem(1, player);
						height = 1;
					}
					else if(KeyBindings.ITEM1.isDown())
					{
						if(player.height < ConfigHandler.SIZE_MAX)
						{
							height += 2;

							player.removePotionEffect(PotionInit.GROWTH_EFFECT);

							PotionEffect effect = new PotionEffect(PotionInit.GROWTH_EFFECT, 10000000, height);
							effect.setPotionDurationMax(true);
							player.addPotionEffect(effect);

							clicked = true;
							stack.damageItem(1, player);
						}
						else
							clicked = false;
					}
					else if(KeyBindings.ITEM2.isDown())
					{
						if(player.height > ConfigHandler.SIZE_MIN)
						{
							height -= 2;

							player.removePotionEffect(PotionInit.GROWTH_EFFECT);

							PotionEffect effect = new PotionEffect(PotionInit.GROWTH_EFFECT, 10000000, height);
							effect.setPotionDurationMax(true);
							player.addPotionEffect(effect);

							clicked = true;
							stack.damageItem(1, player);
						}
						else
							clicked = false;
					}
					else
						clicked = false;

				}

				if(stack.getItemDamage() >= 1000 &&  KeyBindings.BATTERY.isDown())
				{
					ItemStack itemstack = findAmmo(player);

					if(itemstack.getItem() instanceof ItemBasic && player.getHeldItemMainhand().getItem() instanceof MagicFlashLight)
					{
						stack.setItemDamage(stack.getItemDamage() - 1000);
						itemstack.shrink(1);
					}
				}
			}
		}
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}


	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn)
	{
		if (!world.isRemote && thisStack != null && thisStack.getItemDamage() <= 1999)
		{
			EntityLivingBase entityLiving = getMouseOver(player, world);

			if(entityLiving != null && entityLiving != player && !(entityLiving instanceof EntityGideonBot)) {

				/*
				if(KeyBindings.ITEM1.isDown() && KeyBindings.ITEM2.isDown()) {
					entityLiving.removePotionEffect(PotionInit.GROWTH_EFFECT);

					clicked = true;
					thisStack.damageItem(1, player);

					entityHeight = 1;
				}
				*/
				if(KeyBindings.ITEM1.isDown()) {
					if(entityLiving.height <= ConfigHandler.SIZE_MAX) {
						entityLiving.removePotionEffect(PotionInit.GROWTH_EFFECT);
						entityHeight += 10;

						PotionEffect effect = new PotionEffect(PotionInit.GROWTH_EFFECT, 10000000, entityHeight);
						effect.setPotionDurationMax(true);
						entityLiving.addPotionEffect(effect);

						clicked = true;
						thisStack.damageItem(1, player);		
					}
					else
						clicked = false;
				}
				else if(KeyBindings.ITEM2.isDown())  {
					if(entityLiving.height >= ConfigHandler.SIZE_MIN) {
						entityLiving.removePotionEffect(PotionInit.GROWTH_EFFECT);
						entityHeight -= 10;

						PotionEffect effect = new PotionEffect(PotionInit.GROWTH_EFFECT, 10000000, entityHeight);
						effect.setPotionDurationMax(true);
						entityLiving.addPotionEffect(effect);

						clicked = true;
						thisStack.damageItem(1, player);
					}
					else
						clicked = false;
				}
				else
					clicked = false;
			}	
			else
				clicked = false;
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
}