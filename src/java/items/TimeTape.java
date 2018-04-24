package items;

import commands.Teleport;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class TimeTape extends Item implements IHasModel{

	public TimeTape(String name)
	{
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
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) 
	{
		if(!worldIn.isRemote)
		{
			if(playerIn.dimension == 0)
				Teleport.teleportToDimension(playerIn, 2, 0, 67, 0);
			if(playerIn.dimension == 2)
			{
			//	playerIn.preparePlayerToSpawn();
			//	playerIn.respawnPlayer();
				Teleport.teleportToDimension(playerIn, 2, 0, 67, 0);

			}
		}
		
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
}
