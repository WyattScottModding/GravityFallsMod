package items;

import entity.EntityGnome;
import entity.EntityGolfCart;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import main.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class GolfCart extends Item implements IHasModel{

	public GolfCart(String name)
	{
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(GravityFalls.gravityfallsitems);
		
		ItemInit.ITEMS.add(this);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) 
	{
		ItemStack itemstack = playerIn.getHeldItem(EnumHand.MAIN_HAND);
		
		EntityGolfCart golfcart = new EntityGolfCart(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ);
		worldIn.spawnEntity(golfcart);
		
		itemstack.shrink(1);
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
