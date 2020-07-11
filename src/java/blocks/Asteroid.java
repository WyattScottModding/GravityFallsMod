package blocks;

import init.BlockInit;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class Asteroid extends Block implements IHasModel{


	public Asteroid(String name, Material material)
	{
		super(material);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setSoundType(SoundType.STONE);
		this.setHardness(2.0F);
		this.setResistance(10.0F);
		this.setCreativeTab(GravityFalls.gravityfallsblocks);

		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

	@Override
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
	public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction,
			IPlantable plantable) {
		return true;
	}
}