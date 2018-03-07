package containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tileEntities.TileEntityBook1;

public class ContainerBook1 extends Container 
{
	private final TileEntityBook1 tileBook1;

	public ContainerBook1(InventoryPlayer playerInventory, TileEntityBook1 bookInventory)
	{
		this.tileBook1 = bookInventory;
	}

	@Override
	public void addListener(IContainerListener listener) 
	{
		super.addListener(listener);
		listener.sendAllWindowProperties(this, this.tileBook1);
	}

	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return true;
	}


}
