package blocks.slabs;

import java.util.Random;

import init.BlockInit;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public abstract class BlockSlabBase extends BlockSlab
{
	public static final PropertyEnum<Variant> VARIANT = PropertyEnum.<Variant>create("variant", Variant.class);
	
	public BlockSlabBase(String name, Material materialIn) 
	{
		super(materialIn);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsblocks);
		this.useNeighborBrightness = !this.isDouble();
		this.setHardness(2.0F);
		this.setResistance(15.0F);
		
		IBlockState state = this.blockState.getBaseState();
		if(!this.isDouble()) 
			state = state.withProperty(HALF, EnumBlockHalf.BOTTOM);
		setDefaultState(state);
				
		BlockInit.BLOCKS.add(this);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(BlockInit.REDWOOD_SLAB_HALF);
	}
	
	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) 
	{
		return new ItemStack(BlockInit.REDWOOD_SLAB_HALF);
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos,
			EntityPlayer player) {
		return new ItemStack(BlockInit.REDWOOD_SLAB_HALF);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) 
	{
		IBlockState state = this.blockState.getBaseState();
		if(!this.isDouble()) state = state.withProperty(HALF, ((meta&8) != 0) ? EnumBlockHalf.TOP : EnumBlockHalf.BOTTOM);
		return state;
	}
	
	@Override
	public int getMetaFromState(IBlockState state) 
	{
		int meta = 0;
		if(!this.isDouble() && state.getValue(HALF) == EnumBlockHalf.TOP) meta |= 8;
		return meta;
	}
	
	@Override
	protected BlockStateContainer createBlockState() 
	{
		if(!this.isDouble()) 
			return new BlockStateContainer(this, new IProperty[] {VARIANT,HALF});
		else 
			return new BlockStateContainer(this, new IProperty[] {VARIANT});
	}
	
	@Override
	public String getUnlocalizedName(int meta) 
	{
		return super.getUnlocalizedName();
	}

	@Override
	public Comparable<?> getTypeForItem(ItemStack stack) 
	{
		return Variant.DEFAULT;
	}
	
	@Override
	public IProperty<?> getVariantProperty()
	{
		return VARIANT;
	}
	
	public static enum Variant implements IStringSerializable
	{
		DEFAULT;
		
		@Override
		public String getName()
		{
			return "default";
		}
	}
}
