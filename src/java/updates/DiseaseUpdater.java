package updates;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class DiseaseUpdater 
{		
	public static void radiationEffect(EntityLivingBase entity, World world)
	{
		int randomNausea =  (int) (Math.random() * 400);
		int randomPoison =  (int) (Math.random() * 400);
		int randomHurt = (int) (Math.random() * 4000);
		int randomDeath = (int) (Math.random() * 16000);


		//Hurt the entity occasionally
		if(randomHurt == 0)
		{
			float damage = entity.getHealth() / 4;

			entity.setHealth((float) (entity.getHealth() - damage));
		}

		//Give the entity poison occasionally
		if(randomPoison == 0)
		{
			entity.addPotionEffect(new PotionEffect(MobEffects.POISON, 100, 0));
		}

		//Give the entity nausea occasionally
		if(randomNausea == 0)
		{
			entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 100, 0));
		}


		//Pretty good chance of killing the victim at some point while infected
		if(randomDeath == 0)
		{
			if(entity instanceof EntityPlayer && !((EntityPlayer) entity).isCreative())
			{
				EntityPlayer player = (EntityPlayer) entity;

				ITextComponent msg = new TextComponentString(player.getName() + " was killed by Radiation");
				world.getMinecraftServer().sendMessage(msg);

				player.setHealth(0);
			}
			else if(!(entity instanceof EntityPlayer))
			{
				entity.setHealth(0);
			}
		}
	}
}