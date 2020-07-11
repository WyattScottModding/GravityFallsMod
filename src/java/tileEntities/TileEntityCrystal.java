package tileEntities;

import java.util.List;

import init.PotionInit;
import main.ConfigHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityCrystal extends TileEntity implements ITickable{

	public TileEntityCrystal() {
	}
	
	@Override
	public void update() {
		AxisAlignedBB Box = new AxisAlignedBB(pos.getX() - 3, pos.getY() - 3, pos.getZ() - 3, pos.getX() + 3, pos.getY() + 3, pos.getZ() + 3);

		List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, Box);

		for (EntityLivingBase element : list) {
			if((int)(Math.random() * 200) == 0) {
				boolean grow = (int)(Math.random() * 2) == 0;
				int growth = 0;

				if(grow)
					growth = (int)(Math.random() * 400);
				else
					growth = (int)(Math.random() * -60);

				element.removePotionEffect(PotionInit.GROWTH_EFFECT);
				PotionEffect effect = new PotionEffect(PotionInit.GROWTH_EFFECT, 10000000, growth, true, false);
				effect.setPotionDurationMax(true);
				element.addPotionEffect(effect);
			}
		}
	}

}
