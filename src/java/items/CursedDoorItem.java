package items;

import init.BlockInit;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CursedDoorItem extends ItemDoor implements IHasModel{

	public CursedDoorItem(String name)
	{
		super(BlockInit.CURSED_DOOR);
		this.setMaxStackSize(16);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsblocks);

		ItemInit.ITEMS.add(this);
	}

	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}

	/**
	 * Called when a Block is right-clicked with this Item
	 */
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (facing != EnumFacing.UP)
		{
			return EnumActionResult.FAIL;
		}
		else
		{
			IBlockState iblockstate = worldIn.getBlockState(pos);
			Block block = iblockstate.getBlock();

			if (!block.isReplaceable(worldIn, pos))
			{
				pos = pos.offset(facing);
			}

			ItemStack itemstack = player.getHeldItem(hand);

			if (player.canPlayerEdit(pos, facing, itemstack))
			{
				if(block.canPlaceBlockAt(worldIn, pos))
				{
					EnumFacing enumfacing = EnumFacing.fromAngle((double)player.rotationYaw);
					int i = enumfacing.getFrontOffsetX();
					int j = enumfacing.getFrontOffsetZ();
					boolean flag = i < 0 && hitZ < 0.5F || i > 0 && hitZ > 0.5F || j < 0 && hitX > 0.5F || j > 0 && hitX < 0.5F;
					placeDoor(worldIn, pos, enumfacing, BlockInit.CURSED_DOOR, flag);
					SoundType soundtype = worldIn.getBlockState(pos).getBlock().getSoundType(worldIn.getBlockState(pos), worldIn, pos, player);
					worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
					itemstack.shrink(1);
					return EnumActionResult.SUCCESS;
				}				
			}
			return EnumActionResult.FAIL;
		}
	}
}