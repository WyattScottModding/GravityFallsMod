package items;

import org.lwjgl.input.Keyboard;

import commands.Teleport;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
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
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) 
	{
		if(!worldIn.isRemote)
		{
			if(playerIn.dimension == 0)
			{
				pos = playerIn.getPosition();

				Teleport.teleportToDimension(playerIn, 2, -18.5, 61, -18.5);
			}
			else if(playerIn.dimension == 2)
			{
				if(pos != null)
					Teleport.teleportToDimension(playerIn, 0, pos.getX(), pos.getY(), pos.getZ());
				else
					Teleport.teleportToDimension(playerIn, 0, 0, 67, 0);
			}
		}

		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) 
	{
		if(entityIn instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entityIn;

			if(player.getHeldItemMainhand().isItemEqual(new ItemStack(ItemInit.TIME_TAPE)))
			{
				if(Keyboard.isKeyDown(Keyboard.KEY_R))
				{
					if(Keyboard.isKeyDown(Keyboard.KEY_W))
					{
						worldIn.setWorldTime(worldIn.getWorldTime() + 150);
					}
					if(Keyboard.isKeyDown(Keyboard.KEY_S))
					{
						worldIn.setWorldTime(worldIn.getWorldTime() - 150);
					}
				}
			}
		}

		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}
}
