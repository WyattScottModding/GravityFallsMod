package items;

import java.util.ArrayList;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import handlers.BlockHandler;
import handlers.RegistryHandler;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
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
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FlashLight extends ItemSword implements IHasModel
{
	public boolean clicked = false;
	public int counter = 0;
	public static World world = null;
	public ItemStack stack = null;

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
			else if(!clicked && counter == 0 && stack != null && stack.getItemDamage() <= 99)
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
		this.stack = stack;
		
		if(entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;

			if(stack.getItemDamage() >= 99)
				RegistryHandler.clicked = false;
			else
				RegistryHandler.clicked = clicked;

			if(clicked && world.getWorldTime() % 120 == 0)
			{
				stack.damageItem(1, player);
			}
			
			
			if(stack.getItemDamage() >= 50 &&  Keyboard.isKeyDown(Keyboard.KEY_R))
			{
				ItemStack itemstack = findAmmo(player);

				if(itemstack.getItem() instanceof Battery && player.getHeldItemMainhand().getItem() instanceof FlashLight)
				{
					stack.setItemDamage(stack.getItemDamage() - 50);
					itemstack.shrink(1);
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