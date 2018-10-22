package init;

import java.util.ArrayList;
import java.util.List;

import armor.CloakOfInvisibility;
import armor.Dipper;
import armor.FireHelmet;
import armor.GoldKnuckles;
import armor.Mabel;
import armor.MysticAmulet;
import armor.RegenerationLegs;
import armor.RubberBoots;
import armor.RubberChestplate;
import armor.RubberHat;
import armor.RubberLeggings;
import armor.SpeedBoots;
import armor.StrengthChestplate;
import armor.TieOfPossession;
import armor.TieOfPossession2;
import food.InfinityPizza;
import food.SmileDip;
import handlers.SoundsHandler;
import items.Battery;
import items.BlackLight;
import items.Book1;
import items.Book2;
import items.Book3;
import items.CopperIngot;
import items.CrystalShard;
import items.CustomRecords;
import items.EyeBat;
import items.FlashLight;
import items.GideonBot;
import items.GideonBotPart;
import items.GolfCart;
import items.GrapplingHook;
import items.InfinitySidedDie;
import items.IterdimensionalRift;
import items.LaserArmCannon;
import items.Latex;
import items.LeafBlower;
import items.LightBulb;
import items.MagicFlashLight;
import items.MagnetGun;
import items.MemoryGun;
import items.Net;
import items.PresidentKey;
import items.QuantumDestabilizer;
import items.ReturnDevice;
import items.Rift;
import items.Rubber;
import items.TimeTape;
import items.TimeWish;
import items.UraniumBucket;
import main.Reference;
import net.minecraft.block.material.Material;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.util.EnumHelper;
import potions.PotionFreeze;

public class ItemInit {


	public static final List<Item> ITEMS = new ArrayList<Item>();


	//Armor Material
	public static final ArmorMaterial DIPPER = EnumHelper.addArmorMaterial("dipper", Reference.MODID + ":dipper", 20, new int[]{2, 3, 4, 2}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.5F);
	public static final ArmorMaterial MABEL = EnumHelper.addArmorMaterial("mabel", Reference.MODID + ":mabel", 20, new int[]{2, 3, 4, 2}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.5F);
	public static final ArmorMaterial RUBBER = EnumHelper.addArmorMaterial("rubber", Reference.MODID + ":rubber", 10, new int[]{2, 3, 4, 2}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
	public static final ArmorMaterial MAGIC = EnumHelper.addArmorMaterial("magic", Reference.MODID + ":magic", 50, new int[]{7, 8, 9, 6}, 0, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0F);
	public static final ArmorMaterial CLOAK = EnumHelper.addArmorMaterial("cloak", Reference.MODID + ":cloak", 40, new int[]{2, 2, 2, 2}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);
	public static final ArmorMaterial KNUCKLES = EnumHelper.addArmorMaterial("knuckles", Reference.MODID + ":knuckles", 30, new int[]{1, 1, 1, 1}, 0, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0F);
	public static final ArmorMaterial AMULET = EnumHelper.addArmorMaterial("amulet", Reference.MODID + ":amulet", 30, new int[]{1, 1, 1, 1}, 0, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 0.0F);
	public static final ArmorMaterial TIE = EnumHelper.addArmorMaterial("tie", Reference.MODID + ":tie", 40, new int[]{1, 1, 1, 1}, 0, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0.0F);

	//Armor
	public static final Item PINE_HAT = new Dipper("pinehat", DIPPER, 1, EntityEquipmentSlot.HEAD);
	public static final Item PINE_SHIRT = new Dipper("pinevest", DIPPER, 1, EntityEquipmentSlot.CHEST);
	public static final Item PINE_PANTS = new Dipper("pinepants", DIPPER, 1, EntityEquipmentSlot.LEGS);
	public static final Item PINE_SHOES = new Dipper("pineshoes", DIPPER, 1, EntityEquipmentSlot.FEET);

	public static final Item MABEL_BANDANA = new Mabel("mabelbandana", MABEL, 1, EntityEquipmentSlot.HEAD);
	public static final Item MABEL_SWEATER = new Mabel("mabelsweater", MABEL, 1, EntityEquipmentSlot.CHEST);
	public static final Item MABEL_PANTS = new Mabel("mabelpants", MABEL, 1, EntityEquipmentSlot.LEGS);
	public static final Item MABEL_SHOES = new Mabel("mabelshoes", MABEL, 1, EntityEquipmentSlot.FEET);

	public static final Item RUBBER_HAT = new RubberHat("rubberhat", RUBBER, 1, EntityEquipmentSlot.HEAD);
	public static final Item RUBBER_CHESTPLATE = new RubberChestplate("rubberchestplate", RUBBER, 1, EntityEquipmentSlot.CHEST);
	public static final Item RUBBER_LEGGINGS = new RubberLeggings("rubberleggings", RUBBER, 1, EntityEquipmentSlot.LEGS);
	public static final Item RUBBER_BOOTS = new RubberBoots("rubberboots", RUBBER, 1, EntityEquipmentSlot.FEET);

	public static final Item FIRE_HELMET = new FireHelmet("firehelmet", MAGIC, 1, EntityEquipmentSlot.HEAD);
	public static final Item STRENGTH_CHESTPLATE = new StrengthChestplate("strengthchestplate", MAGIC, 1, EntityEquipmentSlot.CHEST);
	public static final Item REGENERATION_LEGS = new RegenerationLegs("regenerationlegs", MAGIC, 1, EntityEquipmentSlot.LEGS);
	public static final Item SPEED_BOOTS = new SpeedBoots("speedboots", MAGIC, 1, EntityEquipmentSlot.FEET);

	public static final Item CLOAK_OF_INVISIBILITY = new CloakOfInvisibility("cloak", CLOAK, 1, EntityEquipmentSlot.CHEST);
	public static final Item GOLD_KNUCKLES = new GoldKnuckles("goldknuckles", KNUCKLES, 1, EntityEquipmentSlot.CHEST);
	public static final Item MYSTIC_AMULET = new MysticAmulet("mysticamulet", AMULET, 1, EntityEquipmentSlot.CHEST);
	public static final Item TIE_OF_POSSESSION = new TieOfPossession("tie", TIE, 1, EntityEquipmentSlot.CHEST);
	public static final Item TIE_OF_POSSESSION2 = new TieOfPossession2("tie2", TIE, 1, EntityEquipmentSlot.CHEST);

	
	
	//Items
	public static final Item CRYSTALSHARD = new CrystalShard("crystalshard");

	public static final Item FLASHLIGHT = new FlashLight("flashlight");

	public static final Item MAGICFLASHLIGHT = new MagicFlashLight("magicflashlight");

	public static final Item BOOK1 = new Book1("book1");

	public static final Item BOOK2 = new Book2("book2");

	public static final Item BOOK3 = new Book3("book3");

	public static final Item INFINITY_SIDED_DIE = new InfinitySidedDie("infinitysideddie");

	public static final Item GRAPPLING_HOOK = new GrapplingHook("grapplinghook");

	public static final Item MEMORY_GUN = new MemoryGun("memorygun");

	public static final Item SMILE_DIP = new SmileDip("smiledip", 2, 4.0F, false, new PotionEffect(Potion.getPotionById(1), 3000, 0), new PotionEffect(Potion.getPotionById(3), 3000, 0), new PotionEffect(Potion.getPotionById(9), 3000, 0));

	public static final Item INFINITY_PIZZA = new InfinityPizza("infinitypizza", 4, 4.0F, false);

	public static final Item TIME_WISH = new TimeWish("timewish", 4, 4.0F, false, new PotionEffect(MobEffects.ABSORPTION, 60000, 100), new PotionEffect(MobEffects.REGENERATION, 60000, 100), new PotionEffect(MobEffects.FIRE_RESISTANCE, 60000, 100), new PotionEffect(MobEffects.HASTE, 60000, 100), new PotionEffect(MobEffects.RESISTANCE, 60000, 100), new PotionEffect(MobEffects.SPEED, 60000, 3));

	public static final Item TIME_TAPE = new TimeTape("timetape");

	public static final Item URANIUM_BUCKET = new UraniumBucket("uraniumbucket");

	public static final Item LATEX = new Latex("latex");

	public static final Item RUBBER_ITEM = new Rubber("rubber");

	public static final Item MAGNET_GUN = new MagnetGun("magnetgun"); 

	public static final Item LIGHT_BULB = new LightBulb("lightbulb");

	public static final Item QUANTTUM_DESTABILIZER = new QuantumDestabilizer("quantum_destabilizer");

	public static final Item LASER_ARM_CANNON = new LaserArmCannon("laser_arm_cannon", ToolMaterial.DIAMOND);

	public static final Item COPPER_INGOT = new CopperIngot("copper_ingot");

	public static final Item BATTERY = new Battery("battery");
	
	public static final Item LEAFBLOWER = new LeafBlower("leafblower", ToolMaterial.IRON);
	
	public static final Item RIFT = new Rift("rift");

	public static final Item GOLF_CART = new GolfCart("golfcart");
	
	public static final Item BLACK_LIGHT = new BlackLight("blacklight");
	
	public static final Item PRESIDENT_KEY	= new PresidentKey("presidentkey", ToolMaterial.IRON);
	
	public static final Item GIDEONBOT	= new GideonBot("gideonbot");
	public static final Item GIDEONBOT_HEAD	= new GideonBotPart("gideonbot_head");
	public static final Item GIDEONBOT_BODY	= new GideonBotPart("gideonbot_body");
	public static final Item GIDEONBOT_LEG	= new GideonBotPart("gideonbot_leg");
	public static final Item GIDEONBOT_ARM	= new GideonBotPart("gideonbot_arm");
	
	public static final Item INTERDIMENSIONAL_RIFT = new IterdimensionalRift("interdimensional_rift");
	
	public static final Item RETURN_DEVICE = new ReturnDevice("return_device");
	
	public static final Item EYEBAT = new EyeBat("eyebat");

	public static final Item NET = new Net("net", ToolMaterial.STONE);


	//Discs
	public static final Item DISC_THEMESONG = new CustomRecords("themesong", SoundsHandler.RECORD_THEMESONG);
	public static final Item DISC_THEMESONG2 = new CustomRecords("themesong2", SoundsHandler.RECORD_THEMESONG2);
	public static final Item DISC_WEIRDMAGEDDON  = new CustomRecords("weirdmageddon", SoundsHandler.RECORD_WEIRDMAGEDDON);
	public static final Item DISC_STRAIGHTBLANCHIN = new CustomRecords("straightblanchin", SoundsHandler.RECORD_STRAIGHTBLANCHIN);
	public static final Item DISC_DISCOGIRL = new CustomRecords("discogirl", SoundsHandler.RECORD_DISCOGIRL);

	
}