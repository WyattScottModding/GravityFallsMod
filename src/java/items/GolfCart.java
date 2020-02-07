package items;

import entity.EntityGolfCart;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
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
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
		ItemStack itemstack = player.getHeldItem(EnumHand.MAIN_HAND);

		if (!world.isRemote)
        {
            EntityGolfCart golfCart = new EntityGolfCart(world, (double)pos.getX() + 0.5D, (double)pos.getY() + 0.0625D, (double)pos.getZ() + 0.5D);

            if (itemstack.hasDisplayName())
            {
            	golfCart.setCustomNameTag(itemstack.getDisplayName());
            }

            world.spawnEntity(golfCart);
        
			if(!player.isCreative())
				itemstack.shrink(1);
		}
		
        return EnumActionResult.PASS;
    }


	public void registerModels()
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
