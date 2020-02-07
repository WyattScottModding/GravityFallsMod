package armor;

import javax.annotation.Nullable;

import handlers.KeyBindings;
import init.BlockInit;
import init.ItemInit;
import items.ItemBasic;
import items.FlashLight;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LightSweater extends ItemArmor implements IHasModel{

	private boolean clicked = false;
	public int counter = 0;

	public LightSweater(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) 
	{
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsarmor);
		this.setMaxDamage(100);

		this.addPropertyOverride(new ResourceLocation("on"), new IItemPropertyGetter()
		{
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
			{
                return entityIn != null && entityIn.getItemStackFromSlot(EntityEquipmentSlot.CHEST) == stack && clicked ? 1.0F : 0.0F;
			}
		});

		ItemInit.ITEMS.add(this);

		//this.renderHelmetOverlay(stack, player, resolution, partialTicks);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		if(!world.isRemote) {
			if(KeyBindings.ARMOR1.isDown()) {
				if(clicked && counter == 0)
				{
					clicked = false;
					counter = 20;
				}
				else if(!clicked && counter == 0 && itemStack != null && itemStack.getItemDamage() <= 99)
				{
					clicked = true;
					counter = 20;
				}
			}

			if(clicked  && itemStack.getItemDamage() <= 99) {
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
			
			if(clicked && world.getWorldTime() % 120 == 0)
			{
				itemStack.damageItem(1, player);
			}
		}
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {

		if(counter > 0)
			counter--;
		
		//Processes Battery in the sweater
		if(entity instanceof EntityPlayer && !world.isRemote && stack.getItemDamage() <= 99)
		{
			EntityPlayer player = (EntityPlayer) entity;

			if(clicked && world.getWorldTime() % 120 == 0)
			{
				stack.damageItem(1, player);
			}

			if(stack.getItemDamage() >= 50 &&  KeyBindings.BATTERY.isDown())
			{
				ItemStack itemStack = findAmmo(player);

				if(itemStack.getItem() instanceof ItemBasic && (player.getHeldItemMainhand().getItem() instanceof LightSweater || player.getHeldItemOffhand().getItem() instanceof LightSweater))
				{
					stack.setItemDamage(stack.getItemDamage() - 50);
					itemStack.shrink(1);
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

	@Override
	public void registerModels() 
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}

}
