package entity;

import main.GravityFalls;
import main.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class EntityRegistry 
{
	public static void registerEntities()
	{
		registerEntity("gnome", EntityGnome.class, Reference.ENTITY_GNOME, 40, 15532032, 16777215);
		registerEntity("timecoplolph", EntityTimeCopLolph.class, Reference.ENTITY_TIMECOP_LOLPH, 80, 3421236, 1822835);
		registerEntity("timecopdundgren", EntityTimeCopLolph.class, Reference.ENTITY_TIMECOP_DUNDGREN, 80, 3421236, 1822835);
	}
	
	public static void registerEntity(String name, Class<? extends Entity> entity, int id, int range, int color1, int color2)
	{
		net.minecraftforge.fml.common.registry.EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID + ":" + name), entity, name, id, GravityFalls.instance, range, 1, true, color1, color2);
	}

}
