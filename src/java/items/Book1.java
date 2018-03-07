package items;

import gui.GuiBook1;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import main.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBook;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWrittenBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentUtils;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Book1 extends ItemWrittenBook implements IHasModel{

	public Book1(String name)
	{
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallstab);

		ItemInit.ITEMS.add(this);
	}

	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}
	

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) 
	{
		if(!worldIn.isRemote)
		{
			player.openGui(GravityFalls.instance, Reference.GUI_JOURNAL1, worldIn, (int) player.posX, (int) player.posY, (int) player.posZ);
		}
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}


	
	@Override
	public boolean isEnchantable(ItemStack stack) 
	{

		return false;
	}

	private void resolveContents(ItemStack stack, EntityPlayer player)
	{
		if (stack.getTagCompound() != null)
		{
			NBTTagCompound nbttagcompound = stack.getTagCompound();

			if (!nbttagcompound.getBoolean("resolved"))
			{
				nbttagcompound.setBoolean("resolved", true);

				if (validBookTagContents(nbttagcompound))
				{
					NBTTagList nbttaglist = nbttagcompound.getTagList("pages", 8);

					for (int i = 0; i < nbttaglist.tagCount(); ++i)
					{
						String s = nbttaglist.getStringTagAt(i);
						ITextComponent itextcomponent;

						try
						{
							itextcomponent = ITextComponent.Serializer.fromJsonLenient(s);
							itextcomponent = TextComponentUtils.processComponent(player, itextcomponent, player);
						}
						catch (Exception var9)
						{
							itextcomponent = new TextComponentString(s);
						}

						nbttaglist.set(i, new NBTTagString(ITextComponent.Serializer.componentToJson(itextcomponent)));
					}

					nbttagcompound.setTag("pages", nbttaglist);

					if (player instanceof EntityPlayerMP && player.getHeldItemMainhand() == stack)
					{
						Slot slot = player.openContainer.getSlotFromInventory(player.inventory, player.inventory.currentItem);
						((EntityPlayerMP)player).connection.sendPacket(new SPacketSetSlot(0, slot.slotNumber, stack));
					}
				}
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		return false;
	}
}
