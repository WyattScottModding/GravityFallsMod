package items;

import javax.annotation.Nullable;

import blocks.LightSource;
import handlers.KeyBindings;
import init.BlockInit;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import updates.PlayerUpdate;

public class FlashLight extends ItemSword implements IHasModel
{
	public boolean clicked = false;
	public int counter = 0;
	public static World world = null;
	private ItemStack thisStack = null;

	public FlashLight(String name)
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
                return entityIn != null && (entityIn.getHeldItemMainhand() == stack || entityIn.getHeldItemOffhand() == stack) && clicked ? 1.0F : 0.0F;
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
		world = worldIn;

		if (!worldIn.isRemote)
		{
			if(clicked && counter == 0)
			{
				clicked = false;
				counter = 4;
			}
			else if(!clicked && counter == 0 && thisStack != null && thisStack.getItemDamage() <= 99)
			{
				clicked = true;
				counter = 4;
			}
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected)
	{	
		if(counter > 0)
			counter--;

		if(this.thisStack == null)
			this.thisStack = stack;

		if(entity instanceof EntityPlayer && !world.isRemote && stack.getItemDamage() <= 99)
		{
			EntityPlayer player = (EntityPlayer) entity;

			if(clicked && world.getWorldTime() % 120 == 0)
			{
				stack.damageItem(1, player);
			}


			if(stack.getItemDamage() >= 50 &&  KeyBindings.BATTERY.isDown())
			{
				ItemStack itemstack = findAmmo(player);

				if(itemstack.getItem() instanceof ItemBasic && (player.getHeldItemMainhand().getItem() instanceof FlashLight || player.getHeldItemOffhand().getItem() instanceof FlashLight))
				{
					stack.setItemDamage(stack.getItemDamage() - 50);
					itemstack.shrink(1);
				}
			}

			if(clicked) {
				if ((player.getHeldItemMainhand() != null && LightSource.isLightEmittingItem(player.getHeldItemMainhand().getItem())) || (player.getHeldItemOffhand() != null && LightSource.isLightEmittingItem(player.getHeldItemOffhand().getItem())))
				{
					int blockX = MathHelper.floor(player.posX);
					int blockY = MathHelper.floor(player.posY-0.2D - player.getYOffset());
					int blockZ = MathHelper.floor(player.posZ);
					// place light at head level
					BlockPos blockLocation = new BlockPos(blockX, blockY, blockZ).up();
					if (player.world.getBlockState(blockLocation).getBlock() == Blocks.AIR)
					{
						player.world.setBlockState(blockLocation, BlockInit.LIGHT_SOURCE.getDefaultState());

					}
					else if (player.world.getBlockState(blockLocation.add(player.getLookVec().x, 
							player.getLookVec().y, player.getLookVec().z)).getBlock() == Blocks.AIR)
					{

						player.world.setBlockState(blockLocation.add(player.getLookVec().x, player.getLookVec().y, 
								player.getLookVec().z), BlockInit.LIGHT_SOURCE.getDefaultState());

					}
				}
			}
		} 
	}

	public World getWorld()
	{
		return world;
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