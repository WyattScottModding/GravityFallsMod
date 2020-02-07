package items;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import entity.EntityEyeBat;
import init.ItemInit;
import main.GravityFalls;
import main.IHasModel;
import main.Reference;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Net extends ItemSword implements IHasModel
{

	public Net(String name, ToolMaterial material)
	{
		super(material);
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
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) 
	{
		EntityEyeBat eyebat = getMouseOver(player, world);
		
		if(eyebat != null)
		{
			boolean added = player.inventory.addItemStackToInventory(new ItemStack(ItemInit.EYEBAT));
			
			if(added)
			{
				eyebat.setDead();
				this.setDamage(player.getHeldItemMainhand(), 1);
			}
		}
				
		return super.onItemRightClick(world, player, hand);
	}

	
	public EntityEyeBat getMouseOver(EntityPlayer player, World world)
	{
		Vec3d lookVec = player.getLookVec();

		BlockPos pos = player.getPosition().add(0, player.eyeHeight, 0);

		float yaw = player.rotationYaw;
		float pitch = player.rotationPitch;

		List<Entity> list = null;

		for(int f = 0; f <= 5; f++)
		{
			double x = (double)(-MathHelper.sin(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);
			double y = (double)(-MathHelper.sin((pitch) / 180.0F * (float)Math.PI) * f);
			double z = (double)(MathHelper.cos(yaw / 180.0F * (float)Math.PI) * MathHelper.cos(pitch / 180.0F * (float)Math.PI) * f);


			AxisAlignedBB entityPos = new AxisAlignedBB(pos.getX() + x, pos.getY() + y, pos.getZ() + z, pos.getX() + x + 1, pos.getY() + y + 1, pos.getZ() + z + 1);

			list = world.getEntitiesInAABBexcluding(player, entityPos, Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>()
			{
				public boolean apply(@Nullable Entity p_apply_1_)
				{
					return p_apply_1_ != null && p_apply_1_.canBeCollidedWith();
				}
			}));
			
			if(!list.isEmpty())
			{
				if(list.get(0) instanceof EntityEyeBat)
					return (EntityEyeBat) list.get(0);
			}
		}
		return null;
	}
}