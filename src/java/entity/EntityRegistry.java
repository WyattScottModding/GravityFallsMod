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
		registerEntity("timecopdundgren", EntityTimeCopDundgren.class, Reference.ENTITY_TIMECOP_DUNDGREN, 80, 3421236, 1822835);
		registerEntity2("golfcart", EntityGolfCart.class, Reference.ENTITY_GOLFCART, 100);
		registerEntity("eyebat", EntityEyeBat.class, Reference.ENTITY_EYEBAT, 40, 00000000, 15846530);
		registerEntity("hidebehind", EntityHideBehind.class, Reference.ENTITY_HIDEBEHIND, 100, 00000000, 00000000);
		registerEntity("securitydroid", EntitySecurityDroid.class, Reference.ENTITY_SECURITYDROID, 100, 10000000, 00000000);
		registerEntity("bill", EntityBill.class, Reference.ENTITY_BILL, 300, 16774985, 00000000);
		registerEntity("eightball", EntityEightBall.class, Reference.ENTITY_EIGHTBALL, 40, 4472596, 16711543);
		registerEntity("eyebathuge", EntityEyeBatHuge.class, Reference.ENTITY_EYEBATHUGE, 40, 00000000, 10944522);
		registerEntity("keyhole", EntityKeyhole.class, Reference.ENTITY_KEYHOLE, 40, 7403753, 14898591);
		registerEntity("unicorn", EntityUnicorn.class, Reference.ENTITY_UNICORN, 60, 9669872, 16747519);
		registerEntity2("gideonbot", EntityGideonBot.class, Reference.ENTITY_GIDEONBOT, 60);
		registerEntity("timebaby", EntityTimeBaby.class, Reference.ENTITY_TIMEBABY, 100, 15460340, 1703889);

	}

	public static void registerEntity(String name, Class<? extends Entity> entity, int id, int range, int color1, int color2)
	{
		net.minecraftforge.fml.common.registry.EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID + ":" + name), entity, name, id, GravityFalls.instance, range, 1, true, color1, color2);
	}
	
	public static void registerEntity2(String name, Class<? extends Entity> entity, int id, int range)
	{
		net.minecraftforge.fml.common.registry.EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID + ":" + name), entity, name, id, GravityFalls.instance, range, 1, true);
	}
}