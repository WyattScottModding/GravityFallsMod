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
import tileEntities.TileEntityComputer;

public class ContainerComputer extends Container 
{
	private final TileEntityComputer tileComputer;

	public ContainerComputer(InventoryPlayer playerInventory, TileEntityComputer computerInventory)
	{
		this.tileComputer = computerInventory;
	}

	@Override
	public void addListener(IContainerListener listener) 
	{
		super.addListener(listener);
		listener.sendAllWindowProperties(this, this.tileComputer);
	}

	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return true;
	}

}