package items;

import org.lwjgl.input.Keyboard;

import init.BlockInit;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import main.Reference;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBook;
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
		IBlockState state = BlockInit.Book2.getDefaultState().withProperty(FACING, player.getHorizontalFacing());

		BlockPos pos = player.getPosition();

		IBlockState block1 = world.getBlockState(pos);
		IBlockState block2 = world.getBlockState(pos.up());

		if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
		{
			if(player != null && player.getHeldItemMainhand().isItemEqual(new ItemStack(ItemInit.BOOK2)))
				player.openGui(GravityFalls.instance, Reference.GUI_JOURNAL2, world, (int) player.posX, (int) player.posY, (int) player.posZ);
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
		
		if(!Keyboard.isKeyDown(Keyboard.KEY_LCONTROL))
		{
			if(player.isCreative())
			{
				if(block1.getBlock() != Blocks.AIR && block2.getBlock() == Blocks.AIR && block1.getBlock() != BlockInit.PORTAL_CONTROL && block1.getBlock() != BlockInit.PORTAL_CONTROLALLBOOKS && block1.getBlock() != BlockInit.PORTAL_CONTROLBOOK1 && block1.getBlock() != BlockInit.PORTAL_CONTROLBOOK1BOOK2 && block1.getBlock() != BlockInit.PORTAL_CONTROLBOOK1BOOK3 && block1.getBlock() != BlockInit.PORTAL_CONTROLBOOK2 && block1.getBlock() != BlockInit.PORTAL_CONTROLBOOK2BOOK3 && block1.getBlock() != BlockInit.PORTAL_CONTROLBOOK3)
				{
					if(block1.isFullBlock())
					{
						world.setBlockState(pos.up(), state);
					}
				}
			}
			else
			{
				if(block1.getBlock() != Blocks.AIR && block2.getBlock() == Blocks.AIR && block1.getBlock() != BlockInit.PORTAL_CONTROL && block1.getBlock() != BlockInit.PORTAL_CONTROLALLBOOKS && block1.getBlock() != BlockInit.PORTAL_CONTROLBOOK1 && block1.getBlock() != BlockInit.PORTAL_CONTROLBOOK1BOOK2 && block1.getBlock() != BlockInit.PORTAL_CONTROLBOOK1BOOK3 && block1.getBlock() != BlockInit.PORTAL_CONTROLBOOK2 && block1.getBlock() != BlockInit.PORTAL_CONTROLBOOK2BOOK3 && block1.getBlock() != BlockInit.PORTAL_CONTROLBOOK3)
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
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		return false;
	}
}