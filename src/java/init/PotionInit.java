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
import items.PresidentKey;
import items.QuantumDestabilizer;
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

public class PotionInit 
{

	public static final List<Potion> POTIONS = new ArrayList<Potion>();

	public static final Potion POTION_FREEZE = new PotionFreeze("freeze", true, 25);
	
}