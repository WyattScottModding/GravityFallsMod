package items;

import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import updates.WeirdmageddonEvent;

public class Rift extends Item implements IHasModel
{
	public Rift(String name)
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
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

		if(!world.isRemote) {
			if(!WeirdmageddonEvent.eventActive) {
				//RegistryHandler.endWeirdMageddon();
				//WeirdmageddonEvent.startEvent(world);
				
				//ITextComponent msg = new TextComponentString("WeirdMageddon has ended");
				//player.sendMessage(msg);
			}
			else {
				//RegistryHandler.startWeirdMageddon();
				//WeirdmageddonEvent.endEvent(world);

				//ITextComponent msg = new TextComponentString("WeirdMageddon has begun");
				//player.sendMessage(msg);
			}
		}


		return super.onItemRightClick(world, player, hand);
	}
}