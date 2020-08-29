package containers;

import entity.EntityGolfCart;
import init.ItemInit;
import net.minecraft.entity.passive.AbstractChestHorse;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBrewingStand;
import net.minecraft.inventory.ContainerHorseChest;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerCartInventory extends Container
{
	public final EntityGolfCart cart;
	public int numRows = 4;
	public int batteryLife;
	public IInventory lowerInv;
	public IInventory upperInv;
	
	public ContainerCartInventory(IInventory playerInventory, IInventory cartInventoryIn, final EntityGolfCart cart, EntityPlayer player)
	{
		this.lowerInv = playerInventory;
		this.upperInv = cartInventoryIn;
		this.cart = cart;
		cartInventoryIn.openInventory(player);

		//Battery Slot
		this.addSlotToContainer(new Slot(cartInventoryIn, 0, 24, 18));

		//The Cart's Inventory
		for (int k = 0; k < this.numRows; ++k)
		{
			for (int l = 0; l < cart.getInventoryColumns(); ++l)
			{
				this.addSlotToContainer(new Slot(cartInventoryIn, l + k * cart.getInventoryColumns() + 1, 62 + l * 18, 18 + k * 18));
			}
		}


		//The Player's Inventory
		for (int i1 = 0; i1 < 3; ++i1)
		{
			for (int k1 = 0; k1 < 9; ++k1)
			{
				this.addSlotToContainer(new Slot(playerInventory, k1 + i1 * 9 + 9, 8 + k1 * 18, 103 + i1 * 18));
			}
		}

		for (int j1 = 0; j1 < 9; ++j1)
		{
			this.addSlotToContainer(new Slot(playerInventory, j1, 8 + j1 * 18, 161));
		}
	}

	/**
	 * Determines whether supplied player can use this container
	 */
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return this.upperInv.isUsableByPlayer(playerIn) && this.cart.isEntityAlive() && this.cart.getDistance(playerIn) < 8.0F;
	}
	
	@Override
	public void addListener(IContainerListener listener) 
	{
		super.addListener(listener);
		listener.sendAllWindowProperties(this, this.upperInv);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for(int i = 0; i < this.listeners.size(); ++i) 
		{
			IContainerListener listener = (IContainerListener)this.listeners.get(i);

			if(this.batteryLife != this.cart.batteryLife) 
				listener.sendWindowProperty(this, 0, this.cart.batteryLife);
		}
		
		this.batteryLife = this.cart.batteryLife;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) 
	{
		this.cart.batteryLife = data;
	}
	
	/**
	 * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
	 * inventory and the other inventory(s).
	 */
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index < this.numRows * 6)
			{
				if (!this.mergeItemStack(itemstack1, this.numRows * 6, this.inventorySlots.size(), true))
				{
					return ItemStack.EMPTY;
				}
			}
			else if (!this.mergeItemStack(itemstack1, 1, this.numRows * 6, false))
			{
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}


	/**
	 * Called when the container is closed.
	 */
	public void onContainerClosed(EntityPlayer playerIn)
	{
		super.onContainerClosed(playerIn);
		this.upperInv.closeInventory(playerIn);
	}
}