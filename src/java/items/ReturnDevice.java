package items;

import commands.Teleport;
import handlers.RegistryHandler;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import updates.PortalUpdate;

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
		boolean active = false;

		if(PortalUpdate.nbt.hasKey("portalActive"))
			active = PortalUpdate.nbt.getBoolean("portalActive");

		if(player.dimension == 3 && !world.isRemote && active)
		{
			if(PortalUpdate.nbt != null && PortalUpdate.nbt.hasKey("PortalX"))
			{
				Teleport.teleportToDimension(player, 0, PortalUpdate.nbt.getInteger("PortalX"), PortalUpdate.nbt.getInteger("PortalY"), PortalUpdate.nbt.getInteger("PortalZ"));
			}
			else if(player.bedLocation != null)
			{
				BlockPos bedPos = player.bedLocation;
				Teleport.teleportToDimension(player, 0, bedPos.getX(), bedPos.getY(), bedPos.getZ());
			}
			else
			{
				Teleport.teleportToDimension(player, 0, 0, 67, 0);
			}
		}

		return super.onItemRightClick(world, player, hand);
	}

	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}	
}