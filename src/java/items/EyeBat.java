package items;

import entity.EntityEyeBat;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class EyeBat extends Item implements IHasModel
{
	public EyeBat(String name)
	{
		this.setMaxStackSize(64);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);

		ItemInit.ITEMS.add(this);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) 
	{
		ItemStack itemstack = player.getHeldItem(EnumHand.MAIN_HAND);

		if(!world.isRemote)
		{
			EntityEyeBat eyebat = new EntityEyeBat(world, player.posX, player.posY, player.posZ);
			world.spawnEntity(eyebat);

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