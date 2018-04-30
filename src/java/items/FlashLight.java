package items;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import handlers.BlockHandler;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

public class FlashLight extends ItemSword implements IHasModel
{

	public boolean clicked = false;
	public int counter = 0;
	public World world = null;

	public ArrayList<BlockHandler> blocks = new ArrayList<BlockHandler>();


	public FlashLight(String name)
	{
		super(ToolMaterial.WOOD);
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
	//	this.setCreativeTab(GravityFalls.gravityfallsitems);
		this.setMaxDamage(1000);

		ItemInit.ITEMS.add(this);
	}

	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		if(clicked && counter % 2 == 0)
		{
			clicked = false;
		}
		else if(!clicked && counter % 2 == 0)
		{
			clicked = true;
		}
		counter++;

		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
	{	
		//this.world = worldIn;

		if(worldIn.getWorldTime() % 10 == 0 && blocks != null && !blocks.isEmpty())
		{
			for(int i = 0; i < blocks.size(); i++)
			{
				if(blocks.get(i) != null)
				{
					BlockPos pos = new BlockPos(blocks.get(i).blockPos());

					if(worldIn.getLight(pos) == 15)
					{
						worldIn.setLightFor(EnumSkyBlock.BLOCK, pos, 0);
					}
					else
					{
						blocks.remove(i);
						i--;
					}
				}
			}
		}

		if(entityIn instanceof EntityPlayer)
		{
			EntityPlayer entityPlayer = (EntityPlayer) entityIn;

			if(entityPlayer.getHeldItemMainhand().isItemEqual(new ItemStack(ItemInit.FLASHLIGHT)))
			{
				RayTraceResult blockPosition = entityIn.rayTrace(25, 1.0F);

				if(clicked && worldIn.getWorldTime() % 10 == 0)
				{
					stack.damageItem(1, entityPlayer);

					for(int i = -2; i < 2; i++)
					{
						for(int k = -2; k < 2; k++)
						{
							for(int j = -3; j < 3; j++)
							{
								BlockPos pos = blockPosition.getBlockPos().add(i, j, k);
								IBlockState block = worldIn.getBlockState(blockPosition.getBlockPos());
								Block blockType = block.getBlock();

								if(blockType != Blocks.AIR && blockType != Blocks.TALLGRASS && blockType != Blocks.YELLOW_FLOWER && blockType != Blocks.RED_FLOWER && blockType != Blocks.CHORUS_FLOWER && blockType != Blocks.WATER && blockType != Blocks.FLOWING_WATER)
								{
									IBlockState block2 = worldIn.getBlockState(blockPosition.getBlockPos().up());
									Block blockType2 = block2.getBlock();

									if(blockType2 != Blocks.YELLOW_FLOWER && blockType2 != Blocks.RED_FLOWER && blockType2 != Blocks.CHORUS_FLOWER && blockType2 != Blocks.WATER && blockType2 != Blocks.FLOWING_WATER)
									{
										if(worldIn.getLightFor(EnumSkyBlock.BLOCK, pos) != 15)
										{
											worldIn.setLightFor(EnumSkyBlock.BLOCK, pos, 15);
											worldIn.notifyLightSet(pos);

											BlockHandler newBlock = new BlockHandler(blockType, pos);
											blocks.add(newBlock);
										}
									}
								}
							}
						}
					}
				}
			}

			if(stack.getItemDamage() >= 500 &&  Keyboard.isKeyDown(Keyboard.KEY_R))
			{
				ItemStack itemstack = findAmmo(entityPlayer);

				if(itemstack.isItemEqual(new ItemStack(ItemInit.BATTERY)))
				{
					stack.damageItem(-500, entityPlayer);
					itemstack.shrink(1);
				}
			}
		} 
	}

	public World getWorld()
	{
		return world;
	}

	private ItemStack findAmmo(EntityPlayer player)
	{
		if (player.inventory.getCurrentItem().areItemsEqualIgnoreDurability(player.getHeldItem(EnumHand.OFF_HAND), new ItemStack(ItemInit.BATTERY)))
		{
			return player.getHeldItem(EnumHand.OFF_HAND);
		}
		if (player.inventory.getCurrentItem().areItemsEqualIgnoreDurability(player.getHeldItem(EnumHand.MAIN_HAND), new ItemStack(ItemInit.BATTERY)))
		{
			return player.getHeldItem(EnumHand.MAIN_HAND);
		}
		else
		{
			for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
			{
				ItemStack itemstack = player.inventory.getStackInSlot(i);

				if (	player.inventory.getCurrentItem().areItemsEqualIgnoreDurability(itemstack, new ItemStack(ItemInit.BATTERY)))
				{
					return itemstack;
				}
			}

			return ItemStack.EMPTY;
		}
	}
}