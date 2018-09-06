package items;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import handlers.RegistryHandler;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MagicFlashLight extends ItemSword implements IHasModel
{

	public float height = 1.8F;
	public float eyeHeight = 1.62F;

	public double playerX;
	public double playerY;
	public double playerZ;

	private boolean grow = true;
	public boolean clicked = false;
	private int counter = 0;
	public ItemStack stack = null;


	public MagicFlashLight(String name)
	{
		super(ToolMaterial.WOOD);
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
				return !clicked ? 0.0F : 1.0F;
			}
		});

		ItemInit.ITEMS.add(this);
	}


	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}


	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) 
	{
		if(counter > 0)
			counter--;

		this.stack = stack;

		if(entityIn instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entityIn;

			if(stack.getItemDamage() >= 99)
				RegistryHandler.magicClicked = false;
			else
				RegistryHandler.magicClicked = clicked;

			if(player.getHeldItemMainhand().getItem() instanceof MagicFlashLight)
			{
				if(Keyboard.isKeyDown(Keyboard.KEY_B))
				{
					grow = true;
				}

				if(Keyboard.isKeyDown(Keyboard.KEY_V))
				{
					grow = false;
				}
				if(clicked && worldIn.isRemote)
				{
					if(grow)
					{
						if(height < 10)
						{
							//player.getCollisionBoundingBox().grow(1.1);
						}
					}
					else
					{
						if(height > 0.1)
						{
							//player.getCollisionBoundingBox().shrink(1.1);
						}
					}
				}


				if(clicked && !worldIn.isRemote)
				{
					NBTTagCompound nbt = stack.getTagCompound();

					if(nbt == null)
						nbt = new NBTTagCompound();

					stack.setTagCompound(nbt);

					if(nbt.hasKey("height"))
						height = nbt.getFloat("height");

					System.out.println("Player height: " + height);

					float heightChange = 0.015F;

					float scale = (float)(height / 1.8);

					RegistryHandler.scale = scale;
					RegistryHandler.nbt = nbt;


					if(grow)
					{
						if(height < 10)
						{
							nbt.setFloat("height", height + heightChange);
					//		if(player != null && player instanceof EntityPlayerMP)
						//		player.getCollisionBoundingBox().grow(1.1);
						}
					}
					else
					{
						if(height > 0.1)
						{
							nbt.setFloat("height", height - heightChange);
				//			if(player != null && player instanceof EntityPlayerMP)
					//			player.getCollisionBoundingBox().shrink(1.1);
						}
					}
				}

				if(clicked && worldIn.getWorldTime() % 120 == 0)
				{
					stack.damageItem(1, player);
				}

				if(stack.getItemDamage() >= 50 &&  Keyboard.isKeyDown(Keyboard.KEY_R))
				{
					ItemStack itemstack = findAmmo(player);

					if(itemstack.getItem() instanceof Battery && player.getHeldItemMainhand().getItem() instanceof MagicFlashLight)
					{
						stack.setItemDamage(stack.getItemDamage() - 50);
						itemstack.shrink(1);
					}
				}
			}
		}
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}


	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn)
	{
		if (!worldIn.isRemote)
		{
			if(clicked && counter == 0)
			{
				clicked = false;
				counter = 4;
			}
			else if(!clicked && counter == 0 && stack != null && stack.getItemDamage() <= 99)
			{
				clicked = true;
				counter = 4;
			}
		}

		return super.onItemRightClick(worldIn, player, handIn);
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