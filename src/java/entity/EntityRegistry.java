package entity;

import main.ConfigHandler;
import main.GravityFalls;
import main.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class EntityRegistry 
{
	public static void registerEntities()
	{
		registerEntity("gnome", EntityGnome.class, ConfigHandler.ENTITY_GNOME, 40, 15532032, 16777215);
		registerEntity("timecoplolph", EntityTimeCopLolph.class, ConfigHandler.ENTITY_TIMECOP_LOLPH, 80, 3421236, 1822835);
		registerEntity("timecopdundgren", EntityTimeCopDundgren.class, ConfigHandler.ENTITY_TIMECOP_DUNDGREN, 80, 3421236, 1822835);
		registerEntity2("golfcart", EntityGolfCart.class, ConfigHandler.ENTITY_GOLFCART, 100);
		registerEntity("eyebat", EntityEyeBat.class, ConfigHandler.ENTITY_EYEBAT, 40, 00000000, 15846530);
		registerEntity("hidebehind", EntityHideBehind.class, ConfigHandler.ENTITY_HIDEBEHIND, 100, 00000000, 00000000);
		registerEntity("securitydroid", EntitySecurityDroid.class, ConfigHandler.ENTITY_SECURITYDROID, 100, 10000000, 00000000);
		registerEntity("bill", EntityBill.class, ConfigHandler.ENTITY_BILL, 300, 16774985, 00000000);
		registerEntity("eightball", EntityEightBall.class, ConfigHandler.ENTITY_EIGHTBALL, 40, 4472596, 16711543);
		registerEntity("eyebathuge", EntityEyeBatHuge.class, ConfigHandler.ENTITY_EYEBATHUGE, 40, 00000000, 10944522);
		registerEntity("keyhole", EntityKeyhole.class, ConfigHandler.ENTITY_KEYHOLE, 40, 7403753, 14898591);
		registerEntity("unicorn", EntityUnicorn.class, ConfigHandler.ENTITY_UNICORN, 60, 9669872, 16747519);
		registerEntity2("gideonbot", EntityGideonBot.class, ConfigHandler.ENTITY_GIDEONBOT, 60);
		registerEntity("timebaby", EntityTimeBaby.class, ConfigHandler.ENTITY_TIMEBABY, 100, 15460340, 1703889);
		registerEntity("shapeshifter", EntityShapeShifter.class, ConfigHandler.ENTITY_SHAPESHIFTER, 100, 14406843, 12742797);
		registerEntity2("billstatue", EntityBillStatue.class, ConfigHandler.ENTITY_BILL_STATUE, 100);
		registerEntity2("cipherwheel", EntityCipherWheel.class, ConfigHandler.ENTITY_CIPHER_WHEEL, 100);
		registerEntity("evil_tree", EntityEvilTree.class, ConfigHandler.ENTITY_EVIL_TREE, 40, 9408399, 14803354);

		registerEntity2("entitybullet", EntityForget.class, ConfigHandler.ENTITY_FORGET, 100);
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