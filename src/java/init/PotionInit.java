package init;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import potions.Freeze;
import potions.Size;
import potions.SizeNormal;
import potions.Radiation;

public class PotionInit 
{	
	public static final Potion FREEZE_EFFECT = new Freeze("freeze_potion", true, 16529206, 0, 0).registerPotionAttributeModifier(SharedMonsterAttributes.MOVEMENT_SPEED, MathHelper.getRandomUUID().toString(), -1.0D, 2).registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_SPEED, MathHelper.getRandomUUID().toString(), -1.0D, 2);
	public static final PotionType FREEZE_POTION = new PotionType("freeze_potion", new PotionEffect[] {new PotionEffect(FREEZE_EFFECT, 400)}).setRegistryName("freeze_potion");
	public static final PotionType FREEZE_POTION_LONG = new PotionType("freeze_potion", new PotionEffect[] {new PotionEffect(FREEZE_EFFECT, 800)}).setRegistryName("freeze_potion_long");

	public static final Potion RADIATION_EFFECT = new Radiation("radiation_potion", true, 6145843, 0, 1).registerPotionAttributeModifier(SharedMonsterAttributes.ARMOR_TOUGHNESS, MathHelper.getRandomUUID().toString(), -0.05D, 2).registerPotionAttributeModifier(SharedMonsterAttributes.ATTACK_SPEED, MathHelper.getRandomUUID().toString(), -0.5D, 2).registerPotionAttributeModifier(SharedMonsterAttributes.KNOCKBACK_RESISTANCE, MathHelper.getRandomUUID().toString(), -0.1D, 2);
	
	public static final Potion GROWTH_EFFECT = new Size("size_potion", false, 13893628, 0, 2);
	public static final Potion NORMAL_EFFECT = new SizeNormal("normal_size_potion", false, 16574717, 0, 2);
	
	
	public static void registerPotions()
	{		
		registerPotion(FREEZE_POTION, FREEZE_POTION_LONG, FREEZE_EFFECT);

		registerEffects();
		registerPotionMixes();
	}
	
	public static void registerEffects()
	{
		ForgeRegistries.POTIONS.register(RADIATION_EFFECT);
		ForgeRegistries.POTIONS.register(NORMAL_EFFECT);
		ForgeRegistries.POTIONS.register(GROWTH_EFFECT);

	}
	
	public static void registerPotion(PotionType defaultPotion, PotionType longPotion, Potion  effect)
	{
		ForgeRegistries.POTIONS.register(effect);
		ForgeRegistries.POTION_TYPES.register(defaultPotion);
		ForgeRegistries.POTION_TYPES.register(longPotion);	
	}
	
	public static void registerPotionMixes() {
		//PotionHelper.addMix(PotionTypes.AWKWARD, ItemInit.CRYSTALFLAKE, GROWTH_BLAND_POTION);
		//PotionHelper.addMix(GROWTH_BLAND_POTION, Items.REDSTONE, GROWTH_DOUBLE_POTION);
		//PotionHelper.addMix(GROWTH_BLAND_POTION, Items.GLOWSTONE_DUST, GROWTH_HALF_POTION);
	}
}