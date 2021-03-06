package items;

import org.lwjgl.input.Keyboard;

import handlers.KeyBindings;
import init.BlockInit;
import init.ItemInit;
import main.ConfigHandler;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWrittenBook;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import network.Messages;

public class Book2 extends ItemWrittenBook implements IHasModel{

	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	public Book2(String name)
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
	public boolean isEnchantable(ItemStack stack) 
	{
		return false;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) 
	{
		GameSettings gameSettings = Minecraft.getMinecraft().gameSettings;
		
		if(gameSettings.keyBindSprint.isKeyDown())
		{
			if(player != null && player.getHeldItemMainhand().isItemEqual(new ItemStack(ItemInit.BOOK2))) {
				player.openGui(GravityFalls.instance, ConfigHandler.GUI_JOURNAL2, player.world, (int) player.posX, (int) player.posY, (int) player.posZ);
			}
		}
		return super.onItemRightClick(world, player, hand);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) 
	{
		IBlockState state = BlockInit.Book2.getDefaultState().withProperty(FACING, player.getHorizontalFacing());

		IBlockState block1 = world.getBlockState(pos);
		IBlockState block2 = world.getBlockState(pos.up());

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


		return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
	}


	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		return false;
	}
}