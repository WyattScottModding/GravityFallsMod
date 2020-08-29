package armor;

import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class TieOfPossession2 extends ItemArmor implements IHasModel
{
	public TieOfPossession2(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) 
	{
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(null);

		ItemInit.ITEMS.add(this);
	}

	@Override
	public void registerModels() 
	{
		GravityFalls.proxy.registerItemRenderer(this, 0, "inventory");
	}
	
	@Override
	public Item setCreativeTab(CreativeTabs tab) {
		return this;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) 
	{
		player.getHeldItemMainhand().shrink(1);
		
		return super.onItemRightClick(world, player, hand);
	}
}