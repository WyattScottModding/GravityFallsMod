package items;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import handlers.BlockHandler;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class FlashLight extends ItemSword implements IHasModel
{

	public boolean clicked = false;
	public int counter = 0;
	public static World world = null;

	public static SimpleNetworkWrapper network;

	public FlashLight(String name)
	{
		super(ToolMaterial.WOOD);
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsitems);
		this.setMaxDamage(1000);

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
		
		if(clicked && counter % 2 == 0)
		{
			clicked = false;
		}
		else if(!clicked && counter % 2 == 0)
		{
			clicked = true;
		}
		counter++;

		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{	
		if(entityIn instanceof EntityPlayer)
		{
			EntityPlayer entityPlayer = (EntityPlayer) entityIn;

			if(clicked)
			{
				network = NetworkRegistry.INSTANCE.newSimpleChannel("flashlight");
				network.registerMessage(PacketMakeLight.HandlerMakeLightServer.class, PacketMakeLight.class, 1, Side.SERVER);
			}


			if(stack.getItemDamage() >= 500 &&  Keyboard.isKeyDown(Keyboard.KEY_R))
			{
				ItemStack itemstack = findAmmo(entityPlayer);

				if(itemstack.isItemEqual(new ItemStack(ItemInit.BATTERY)))
				{
					stack.damageItem(-500, entityPlayer);
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
	
	public static abstract class HandlerMakeLightClient implements IMessageHandler<PacketMakeLight, IMessage>
  {
    public IMessage onMessage(PacketMakeLight message, MessageContext ctx)
    {
      World world = FlashLight.world;
      
      world.func_72915_b(EnumSkyBlock.BLOCK, message.x, message.y, message.z, Flashlight.instance.getLanternLightValue());
      world.func_147463_c(EnumSkyBlock.BLOCK, message.oldX, message.oldY, message.oldZ);
      world.func_147463_c(EnumSkyBlock.BLOCK, message.x, message.y + 1, message.z);
      world.func_147463_c(EnumSkyBlock.BLOCK, message.x + 1, message.y, message.z);
      world.func_147463_c(EnumSkyBlock.BLOCK, message.x, message.y, message.z + 1);
      world.func_147463_c(EnumSkyBlock.BLOCK, message.x - 1, message.y, message.z);
      world.func_147463_c(EnumSkyBlock.BLOCK, message.x, message.y, message.z - 1);
      
      return null;
    }
  }

	public static abstract class HandlerMakeLightServer implements IMessageHandler<PacketMakeLight, IMessage>
	{
		public IMessage onMessage(PacketMakeLight message, MessageContext ctx)
		{
			Lantern.network.sendToAll(message);
			return null;
		}

		@Override
		public IMessage onMessage(PacketMakeLight message, MessageContext ctx) {
			// TODO Auto-generated method stub
			return null;
		}
	}
}