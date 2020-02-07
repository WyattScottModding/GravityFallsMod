package items;

import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import network.MessageOpenReturnDevice;
import network.Messages;

public class ReturnDevice extends Item implements IHasModel
{

	public ReturnDevice(String name)
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
		if(player.dimension == 3 && !world.isRemote) //&& active)
		{
			if(player instanceof EntityPlayerMP) {
				EntityPlayerMP serverPlayer = (EntityPlayerMP) player;
				Messages.INSTANCE.sendTo(new MessageOpenReturnDevice(),  serverPlayer);
			}
		}

		return super.onItemRightClick(world, player, hand);
	}

	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}	
}