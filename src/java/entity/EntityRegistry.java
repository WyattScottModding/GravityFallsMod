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
		registerEntity("golfcart", EntityGolfCart.class, Reference.ENTITY_GOLFCART, 100, 10631725, 15841385);
		registerEntity("eyebat", EntityEyeBat.class, Reference.ENTITY_EYEBAT, 40, 00000000, 15846530);
		registerEntity("hidebehind", EntityHideBehind.class, Reference.ENTITY_HIDEBEHIND, 100, 00000000, 00000000);
		registerEntity("securitydroid", EntitySecurityDroid.class, Reference.ENTITY_SECURITYDROID, 100, 10000000, 00000000);
		registerEntity("bill", EntityBill.class, Reference.ENTITY_BILL, 300, 16774985, 00000000);
		registerEntity("eightball", EntityEightBall.class, Reference.ENTITY_EIGHTBALL, 40, 4472596, 16711543);
		registerEntity("eyebathuge", EntityEyeBatHuge.class, Reference.ENTITY_EYEBATHUGE, 40, 00000000, 10944522);
		registerEntity("keyhole", EntityKeyhole.class, Reference.ENTITY_KEYHOLE, 40, 7403753, 14898591);
		registerEntity("unicorn", EntityUnicorn.class, Reference.ENTITY_UNICORN, 60, 9669872, 16747519);
		registerEntity("gideonbot", EntityGideonBot.class, Reference.ENTITY_GIDEONBOT, 60, 10735355, 15518927);
		registerEntity("timebaby", EntityTimeBaby.class, Reference.ENTITY_TIMEBABY, 100, 15460340, 1703889);

	}

	public static void registerEntity(String name, Class<? extends Entity> entity, int id, int range, int color1, int color2)
	{
		net.minecraftforge.fml.common.registry.EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID + ":" + name), entity, name, id, GravityFalls.instance, range, 1, true, color1, color2);
	}
}