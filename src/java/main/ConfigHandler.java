package main;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@SuppressWarnings("unused")
public class ConfigHandler 
{
	public static Configuration config;

	//IDs
	public static int ENTITY_GNOME;
	public static int ENTITY_FORGET;
	public static int ENTITY_HOOK;
	public static int ENTITY_TIMECOP_LOLPH;
	public static int ENTITY_TIMECOP_DUNDGREN;
	public static int ENTITY_GOLFCART;
	public static int ENTITY_EYEBAT;
	public static int ENTITY_HIDEBEHIND;
	public static int ENTITY_SECURITYDROID;
	public static int ENTITY_BILL;
	public static int ENTITY_EIGHTBALL;
	public static int ENTITY_EYEBATHUGE;
	public static int ENTITY_KEYHOLE;
	public static int ENTITY_UNICORN;
	public static int ENTITY_GIDEONBOT;
	public static int ENTITY_TIMEBABY;
	public static int ENTITY_SHAPESHIFTER;

	public static int GUI_URANIUM_FURNACE;
	public static int GUI_JOURNAL1;
	public static int GUI_JOURNAL2;
	public static int GUI_JOURNAL3;
	public static int SCOPE;
	public static int COMPUTER;
	public static int RETURN_DEVICE;

	public static int THE_FUTURE;
	public static int NIGHTMARE_REALM;
	public static int WEIRDMAGEDDON;

	public static boolean INFINITY_PIZZA;
	public static boolean DIAMOND_BLOCKS;
	public static boolean FIRE_HELMET;
	public static boolean STRENGTH_CHESTPLATE;
	public static boolean REGENERATION_LEGS;
	public static boolean SPEED_BOOTS;
	
	public static boolean INFINITY_SIDED_DICE;
	public static int PORTAL_COUNTDOWN;

	public static float SIZE_MAX;
	public static float SIZE_MIN;


	public static void init(File file)
	{
		config = new Configuration(file);
		syncConfig();
	}

	public static void syncConfig()
	{
		String category;
		category = "Various Settings";
		config.addCustomCategoryComment(category, "Settings");

		//name, category, default value, min Value, max Value
		INFINITY_SIDED_DICE = config.getBoolean("Infinity Sided Dice Settings", category, false, "Can the Infinity Sided Dice permanently affect your world?");
		PORTAL_COUNTDOWN = config.getInt("Countdown time to portal opening", category, 36000, 800, 1000000, "This value is in ticks.  20 ticks = 1 second.");

		SIZE_MAX = config.getFloat("Magic Flashlight Max", category, 400F, 1.8F, 1000F, "Maximum size an entity can grow");
		SIZE_MIN = config.getFloat("Magic Flashlight Min", category, .3F, .3F, 1.8F, "Minimum size an entity can shrink");

		
		category = "Dimension";
		config.addCustomCategoryComment(category, "Dimension Settings");
		
		THE_FUTURE = config.getInt("Future Dimension ID", category, 2, 2, 100, "ID for The Future Dimension");
		NIGHTMARE_REALM = config.getInt("Nightmare Realm Dimension ID", category, 3, 2, 100, "ID for The Nightmare Realm");
		WEIRDMAGEDDON = config.getInt("Weirdmageddon Realm Dimension ID", category, 4, 2, 100, "ID for The Weirdmageddon Realm");


		category = "Entity IDs";
		config.addCustomCategoryComment(category, "Set ID's for the entities");
		
		ENTITY_GNOME = config.getInt("Entity Gnome ID", category, 300, 120, 10000, "ID for the Gnome entity");
		ENTITY_FORGET = config.getInt("Entity Forget ID", category, 301, 120, 10000, "ID for the Forget entity");
		ENTITY_HOOK = config.getInt("Entity Hook ID", category, 302, 120, 10000, "ID for the Hook entity");
		ENTITY_TIMECOP_LOLPH = config.getInt("Entity TimeCop Lolph ID", category, 303, 120, 10000, "ID for the TimeCop Lolph entity");
		ENTITY_TIMECOP_DUNDGREN = config.getInt("Entity TimeCop Dundgren ID", category, 304, 120, 10000, "ID for the TimeCop Dundgren entity");
		ENTITY_GOLFCART = config.getInt("Entity Golfcart ID", category, 305, 120, 10000, "ID for the Golfcart entity");
		ENTITY_EYEBAT = config.getInt("Entity Eyebat ID", category, 306, 120, 10000, "ID for the Eyebat entity");
		ENTITY_HIDEBEHIND = config.getInt("Entity Hidebehind ID", category, 307, 120, 10000, "ID for the Hidebehind entity");
		ENTITY_SECURITYDROID = config.getInt("Entity Security Droid ID", category, 308, 120, 10000, "ID for the Security Droid entity");
		ENTITY_BILL = config.getInt("Entity Bill ID", category, 309, 120, 10000, "ID for the Bill entity");
		ENTITY_EIGHTBALL = config.getInt("Entity Eightball ID", category, 310, 120, 10000, "ID for the Eightball entity");
		ENTITY_EYEBATHUGE = config.getInt("Entity Eyebat Huge ID", category, 311, 120, 10000, "ID for the Huge Eyebat entity");
		ENTITY_KEYHOLE = config.getInt("Entity Keyhole ID", category, 312, 120, 10000, "ID for the Keyhole entity");
		ENTITY_UNICORN = config.getInt("Entity Unicorn ID", category, 313, 120, 10000, "ID for the Unicorn entity");
		ENTITY_GIDEONBOT = config.getInt("Entity Gideon Bot ID", category, 314, 120, 10000, "ID for the Gideon Bot entity");
		ENTITY_TIMEBABY = config.getInt("Entity Time Baby ID", category, 315, 120, 10000, "ID for the Time Baby entity");
		ENTITY_SHAPESHIFTER = config.getInt("Entity Shape Shifter ID", category, 316, 120, 10000, "ID for the Shape Shifter entity");

		
		category = "Gui IDs";
		config.addCustomCategoryComment(category, "Set ID's for the GUI's");
		
		GUI_URANIUM_FURNACE = config.getInt("Gui Uranium ID", category, 400, 120, 10000, "ID for the Uranium gui");
		GUI_JOURNAL1 = config.getInt("Gui Journal 1 ID", category, 401, 120, 10000, "ID for the Journal 1 gui");
		GUI_JOURNAL2 = config.getInt("Gui Journal 2 ID", category, 402, 120, 10000, "ID for the Journal 2 gui");
		GUI_JOURNAL3 = config.getInt("Gui Journal 3 ID", category, 403, 120, 10000, "ID for the Journal 3 gui");
		SCOPE = config.getInt("Gui Scope ID", category, 404, 120, 10000, "ID for the Scope gui");
		COMPUTER = config.getInt("Gui Computer ID", category, 405, 120, 10000, "ID for the Computer gui");
		RETURN_DEVICE = config.getInt("Gui Return Device ID", category, 406, 120, 10000, "ID for the Return Device gui");

		
		category = "Time Wishes";
		config.addCustomCategoryComment(category, "If you don't want certain Time Wishes to be craftable, then change the value to false.");
		INFINITY_PIZZA = config.getBoolean("Infinity Pizza", category, true, "Crafting for the Infinity Pizza");
		DIAMOND_BLOCKS = config.getBoolean("Diamond Blocks", category, true, "Crafting for the Diamond Blocks");
		FIRE_HELMET = config.getBoolean("Fire Helmet", category, true, "Crafting for the Fire Helmet");
		STRENGTH_CHESTPLATE = config.getBoolean("Strength Chestplate", category, true, "Crafting for the Strength Chestplate");
		REGENERATION_LEGS = config.getBoolean("Regeneration Leggings", category, true, "Crafting for the Regeneration Leggings");
		SPEED_BOOTS = config.getBoolean("Speed Boots", category, true, "Crafting for the Speed Boots");

		config.save();
	}
	
	public static void registerConfig(FMLPreInitializationEvent event) {
		GravityFalls.config = new File(event.getModConfigurationDirectory() + "/" + Reference.MODID);
		GravityFalls.config.mkdirs();
		init(new File(GravityFalls.config.getPath(), Reference.MODID + ".cfg"));
	}

}
