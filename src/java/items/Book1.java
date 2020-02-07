package items;

import org.lwjgl.input.Keyboard;

import init.BlockInit;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Slot;
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
import network.MessageOpenBook1;
import network.Messages;

public class Book1 extends ItemWrittenBook implements IHasModel
{

	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	public Book1(String name)
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
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) 
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
		{
			if(player != null && player.getHeldItemMainhand().isItemEqual(new ItemStack(ItemInit.BOOK1))) {
				if(!world.isRemote && player instanceof EntityPlayerMP) {
					EntityPlayerMP serverPlayer = (EntityPlayerMP) player;
					Messages.INSTANCE.sendTo(new MessageOpenBook1(),  serverPlayer);
				}
			}
		}
		return super.onItemRightClick(world, player, hand);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) 
	{
		IBlockState state = BlockInit.Book1.getDefaultState().withProperty(FACING, player.getHorizontalFacing());

		IBlockState block1 = world.getBlockState(pos);
		IBlockState block2 = world.getBlockState(pos.up());
		
		if(!Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
		{
			if(player.isCreative())
			{
				if(block1.getBlock() != Blocks.AIR && block2.getBlock() == Blocks.AIR && block1.getBlock() != BlockInit.PORTAL_CONTROL)
				{
					if(block1.isFullBlock())
					{
						world.setBlockState(pos.up(), state);
					}
				}
			}
			else
			{
				if(block1.getBlock() != Blocks.AIR && block2.getBlock() == Blocks.AIR && block1.getBlock() != BlockInit.PORTAL_CONTROL)
				{
					if(block1.isFullBlock())
					{
						world.setBlockState(pos.up(), state);
						player.getHeldItemMainhand().shrink(1);
					}
				}
			}
		}
		
		return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
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
					NBTTagList nbttaglist = nbttagcompound.getTagList("pages", 9);

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
