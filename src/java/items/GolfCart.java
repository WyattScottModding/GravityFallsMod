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
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) 
	{
		ItemStack itemstack = player.getHeldItem(EnumHand.MAIN_HAND);

		if(!world.isRemote)
		{
			EntityGolfCart golfcart = new EntityGolfCart(world, player.posX, player.posY, player.posZ);
			world.spawnEntity(golfcart);

			if(!player.isCreative())
				itemstack.shrink(1);
		}

		return super.onItemRightClick(world, player, hand);
	}

	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
