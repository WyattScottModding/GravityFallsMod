package items;

import entity.EntityGideonBot;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class GideonBot extends Item implements IHasModel
{
	/**
		This item was never implemented into the mod
	*/
	public GideonBot(String name)
	{
		this.setMaxStackSize(1);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		//this.setCreativeTab(GravityFalls.gravityfallsitems);
		
		ItemInit.ITEMS.add(this);
	}
	
	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}	
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) 
	{
		ItemStack itemstack = player.getHeldItem(EnumHand.MAIN_HAND);

		if(!world.isRemote)
		{
			EntityGideonBot gideonBot = new EntityGideonBot(world, player.posX, player.posY, player.posZ);
			world.spawnEntity(gideonBot);

			if(!player.isCreative())
				itemstack.shrink(1);
		}

		return super.onItemRightClick(world, player, hand);
	}
}