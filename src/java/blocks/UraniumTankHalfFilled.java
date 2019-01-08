package blocks;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.Random;

import init.BlockInit;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArrow;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class UraniumTankHalfFilled extends Block implements IHasModel{

	public static final AxisAlignedBB URANIUMTANKHALFFILLED = new AxisAlignedBB(0D, 0D, 0D, 1.0D, 1.0D, 1.0D);

	public UraniumTankHalfFilled(String name, Material material)
	{
		super(material);
		this.setHardness(6.0F);
		this.setResistance(30.0F);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setLightLevel(0.5F);
		this.setSoundType(SoundType.METAL);

		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) 
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state) 
	{
		return false;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return URANIUMTANKHALFFILLED;
	}


	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
			EntityPlayer player) 
	{
		return new ItemStack(Item.getItemFromBlock(this), 1, getMetaFromState(world.getBlockState(pos)));
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) 
	{
		ItemStack itemstack = playerIn.getHeldItem(hand);

		if (itemstack.isEmpty())
		{
			return true;
		}
		else
		{

			Item item = itemstack.getItem();

			if (item == Items.BUCKET)
			{
				worldIn.setBlockState(pos, BlockInit.URANIUM_TANK.getDefaultState());

				if(!playerIn.capabilities.isCreativeMode)
					playerIn.setHeldItem(hand, new ItemStack(ItemInit.URANIUM_BUCKET));

				return true;
			}
			else if (item == ItemInit.URANIUM_BUCKET)
			{
				worldIn.setBlockState(pos, BlockInit.URANIUM_TANK_FILLED.getDefaultState());

				if(!playerIn.capabilities.isCreativeMode)
					playerIn.setHeldItem(hand, new ItemStack(Items.BUCKET));

				return true;
			}


			return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
		}
	}






}
