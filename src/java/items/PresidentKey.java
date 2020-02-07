package items;

import java.util.Set;

import com.google.common.collect.Sets;

import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;

public class PresidentKey extends ItemTool implements IHasModel
{
	private static final Set<Block> EFFECTIVE_ON = Sets.newHashSet();


	public PresidentKey(String name, ToolMaterial material)
	{
		super(1.0F, -2.8F, material, EFFECTIVE_ON);
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
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) 
	{
		ItemStack stack = player.getHeldItem(hand);

		Block block = world.getBlockState(pos).getBlock();
		if ((block instanceof BlockDoor))
		{
			stack.damageItem(1, player);			
			BlockPos blockpos1 = world.getBlockState(pos).getValue(BlockDoor.HALF) == BlockDoor.EnumDoorHalf.LOWER ? pos : pos.toImmutable();
			IBlockState iblockstate1 = pos.equals(blockpos1) ? world.getBlockState(pos) : world.getBlockState(blockpos1);
			if (iblockstate1.getBlock() == block)
			{
				IBlockState s = iblockstate1.cycleProperty(BlockDoor.OPEN);

				if(iblockstate1.getMaterial() == Material.IRON) {
					if(iblockstate1.getValue(BlockDoor.OPEN))
						world.playSound(player, blockpos1, SoundEvents.BLOCK_IRON_DOOR_CLOSE, SoundCategory.BLOCKS, 1.0F, 1.0F);
					else
						world.playSound(player, blockpos1, SoundEvents.BLOCK_IRON_DOOR_OPEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}
				else {
					if(iblockstate1.getValue(BlockDoor.OPEN))
						world.playSound(player, blockpos1, SoundEvents.BLOCK_WOODEN_DOOR_CLOSE, SoundCategory.BLOCKS, 1.0F, 1.0F);
					else
						world.playSound(player, blockpos1, SoundEvents.BLOCK_WOODEN_DOOR_OPEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}

				world.setBlockState(blockpos1, s, 2);
				world.markBlockRangeForRenderUpdate(blockpos1, pos);
			}
		}
		if ((block instanceof BlockTrapDoor))
		{
			stack.damageItem(1, player);
			IBlockState iblockstate1 = pos.equals(pos) ? world.getBlockState(pos) : world.getBlockState(pos);
			if (iblockstate1.getBlock() == block)
			{
				IBlockState s = iblockstate1.cycleProperty(BlockTrapDoor.OPEN);
				
				if(iblockstate1.getMaterial() == Material.IRON) {
					if(iblockstate1.getValue(BlockDoor.OPEN))
						world.playSound(player, pos, SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, SoundCategory.BLOCKS, 1.0F, 1.0F);
					else
						world.playSound(player, pos, SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}
				else {
					if(iblockstate1.getValue(BlockDoor.OPEN))
						world.playSound(player, pos, SoundEvents.BLOCK_WOODEN_TRAPDOOR_CLOSE, SoundCategory.BLOCKS, 1.0F, 1.0F);
					else
						world.playSound(player, pos, SoundEvents.BLOCK_WOODEN_TRAPDOOR_OPEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
				}
				
				world.setBlockState(pos, s, 2);
				world.markBlockRangeForRenderUpdate(pos, pos);
			}
		}
		return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);

	}
}