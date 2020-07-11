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
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import network.MessageOpenFordWorkbench;
import network.Messages;
import tileEntities.TileEntityComputer;
import tileEntities.TileEntityFordWorkbench;

public class FordWorkbench extends Block implements IHasModel
{
	public FordWorkbench(String name) 
	{
		super(Material.IRON);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsblocks);
		this.setHardness(4.0F);
		this.setResistance(20.0F);

		this.setSoundType(SoundType.METAL);

		BlockInit.BLOCKS.add(this);
		ItemInit.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if(!worldIn.isRemote)
		{	
			if(!worldIn.isRemote && playerIn instanceof EntityPlayerMP) {
				EntityPlayerMP serverPlayer = (EntityPlayerMP) playerIn;
				Messages.INSTANCE.sendTo(new MessageOpenFordWorkbench(),  serverPlayer);
			}
		}

		return true;
	}
	
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityFordWorkbench();
	}
	
	@Override
	public boolean hasTileEntity() {
		return true;
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) 
	{
		return new ItemStack(Item.getItemFromBlock(this));
	}
	
	@Override
	public void registerModels() 
	{
		GravityFalls.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
	}
}