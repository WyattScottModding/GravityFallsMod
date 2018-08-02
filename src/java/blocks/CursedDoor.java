package blocks;

import init.BlockInit;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class CursedDoor extends BlockDoor implements IHasModel
{

	public CursedDoor(String name, Material material)
	{
		super(material);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setSoundType(SoundType.STONE);

		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
			EntityPlayer player) 
	{
		return new ItemStack(Item.getItemFromBlock(this), 1, getMetaFromState(world.getBlockState(pos)));
	}


	@Override
	public Boolean isEntityInsideMaterial(IBlockAccess world, BlockPos blockpos, IBlockState iblockstate, Entity entity,
			double yToTest, Material materialIn, boolean testingHead) 
	{
		if(entity instanceof EntityLivingBase)
		{
			EntityLivingBase entityLiving = (EntityLivingBase) entity;
			int x = (int) (Math.random() * 20000) - 10000;
			int y = (int)entityLiving.posY;
			int z = (int) (Math.random() * 20000) - 10000;


			entityLiving.attemptTeleport(x, y, z);
		}

		return super.isEntityInsideMaterial(world, blockpos, iblockstate, entity, yToTest, materialIn, testingHead);
	}



}